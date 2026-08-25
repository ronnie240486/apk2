package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.Format;
import com.google.android.exoplayer2.audio.AudioRendererEventListener;
import com.google.android.exoplayer2.decoder.DecoderReuseEvaluation;
import com.google.android.exoplayer2.source.MediaLoadData;
import com.google.android.exoplayer2.source.MediaSourceEventListener;
import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* JADX INFO: renamed from: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda1 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1061x283b7c84 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ RunnableC1061x283b7c84(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DrmSessionEventListener.EventDispatcher) this.f$0).lambda$drmSessionManagerError$2((DrmSessionEventListener) this.f$1, (Exception) this.f$2);
                break;
            case 1:
                ((AudioRendererEventListener.EventDispatcher) this.f$0).lambda$inputFormatChanged$2((Format) this.f$1, (DecoderReuseEvaluation) this.f$2);
                break;
            case 2:
                ((MediaSourceEventListener.EventDispatcher) this.f$0).lambda$downstreamFormatChanged$5((MediaSourceEventListener) this.f$1, (MediaLoadData) this.f$2);
                break;
            default:
                ((VideoRendererEventListener.EventDispatcher) this.f$0).lambda$inputFormatChanged$2((Format) this.f$1, (DecoderReuseEvaluation) this.f$2);
                break;
        }
    }
}
