package androidx.emoji2.text.flatbuffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/* JADX INFO: loaded from: classes2.dex */
public class Table {

    /* JADX INFO: renamed from: bb */
    public ByteBuffer f86bb;
    public int bb_pos;
    public Utf8 utf8 = Utf8.getDefault();
    private int vtable_size;
    private int vtable_start;

    public final int __offset(int i) {
        if (i < this.vtable_size) {
            return this.f86bb.getShort(this.vtable_start + i);
        }
        return 0;
    }

    public final void __reset(int i, ByteBuffer byteBuffer) {
        this.f86bb = byteBuffer;
        if (byteBuffer == null) {
            this.bb_pos = 0;
            this.vtable_start = 0;
            this.vtable_size = 0;
        } else {
            this.bb_pos = i;
            int i2 = i - byteBuffer.getInt(i);
            this.vtable_start = i2;
            this.vtable_size = this.f86bb.getShort(i2);
        }
    }

    public final int __vector(int i) {
        int i2 = i + this.bb_pos;
        return this.f86bb.getInt(i2) + i2 + 4;
    }

    public final ByteBuffer __vector_as_bytebuffer(int i, int i2) {
        int i__offset = __offset(i);
        if (i__offset == 0) {
            return null;
        }
        ByteBuffer byteBufferOrder = this.f86bb.duplicate().order(ByteOrder.LITTLE_ENDIAN);
        int i__vector = __vector(i__offset);
        byteBufferOrder.position(i__vector);
        byteBufferOrder.limit((__vector_len(i__offset) * i2) + i__vector);
        return byteBufferOrder;
    }

    public final ByteBuffer __vector_in_bytebuffer(ByteBuffer byteBuffer, int i, int i2) {
        int i__offset = __offset(i);
        if (i__offset == 0) {
            return null;
        }
        int i__vector = __vector(i__offset);
        byteBuffer.rewind();
        byteBuffer.limit((__vector_len(i__offset) * i2) + i__vector);
        byteBuffer.position(i__vector);
        return byteBuffer;
    }

    public final int __vector_len(int i) {
        int i2 = i + this.bb_pos;
        return this.f86bb.getInt(this.f86bb.getInt(i2) + i2);
    }

    public ByteBuffer getByteBuffer() {
        return this.f86bb;
    }

    public void __reset() {
        __reset(0, null);
    }
}
