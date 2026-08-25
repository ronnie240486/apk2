package retrofit2;

/* JADX INFO: renamed from: retrofit2.DefaultCallAdapterFactory$ExecutorCallbackCall$1$$ExternalSyntheticLambda0 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class RunnableC2841xee4a199c implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DefaultCallAdapterFactory.ExecutorCallbackCall.C28401 f$0;
    public final /* synthetic */ Callback f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ RunnableC2841xee4a199c(DefaultCallAdapterFactory.ExecutorCallbackCall.C28401 c28401, Callback callback, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = c28401;
        this.f$1 = callback;
        this.f$2 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onResponse$0(this.f$1, (Response) this.f$2);
                break;
            default:
                this.f$0.lambda$onFailure$1(this.f$1, (Throwable) this.f$2);
                break;
        }
    }
}
