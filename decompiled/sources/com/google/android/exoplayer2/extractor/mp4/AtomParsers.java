package com.google.android.exoplayer2.extractor.mp4;

import android.util.Pair;
import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.C1022C;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.ParserException;
import com.google.android.exoplayer2.audio.AacUtil;
import com.google.android.exoplayer2.audio.Ac3Util;
import com.google.android.exoplayer2.audio.Ac4Util;
import com.google.android.exoplayer2.audio.OpusUtil;
import com.google.android.exoplayer2.drm.DrmInitData;
import com.google.android.exoplayer2.extractor.ExtractorUtil;
import com.google.android.exoplayer2.extractor.GaplessInfoHolder;
import com.google.android.exoplayer2.extractor.p014ts.PsExtractor;
import com.google.android.exoplayer2.metadata.Metadata;
import com.google.android.exoplayer2.metadata.mp4.MdtaMetadataEntry;
import com.google.android.exoplayer2.metadata.mp4.SmtaMetadataEntry;
import com.google.android.exoplayer2.util.Assertions;
import com.google.android.exoplayer2.util.CodecSpecificDataUtil;
import com.google.android.exoplayer2.util.Log;
import com.google.android.exoplayer2.util.MimeTypes;
import com.google.android.exoplayer2.util.ParsableByteArray;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.AvcConfig;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.android.exoplayer2.video.DolbyVisionConfig;
import com.google.android.exoplayer2.video.HevcConfig;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
final class AtomParsers {
    private static final int MAX_GAPLESS_TRIM_SIZE_SAMPLES = 4;
    private static final String TAG = "AtomParsers";
    private static final int TYPE_clcp = 1668047728;
    private static final int TYPE_mdta = 1835299937;
    private static final int TYPE_meta = 1835365473;
    private static final int TYPE_nclc = 1852009571;
    private static final int TYPE_nclx = 1852009592;
    private static final int TYPE_sbtl = 1935832172;
    private static final int TYPE_soun = 1936684398;
    private static final int TYPE_subt = 1937072756;
    private static final int TYPE_text = 1952807028;
    private static final int TYPE_vide = 1986618469;
    private static final byte[] opusMagic = Util.getUtf8Bytes("OpusHead");

    public static final class ChunkIterator {
        private final ParsableByteArray chunkOffsets;
        private final boolean chunkOffsetsAreLongs;
        public int index;
        public final int length;
        private int nextSamplesPerChunkChangeIndex;
        public int numSamples;
        public long offset;
        private int remainingSamplesPerChunkChanges;
        private final ParsableByteArray stsc;

        public ChunkIterator(ParsableByteArray parsableByteArray, ParsableByteArray parsableByteArray2, boolean z) throws ParserException {
            this.stsc = parsableByteArray;
            this.chunkOffsets = parsableByteArray2;
            this.chunkOffsetsAreLongs = z;
            parsableByteArray2.setPosition(12);
            this.length = parsableByteArray2.readUnsignedIntToInt();
            parsableByteArray.setPosition(12);
            this.remainingSamplesPerChunkChanges = parsableByteArray.readUnsignedIntToInt();
            ExtractorUtil.checkContainerInput(parsableByteArray.readInt() == 1, "first_chunk must be 1");
            this.index = -1;
        }

        public boolean moveNext() {
            int i = this.index + 1;
            this.index = i;
            if (i == this.length) {
                return false;
            }
            this.offset = this.chunkOffsetsAreLongs ? this.chunkOffsets.readUnsignedLongToLong() : this.chunkOffsets.readUnsignedInt();
            if (this.index == this.nextSamplesPerChunkChangeIndex) {
                this.numSamples = this.stsc.readUnsignedIntToInt();
                this.stsc.skipBytes(4);
                int i2 = this.remainingSamplesPerChunkChanges - 1;
                this.remainingSamplesPerChunkChanges = i2;
                this.nextSamplesPerChunkChangeIndex = i2 > 0 ? this.stsc.readUnsignedIntToInt() - 1 : -1;
            }
            return true;
        }
    }

    /* JADX INFO: loaded from: classes.dex */
    public static final class EsdsData {
        private final long bitrate;
        private final byte[] initializationData;
        private final String mimeType;
        private final long peakBitrate;

        public EsdsData(String str, byte[] bArr, long j, long j2) {
            this.mimeType = str;
            this.initializationData = bArr;
            this.bitrate = j;
            this.peakBitrate = j2;
        }
    }

    public interface SampleSizeBox {
        int getFixedSampleSize();

        int getSampleCount();

        int readNextSampleSize();
    }

    public static final class StsdData {
        public static final int STSD_HEADER_SIZE = 8;

        @Nullable
        public Format format;
        public int nalUnitLengthFieldLength;
        public int requiredSampleTransformation = 0;
        public final TrackEncryptionBox[] trackEncryptionBoxes;

        public StsdData(int i) {
            this.trackEncryptionBoxes = new TrackEncryptionBox[i];
        }
    }

    /* JADX INFO: loaded from: classes.dex */
    public static final class StszSampleSizeBox implements SampleSizeBox {
        private final ParsableByteArray data;
        private final int fixedSampleSize;
        private final int sampleCount;

