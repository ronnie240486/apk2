package com.google.android.exoplayer2.audio;

/* JADX INFO: renamed from: com.google.android.exoplayer2.audio.AudioRendererEventListener$EventDispatcher$$ExternalSyntheticLambda1 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1040x1350c7c2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AudioRendererEventListener.EventDispatcher f$0;
    public final /* synthetic */ Exception f$1;

    public /* synthetic */ RunnableC1040x1350c7c2(AudioRendererEventListener.EventDispatcher eventDispatcher, Exception exc, int i) {
        this.$r8$classId = i;
        this.f$0 = eventDispatcher;
        this.f$1 = exc;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$audioSinkError$8(this.f$1);
                break;
            default:
                this.f$0.lambda$audioCodecError$9(this.f$1);
                break;
        }
    }
}
