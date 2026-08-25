package com.google.android.exoplayer2;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class ExoPlayerImpl$$ExternalSyntheticLambda9 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ ExoPlayerImpl$$ExternalSyntheticLambda9(Object obj, Object obj2, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((ExoPlayerImpl) this.f$0).lambda$new$1((ExoPlayerImplInternal.PlaybackInfoUpdate) this.f$1);
                break;
            default:
                ((ExoPlayerImplInternal) this.f$0).lambda$sendMessageToTargetThread$1((PlayerMessage) this.f$1);
                break;
        }
    }
}
