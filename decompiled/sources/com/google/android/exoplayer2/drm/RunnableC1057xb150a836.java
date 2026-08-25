package com.google.android.exoplayer2.drm;

/* JADX INFO: renamed from: com.google.android.exoplayer2.drm.DefaultDrmSessionManager$ReferenceCountListenerImpl$$ExternalSyntheticLambda0 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1057xb150a836 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ RunnableC1057xb150a836(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DefaultDrmSession) this.f$0).release(null);
                break;
            default:
                ((DefaultDrmSessionManager.PreacquiredSessionReference) this.f$0).lambda$release$1();
                break;
        }
    }
}
