package com.google.android.exoplayer2.drm;

import com.google.android.exoplayer2.Format;

/* JADX INFO: renamed from: com.google.android.exoplayer2.drm.DrmSessionEventListener$EventDispatcher$$ExternalSyntheticLambda0 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class RunnableC1060x283b7c83 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RunnableC1060x283b7c83(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((DrmSessionEventListener.EventDispatcher) this.f$0).lambda$drmSessionReleased$5((DrmSessionEventListener) this.f$1);
                break;
            case 1:
                ((DrmSessionEventListener.EventDispatcher) this.f$0).lambda$drmKeysRemoved$4((DrmSessionEventListener) this.f$1);
                break;
            case 2:
                ((DrmSessionEventListener.EventDispatcher) this.f$0).lambda$drmKeysLoaded$1((DrmSessionEventListener) this.f$1);
                break;
            case 3:
                ((DrmSessionEventListener.EventDispatcher) this.f$0).lambda$drmKeysRestored$3((DrmSessionEventListener) this.f$1);
                break;
            default:
                ((DefaultDrmSessionManager.PreacquiredSessionReference) this.f$0).lambda$acquire$0((Format) this.f$1);
                break;
        }
    }
}
