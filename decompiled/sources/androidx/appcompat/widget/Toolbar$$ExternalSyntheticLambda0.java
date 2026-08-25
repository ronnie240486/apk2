package androidx.appcompat.widget;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class Toolbar$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ Toolbar$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ((Toolbar) this.f$0).invalidateMenu();
                break;
            case 1:
                ((TooltipCompatHandler) this.f$0).lambda$new$0();
                break;
            default:
                ((TooltipCompatHandler) this.f$0).hide();
                break;
        }
    }
}
