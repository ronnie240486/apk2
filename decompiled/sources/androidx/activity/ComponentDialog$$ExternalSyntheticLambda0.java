package androidx.activity;

import androidx.constraintlayout.helper.widget.Carousel;
import com.google.android.exoplayer2.analytics.DefaultAnalyticsCollector;
import com.xplay.ibotv.activities.CatchUpPlayerActivity;
import com.xplay.ibotv.activities.mobile.LiveChannelMobileActivity;
import com.xplay.ibotv.activities.mobile.LiveMobileActivity;
import com.xplay.ibotv.activities.mobile.MovieMobilePlayer;
import com.xplay.ibotv.activities.mobile.SeriesMobilePlayer;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class ComponentDialog$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ ComponentDialog$$ExternalSyntheticLambda0(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                ComponentDialog.m381onBackPressedDispatcher$lambda1((ComponentDialog) this.f$0);
                break;
            case 1:
                ((ComponentActivity) this.f$0).invalidateMenu();
                break;
            case 2:
                ((Carousel) this.f$0).lambda$updateItems$0();
                break;
            case 3:
                ((CatchUpPlayerActivity) this.f$0).lambda$listTimer$0();
                break;
            case 4:
                ((LiveChannelMobileActivity) this.f$0).lambda$mInfoHideTimer$4();
                break;
            case 5:
                ((LiveMobileActivity) this.f$0).lambda$mInfoHideTimer$5();
                break;
            case 6:
                ((MovieMobilePlayer) this.f$0).lambda$listTimer$2();
                break;
            case 7:
                ((SeriesMobilePlayer) this.f$0).lambda$listTimer$0();
                break;
            default:
                ((DefaultAnalyticsCollector) this.f$0).releaseInternal();
                break;
        }
    }
}
