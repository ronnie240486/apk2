package com.google.android.exoplayer2.source.hls;

import androidx.annotation.Nullable;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.IOException;

/* JADX INFO: loaded from: classes3.dex */
public final class SampleQueueMappingException extends IOException {
    public SampleQueueMappingException(@Nullable String str) {
        super(Insets$$ExternalSyntheticOutline0.m20m("Unable to bind a sample queue to TrackGroup with mime type ", str, "."));
    }
}
