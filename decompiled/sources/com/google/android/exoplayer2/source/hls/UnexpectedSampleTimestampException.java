package com.google.android.exoplayer2.source.hls;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.android.exoplayer2.source.chunk.MediaChunk;
import com.google.android.exoplayer2.util.Util;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class UnexpectedSampleTimestampException extends IOException {
    public final long lastAcceptedSampleTimeUs;
    public final MediaChunk mediaChunk;
    public final long rejectedSampleTimeUs;

    /* JADX WARN: Illegal instructions before constructor call */
    public UnexpectedSampleTimestampException(MediaChunk mediaChunk, long j, long j2) {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Unexpected sample timestamp: ");
        sbM26m.append(Util.usToMs(j2));
        sbM26m.append(" in chunk [");
        sbM26m.append(mediaChunk.startTimeUs);
        sbM26m.append(", ");
        sbM26m.append(mediaChunk.endTimeUs);
        sbM26m.append("]");
        super(sbM26m.toString());
        this.mediaChunk = mediaChunk;
        this.lastAcceptedSampleTimeUs = j;
        this.rejectedSampleTimeUs = j2;
    }
}
