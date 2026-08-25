package com.google.common.hash;

import com.google.common.primitives.UnsignedBytes;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes.dex */
@Immutable
@ElementTypesAreNonnullByDefault
final class Murmur3_128HashFunction extends AbstractHashFunction implements Serializable {
    private static final long serialVersionUID = 0;
    private final int seed;
    public static final HashFunction MURMUR3_128 = new Murmur3_128HashFunction(0);
    public static final HashFunction GOOD_FAST_HASH_128 = new Murmur3_128HashFunction(Hashing.GOOD_FAST_HASH_SEED);

    /* JADX INFO: loaded from: classes2.dex */
    public static final class Murmur3_128Hasher extends AbstractStreamingHasher {

        /* JADX INFO: renamed from: C1 */
        private static final long f254C1 = -8663945395140668459L;

        /* JADX INFO: renamed from: C2 */
        private static final long f255C2 = 5545529020109919103L;
        private static final int CHUNK_SIZE = 16;

        /* JADX INFO: renamed from: h1 */
        private long f256h1;

        /* JADX INFO: renamed from: h2 */
        private long f257h2;
        private int length;

        public Murmur3_128Hasher(int i) {
            super(16);
            long j = i;
            this.f256h1 = j;
            this.f257h2 = j;
            this.length = 0;
        }

        private void bmix64(long j, long j2) {
            long jMixK1 = mixK1(j) ^ this.f256h1;
            this.f256h1 = jMixK1;
            long jRotateLeft = Long.rotateLeft(jMixK1, 27);
            long j3 = this.f257h2;
            this.f256h1 = ((jRotateLeft + j3) * 5) + 1390208809;
            long jMixK2 = mixK2(j2) ^ j3;
            this.f257h2 = jMixK2;
            this.f257h2 = ((Long.rotateLeft(jMixK2, 31) + this.f256h1) * 5) + 944331445;
        }

        private static long fmix64(long j) {
            long j2 = (j ^ (j >>> 33)) * (-49064778989728563L);
            long j3 = (j2 ^ (j2 >>> 33)) * (-4265267296055464877L);
            return j3 ^ (j3 >>> 33);
        }

        private static long mixK1(long j) {
            return Long.rotateLeft(j * f254C1, 31) * f255C2;
        }

        private static long mixK2(long j) {
            return Long.rotateLeft(j * f255C2, 33) * f254C1;
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public final HashCode makeHash() {
            long j = this.f256h1;
            int i = this.length;
            long j2 = j ^ ((long) i);
            long j3 = this.f257h2 ^ ((long) i);
            long j4 = j2 + j3;
            this.f256h1 = j4;
            this.f257h2 = j3 + j4;
            this.f256h1 = fmix64(j4);
            long jFmix64 = fmix64(this.f257h2);
            long j5 = this.f256h1 + jFmix64;
            this.f256h1 = j5;
            this.f257h2 = jFmix64 + j5;
            byte[] bArrArray = ByteBuffer.wrap(new byte[16]).order(ByteOrder.LITTLE_ENDIAN).putLong(this.f256h1).putLong(this.f257h2).array();
            int i2 = HashCode.$r8$clinit;
            return new HashCode.BytesHashCode(bArrArray);
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public final void process(ByteBuffer byteBuffer) {
            bmix64(byteBuffer.getLong(), byteBuffer.getLong());
            this.length += 16;
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public final void processRemaining(ByteBuffer byteBuffer) {
            long j;
            long j2;
            long j3;
            long j4;
            long j5;
            long j6;
            long j7;
            long j8;
            long j9;
            long j10;
            long j11;
            long j12;
            long j13;
            long j14;
            this.length = byteBuffer.remaining() + this.length;
            long j15 = 0;
            switch (byteBuffer.remaining()) {
                case 1:
                    j = 0;
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 2:
                    j2 = 0;
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 3:
                    j3 = 0;
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 4:
                    j4 = 0;
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 5:
                    j5 = 0;
                    j4 = j5 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(4))) << 32);
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 6:
                    j6 = 0;
                    j5 = j6 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(5))) << 40);
                    j4 = j5 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(4))) << 32);
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 7:
                    j6 = (((long) UnsignedBytes.toInt(byteBuffer.get(6))) << 48) ^ 0;
                    j5 = j6 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(5))) << 40);
                    j4 = j5 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(4))) << 32);
                    j3 = j4 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(3))) << 24);
                    j2 = j3 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(2))) << 16);
                    j = j2 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(1))) << 8);
                    j7 = ((long) UnsignedBytes.toInt(byteBuffer.get(0))) ^ j;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 8:
                    j8 = 0;
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 9:
                    j9 = 0;
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 10:
                    j10 = 0;
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 11:
                    j11 = 0;
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 12:
                    j12 = 0;
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 13:
                    j13 = 0;
                    j12 = j13 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(12))) << 32);
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 14:
                    j14 = 0;
                    j13 = j14 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(13))) << 40);
                    j12 = j13 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(12))) << 32);
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                case 15:
                    j14 = (((long) UnsignedBytes.toInt(byteBuffer.get(14))) << 48) ^ 0;
                    j13 = j14 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(13))) << 40);
                    j12 = j13 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(12))) << 32);
                    j11 = j12 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(11))) << 24);
                    j10 = j11 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(10))) << 16);
                    j9 = j10 ^ (((long) UnsignedBytes.toInt(byteBuffer.get(9))) << 8);
                    j8 = j9 ^ ((long) UnsignedBytes.toInt(byteBuffer.get(8)));
                    j7 = byteBuffer.getLong() ^ 0;
                    j15 = j8;
                    this.f256h1 ^= mixK1(j7);
                    this.f257h2 ^= mixK2(j15);
                    return;
                default:
                    throw new AssertionError("Should never get here.");
            }
        }
    }

    public Murmur3_128HashFunction(int i) {
        this.seed = i;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 128;
    }

    public boolean equals(@CheckForNull Object obj) {
        return (obj instanceof Murmur3_128HashFunction) && this.seed == ((Murmur3_128HashFunction) obj).seed;
    }

    public int hashCode() {
        return Murmur3_128HashFunction.class.hashCode() ^ this.seed;
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new Murmur3_128Hasher(this.seed);
    }

    public String toString() {
        int i = this.seed;
        StringBuilder sb = new StringBuilder(32);
        sb.append("Hashing.murmur3_128(");
        sb.append(i);
        sb.append(")");
        return sb.toString();
    }
}