        public StszSampleSizeBox(Atom.LeafAtom leafAtom, Format format) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (MimeTypes.AUDIO_RAW.equals(format.sampleMimeType)) {
                int pcmFrameSize = Util.getPcmFrameSize(format.pcmEncoding, format.channelCount);
                if (unsignedIntToInt == 0 || unsignedIntToInt % pcmFrameSize != 0) {
                    Log.m72w(AtomParsers.TAG, "Audio sample size mismatch. stsd sample size: " + pcmFrameSize + ", stsz sample size: " + unsignedIntToInt);
                    unsignedIntToInt = pcmFrameSize;
                }
            }
            this.fixedSampleSize = unsignedIntToInt == 0 ? -1 : unsignedIntToInt;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return this.fixedSampleSize;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fixedSampleSize;
            return i == -1 ? this.data.readUnsignedIntToInt() : i;
        }
    }

    /* JADX INFO: loaded from: classes.dex */
    public static final class Stz2SampleSizeBox implements SampleSizeBox {
        private int currentByte;
        private final ParsableByteArray data;
        private final int fieldSize;
        private final int sampleCount;
        private int sampleIndex;

        public Stz2SampleSizeBox(Atom.LeafAtom leafAtom) {
            ParsableByteArray parsableByteArray = leafAtom.data;
            this.data = parsableByteArray;
            parsableByteArray.setPosition(12);
            this.fieldSize = parsableByteArray.readUnsignedIntToInt() & 255;
            this.sampleCount = parsableByteArray.readUnsignedIntToInt();
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getFixedSampleSize() {
            return -1;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int getSampleCount() {
            return this.sampleCount;
        }

        @Override // com.google.android.exoplayer2.extractor.mp4.AtomParsers.SampleSizeBox
        public int readNextSampleSize() {
            int i = this.fieldSize;
            if (i == 8) {
                return this.data.readUnsignedByte();
            }
            if (i == 16) {
                return this.data.readUnsignedShort();
            }
            int i2 = this.sampleIndex;
            this.sampleIndex = i2 + 1;
            if (i2 % 2 != 0) {
                return this.currentByte & 15;
            }
            int unsignedByte = this.data.readUnsignedByte();
            this.currentByte = unsignedByte;
            return (unsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
        }
    }

    /* JADX INFO: loaded from: classes.dex */
    public static final class TkhdData {
        private final long duration;

        /* JADX INFO: renamed from: id */
        private final int f137id;
        private final int rotationDegrees;

        public TkhdData(int i, long j, int i2) {
            this.f137id = i;
            this.duration = j;
            this.rotationDegrees = i2;
        }
    }

    private AtomParsers() {
    }

    private static ByteBuffer allocateHdrStaticInfo() {
        return ByteBuffer.allocate(25).order(ByteOrder.LITTLE_ENDIAN);
    }

    private static boolean canApplyEditWithGaplessInfo(long[] jArr, long j, long j2, long j3) {
        int length = jArr.length - 1;
        return jArr[0] <= j2 && j2 < jArr[Util.constrainValue(4, 0, length)] && jArr[Util.constrainValue(jArr.length - 4, 0, length)] < j3 && j3 <= j;
    }

    private static int findBoxPosition(ParsableByteArray parsableByteArray, int i, int i2, int i3) throws ParserException {
        int position = parsableByteArray.getPosition();
        ExtractorUtil.checkContainerInput(position >= i2, null);
        while (position - i2 < i3) {
            parsableByteArray.setPosition(position);
            int i4 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i4 > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == i) {
                return position;
            }
            position += i4;
        }
        return -1;
    }

    private static int getTrackTypeForHdlr(int i) {
        if (i == TYPE_soun) {
            return 1;
        }
        if (i == TYPE_vide) {
            return 2;
        }
        if (i == TYPE_text || i == TYPE_sbtl || i == TYPE_subt || i == TYPE_clcp) {
            return 3;
        }
        return i == 1835365473 ? 5 : -1;
    }

    public static void maybeSkipRemainingMetaAtomHeaderBytes(ParsableByteArray parsableByteArray) {
        int position = parsableByteArray.getPosition();
        parsableByteArray.skipBytes(4);
        if (parsableByteArray.readInt() != 1751411826) {
            position += 4;
        }
        parsableByteArray.setPosition(position);
    }

    /* JADX WARN: Code duplicated, block: B:100:0x0166  */
    /* JADX WARN: Code duplicated, block: B:102:0x016f  */
    /* JADX WARN: Code duplicated, block: B:103:0x0171  */
    /* JADX WARN: Code duplicated, block: B:106:0x0180  */
    /* JADX WARN: Code duplicated, block: B:109:0x019b  */
    /* JADX WARN: Code duplicated, block: B:147:0x02ce  */
    /* JADX WARN: Code duplicated, block: B:148:0x02d0  */
    /* JADX WARN: Code duplicated, block: B:151:0x02d7  */
    /* JADX WARN: Code duplicated, block: B:153:0x02e5  */
    /* JADX WARN: Code duplicated, block: B:155:0x02ed  */
    /* JADX WARN: Code duplicated, block: B:170:0x02fd A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:171:0x02fd A[SYNTHETIC] */
    private static void parseAudioSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, boolean z, @Nullable DrmInitData drmInitData, StsdData stsdData, int i5) throws ParserException {
        int unsignedShort;
        int i6;
        int iIntValue;
        int unsignedIntToInt;
        int i7;
        String str2;
        EsdsData esdsFromParent;
        String str3;
        List<byte[]> listM136of;
        int i8;
        boolean z2;
        int i9;
        int iFindBoxPosition;
        byte[] bArr;
        int i10 = i2;
        int i11 = i3;
        DrmInitData drmInitDataCopyWithSchemeType = drmInitData;
        parsableByteArray.setPosition(i10 + 8 + 8);
        if (z) {
            unsignedShort = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
        } else {
            parsableByteArray.skipBytes(8);
            unsignedShort = 0;
        }
        if (unsignedShort == 0 || unsignedShort == 1) {
            int unsignedShort2 = parsableByteArray.readUnsignedShort();
            parsableByteArray.skipBytes(6);
            int unsignedFixedPoint1616 = parsableByteArray.readUnsignedFixedPoint1616();
            parsableByteArray.setPosition(parsableByteArray.getPosition() - 4);
            i6 = parsableByteArray.readInt();
            if (unsignedShort == 1) {
                parsableByteArray.skipBytes(16);
            }
            iIntValue = unsignedFixedPoint1616;
            unsignedIntToInt = unsignedShort2;
        } else {
            if (unsignedShort != 2) {
                return;
            }
            parsableByteArray.skipBytes(16);
            iIntValue = (int) Math.round(parsableByteArray.readDouble());
            unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            parsableByteArray.skipBytes(20);
            i6 = 0;
        }
        int position = parsableByteArray.getPosition();
        int iIntValue2 = i;
        if (iIntValue2 == 1701733217) {
            Pair<Integer, TrackEncryptionBox> sampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i10, i11);
            if (sampleEntryEncryptionData != null) {
                iIntValue2 = ((Integer) sampleEntryEncryptionData.first).intValue();
                drmInitDataCopyWithSchemeType = drmInitDataCopyWithSchemeType == null ? null : drmInitDataCopyWithSchemeType.copyWithSchemeType(((TrackEncryptionBox) sampleEntryEncryptionData.second).schemeType);
                stsdData.trackEncryptionBoxes[i5] = (TrackEncryptionBox) sampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        String str4 = MimeTypes.AUDIO_RAW;
        if (iIntValue2 == 1633889587) {
            str4 = MimeTypes.AUDIO_AC3;
        } else if (iIntValue2 == 1700998451) {
            str4 = MimeTypes.AUDIO_E_AC3;
        } else if (iIntValue2 == 1633889588) {
            str4 = MimeTypes.AUDIO_AC4;
        } else if (iIntValue2 == 1685353315) {
            str4 = MimeTypes.AUDIO_DTS;
        } else if (iIntValue2 == 1685353320 || iIntValue2 == 1685353324) {
            str4 = MimeTypes.AUDIO_DTS_HD;
        } else if (iIntValue2 == 1685353317) {
            str4 = MimeTypes.AUDIO_DTS_EXPRESS;
        } else if (iIntValue2 == 1685353336) {
            str4 = MimeTypes.AUDIO_DTS_X;
        } else if (iIntValue2 == 1935764850) {
            str4 = MimeTypes.AUDIO_AMR_NB;
        } else {
            if (iIntValue2 != 1935767394) {
                if (iIntValue2 == 1819304813 || iIntValue2 == 1936684916) {
                    i7 = 2;
                } else if (iIntValue2 == 1953984371) {
                    i7 = 268435456;
                } else if (iIntValue2 == 778924082 || iIntValue2 == 778924083) {
                    str4 = MimeTypes.AUDIO_MPEG;
                } else if (iIntValue2 == 1835557169) {
                    str4 = MimeTypes.AUDIO_MPEGH_MHA1;
                } else if (iIntValue2 == 1835560241) {
                    str4 = MimeTypes.AUDIO_MPEGH_MHM1;
                } else if (iIntValue2 == 1634492771) {
                    str4 = MimeTypes.AUDIO_ALAC;
                } else if (iIntValue2 == 1634492791) {
                    str4 = MimeTypes.AUDIO_ALAW;
                } else if (iIntValue2 == 1970037111) {
                    str4 = MimeTypes.AUDIO_MLAW;
                } else if (iIntValue2 == 1332770163) {
                    str4 = MimeTypes.AUDIO_OPUS;
                } else if (iIntValue2 == 1716281667) {
                    str4 = MimeTypes.AUDIO_FLAC;
                } else if (iIntValue2 == 1835823201) {
                    str4 = MimeTypes.AUDIO_TRUEHD;
                } else {
                    i7 = -1;
                    str4 = null;
                }
                str2 = str4;
                esdsFromParent = null;
                str3 = null;
                listM136of = null;
                while (position - i10 < i11) {
                    parsableByteArray.setPosition(position);
                    i8 = parsableByteArray.readInt();
                    if (i8 > 0) {
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    ExtractorUtil.checkContainerInput(z2, "childAtomSize must be positive");
                    i9 = parsableByteArray.readInt();
                    if (i9 == 1835557187) {
                        int i12 = i8 - 13;
                        byte[] bArr2 = new byte[i12];
                        parsableByteArray.setPosition(position + 13);
                        parsableByteArray.readBytes(bArr2, 0, i12);
                        listM136of = ImmutableList.m136of(bArr2);
                    } else {
                        if (i9 != 1702061171 || (z && i9 == 2002876005)) {
                            if (i9 == 1702061171) {
                                iFindBoxPosition = position;
                            } else {
                                iFindBoxPosition = findBoxPosition(parsableByteArray, Atom.TYPE_esds, position, i8);
                            }
                            if (iFindBoxPosition != -1) {
                                esdsFromParent = parseEsdsFromParent(parsableByteArray, iFindBoxPosition);
                                str2 = esdsFromParent.mimeType;
                                bArr = esdsFromParent.initializationData;
                                if (bArr == null) {
                                    if (MimeTypes.AUDIO_AAC.equals(str2)) {
                                        AacUtil.Config audioSpecificConfig = AacUtil.parseAudioSpecificConfig(bArr);
                                        iIntValue = audioSpecificConfig.sampleRateHz;
                                        unsignedIntToInt = audioSpecificConfig.channelCount;
                                        str3 = audioSpecificConfig.codecs;
                                    }
                                    listM136of = ImmutableList.m136of(bArr);
                                }
                            }
                        } else if (i9 == 1684103987) {
                            parsableByteArray.setPosition(position + 8);
                            stsdData.format = Ac3Util.parseAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitDataCopyWithSchemeType);
                        } else if (i9 == 1684366131) {
                            parsableByteArray.setPosition(position + 8);
                            stsdData.format = Ac3Util.parseEAc3AnnexFFormat(parsableByteArray, Integer.toString(i4), str, drmInitDataCopyWithSchemeType);
                        } else if (i9 == 1684103988) {
                            parsableByteArray.setPosition(position + 8);
                            stsdData.format = Ac4Util.parseAc4AnnexEFormat(parsableByteArray, Integer.toString(i4), str, drmInitDataCopyWithSchemeType);
                        } else if (i9 == 1684892784) {
                            if (i6 <= 0) {
                                throw ParserException.createForMalformedContainer("Invalid sample rate for Dolby TrueHD MLP stream: " + i6, null);
                            }
                            iIntValue = i6;
                            unsignedIntToInt = 2;
                        } else if (i9 == 1684305011) {
                            stsdData.format = new Format.Builder().setId(i4).setSampleMimeType(str2).setChannelCount(unsignedIntToInt).setSampleRate(iIntValue).setDrmInitData(drmInitDataCopyWithSchemeType).setLanguage(str).build();
                        } else if (i9 == 1682927731) {
                            int i13 = i8 - 8;
                            byte[] bArr3 = opusMagic;
                            byte[] bArrCopyOf = Arrays.copyOf(bArr3, bArr3.length + i13);
                            parsableByteArray.setPosition(position + 8);
                            parsableByteArray.readBytes(bArrCopyOf, bArr3.length, i13);
                            listM136of = OpusUtil.buildInitializationData(bArrCopyOf);
                        } else if (i9 == 1684425825) {
                            int i14 = i8 - 12;
                            byte[] bArr4 = new byte[i14 + 4];
                            bArr4[0] = 102;
                            bArr4[1] = 76;
                            bArr4[2] = 97;
                            bArr4[3] = 67;
                            parsableByteArray.setPosition(position + 12);
                            parsableByteArray.readBytes(bArr4, 4, i14);
                            listM136of = ImmutableList.m136of(bArr4);
                        } else if (i9 == 1634492771) {
                            int i15 = i8 - 12;
                            byte[] bArr5 = new byte[i15];
                            parsableByteArray.setPosition(position + 12);
                            parsableByteArray.readBytes(bArr5, 0, i15);
                            Pair<Integer, Integer> alacAudioSpecificConfig = CodecSpecificDataUtil.parseAlacAudioSpecificConfig(bArr5);
                            iIntValue = ((Integer) alacAudioSpecificConfig.first).intValue();
                            int iIntValue3 = ((Integer) alacAudioSpecificConfig.second).intValue();
                            listM136of = ImmutableList.m136of(bArr5);
                            unsignedIntToInt = iIntValue3;
                        }
                        position += i8;
                        i10 = i2;
                        i11 = i3;
                    }
                    position += i8;
                    i10 = i2;
                    i11 = i3;
                }
                if (stsdData.format == null || str2 == null) {
                }
                Format.Builder language = new Format.Builder().setId(i4).setSampleMimeType(str2).setCodecs(str3).setChannelCount(unsignedIntToInt).setSampleRate(iIntValue).setPcmEncoding(i7).setInitializationData(listM136of).setDrmInitData(drmInitDataCopyWithSchemeType).setLanguage(str);
                if (esdsFromParent != null) {
                    language.setAverageBitrate(Ints.saturatedCast(esdsFromParent.bitrate)).setPeakBitrate(Ints.saturatedCast(esdsFromParent.peakBitrate));
                }
                stsdData.format = language.build();
                return;
            }
            str4 = MimeTypes.AUDIO_AMR_WB;
        }
        i7 = -1;
        str2 = str4;
        esdsFromParent = null;
        str3 = null;
        listM136of = null;
        while (position - i10 < i11) {
            parsableByteArray.setPosition(position);
            i8 = parsableByteArray.readInt();
            if (i8 > 0) {
                z2 = true;
            } else {
                z2 = false;
            }
            ExtractorUtil.checkContainerInput(z2, "childAtomSize must be positive");
            i9 = parsableByteArray.readInt();
            if (i9 == 1835557187) {
                int i16 = i8 - 13;
                byte[] bArr6 = new byte[i16];
                parsableByteArray.setPosition(position + 13);
                parsableByteArray.readBytes(bArr6, 0, i16);
                listM136of = ImmutableList.m136of(bArr6);
            } else {
                if (i9 != 1702061171) {
                }
                if (i9 == 1702061171) {
                    iFindBoxPosition = position;
                } else {
                    iFindBoxPosition = findBoxPosition(parsableByteArray, Atom.TYPE_esds, position, i8);
                }
                if (iFindBoxPosition != -1) {
                    esdsFromParent = parseEsdsFromParent(parsableByteArray, iFindBoxPosition);
                    str2 = esdsFromParent.mimeType;
                    bArr = esdsFromParent.initializationData;
                    if (bArr == null) {
                        if (MimeTypes.AUDIO_AAC.equals(str2)) {
                            AacUtil.Config audioSpecificConfig2 = AacUtil.parseAudioSpecificConfig(bArr);
                            iIntValue = audioSpecificConfig2.sampleRateHz;
                            unsignedIntToInt = audioSpecificConfig2.channelCount;
                            str3 = audioSpecificConfig2.codecs;
                        }
                        listM136of = ImmutableList.m136of(bArr);
                    }
                }
                position += i8;
                i10 = i2;
                i11 = i3;
            }
            position += i8;
            i10 = i2;
            i11 = i3;
        }
        if (stsdData.format == null) {
        }
    }

    @Nullable
    private static Pair<long[], long[]> parseEdts(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_elst);
        if (leafAtomOfType == null) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType.data;
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        int unsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
        long[] jArr = new long[unsignedIntToInt];
        long[] jArr2 = new long[unsignedIntToInt];
        for (int i = 0; i < unsignedIntToInt; i++) {
            jArr[i] = fullAtomVersion == 1 ? parsableByteArray.readUnsignedLongToLong() : parsableByteArray.readUnsignedInt();
            jArr2[i] = fullAtomVersion == 1 ? parsableByteArray.readLong() : parsableByteArray.readInt();
            if (parsableByteArray.readShort() != 1) {
                throw new IllegalArgumentException("Unsupported media rate.");
            }
            parsableByteArray.skipBytes(2);
        }
        return Pair.create(jArr, jArr2);
    }

    private static EsdsData parseEsdsFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8 + 4);
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        parsableByteArray.skipBytes(2);
        int unsignedByte = parsableByteArray.readUnsignedByte();
        if ((unsignedByte & 128) != 0) {
            parsableByteArray.skipBytes(2);
        }
        if ((unsignedByte & 64) != 0) {
            parsableByteArray.skipBytes(parsableByteArray.readUnsignedByte());
        }
        if ((unsignedByte & 32) != 0) {
            parsableByteArray.skipBytes(2);
        }
        parsableByteArray.skipBytes(1);
        parseExpandableClassSize(parsableByteArray);
        String mimeTypeFromMp4ObjectType = MimeTypes.getMimeTypeFromMp4ObjectType(parsableByteArray.readUnsignedByte());
        if (MimeTypes.AUDIO_MPEG.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS.equals(mimeTypeFromMp4ObjectType) || MimeTypes.AUDIO_DTS_HD.equals(mimeTypeFromMp4ObjectType)) {
            return new EsdsData(mimeTypeFromMp4ObjectType, null, -1L, -1L);
        }
        parsableByteArray.skipBytes(4);
        long unsignedInt = parsableByteArray.readUnsignedInt();
        long unsignedInt2 = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(1);
        int expandableClassSize = parseExpandableClassSize(parsableByteArray);
        byte[] bArr = new byte[expandableClassSize];
        parsableByteArray.readBytes(bArr, 0, expandableClassSize);
        return new EsdsData(mimeTypeFromMp4ObjectType, bArr, unsignedInt2 > 0 ? unsignedInt2 : -1L, unsignedInt > 0 ? unsignedInt : -1L);
    }

    private static int parseExpandableClassSize(ParsableByteArray parsableByteArray) {
        int unsignedByte = parsableByteArray.readUnsignedByte();
        int i = unsignedByte & 127;
        while ((unsignedByte & 128) == 128) {
            unsignedByte = parsableByteArray.readUnsignedByte();
            i = (i << 7) | (unsignedByte & 127);
        }
        return i;
    }

    private static int parseHdlr(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(16);
        return parsableByteArray.readInt();
    }

    @Nullable
    private static Metadata parseIlst(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray.getPosition() < i) {
            Metadata.Entry ilstElement = MetadataUtil.parseIlstElement(parsableByteArray);
            if (ilstElement != null) {
                arrayList.add(ilstElement);
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static Pair<Long, String> parseMdhd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        long unsignedInt = parsableByteArray.readUnsignedInt();
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 4 : 8);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("");
        sbM26m.append((char) (((unsignedShort >> 10) & 31) + 96));
        sbM26m.append((char) (((unsignedShort >> 5) & 31) + 96));
        sbM26m.append((char) ((unsignedShort & 31) + 96));
        return Pair.create(Long.valueOf(unsignedInt), sbM26m.toString());
    }

    @Nullable
    public static Metadata parseMdtaFromMeta(Atom.ContainerAtom containerAtom) {
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_hdlr);
        Atom.LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_keys);
        Atom.LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_ilst);
        if (leafAtomOfType == null || leafAtomOfType2 == null || leafAtomOfType3 == null || parseHdlr(leafAtomOfType.data) != TYPE_mdta) {
            return null;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType2.data;
        parsableByteArray.setPosition(12);
        int i = parsableByteArray.readInt();
        String[] strArr = new String[i];
        for (int i2 = 0; i2 < i; i2++) {
            int i3 = parsableByteArray.readInt();
            parsableByteArray.skipBytes(4);
            strArr[i2] = parsableByteArray.readString(i3 - 8);
        }
        ParsableByteArray parsableByteArray2 = leafAtomOfType3.data;
        parsableByteArray2.setPosition(8);
        ArrayList arrayList = new ArrayList();
        while (parsableByteArray2.bytesLeft() > 8) {
            int position = parsableByteArray2.getPosition();
            int i4 = parsableByteArray2.readInt();
            int i5 = parsableByteArray2.readInt() - 1;
            if (i5 < 0 || i5 >= i) {
                Insets$$ExternalSyntheticOutline0.m34m("Skipped metadata with unknown key index: ", i5, TAG);
            } else {
                MdtaMetadataEntry mdtaMetadataEntryFromIlst = MetadataUtil.parseMdtaMetadataEntryFromIlst(parsableByteArray2, position + i4, strArr[i5]);
                if (mdtaMetadataEntryFromIlst != null) {
                    arrayList.add(mdtaMetadataEntryFromIlst);
                }
            }
            parsableByteArray2.setPosition(position + i4);
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        return new Metadata(arrayList);
    }

    private static void parseMetaDataSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, StsdData stsdData) {
        parsableByteArray.setPosition(i2 + 8 + 8);
        if (i == 1835365492) {
            parsableByteArray.readNullTerminatedString();
            String nullTerminatedString = parsableByteArray.readNullTerminatedString();
            if (nullTerminatedString != null) {
                stsdData.format = new Format.Builder().setId(i3).setSampleMimeType(nullTerminatedString).build();
            }
        }
    }

    private static long parseMvhd(ParsableByteArray parsableByteArray) {
        parsableByteArray.setPosition(8);
        parsableByteArray.skipBytes(Atom.parseFullAtomVersion(parsableByteArray.readInt()) != 0 ? 16 : 8);
        return parsableByteArray.readUnsignedInt();
    }

    private static float parsePaspFromParent(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.setPosition(i + 8);
        return parsableByteArray.readUnsignedIntToInt() / parsableByteArray.readUnsignedIntToInt();
    }

    @Nullable
    private static byte[] parseProjFromParent(ParsableByteArray parsableByteArray, int i, int i2) {
        int i3 = i + 8;
        while (i3 - i < i2) {
            parsableByteArray.setPosition(i3);
            int i4 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1886547818) {
                return Arrays.copyOfRange(parsableByteArray.getData(), i3, i4 + i3);
            }
            i3 += i4;
        }
        return null;
    }

    @Nullable
    private static Pair<Integer, TrackEncryptionBox> parseSampleEntryEncryptionData(ParsableByteArray parsableByteArray, int i, int i2) throws ParserException {
        int position = parsableByteArray.getPosition();
        while (true) {
            Pair<Integer, TrackEncryptionBox> pairCreate = null;
            if (position - i >= i2) {
                return null;
            }
            parsableByteArray.setPosition(position);
            int i3 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i3 > 0, "childAtomSize must be positive");
            if (parsableByteArray.readInt() == 1936289382) {
                int i4 = position + 8;
                String string = null;
                Integer numValueOf = null;
                int i5 = -1;
                int i6 = 0;
                while (i4 - position < i3) {
                    parsableByteArray.setPosition(i4);
                    int i7 = parsableByteArray.readInt();
                    int i8 = parsableByteArray.readInt();
                    if (i8 == 1718775137) {
                        numValueOf = Integer.valueOf(parsableByteArray.readInt());
                    } else if (i8 == 1935894637) {
                        parsableByteArray.skipBytes(4);
                        string = parsableByteArray.readString(4);
                    } else if (i8 == 1935894633) {
                        i5 = i4;
                        i6 = i7;
                    }
                    i4 += i7;
                }
                if (C1022C.CENC_TYPE_cenc.equals(string) || C1022C.CENC_TYPE_cbc1.equals(string) || C1022C.CENC_TYPE_cens.equals(string) || C1022C.CENC_TYPE_cbcs.equals(string)) {
                    ExtractorUtil.checkContainerInput(numValueOf != null, "frma atom is mandatory");
                    ExtractorUtil.checkContainerInput(i5 != -1, "schi atom is mandatory");
                    TrackEncryptionBox schiFromParent = parseSchiFromParent(parsableByteArray, i5, i6, string);
                    ExtractorUtil.checkContainerInput(schiFromParent != null, "tenc atom is mandatory");
                    pairCreate = Pair.create(numValueOf, (TrackEncryptionBox) Util.castNonNull(schiFromParent));
                }
                if (pairCreate != null) {
                    return pairCreate;
                }
            }
            position += i3;
        }
    }

    @Nullable
    private static TrackEncryptionBox parseSchiFromParent(ParsableByteArray parsableByteArray, int i, int i2, String str) {
        int i3;
        int i4;
        int i5 = i + 8;
        while (true) {
            byte[] bArr = null;
            if (i5 - i >= i2) {
                return null;
            }
            parsableByteArray.setPosition(i5);
            int i6 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1952804451) {
                int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
                parsableByteArray.skipBytes(1);
                if (fullAtomVersion == 0) {
                    parsableByteArray.skipBytes(1);
                    i4 = 0;
                    i3 = 0;
                } else {
                    int unsignedByte = parsableByteArray.readUnsignedByte();
                    i3 = unsignedByte & 15;
                    i4 = (unsignedByte & PsExtractor.VIDEO_STREAM_MASK) >> 4;
                }
                boolean z = parsableByteArray.readUnsignedByte() == 1;
                int unsignedByte2 = parsableByteArray.readUnsignedByte();
                byte[] bArr2 = new byte[16];
                parsableByteArray.readBytes(bArr2, 0, 16);
                if (z && unsignedByte2 == 0) {
                    int unsignedByte3 = parsableByteArray.readUnsignedByte();
                    bArr = new byte[unsignedByte3];
                    parsableByteArray.readBytes(bArr, 0, unsignedByte3);
                }
                return new TrackEncryptionBox(z, str, unsignedByte2, bArr2, i4, i3, bArr);
            }
            i5 += i6;
        }
    }

    @Nullable
    private static Metadata parseSmta(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(12);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int i2 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1935766900) {
                if (i2 < 14) {
                    return null;
                }
                parsableByteArray.skipBytes(5);
                int unsignedByte = parsableByteArray.readUnsignedByte();
                if (unsignedByte != 12 && unsignedByte != 13) {
                    return null;
                }
                float f = unsignedByte == 12 ? 240.0f : 120.0f;
                parsableByteArray.skipBytes(1);
                return new Metadata(new SmtaMetadataEntry(f, parsableByteArray.readUnsignedByte()));
            }
            parsableByteArray.setPosition(position + i2);
        }
        return null;
    }

    /* JADX WARN: Code duplicated, block: B:108:0x0247  */
    /* JADX WARN: Code duplicated, block: B:111:0x0284  */
    /* JADX WARN: Code duplicated, block: B:112:0x0287  */
    /* JADX WARN: Code duplicated, block: B:117:0x02ad  */
    /* JADX WARN: Code duplicated, block: B:119:0x02bd  */
    /* JADX WARN: Code duplicated, block: B:137:0x0353  */
    /* JADX WARN: Code duplicated, block: B:150:0x03a2  */
    /* JADX WARN: Code duplicated, block: B:151:0x03a4  */
    /* JADX WARN: Code duplicated, block: B:155:0x03be  */
    /* JADX WARN: Code duplicated, block: B:157:0x03c8  */
    /* JADX WARN: Code duplicated, block: B:165:0x040c  */
    /* JADX WARN: Code duplicated, block: B:166:0x040e  */
    /* JADX WARN: Code duplicated, block: B:168:0x0417  */
    /* JADX WARN: Code duplicated, block: B:173:0x0431  */
    /* JADX WARN: Code duplicated, block: B:176:0x0436  */
    /* JADX WARN: Code duplicated, block: B:177:0x0439  */
    /* JADX WARN: Code duplicated, block: B:179:0x043c  */
    /* JADX WARN: Code duplicated, block: B:180:0x043f  */
    /* JADX WARN: Code duplicated, block: B:182:0x0443  */
    /* JADX WARN: Code duplicated, block: B:183:0x0445  */
    /* JADX WARN: Code duplicated, block: B:185:0x0449  */
    /* JADX WARN: Code duplicated, block: B:186:0x044c  */
    /* JADX WARN: Code duplicated, block: B:190:0x0459  */
    /* JADX WARN: Code duplicated, block: B:192:0x0465  */
    /* JADX WARN: Code duplicated, block: B:193:0x0477  */
    /* JADX WARN: Code duplicated, block: B:196:0x0481  */
    /* JADX WARN: Code duplicated, block: B:209:0x0424 A[EDGE_INSN: B:209:0x0424->B:170:0x0424 BREAK  A[LOOP:2: B:153:0x03b7->B:169:0x041c], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:219:0x0183 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:224:0x017d A[EDGE_INSN: B:224:0x017d->B:64:0x017d BREAK  A[LOOP:7: B:60:0x0164->B:63:0x016c], SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:227:0x0232 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:228:0x022a A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:48:0x00fc  */
    /* JADX WARN: Code duplicated, block: B:50:0x00ff  */
    /* JADX WARN: Code duplicated, block: B:53:0x010b A[LOOP:0: B:51:0x0105->B:53:0x010b, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:56:0x0132  */
    /* JADX WARN: Code duplicated, block: B:59:0x0162  */
    /* JADX WARN: Code duplicated, block: B:61:0x0166  */
    /* JADX WARN: Code duplicated, block: B:63:0x016c A[LOOP:7: B:60:0x0164->B:63:0x016c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:67:0x019b A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:68:0x019d A[ADDED_TO_REGION, LOOP:8: B:68:0x019d->B:70:0x01a1, LOOP_START, PHI: r16 r26 r27
      0x019d: PHI (r16v9 int) = (r16v2 int), (r16v10 int) binds: [B:67:0x019b, B:70:0x01a1] A[DONT_GENERATE, DONT_INLINE]
      0x019d: PHI (r26v4 int) = (r26v1 int), (r26v5 int) binds: [B:67:0x019b, B:70:0x01a1] A[DONT_GENERATE, DONT_INLINE]
      0x019d: PHI (r27v3 int) = (r27v1 int), (r27v5 int) binds: [B:67:0x019b, B:70:0x01a1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:74:0x01bc  */
    /* JADX WARN: Code duplicated, block: B:77:0x01c8  */
    /* JADX WARN: Code duplicated, block: B:78:0x01ca  */
    /* JADX WARN: Code duplicated, block: B:81:0x01d1  */
    /* JADX WARN: Code duplicated, block: B:83:0x01d8  */
    /* JADX WARN: Code duplicated, block: B:88:0x01fa  */
    /* JADX WARN: Code duplicated, block: B:93:0x0222 A[DONT_INVERT, LOOP:9: B:93:0x0222->B:97:0x022c, LOOP_START, PHI: r16
      0x0222: PHI (r16v6 int) = (r16v2 int), (r16v7 int) binds: [B:92:0x0220, B:97:0x022c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:94:0x0224  */
    /* JADX WARN: Code duplicated, block: B:97:0x022c A[LOOP:9: B:93:0x0222->B:97:0x022c, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:98:0x0232 A[EDGE_INSN: B:98:0x0232->B:99:0x0233 BREAK  A[LOOP:9: B:93:0x0222->B:97:0x022c]] */
    private static TrackSampleTable parseStbl(Track track, Atom.ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder) throws ParserException {
        SampleSizeBox stz2SampleSizeBox;
        boolean z;
        int unsignedIntToInt;
        int unsignedIntToInt2;
        int unsignedIntToInt3;
        int fixedSampleSize;
        boolean z2;
        long[] jArrCopyOf;
        int[] iArrCopyOf;
        long[] jArrCopyOf2;
        int[] iArrCopyOf2;
        int i;
        long j;
        long j2;
        int i2;
        int i3;
        int i4;
        int i5;
        int unsignedIntToInt4;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        long j3;
        boolean z3;
        int i11;
        Track track2;
        String str;
        int[] iArr;
        long[] jArr;
        int i12;
        int[] iArr2;
        int i13;
        boolean zMoveNext;
        int i14;
        int i15;
        int i16;
        int unsignedIntToInt5;
        int i17;
        int unsignedIntToInt6;
        int i18;
        long jScaleLargeTimestamp;
        long[] jArr2;
        int i19;
        long[] jArr3;
        boolean z4;
        int[] iArr3;
        int[] iArr4;
        long[] jArr4;
        int i20;
        boolean z5;
        int i21;
        int i22;
        long[] jArr5;
        int i23;
        int[] iArr5;
        int i24;
        boolean z6;
        long[] jArr6;
        int[] iArr6;
        int i25;
        int[] iArr7;
        long[] jArr7;
        long j4;
        int i26;
        int i27;
        long j5;
        int i28;
        int i29;
        int[] iArr8;
        long j6;
        int i30;
        boolean z7;
        long[] jArr8;
        int[] iArr9;
        Atom.LeafAtom leafAtomOfType = containerAtom.getLeafAtomOfType(Atom.TYPE_stsz);
        if (leafAtomOfType != null) {
            stz2SampleSizeBox = new StszSampleSizeBox(leafAtomOfType, track.format);
        } else {
            Atom.LeafAtom leafAtomOfType2 = containerAtom.getLeafAtomOfType(Atom.TYPE_stz2);
            if (leafAtomOfType2 == null) {
                throw ParserException.createForMalformedContainer("Track has no sample table size information", null);
            }
            stz2SampleSizeBox = new Stz2SampleSizeBox(leafAtomOfType2);
        }
        int sampleCount = stz2SampleSizeBox.getSampleCount();
        if (sampleCount == 0) {
            return new TrackSampleTable(track, new long[0], new int[0], 0, new long[0], new int[0], 0L);
        }
        Atom.LeafAtom leafAtomOfType3 = containerAtom.getLeafAtomOfType(Atom.TYPE_stco);
        if (leafAtomOfType3 == null) {
            leafAtomOfType3 = (Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_co64));
            z = true;
        } else {
            z = false;
        }
        ParsableByteArray parsableByteArray = leafAtomOfType3.data;
        ParsableByteArray parsableByteArray2 = ((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_stsc))).data;
        ParsableByteArray parsableByteArray3 = ((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_stts))).data;
        Atom.LeafAtom leafAtomOfType4 = containerAtom.getLeafAtomOfType(Atom.TYPE_stss);
        ParsableByteArray parsableByteArray4 = leafAtomOfType4 != null ? leafAtomOfType4.data : null;
        Atom.LeafAtom leafAtomOfType5 = containerAtom.getLeafAtomOfType(Atom.TYPE_ctts);
        ParsableByteArray parsableByteArray5 = leafAtomOfType5 != null ? leafAtomOfType5.data : null;
        ChunkIterator chunkIterator = new ChunkIterator(parsableByteArray2, parsableByteArray, z);
        parsableByteArray3.setPosition(12);
        int unsignedIntToInt7 = parsableByteArray3.readUnsignedIntToInt() - 1;
        int unsignedIntToInt8 = parsableByteArray3.readUnsignedIntToInt();
        int unsignedIntToInt9 = parsableByteArray3.readUnsignedIntToInt();
        if (parsableByteArray5 != null) {
            parsableByteArray5.setPosition(12);
            unsignedIntToInt = parsableByteArray5.readUnsignedIntToInt();
        } else {
            unsignedIntToInt = 0;
        }
        if (parsableByteArray4 != null) {
            parsableByteArray4.setPosition(12);
            unsignedIntToInt2 = parsableByteArray4.readUnsignedIntToInt();
            if (unsignedIntToInt2 > 0) {
                unsignedIntToInt3 = parsableByteArray4.readUnsignedIntToInt() - 1;
            } else {
                parsableByteArray4 = null;
            }
            fixedSampleSize = stz2SampleSizeBox.getFixedSampleSize();
            String str2 = track.format.sampleMimeType;
            if (fixedSampleSize == -1 && ((MimeTypes.AUDIO_RAW.equals(str2) || MimeTypes.AUDIO_MLAW.equals(str2) || MimeTypes.AUDIO_ALAW.equals(str2)) && unsignedIntToInt7 == 0 && unsignedIntToInt == 0 && unsignedIntToInt2 == 0)) {
                z2 = true;
            } else {
                z2 = false;
            }
            if (z2) {
                int i31 = chunkIterator.length;
                jArr8 = new long[i31];
                iArr9 = new int[i31];
                while (chunkIterator.moveNext()) {
                    int i32 = chunkIterator.index;
                    jArr8[i32] = chunkIterator.offset;
                    iArr9[i32] = chunkIterator.numSamples;
                }
                FixedSampleSizeRechunker.Results resultsRechunk = FixedSampleSizeRechunker.rechunk(fixedSampleSize, jArr8, iArr9, unsignedIntToInt9);
                long[] jArr9 = resultsRechunk.offsets;
                int[] iArr10 = resultsRechunk.sizes;
                int i33 = resultsRechunk.maximumSize;
                long[] jArr10 = resultsRechunk.timestamps;
                int[] iArr11 = resultsRechunk.flags;
                j3 = resultsRechunk.duration;
                track2 = track;
                i13 = sampleCount;
                jArr = jArr9;
                iArr2 = iArr10;
                i12 = i33;
                jArrCopyOf2 = jArr10;
                iArr = iArr11;
            } else {
                jArrCopyOf = new long[sampleCount];
                iArrCopyOf = new int[sampleCount];
                jArrCopyOf2 = new long[sampleCount];
                int i34 = unsignedIntToInt2;
                iArrCopyOf2 = new int[sampleCount];
                i = 0;
                j = 0;
                j2 = 0;
                i2 = unsignedIntToInt3;
                i3 = 0;
                i4 = 0;
                i5 = 0;
                unsignedIntToInt4 = 0;
                i6 = unsignedIntToInt9;
                int i35 = unsignedIntToInt;
                i7 = unsignedIntToInt8;
                i8 = unsignedIntToInt7;
                i9 = i34;
                i10 = i35;
                while (i3 < sampleCount) {
                    zMoveNext = true;
                    while (i == 0) {
                        zMoveNext = chunkIterator.moveNext();
                        if (zMoveNext) {
                            break;
                        }
                        j2 = chunkIterator.offset;
                        i = chunkIterator.numSamples;
                        sampleCount = sampleCount;
                        i2 = i2;
                    }
                    i14 = i2;
                    i15 = sampleCount;
                    if (!zMoveNext) {
                        Log.m72w(TAG, "Unexpected end of chunk data");
                        jArrCopyOf = Arrays.copyOf(jArrCopyOf, i3);
                        iArrCopyOf = Arrays.copyOf(iArrCopyOf, i3);
                        jArrCopyOf2 = Arrays.copyOf(jArrCopyOf2, i3);
                        iArrCopyOf2 = Arrays.copyOf(iArrCopyOf2, i3);
                        sampleCount = i3;
                        break;
                    }
                    if (parsableByteArray5 != null) {
                        while (unsignedIntToInt4 == 0 && i10 > 0) {
                            unsignedIntToInt4 = parsableByteArray5.readUnsignedIntToInt();
                            i5 = parsableByteArray5.readInt();
                            i10--;
                        }
                        unsignedIntToInt4--;
                    }
                    int i36 = i5;
                    jArrCopyOf[i3] = j2;
                    iArrCopyOf[i3] = stz2SampleSizeBox.readNextSampleSize();
                    if (iArrCopyOf[i3] > i4) {
                        i4 = iArrCopyOf[i3];
                    }
                    SampleSizeBox sampleSizeBox = stz2SampleSizeBox;
                    jArrCopyOf2[i3] = j + ((long) i36);
                    if (parsableByteArray4 == null) {
                        i16 = 1;
                    } else {
                        i16 = 0;
                    }
                    iArrCopyOf2[i3] = i16;
                    unsignedIntToInt5 = i14;
                    if (i3 == unsignedIntToInt5) {
                        iArrCopyOf2[i3] = 1;
                        i9--;
                        if (i9 > 0) {
                            unsignedIntToInt5 = ((ParsableByteArray) Assertions.checkNotNull(parsableByteArray4)).readUnsignedIntToInt() - 1;
                        }
                    }
                    int i37 = unsignedIntToInt5;
                    j += (long) i6;
                    i17 = i7 - 1;
                    if (i17 == 0 || i8 <= 0) {
                        unsignedIntToInt6 = i17;
                        i18 = i8;
                    } else {
                        unsignedIntToInt6 = parsableByteArray3.readUnsignedIntToInt();
                        i18 = i8 - 1;
                        i6 = parsableByteArray3.readInt();
                    }
                    int i38 = unsignedIntToInt6;
                    j2 += (long) iArrCopyOf[i3];
                    i--;
                    i3++;
                    i5 = i36;
                    i2 = i37;
                    stz2SampleSizeBox = sampleSizeBox;
                    sampleCount = i15;
                    int i39 = i18;
                    i7 = i38;
                    i8 = i39;
                }
                int i40 = i;
                j3 = j + ((long) i5);
                if (parsableByteArray5 != null) {
                    z3 = true;
                    break;
                }
                while (true) {
                    if (i10 > 0) {
                        z3 = true;
                        break;
                    }
                    if (parsableByteArray5.readUnsignedIntToInt() != 0) {
                        z3 = false;
                        break;
                    }
                    parsableByteArray5.readInt();
                    i10--;
                }
                if (i9 != 0 && i7 == 0 && i40 == 0 && i8 == 0) {
                    i11 = unsignedIntToInt4;
                    if (i11 == 0 && z3) {
                        track2 = track;
                    }
                    iArr = iArrCopyOf2;
                    jArr = jArrCopyOf;
                    i12 = i4;
                    iArr2 = iArrCopyOf;
                    i13 = sampleCount;
                } else {
                    i11 = unsignedIntToInt4;
                }
                StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Inconsistent stbl box for track ");
                track2 = track;
                sbM26m.append(track2.f138id);
                sbM26m.append(": remainingSynchronizationSamples ");
                sbM26m.append(i9);
                sbM26m.append(", remainingSamplesAtTimestampDelta ");
                sbM26m.append(i7);
                sbM26m.append(", remainingSamplesInChunk ");
                sbM26m.append(i40);
                sbM26m.append(", remainingTimestampDeltaChanges ");
                sbM26m.append(i8);
                sbM26m.append(", remainingSamplesAtTimestampOffset ");
                sbM26m.append(i11);
                if (z3) {
                    str = "";
                } else {
                    str = ", ctts invalid";
                }
                sbM26m.append(str);
                Log.m72w(TAG, sbM26m.toString());
                iArr = iArrCopyOf2;
                jArr = jArrCopyOf;
                i12 = i4;
                iArr2 = iArrCopyOf;
                i13 = sampleCount;
            }
            long j7 = j3;
            jScaleLargeTimestamp = Util.scaleLargeTimestamp(j7, 1000000L, track2.timescale);
            jArr2 = track2.editListDurations;
            if (jArr2 == null) {
                Util.scaleLargeTimestampsInPlace(jArrCopyOf2, 1000000L, track2.timescale);
                return new TrackSampleTable(track, jArr, iArr2, i12, jArrCopyOf2, iArr, jScaleLargeTimestamp);
            }
            if (jArr2.length == 1 || track2.type != 1 || jArrCopyOf2.length < 2) {
                i19 = i13;
            } else {
                long j8 = ((long[]) Assertions.checkNotNull(track2.editListMediaTimes))[0];
                long jScaleLargeTimestamp2 = j8 + Util.scaleLargeTimestamp(track2.editListDurations[0], track2.timescale, track2.movieTimescale);
                if (canApplyEditWithGaplessInfo(jArrCopyOf2, j7, j8, jScaleLargeTimestamp2)) {
                    long jScaleLargeTimestamp3 = Util.scaleLargeTimestamp(j8 - jArrCopyOf2[0], track2.format.sampleRate, track2.timescale);
                    i19 = i13;
                    long jScaleLargeTimestamp4 = Util.scaleLargeTimestamp(j7 - jScaleLargeTimestamp2, track2.format.sampleRate, track2.timescale);
                    if ((jScaleLargeTimestamp3 != 0 || jScaleLargeTimestamp4 != 0) && jScaleLargeTimestamp3 <= 2147483647L && jScaleLargeTimestamp4 <= 2147483647L) {
                        gaplessInfoHolder.encoderDelay = (int) jScaleLargeTimestamp3;
                        gaplessInfoHolder.encoderPadding = (int) jScaleLargeTimestamp4;
                        Util.scaleLargeTimestampsInPlace(jArrCopyOf2, 1000000L, track2.timescale);
                        return new TrackSampleTable(track, jArr, iArr2, i12, jArrCopyOf2, iArr, Util.scaleLargeTimestamp(track2.editListDurations[0], 1000000L, track2.movieTimescale));
                    }
                } else {
                    i19 = i13;
                }
            }
            jArr3 = track2.editListDurations;
            if (jArr3.length != 1 && jArr3[0] == 0) {
                long j9 = ((long[]) Assertions.checkNotNull(track2.editListMediaTimes))[0];
                for (int i41 = 0; i41 < jArrCopyOf2.length; i41++) {
                    jArrCopyOf2[i41] = Util.scaleLargeTimestamp(jArrCopyOf2[i41] - j9, 1000000L, track2.timescale);
                }
                return new TrackSampleTable(track, jArr, iArr2, i12, jArrCopyOf2, iArr, Util.scaleLargeTimestamp(j7 - j9, 1000000L, track2.timescale));
            }
            if (track2.type == 1) {
                z4 = true;
            } else {
                z4 = false;
            }
            iArr3 = new int[jArr3.length];
            iArr4 = new int[jArr3.length];
            jArr4 = (long[]) Assertions.checkNotNull(track2.editListMediaTimes);
            i20 = 0;
            z5 = false;
            i21 = 0;
            i22 = 0;
            while (true) {
                jArr5 = track2.editListDurations;
                i23 = i12;
                if (i20 < jArr5.length) {
                    break;
                }
                int[] iArr12 = iArr2;
                j6 = jArr4[i20];
                if (j6 != -1) {
                    i30 = i22;
                    boolean z8 = z5;
                    int i42 = i21;
                    long jScaleLargeTimestamp5 = Util.scaleLargeTimestamp(jArr5[i20], track2.timescale, track2.movieTimescale);
                    iArr3[i20] = Util.binarySearchFloor(jArrCopyOf2, j6, true, true);
                    iArr4[i20] = Util.binarySearchCeil(jArrCopyOf2, j6 + jScaleLargeTimestamp5, z4, false);
                    while (iArr3[i20] < iArr4[i20] && (iArr[iArr3[i20]] & 1) == 0) {
                        iArr3[i20] = iArr3[i20] + 1;
                    }
                    int i43 = (iArr4[i20] - iArr3[i20]) + i42;
                    if (i30 != iArr3[i20]) {
                        z7 = true;
                    } else {
                        z7 = false;
                    }
                    i21 = i43;
                    i22 = iArr4[i20];
                    z5 = z8 | z7;
                }
                i20++;
                i12 = i23;
                i19 = i19;
                iArr2 = iArr12;
            }
            iArr5 = iArr2;
            i24 = 0;
            z6 = z5 | (i21 != i19);
            if (z6) {
                jArr6 = new long[i21];
            } else {
                jArr6 = jArr;
            }
            if (z6) {
                iArr6 = new int[i21];
            } else {
                iArr6 = iArr5;
            }
            if (z6) {
                i25 = 0;
            } else {
                i25 = i23;
            }
            if (z6) {
                iArr7 = new int[i21];
            } else {
                iArr7 = iArr;
            }
            jArr7 = new long[i21];
            j4 = 0;
            i26 = 0;
            i27 = i25;
            while (i24 < track2.editListDurations.length) {
                j5 = track2.editListMediaTimes[i24];
                i28 = iArr3[i24];
                int[] iArr13 = iArr3;
                i29 = iArr4[i24];
                if (z6) {
                    int i44 = i29 - i28;
                    System.arraycopy(jArr, i28, jArr6, i26, i44);
                    iArr8 = iArr5;
                    System.arraycopy(iArr8, i28, iArr6, i26, i44);
                    System.arraycopy(iArr, i28, iArr7, i26, i44);
                } else {
                    iArr8 = iArr5;
                }
                int i45 = i27;
                while (i28 < i29) {
                    long[] jArr11 = jArr6;
                    int i46 = i29;
                    long j10 = j4;
                    int i47 = i24;
                    int[] iArr14 = iArr8;
                    jArr7[i26] = Util.scaleLargeTimestamp(j4, 1000000L, track2.movieTimescale) + Util.scaleLargeTimestamp(Math.max(0L, jArrCopyOf2[i28] - j5), 1000000L, track2.timescale);
                    if (!z6 && iArr6[i26] > i45) {
                        i45 = iArr14[i28];
                    }
                    i26++;
                    i28++;
                    i29 = i46;
                    jArr6 = jArr11;
                    i24 = i47;
                    iArr8 = iArr14;
                    j4 = j10;
                }
                int i48 = i24;
                j4 += track2.editListDurations[i48];
                i24 = i48 + 1;
                i27 = i45;
                jArr6 = jArr6;
                iArr5 = iArr8;
                iArr3 = iArr13;
                iArr4 = iArr4;
                jArr = jArr;
            }
            return new TrackSampleTable(track, jArr6, iArr6, i27, jArr7, iArr7, Util.scaleLargeTimestamp(j4, 1000000L, track2.movieTimescale));
        }
        unsignedIntToInt2 = 0;
        unsignedIntToInt3 = -1;
        fixedSampleSize = stz2SampleSizeBox.getFixedSampleSize();
        String str3 = track.format.sampleMimeType;
        if (fixedSampleSize == -1) {
            z2 = false;
        } else {
            z2 = false;
        }
        if (z2) {
            int i310 = chunkIterator.length;
            jArr8 = new long[i310];
            iArr9 = new int[i310];
            while (chunkIterator.moveNext()) {
                int i311 = chunkIterator.index;
                jArr8[i311] = chunkIterator.offset;
                iArr9[i311] = chunkIterator.numSamples;
            }
            FixedSampleSizeRechunker.Results resultsRechunk2 = FixedSampleSizeRechunker.rechunk(fixedSampleSize, jArr8, iArr9, unsignedIntToInt9);
            long[] jArr12 = resultsRechunk2.offsets;
            int[] iArr15 = resultsRechunk2.sizes;
            int i312 = resultsRechunk2.maximumSize;
            long[] jArr13 = resultsRechunk2.timestamps;
            int[] iArr16 = resultsRechunk2.flags;
            j3 = resultsRechunk2.duration;
            track2 = track;
            i13 = sampleCount;
            jArr = jArr12;
            iArr2 = iArr15;
            i12 = i312;
            jArrCopyOf2 = jArr13;
            iArr = iArr16;
        } else {
            jArrCopyOf = new long[sampleCount];
            iArrCopyOf = new int[sampleCount];
            jArrCopyOf2 = new long[sampleCount];
            int i313 = unsignedIntToInt2;
            iArrCopyOf2 = new int[sampleCount];
            i = 0;
            j = 0;
            j2 = 0;
            i2 = unsignedIntToInt3;
            i3 = 0;
            i4 = 0;
            i5 = 0;
            unsignedIntToInt4 = 0;
            i6 = unsignedIntToInt9;
            int i314 = unsignedIntToInt;
            i7 = unsignedIntToInt8;
            i8 = unsignedIntToInt7;
            i9 = i313;
            i10 = i314;
            while (i3 < sampleCount) {
                zMoveNext = true;
                while (i == 0) {
                    zMoveNext = chunkIterator.moveNext();
                    if (zMoveNext) {
                        break;
                        break;
                    }
                    j2 = chunkIterator.offset;
                    i = chunkIterator.numSamples;
                    sampleCount = sampleCount;
                    i2 = i2;
                }
                i14 = i2;
                i15 = sampleCount;
                if (!zMoveNext) {
                    Log.m72w(TAG, "Unexpected end of chunk data");
                    jArrCopyOf = Arrays.copyOf(jArrCopyOf, i3);
                    iArrCopyOf = Arrays.copyOf(iArrCopyOf, i3);
                    jArrCopyOf2 = Arrays.copyOf(jArrCopyOf2, i3);
                    iArrCopyOf2 = Arrays.copyOf(iArrCopyOf2, i3);
                    sampleCount = i3;
                    break;
                }
                if (parsableByteArray5 != null) {
                    while (unsignedIntToInt4 == 0) {
                        unsignedIntToInt4 = parsableByteArray5.readUnsignedIntToInt();
                        i5 = parsableByteArray5.readInt();
                        i10--;
                    }
                    unsignedIntToInt4--;
                }
                int i315 = i5;
                jArrCopyOf[i3] = j2;
                iArrCopyOf[i3] = stz2SampleSizeBox.readNextSampleSize();
                if (iArrCopyOf[i3] > i4) {
                    i4 = iArrCopyOf[i3];
                }
                SampleSizeBox sampleSizeBox2 = stz2SampleSizeBox;
                jArrCopyOf2[i3] = j + ((long) i315);
                if (parsableByteArray4 == null) {
                    i16 = 1;
                } else {
                    i16 = 0;
                }
                iArrCopyOf2[i3] = i16;
                unsignedIntToInt5 = i14;
                if (i3 == unsignedIntToInt5) {
                    iArrCopyOf2[i3] = 1;
                    i9--;
                    if (i9 > 0) {
                        unsignedIntToInt5 = ((ParsableByteArray) Assertions.checkNotNull(parsableByteArray4)).readUnsignedIntToInt() - 1;
                    }
                }
                int i316 = unsignedIntToInt5;
                j += (long) i6;
                i17 = i7 - 1;
                if (i17 == 0) {
                    unsignedIntToInt6 = i17;
                    i18 = i8;
                } else {
                    unsignedIntToInt6 = i17;
                    i18 = i8;
                }
                int i317 = unsignedIntToInt6;
                j2 += (long) iArrCopyOf[i3];
                i--;
                i3++;
                i5 = i315;
                i2 = i316;
                stz2SampleSizeBox = sampleSizeBox2;
                sampleCount = i15;
                int i318 = i18;
                i7 = i317;
                i8 = i318;
            }
            int i49 = i;
            j3 = j + ((long) i5);
            if (parsableByteArray5 != null) {
                z3 = true;
                break;
            }
            while (true) {
                if (i10 > 0) {
                    z3 = true;
                    break;
                }
                if (parsableByteArray5.readUnsignedIntToInt() != 0) {
                    z3 = false;
                    break;
                }
                parsableByteArray5.readInt();
                i10--;
            }
            if (i9 != 0) {
                i11 = unsignedIntToInt4;
                StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("Inconsistent stbl box for track ");
                track2 = track;
                sbM26m2.append(track2.f138id);
                sbM26m2.append(": remainingSynchronizationSamples ");
                sbM26m2.append(i9);
                sbM26m2.append(", remainingSamplesAtTimestampDelta ");
                sbM26m2.append(i7);
                sbM26m2.append(", remainingSamplesInChunk ");
                sbM26m2.append(i49);
                sbM26m2.append(", remainingTimestampDeltaChanges ");
                sbM26m2.append(i8);
                sbM26m2.append(", remainingSamplesAtTimestampOffset ");
                sbM26m2.append(i11);
                if (z3) {
                    str = ", ctts invalid";
                } else {
                    str = "";
                }
                sbM26m2.append(str);
                Log.m72w(TAG, sbM26m2.toString());
            } else {
                i11 = unsignedIntToInt4;
                StringBuilder sbM26m3 = Insets$$ExternalSyntheticOutline0.m26m("Inconsistent stbl box for track ");
                track2 = track;
                sbM26m3.append(track2.f138id);
                sbM26m3.append(": remainingSynchronizationSamples ");
                sbM26m3.append(i9);
                sbM26m3.append(", remainingSamplesAtTimestampDelta ");
                sbM26m3.append(i7);
                sbM26m3.append(", remainingSamplesInChunk ");
                sbM26m3.append(i49);
                sbM26m3.append(", remainingTimestampDeltaChanges ");
                sbM26m3.append(i8);
                sbM26m3.append(", remainingSamplesAtTimestampOffset ");
                sbM26m3.append(i11);
                if (z3) {
                    str = ", ctts invalid";
                } else {
                    str = "";
                }
                sbM26m3.append(str);
                Log.m72w(TAG, sbM26m3.toString());
            }
            iArr = iArrCopyOf2;
            jArr = jArrCopyOf;
            i12 = i4;
            iArr2 = iArrCopyOf;
            i13 = sampleCount;
        }
        long j11 = j3;
        jScaleLargeTimestamp = Util.scaleLargeTimestamp(j11, 1000000L, track2.timescale);
        jArr2 = track2.editListDurations;
        if (jArr2 == null) {
            Util.scaleLargeTimestampsInPlace(jArrCopyOf2, 1000000L, track2.timescale);
            return new TrackSampleTable(track, jArr, iArr2, i12, jArrCopyOf2, iArr, jScaleLargeTimestamp);
        }
        if (jArr2.length == 1) {
            i19 = i13;
        } else {
            i19 = i13;
        }
        jArr3 = track2.editListDurations;
        if (jArr3.length != 1) {
        }
        if (track2.type == 1) {
            z4 = true;
        } else {
            z4 = false;
        }
        iArr3 = new int[jArr3.length];
        iArr4 = new int[jArr3.length];
        jArr4 = (long[]) Assertions.checkNotNull(track2.editListMediaTimes);
        i20 = 0;
        z5 = false;
        i21 = 0;
        i22 = 0;
        while (true) {
            jArr5 = track2.editListDurations;
            i23 = i12;
            if (i20 < jArr5.length) {
                break;
                break;
            }
            int[] iArr17 = iArr2;
            j6 = jArr4[i20];
            if (j6 != -1) {
                i30 = i22;
                boolean z9 = z5;
                int i410 = i21;
                long jScaleLargeTimestamp6 = Util.scaleLargeTimestamp(jArr5[i20], track2.timescale, track2.movieTimescale);
                iArr3[i20] = Util.binarySearchFloor(jArrCopyOf2, j6, true, true);
                iArr4[i20] = Util.binarySearchCeil(jArrCopyOf2, j6 + jScaleLargeTimestamp6, z4, false);
                while (iArr3[i20] < iArr4[i20]) {
                    iArr3[i20] = iArr3[i20] + 1;
                }
                int i411 = (iArr4[i20] - iArr3[i20]) + i410;
                if (i30 != iArr3[i20]) {
                    z7 = true;
                } else {
                    z7 = false;
                }
                i21 = i411;
                i22 = iArr4[i20];
                z5 = z9 | z7;
            }
            i20++;
            i12 = i23;
            i19 = i19;
            iArr2 = iArr17;
        }
        iArr5 = iArr2;
        i24 = 0;
        z6 = z5 | (i21 != i19);
        if (z6) {
            jArr6 = new long[i21];
        } else {
            jArr6 = jArr;
        }
        if (z6) {
            iArr6 = new int[i21];
        } else {
            iArr6 = iArr5;
        }
        if (z6) {
            i25 = 0;
        } else {
            i25 = i23;
        }
        if (z6) {
            iArr7 = new int[i21];
        } else {
            iArr7 = iArr;
        }
        jArr7 = new long[i21];
        j4 = 0;
        i26 = 0;
        i27 = i25;
        while (i24 < track2.editListDurations.length) {
            j5 = track2.editListMediaTimes[i24];
            i28 = iArr3[i24];
            int[] iArr18 = iArr3;
            i29 = iArr4[i24];
            if (z6) {
                int i412 = i29 - i28;
                System.arraycopy(jArr, i28, jArr6, i26, i412);
                iArr8 = iArr5;
                System.arraycopy(iArr8, i28, iArr6, i26, i412);
                System.arraycopy(iArr, i28, iArr7, i26, i412);
            } else {
                iArr8 = iArr5;
            }
            int i413 = i27;
            while (i28 < i29) {
                long[] jArr14 = jArr6;
                int i414 = i29;
                long j12 = j4;
                int i415 = i24;
                int[] iArr19 = iArr8;
                jArr7[i26] = Util.scaleLargeTimestamp(j4, 1000000L, track2.movieTimescale) + Util.scaleLargeTimestamp(Math.max(0L, jArrCopyOf2[i28] - j5), 1000000L, track2.timescale);
                if (!z6) {
                }
                i26++;
                i28++;
                i29 = i414;
                jArr6 = jArr14;
                i24 = i415;
                iArr8 = iArr19;
                j4 = j12;
            }
            int i416 = i24;
            j4 += track2.editListDurations[i416];
            i24 = i416 + 1;
            i27 = i413;
            jArr6 = jArr6;
            iArr5 = iArr8;
            iArr3 = iArr18;
            iArr4 = iArr4;
            jArr = jArr;
        }
        return new TrackSampleTable(track, jArr6, iArr6, i27, jArr7, iArr7, Util.scaleLargeTimestamp(j4, 1000000L, track2.movieTimescale));
    }

    private static StsdData parseStsd(ParsableByteArray parsableByteArray, int i, int i2, String str, @Nullable DrmInitData drmInitData, boolean z) throws ParserException {
        int i3;
        parsableByteArray.setPosition(12);
        int i4 = parsableByteArray.readInt();
        StsdData stsdData = new StsdData(i4);
        for (int i5 = 0; i5 < i4; i5++) {
            int position = parsableByteArray.getPosition();
            int i6 = parsableByteArray.readInt();
            ExtractorUtil.checkContainerInput(i6 > 0, "childAtomSize must be positive");
            int i7 = parsableByteArray.readInt();
            if (i7 == 1635148593 || i7 == 1635148595 || i7 == 1701733238 || i7 == 1831958048 || i7 == 1836070006 || i7 == 1752589105 || i7 == 1751479857 || i7 == 1932670515 || i7 == 1211250227 || i7 == 1987063864 || i7 == 1987063865 || i7 == 1635135537 || i7 == 1685479798 || i7 == 1685479729 || i7 == 1685481573 || i7 == 1685481521) {
                i3 = position;
                parseVideoSampleEntry(parsableByteArray, i7, i3, i6, i, i2, drmInitData, stsdData, i5);
            } else if (i7 == 1836069985 || i7 == 1701733217 || i7 == 1633889587 || i7 == 1700998451 || i7 == 1633889588 || i7 == 1835823201 || i7 == 1685353315 || i7 == 1685353317 || i7 == 1685353320 || i7 == 1685353324 || i7 == 1685353336 || i7 == 1935764850 || i7 == 1935767394 || i7 == 1819304813 || i7 == 1936684916 || i7 == 1953984371 || i7 == 778924082 || i7 == 778924083 || i7 == 1835557169 || i7 == 1835560241 || i7 == 1634492771 || i7 == 1634492791 || i7 == 1970037111 || i7 == 1332770163 || i7 == 1716281667) {
                i3 = position;
                parseAudioSampleEntry(parsableByteArray, i7, position, i6, i, str, z, drmInitData, stsdData, i5);
            } else {
                if (i7 == 1414810956 || i7 == 1954034535 || i7 == 2004251764 || i7 == 1937010800 || i7 == 1664495672) {
                    parseTextSampleEntry(parsableByteArray, i7, position, i6, i, str, stsdData);
                } else if (i7 == 1835365492) {
                    parseMetaDataSampleEntry(parsableByteArray, i7, position, i, stsdData);
                } else if (i7 == 1667329389) {
                    stsdData.format = new Format.Builder().setId(i).setSampleMimeType(MimeTypes.APPLICATION_CAMERA_MOTION).build();
                }
                i3 = position;
            }
            parsableByteArray.setPosition(i3 + i6);
        }
        return stsdData;
    }

    private static void parseTextSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, String str, StsdData stsdData) {
        parsableByteArray.setPosition(i2 + 8 + 8);
        String str2 = MimeTypes.APPLICATION_TTML;
        ImmutableList immutableListM136of = null;
        long j = Long.MAX_VALUE;
        if (i != 1414810956) {
            if (i == 1954034535) {
                int i5 = (i3 - 8) - 8;
                byte[] bArr = new byte[i5];
                parsableByteArray.readBytes(bArr, 0, i5);
                immutableListM136of = ImmutableList.m136of(bArr);
                str2 = MimeTypes.APPLICATION_TX3G;
            } else if (i == 2004251764) {
                str2 = MimeTypes.APPLICATION_MP4VTT;
            } else if (i == 1937010800) {
                j = 0;
            } else {
                if (i != 1664495672) {
                    throw new IllegalStateException();
                }
                stsdData.requiredSampleTransformation = 1;
                str2 = MimeTypes.APPLICATION_MP4CEA608;
            }
        }
        stsdData.format = new Format.Builder().setId(i4).setSampleMimeType(str2).setLanguage(str).setSubsampleOffsetUs(j).setInitializationData(immutableListM136of).build();
    }

    private static TkhdData parseTkhd(ParsableByteArray parsableByteArray) {
        boolean z;
        parsableByteArray.setPosition(8);
        int fullAtomVersion = Atom.parseFullAtomVersion(parsableByteArray.readInt());
        parsableByteArray.skipBytes(fullAtomVersion == 0 ? 8 : 16);
        int i = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int position = parsableByteArray.getPosition();
        int i2 = fullAtomVersion == 0 ? 4 : 8;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i4 >= i2) {
                z = true;
                break;
            }
            if (parsableByteArray.getData()[position + i4] != -1) {
                z = false;
                break;
            }
            i4++;
        }
        long j = C1022C.TIME_UNSET;
        if (z) {
            parsableByteArray.skipBytes(i2);
        } else {
            long unsignedInt = fullAtomVersion == 0 ? parsableByteArray.readUnsignedInt() : parsableByteArray.readUnsignedLongToLong();
            if (unsignedInt != 0) {
                j = unsignedInt;
            }
        }
        parsableByteArray.skipBytes(16);
        int i5 = parsableByteArray.readInt();
        int i6 = parsableByteArray.readInt();
        parsableByteArray.skipBytes(4);
        int i7 = parsableByteArray.readInt();
        int i8 = parsableByteArray.readInt();
        if (i5 == 0 && i6 == 65536 && i7 == -65536 && i8 == 0) {
            i3 = 90;
        } else if (i5 == 0 && i6 == -65536 && i7 == 65536 && i8 == 0) {
            i3 = 270;
        } else if (i5 == -65536 && i6 == 0 && i7 == 0 && i8 == -65536) {
            i3 = 180;
        }
        return new TkhdData(i, j, i3);
    }

    @Nullable
    private static Track parseTrak(Atom.ContainerAtom containerAtom, Atom.LeafAtom leafAtom, long j, @Nullable DrmInitData drmInitData, boolean z, boolean z2) throws ParserException {
        long[] jArr;
        long[] jArr2;
        Atom.ContainerAtom containerAtomOfType;
        Pair<long[], long[]> edts;
        Atom.ContainerAtom containerAtom2 = (Atom.ContainerAtom) Assertions.checkNotNull(containerAtom.getContainerAtomOfType(Atom.TYPE_mdia));
        int trackTypeForHdlr = getTrackTypeForHdlr(parseHdlr(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(Atom.TYPE_hdlr))).data));
        if (trackTypeForHdlr == -1) {
            return null;
        }
        TkhdData tkhd = parseTkhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_tkhd))).data);
        long jScaleLargeTimestamp = C1022C.TIME_UNSET;
        long j2 = j == C1022C.TIME_UNSET ? tkhd.duration : j;
        long mvhd = parseMvhd(leafAtom.data);
        if (j2 != C1022C.TIME_UNSET) {
            jScaleLargeTimestamp = Util.scaleLargeTimestamp(j2, 1000000L, mvhd);
        }
        long j3 = jScaleLargeTimestamp;
        Atom.ContainerAtom containerAtom3 = (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl));
        Pair<Long, String> mdhd = parseMdhd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom2.getLeafAtomOfType(Atom.TYPE_mdhd))).data);
        StsdData stsd = parseStsd(((Atom.LeafAtom) Assertions.checkNotNull(containerAtom3.getLeafAtomOfType(Atom.TYPE_stsd))).data, tkhd.f137id, tkhd.rotationDegrees, (String) mdhd.second, drmInitData, z2);
        if (z || (containerAtomOfType = containerAtom.getContainerAtomOfType(Atom.TYPE_edts)) == null || (edts = parseEdts(containerAtomOfType)) == null) {
            jArr = null;
            jArr2 = null;
        } else {
            long[] jArr3 = (long[]) edts.first;
            jArr2 = (long[]) edts.second;
            jArr = jArr3;
        }
        if (stsd.format == null) {
            return null;
        }
        return new Track(tkhd.f137id, trackTypeForHdlr, ((Long) mdhd.first).longValue(), mvhd, j3, stsd.format, stsd.requiredSampleTransformation, stsd.trackEncryptionBoxes, stsd.nalUnitLengthFieldLength, jArr, jArr2);
    }

    public static List<TrackSampleTable> parseTraks(Atom.ContainerAtom containerAtom, GaplessInfoHolder gaplessInfoHolder, long j, @Nullable DrmInitData drmInitData, boolean z, boolean z2, Function<Track, Track> function) throws ParserException {
        Track trackApply;
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < containerAtom.containerChildren.size(); i++) {
            Atom.ContainerAtom containerAtom2 = containerAtom.containerChildren.get(i);
            if (containerAtom2.type == 1953653099 && (trackApply = function.apply(parseTrak(containerAtom2, (Atom.LeafAtom) Assertions.checkNotNull(containerAtom.getLeafAtomOfType(Atom.TYPE_mvhd)), j, drmInitData, z, z2))) != null) {
                arrayList.add(parseStbl(trackApply, (Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(((Atom.ContainerAtom) Assertions.checkNotNull(containerAtom2.getContainerAtomOfType(Atom.TYPE_mdia))).getContainerAtomOfType(Atom.TYPE_minf))).getContainerAtomOfType(Atom.TYPE_stbl)), gaplessInfoHolder));
            }
        }
        return arrayList;
    }

    public static Pair<Metadata, Metadata> parseUdta(Atom.LeafAtom leafAtom) {
        ParsableByteArray parsableByteArray = leafAtom.data;
        parsableByteArray.setPosition(8);
        Metadata udtaMeta = null;
        Metadata smta = null;
        while (parsableByteArray.bytesLeft() >= 8) {
            int position = parsableByteArray.getPosition();
            int i = parsableByteArray.readInt();
            int i2 = parsableByteArray.readInt();
            if (i2 == 1835365473) {
                parsableByteArray.setPosition(position);
                udtaMeta = parseUdtaMeta(parsableByteArray, position + i);
            } else if (i2 == 1936553057) {
                parsableByteArray.setPosition(position);
                smta = parseSmta(parsableByteArray, position + i);
            }
            parsableByteArray.setPosition(position + i);
        }
        return Pair.create(udtaMeta, smta);
    }

    @Nullable
    private static Metadata parseUdtaMeta(ParsableByteArray parsableByteArray, int i) {
        parsableByteArray.skipBytes(8);
        maybeSkipRemainingMetaAtomHeaderBytes(parsableByteArray);
        while (parsableByteArray.getPosition() < i) {
            int position = parsableByteArray.getPosition();
            int i2 = parsableByteArray.readInt();
            if (parsableByteArray.readInt() == 1768715124) {
                parsableByteArray.setPosition(position);
                return parseIlst(parsableByteArray, position + i2);
            }
            parsableByteArray.setPosition(position + i2);
        }
        return null;
    }

    private static void parseVideoSampleEntry(ParsableByteArray parsableByteArray, int i, int i2, int i3, int i4, int i5, @Nullable DrmInitData drmInitData, StsdData stsdData, int i6) throws ParserException {
        String str;
        DrmInitData drmInitData2;
        List<byte[]> list;
        float f;
        byte[] bArr;
        float f2;
        byte[] bArr2;
        int i7;
        int iIsoColorPrimariesToColorSpace;
        String str2;
        int i8 = i2;
        int i9 = i3;
        DrmInitData drmInitDataCopyWithSchemeType = drmInitData;
        StsdData stsdData2 = stsdData;
        parsableByteArray.setPosition(i8 + 8 + 8);
        parsableByteArray.skipBytes(16);
        int unsignedShort = parsableByteArray.readUnsignedShort();
        int unsignedShort2 = parsableByteArray.readUnsignedShort();
        parsableByteArray.skipBytes(50);
        int position = parsableByteArray.getPosition();
        int iIntValue = i;
        if (iIntValue == 1701733238) {
            Pair<Integer, TrackEncryptionBox> sampleEntryEncryptionData = parseSampleEntryEncryptionData(parsableByteArray, i8, i9);
            if (sampleEntryEncryptionData != null) {
                iIntValue = ((Integer) sampleEntryEncryptionData.first).intValue();
                drmInitDataCopyWithSchemeType = drmInitDataCopyWithSchemeType == null ? null : drmInitDataCopyWithSchemeType.copyWithSchemeType(((TrackEncryptionBox) sampleEntryEncryptionData.second).schemeType);
                stsdData2.trackEncryptionBoxes[i6] = (TrackEncryptionBox) sampleEntryEncryptionData.second;
            }
            parsableByteArray.setPosition(position);
        }
        if (iIntValue == 1831958048) {
            str = MimeTypes.VIDEO_MPEG;
        } else {
            str = iIntValue == 1211250227 ? MimeTypes.VIDEO_H263 : null;
        }
        float paspFromParent = 1.0f;
        byte[] projFromParent = null;
        String str3 = null;
        List<byte[]> listM136of = null;
        int i10 = -1;
        int i11 = -1;
        ByteBuffer byteBufferAllocateHdrStaticInfo = null;
        EsdsData esdsData = null;
        boolean z = false;
        int i12 = -1;
        int iIsoTransferCharacteristicsToColorTransfer = -1;
        while (true) {
            if (position - i8 >= i9) {
                drmInitData2 = drmInitDataCopyWithSchemeType;
                list = listM136of;
                break;
            }
            parsableByteArray.setPosition(position);
            int position2 = parsableByteArray.getPosition();
            drmInitData2 = drmInitDataCopyWithSchemeType;
            int i13 = parsableByteArray.readInt();
            if (i13 == 0) {
                list = listM136of;
                if (parsableByteArray.getPosition() - i8 == i9) {
                    break;
                }
            } else {
                list = listM136of;
            }
            ExtractorUtil.checkContainerInput(i13 > 0, "childAtomSize must be positive");
            int i14 = parsableByteArray.readInt();
            if (i14 == 1635148611) {
                ExtractorUtil.checkContainerInput(str == null, null);
                parsableByteArray.setPosition(position2 + 8);
                AvcConfig avcConfig = AvcConfig.parse(parsableByteArray);
                listM136of = avcConfig.initializationData;
                stsdData2.nalUnitLengthFieldLength = avcConfig.nalUnitLengthFieldLength;
                if (!z) {
                    paspFromParent = avcConfig.pixelWidthHeightRatio;
                }
                str3 = avcConfig.codecs;
                str2 = MimeTypes.VIDEO_H264;
            } else if (i14 == 1752589123) {
                ExtractorUtil.checkContainerInput(str == null, null);
                parsableByteArray.setPosition(position2 + 8);
                HevcConfig hevcConfig = HevcConfig.parse(parsableByteArray);
                listM136of = hevcConfig.initializationData;
                stsdData2.nalUnitLengthFieldLength = hevcConfig.nalUnitLengthFieldLength;
                if (!z) {
                    paspFromParent = hevcConfig.pixelWidthHeightRatio;
                }
                str3 = hevcConfig.codecs;
                str2 = MimeTypes.VIDEO_H265;
            } else {
                if (i14 == 1685480259 || i14 == 1685485123) {
                    unsignedShort2 = unsignedShort2;
                    iIntValue = iIntValue;
                    f2 = paspFromParent;
                    bArr2 = projFromParent;
                    i7 = i10;
                    DolbyVisionConfig dolbyVisionConfig = DolbyVisionConfig.parse(parsableByteArray);
                    if (dolbyVisionConfig != null) {
                        str3 = dolbyVisionConfig.codecs;
                        str = MimeTypes.VIDEO_DOLBY_VISION;
                    }
                } else {
                    if (i14 == 1987076931) {
                        ExtractorUtil.checkContainerInput(str == null, null);
                        str2 = iIntValue == 1987063864 ? MimeTypes.VIDEO_VP8 : MimeTypes.VIDEO_VP9;
                    } else {
                        if (i14 == 1635135811) {
                            ExtractorUtil.checkContainerInput(str == null, null);
                            str2 = MimeTypes.VIDEO_AV1;
                        } else if (i14 == 1668050025) {
                            if (byteBufferAllocateHdrStaticInfo == null) {
                                byteBufferAllocateHdrStaticInfo = allocateHdrStaticInfo();
                            }
                            ByteBuffer byteBuffer = byteBufferAllocateHdrStaticInfo;
                            byteBuffer.position(21);
                            byteBuffer.putShort(parsableByteArray.readShort());
                            byteBuffer.putShort(parsableByteArray.readShort());
                            listM136of = list;
                            byteBufferAllocateHdrStaticInfo = byteBuffer;
                            i10 = i10;
                            projFromParent = projFromParent;
                        } else if (i14 == 1835295606) {
                            if (byteBufferAllocateHdrStaticInfo == null) {
                                byteBufferAllocateHdrStaticInfo = allocateHdrStaticInfo();
                            }
                            ByteBuffer byteBuffer2 = byteBufferAllocateHdrStaticInfo;
                            short s = parsableByteArray.readShort();
                            short s2 = parsableByteArray.readShort();
                            short s3 = parsableByteArray.readShort();
                            short s4 = parsableByteArray.readShort();
                            short s5 = parsableByteArray.readShort();
                            short s6 = parsableByteArray.readShort();
                            short s7 = parsableByteArray.readShort();
                            float f3 = paspFromParent;
                            short s8 = parsableByteArray.readShort();
                            long unsignedInt = parsableByteArray.readUnsignedInt();
                            long unsignedInt2 = parsableByteArray.readUnsignedInt();
                            byteBuffer2.position(1);
                            byteBuffer2.putShort(s5);
                            byteBuffer2.putShort(s6);
                            byteBuffer2.putShort(s);
                            byteBuffer2.putShort(s2);
                            byteBuffer2.putShort(s3);
                            byteBuffer2.putShort(s4);
                            byteBuffer2.putShort(s7);
                            byteBuffer2.putShort(s8);
                            byteBuffer2.putShort((short) (unsignedInt / 10000));
                            byteBuffer2.putShort((short) (unsignedInt2 / 10000));
                            listM136of = list;
                            byteBufferAllocateHdrStaticInfo = byteBuffer2;
                            paspFromParent = f3;
                            i10 = i10;
                            projFromParent = projFromParent;
                        } else {
                            unsignedShort2 = unsignedShort2;
                            iIntValue = iIntValue;
                            f2 = paspFromParent;
                            bArr2 = projFromParent;
                            i7 = i10;
                            if (i14 == 1681012275) {
                                ExtractorUtil.checkContainerInput(str == null, null);
                                str = MimeTypes.VIDEO_H263;
                            } else if (i14 == 1702061171) {
                                ExtractorUtil.checkContainerInput(str == null, null);
                                EsdsData esdsFromParent = parseEsdsFromParent(parsableByteArray, position2);
                                String str4 = esdsFromParent.mimeType;
                                byte[] bArr3 = esdsFromParent.initializationData;
                                listM136of = bArr3 != null ? ImmutableList.m136of(bArr3) : list;
                                i10 = i7;
                                esdsData = esdsFromParent;
                                str = str4;
                                iIsoColorPrimariesToColorSpace = i11;
                                projFromParent = bArr2;
                                i11 = iIsoColorPrimariesToColorSpace;
                                paspFromParent = f2;
                            } else if (i14 == 1885434736) {
                                listM136of = list;
                                i10 = i7;
                                paspFromParent = parsePaspFromParent(parsableByteArray, position2);
                                projFromParent = bArr2;
                                z = true;
                            } else {
                                if (i14 == 1937126244) {
                                    projFromParent = parseProjFromParent(parsableByteArray, position2, i13);
                                    listM136of = list;
                                    i10 = i7;
                                    iIsoColorPrimariesToColorSpace = i11;
                                } else if (i14 == 1936995172) {
                                    int unsignedByte = parsableByteArray.readUnsignedByte();
                                    parsableByteArray.skipBytes(3);
                                    if (unsignedByte == 0) {
                                        int unsignedByte2 = parsableByteArray.readUnsignedByte();
                                        if (unsignedByte2 == 0) {
                                            i10 = 0;
                                        } else if (unsignedByte2 == 1) {
                                            i10 = 1;
                                        } else if (unsignedByte2 == 2) {
                                            i10 = 2;
                                        } else if (unsignedByte2 == 3) {
                                            i10 = 3;
                                        }
                                    }
                                    iIsoColorPrimariesToColorSpace = i11;
                                    listM136of = list;
                                    projFromParent = bArr2;
                                } else if (i14 == 1668246642) {
                                    int i15 = parsableByteArray.readInt();
                                    if (i15 == TYPE_nclx || i15 == TYPE_nclc) {
                                        int unsignedShort3 = parsableByteArray.readUnsignedShort();
                                        int unsignedShort4 = parsableByteArray.readUnsignedShort();
                                        parsableByteArray.skipBytes(2);
                                        boolean z2 = i13 == 19 && (parsableByteArray.readUnsignedByte() & 128) != 0;
                                        iIsoColorPrimariesToColorSpace = ColorInfo.isoColorPrimariesToColorSpace(unsignedShort3);
                                        i12 = z2 ? 1 : 2;
                                        iIsoTransferCharacteristicsToColorTransfer = ColorInfo.isoTransferCharacteristicsToColorTransfer(unsignedShort4);
                                        i10 = i7;
                                    } else {
                                        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Unsupported color type: ");
                                        sbM26m.append(Atom.getAtomTypeString(i15));
                                        Log.m72w(TAG, sbM26m.toString());
                                    }
                                    listM136of = list;
                                    projFromParent = bArr2;
                                }
                                i11 = iIsoColorPrimariesToColorSpace;
                                paspFromParent = f2;
                            }
                        }
                        position += i13;
                        i8 = i2;
                        i9 = i3;
                        stsdData2 = stsdData;
                        drmInitDataCopyWithSchemeType = drmInitData2;
                        iIntValue = iIntValue;
                        unsignedShort2 = unsignedShort2;
                    }
                    listM136of = list;
                }
                i10 = i7;
                iIsoColorPrimariesToColorSpace = i11;
                listM136of = list;
                projFromParent = bArr2;
                i11 = iIsoColorPrimariesToColorSpace;
                paspFromParent = f2;
                position += i13;
                i8 = i2;
                i9 = i3;
                stsdData2 = stsdData;
                drmInitDataCopyWithSchemeType = drmInitData2;
                iIntValue = iIntValue;
                unsignedShort2 = unsignedShort2;
            }
            str = str2;
            i10 = i10;
            projFromParent = projFromParent;
            position += i13;
            i8 = i2;
            i9 = i3;
            stsdData2 = stsdData;
            drmInitDataCopyWithSchemeType = drmInitData2;
            iIntValue = iIntValue;
            unsignedShort2 = unsignedShort2;
        }
        int i16 = i10;
        if (str == null) {
            return;
        }
        Format.Builder drmInitData3 = new Format.Builder().setId(i4).setSampleMimeType(str).setCodecs(str3).setWidth(unsignedShort).setHeight(unsignedShort2).setPixelWidthHeightRatio(f).setRotationDegrees(i5).setProjectionData(bArr).setStereoMode(i16).setInitializationData(list).setDrmInitData(drmInitData2);
        int i17 = i11;
        int i18 = i12;
        int i19 = iIsoTransferCharacteristicsToColorTransfer;
        if (i17 != -1 || i18 != -1 || i19 != -1 || byteBufferAllocateHdrStaticInfo != null) {
            f = paspFromParent;
            bArr = projFromParent;
            drmInitData3.setColorInfo(new ColorInfo(i17, i18, i19, byteBufferAllocateHdrStaticInfo != null ? byteBufferAllocateHdrStaticInfo.array() : null));
        }
        if (esdsData != null) {
            drmInitData3.setAverageBitrate(Ints.saturatedCast(esdsData.bitrate)).setPeakBitrate(Ints.saturatedCast(esdsData.peakBitrate));
        }
        stsdData.format = drmInitData3.build();
    }
}
