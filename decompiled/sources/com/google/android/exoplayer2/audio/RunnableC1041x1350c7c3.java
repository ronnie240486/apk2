package com.google.android.exoplayer2.audio;

import com.google.android.exoplayer2.video.VideoRendererEventListener;

/* JADX INFO: renamed from: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda2 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1041x1350c7c3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ String f$1;
    public final /* synthetic */ long f$2;
    public final /* synthetic */ long f$3;

    public /* synthetic */ RunnableC1041x1350c7c3(Object obj, String str, long j, long j2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = str;
        this.f$2 = j;
        this.f$3 = j2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((AudioRendererEventListener.EventDispatcher) this.f$0).lambda$decoderInitialized$1(this.f$1, this.f$2, this.f$3);
                break;
            default:
                ((VideoRendererEventListener.EventDispatcher) this.f$0).lambda$decoderInitialized$1(this.f$1, this.f$2, this.f$3);
                break;
        }
    }
}
