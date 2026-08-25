package com.bumptech.glide.request.target;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.util.Util;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public abstract class SimpleTarget<Z> extends BaseTarget<Z> {
    private final int height;
    private final int width;

    public SimpleTarget() {
        this(Integer.MIN_VALUE, Integer.MIN_VALUE);
    }

    @Override // com.bumptech.glide.request.target.Target
    public final void getSize(@NonNull SizeReadyCallback sizeReadyCallback) {
        if (Util.isValidDimensions(this.width, this.height)) {
            sizeReadyCallback.onSizeReady(this.width, this.height);
            return;
        }
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Width and height must both be > 0 or Target#SIZE_ORIGINAL, but given width: ");
        sbM26m.append(this.width);
        sbM26m.append(" and height: ");
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m22m(sbM26m, this.height, ", either provide dimensions in the constructor or call override()"));
    }

    @Override // com.bumptech.glide.request.target.Target
    public void removeCallback(@NonNull SizeReadyCallback sizeReadyCallback) {
    }

    public SimpleTarget(int i, int i2) {
        this.width = i;
        this.height = i2;
    }
}
