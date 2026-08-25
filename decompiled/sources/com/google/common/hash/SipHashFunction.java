package com.google.common.hash;

import com.google.common.base.Preconditions;
import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.nio.ByteBuffer;
import javax.annotation.CheckForNull;

/* JADX INFO: loaded from: classes2.dex */
@Immutable
@ElementTypesAreNonnullByDefault
final class SipHashFunction extends AbstractHashFunction implements Serializable {
    public static final HashFunction SIP_HASH_24 = new SipHashFunction(506097522914230528L, 1084818905618843912L);
    private static final long serialVersionUID = 0;

    /* JADX INFO: renamed from: c */
    private final int f261c;

    /* JADX INFO: renamed from: d */
    private final int f262d;

    /* JADX INFO: renamed from: k0 */
    private final long f263k0;

    /* JADX INFO: renamed from: k1 */
    private final long f264k1;

    public static final class SipHasher extends AbstractStreamingHasher {
        private static final int CHUNK_SIZE = 8;

        /* JADX INFO: renamed from: b */
        private long f265b;

        /* JADX INFO: renamed from: c */
        private final int f266c;

        /* JADX INFO: renamed from: d */
        private final int f267d;
        private long finalM;

        /* JADX INFO: renamed from: v0 */
        private long f268v0;

        /* JADX INFO: renamed from: v1 */
        private long f269v1;

        /* JADX INFO: renamed from: v2 */
        private long f270v2;

        /* JADX INFO: renamed from: v3 */
        private long f271v3;

        public SipHasher(int i, int i2, long j, long j2) {
            super(8);
            this.f265b = 0L;
            this.finalM = 0L;
            this.f266c = i;
            this.f267d = i2;
            this.f268v0 = 8317987319222330741L ^ j;
            this.f269v1 = 7237128888997146477L ^ j2;
            this.f270v2 = 7816392313619706465L ^ j;
            this.f271v3 = 8387220255154660723L ^ j2;
        }

        private void processM(long j) {
            this.f271v3 ^= j;
            sipRound(this.f266c);
            this.f268v0 = j ^ this.f268v0;
        }

        private void sipRound(int i) {
            for (int i2 = 0; i2 < i; i2++) {
                long j = this.f268v0;
                long j2 = this.f269v1;
                this.f268v0 = j + j2;
                this.f270v2 += this.f271v3;
                this.f269v1 = Long.rotateLeft(j2, 13);
                long jRotateLeft = Long.rotateLeft(this.f271v3, 16);
                long j3 = this.f269v1;
                long j4 = this.f268v0;
                this.f269v1 = j3 ^ j4;
                this.f271v3 = jRotateLeft ^ this.f270v2;
                long jRotateLeft2 = Long.rotateLeft(j4, 32);
                long j5 = this.f270v2;
                long j6 = this.f269v1;
                this.f270v2 = j5 + j6;
                this.f268v0 = jRotateLeft2 + this.f271v3;
                this.f269v1 = Long.rotateLeft(j6, 17);
                long jRotateLeft3 = Long.rotateLeft(this.f271v3, 21);
                long j7 = this.f269v1;
                long j8 = this.f270v2;
                this.f269v1 = j7 ^ j8;
                this.f271v3 = jRotateLeft3 ^ this.f268v0;
                this.f270v2 = Long.rotateLeft(j8, 32);
            }
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public final HashCode makeHash() {
            long j = this.finalM ^ (this.f265b << 56);
            this.finalM = j;
            processM(j);
            this.f270v2 ^= 255;
            sipRound(this.f267d);
            return HashCode.fromLong(((this.f268v0 ^ this.f269v1) ^ this.f270v2) ^ this.f271v3);
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public final void process(ByteBuffer byteBuffer) {
            this.f265b += 8;
            processM(byteBuffer.getLong());
        }

        @Override // com.google.common.hash.AbstractStreamingHasher
        public final void processRemaining(ByteBuffer byteBuffer) {
            this.f265b += (long) byteBuffer.remaining();
            int i = 0;
            while (byteBuffer.hasRemaining()) {
                this.finalM ^= (((long) byteBuffer.get()) & 255) << i;
                i += 8;
            }
        }
    }

    public SipHashFunction(long j, long j2) {
        Preconditions.checkArgument(true, "The number of SipRound iterations (c=%s) during Compression must be positive.", 2);
        Preconditions.checkArgument(true, "The number of SipRound iterations (d=%s) during Finalization must be positive.", 4);
        this.f261c = 2;
        this.f262d = 4;
        this.f263k0 = j;
        this.f264k1 = j2;
    }

    @Override // com.google.common.hash.HashFunction
    public int bits() {
        return 64;
    }

    public boolean equals(@CheckForNull Object obj) {
        if (!(obj instanceof SipHashFunction)) {
            return false;
        }
        SipHashFunction sipHashFunction = (SipHashFunction) obj;
        return this.f261c == sipHashFunction.f261c && this.f262d == sipHashFunction.f262d && this.f263k0 == sipHashFunction.f263k0 && this.f264k1 == sipHashFunction.f264k1;
    }

    public int hashCode() {
        return (int) ((((long) ((SipHashFunction.class.hashCode() ^ this.f261c) ^ this.f262d)) ^ this.f263k0) ^ this.f264k1);
    }

    @Override // com.google.common.hash.HashFunction
    public Hasher newHasher() {
        return new SipHasher(this.f261c, this.f262d, this.f263k0, this.f264k1);
    }

    public String toString() {
        int i = this.f261c;
        int i2 = this.f262d;
        long j = this.f263k0;
        long j2 = this.f264k1;
        StringBuilder sb = new StringBuilder(81);
        sb.append("Hashing.sipHash");
        sb.append(i);
        sb.append(i2);
        sb.append("(");
        sb.append(j);
        sb.append(", ");
        sb.append(j2);
        sb.append(")");
        return sb.toString();
    }
}
