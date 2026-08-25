package androidx.core.location;

import android.location.Location;
import java.util.List;

/* JADX INFO: renamed from: androidx.core.location.LocationManagerCompat$LocationListenerTransport$$ExternalSyntheticLambda0 */
/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes3.dex */
public final /* synthetic */ class RunnableC0219xa0af9a65 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocationManagerCompat.LocationListenerTransport f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RunnableC0219xa0af9a65(LocationManagerCompat.LocationListenerTransport locationListenerTransport, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = locationListenerTransport;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.lambda$onLocationChanged$1((List) this.f$1);
                break;
            default:
                this.f$0.lambda$onLocationChanged$0((Location) this.f$1);
                break;
        }
    }
}
