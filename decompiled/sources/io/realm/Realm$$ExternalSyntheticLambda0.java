package io.realm;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.exoplayer2.Bundleable;
import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.analytics.PlayerId;
import com.google.android.exoplayer2.extractor.Extractor;
import com.google.android.exoplayer2.extractor.ExtractorInput;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.extractor.TrackOutput;
import com.google.android.exoplayer2.extractor.flac.FlacExtractor;
import com.google.android.exoplayer2.extractor.flv.FlvExtractor;
import com.google.android.exoplayer2.extractor.mkv.MatroskaExtractor;
import com.google.android.exoplayer2.extractor.mp3.Mp3Extractor;
import com.google.android.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import com.google.android.exoplayer2.extractor.mp4.Mp4Extractor;
import com.google.android.exoplayer2.extractor.ogg.OggExtractor;
import com.google.android.exoplayer2.extractor.p014ts.Ac3Extractor;
import com.google.android.exoplayer2.extractor.p014ts.Ac4Extractor;
import com.google.android.exoplayer2.extractor.p014ts.AdtsExtractor;
import com.google.android.exoplayer2.extractor.p014ts.PsExtractor;
import com.google.android.exoplayer2.extractor.p014ts.TsExtractor;
import com.google.android.exoplayer2.extractor.wav.WavExtractor;
import com.google.android.exoplayer2.metadata.id3.Id3Decoder;
import com.google.android.exoplayer2.source.ads.AdPlaybackState;
import com.google.android.exoplayer2.source.chunk.BundledChunkExtractor;
import com.google.android.exoplayer2.source.chunk.ChunkExtractor;
import com.google.android.exoplayer2.source.chunk.MediaParserChunkExtractor;
import com.google.android.exoplayer2.source.hls.HlsExtractorFactory;
import com.google.android.exoplayer2.source.hls.HlsMediaChunkExtractor;
import com.google.android.exoplayer2.source.hls.MediaParserHlsMediaChunkExtractor;
import com.google.android.exoplayer2.text.Cue;
import com.google.android.exoplayer2.text.CueGroup;
import com.google.android.exoplayer2.upstream.DataSpec;
import com.google.android.exoplayer2.upstream.cache.CacheKeyFactory;
import com.google.android.exoplayer2.util.TimestampAdjuster;
import com.google.android.exoplayer2.video.ColorInfo;
import com.google.android.exoplayer2.video.VideoSize;
import io.realm.internal.ObjectServerFacade;
import io.realm.internal.OsSharedRealm;
import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Realm$$ExternalSyntheticLambda0 implements ExtractorsFactory, Id3Decoder.FramePredicate, Bundleable.Creator, ChunkExtractor.Factory, HlsExtractorFactory, CacheKeyFactory, ObjectServerFacade.RealmCacheAccessor, ObjectServerFacade.RealmInstanceFactory {
    public final /* synthetic */ int $r8$classId;
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$1 = new Realm$$ExternalSyntheticLambda0(1);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$2 = new Realm$$ExternalSyntheticLambda0(2);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$3 = new Realm$$ExternalSyntheticLambda0(3);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$4 = new Realm$$ExternalSyntheticLambda0(4);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$5 = new Realm$$ExternalSyntheticLambda0(5);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$6 = new Realm$$ExternalSyntheticLambda0(6);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$7 = new Realm$$ExternalSyntheticLambda0(7);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$8 = new Realm$$ExternalSyntheticLambda0(8);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$9 = new Realm$$ExternalSyntheticLambda0(9);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$10 = new Realm$$ExternalSyntheticLambda0(10);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$11 = new Realm$$ExternalSyntheticLambda0(11);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$12 = new Realm$$ExternalSyntheticLambda0(12);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$13 = new Realm$$ExternalSyntheticLambda0(13);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$14 = new Realm$$ExternalSyntheticLambda0(14);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$15 = new Realm$$ExternalSyntheticLambda0(15);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$16 = new Realm$$ExternalSyntheticLambda0(16);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$17 = new Realm$$ExternalSyntheticLambda0(17);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$18 = new Realm$$ExternalSyntheticLambda0(18);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$19 = new Realm$$ExternalSyntheticLambda0(19);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$20 = new Realm$$ExternalSyntheticLambda0(20);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$21 = new Realm$$ExternalSyntheticLambda0(21);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$22 = new Realm$$ExternalSyntheticLambda0(22);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$23 = new Realm$$ExternalSyntheticLambda0(23);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$24 = new Realm$$ExternalSyntheticLambda0(24);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$25 = new Realm$$ExternalSyntheticLambda0(25);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE = new Realm$$ExternalSyntheticLambda0(0);
    public static final /* synthetic */ Realm$$ExternalSyntheticLambda0 INSTANCE$26 = new Realm$$ExternalSyntheticLambda0(26);

    public /* synthetic */ Realm$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    @Override // com.google.android.exoplayer2.upstream.cache.CacheKeyFactory
    public final String buildCacheKey(DataSpec dataSpec) {
        return CacheKeyFactory.CC.lambda$static$0(dataSpec);
    }

    @Override // com.google.android.exoplayer2.source.hls.HlsExtractorFactory
    public final HlsMediaChunkExtractor createExtractor(Uri uri, Format format, List list, TimestampAdjuster timestampAdjuster, Map map, ExtractorInput extractorInput, PlayerId playerId) {
        return MediaParserHlsMediaChunkExtractor.lambda$static$0(uri, format, list, timestampAdjuster, map, extractorInput, playerId);
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final Extractor[] createExtractors() {
        switch (this.$r8$classId) {
            case 1:
                return FlacExtractor.lambda$static$0();
            case 2:
                return FlvExtractor.lambda$static$0();
            case 3:
                return MatroskaExtractor.lambda$static$0();
            case 4:
                return Mp3Extractor.lambda$static$0();
            case 5:
            default:
                return WavExtractor.lambda$static$0();
            case 6:
                return FragmentedMp4Extractor.lambda$static$0();
            case 7:
                return Mp4Extractor.lambda$static$0();
            case 8:
                return OggExtractor.lambda$static$0();
            case 9:
                return Ac3Extractor.lambda$static$0();
            case 10:
                return Ac4Extractor.lambda$static$0();
            case 11:
                return AdtsExtractor.lambda$static$0();
            case 12:
                return PsExtractor.lambda$static$0();
            case 13:
                return TsExtractor.lambda$static$0();
        }
    }

    @Override // com.google.android.exoplayer2.extractor.ExtractorsFactory
    public final /* synthetic */ Extractor[] createExtractors(Uri uri, Map map) {
        switch (this.$r8$classId) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
            default:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
        }
        return createExtractors();
    }

    @Override // io.realm.internal.ObjectServerFacade.RealmInstanceFactory
    public final Realm createInstance(OsSharedRealm osSharedRealm) {
        return Realm.createInstance(osSharedRealm);
    }

    @Override // com.google.android.exoplayer2.source.chunk.ChunkExtractor.Factory
    public final ChunkExtractor createProgressiveMediaExtractor(int i, Format format, boolean z, List list, TrackOutput trackOutput, PlayerId playerId) {
        switch (this.$r8$classId) {
            case 18:
                return BundledChunkExtractor.lambda$static$0(i, format, z, list, trackOutput, playerId);
            default:
                return MediaParserChunkExtractor.lambda$static$0(i, format, z, list, trackOutput, playerId);
        }
    }

    @Override // io.realm.internal.ObjectServerFacade.RealmCacheAccessor
    public final Realm createRealmOrGetFromCache(RealmConfiguration realmConfiguration, OsSharedRealm.VersionID versionID) {
        return Realm.lambda$initializeRealm$0(realmConfiguration, versionID);
    }

    @Override // com.google.android.exoplayer2.metadata.id3.Id3Decoder.FramePredicate
    public final boolean evaluate(int i, int i2, int i3, int i4, int i5) {
        switch (this.$r8$classId) {
            case 5:
                return Mp3Extractor.lambda$static$1(i, i2, i3, i4, i5);
            default:
                return Id3Decoder.lambda$static$0(i, i2, i3, i4, i5);
        }
    }

    @Override // com.google.android.exoplayer2.Bundleable.Creator
    public final Bundleable fromBundle(Bundle bundle) {
        switch (this.$r8$classId) {
            case 16:
                return AdPlaybackState.fromBundle(bundle);
            case 17:
                return AdPlaybackState.AdGroup.fromBundle(bundle);
            case 18:
            case 19:
            case 20:
            case 23:
            default:
                return VideoSize.lambda$static$0(bundle);
            case 21:
                return Cue.fromBundle(bundle);
            case 22:
                return CueGroup.fromBundle(bundle);
            case 24:
                return ColorInfo.lambda$static$0(bundle);
        }
    }
}
