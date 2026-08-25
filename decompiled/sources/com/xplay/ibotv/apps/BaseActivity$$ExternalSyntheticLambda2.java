package com.xplay.ibotv.apps;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import com.xplay.ibotv.activities.MoviePlayerActivity;
import com.xplay.ibotv.activities.SeriesPlayerActivity;
import com.xplay.ibotv.dlgfragment.SubtitleTrackDlgFragment;
import com.xplay.ibotv.net.NetworkTask;
import io.realm.RealmResults;
import java.util.List;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda2 implements SubtitleTrackDlgFragment.ItemPositionListener, NetworkTask.OnCompleteListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ FragmentActivity f$0;
    public final /* synthetic */ List f$1;
    public final /* synthetic */ List f$2;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda2(AppCompatActivity appCompatActivity, List list, List list2, int i) {
        this.$r8$classId = i;
        this.f$0 = appCompatActivity;
        this.f$2 = list;
        this.f$1 = list2;
    }

    @Override // com.xplay.ibotv.net.NetworkTask.OnCompleteListener
    public final void onComplete(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((BaseActivity) this.f$0).lambda$getChannelModels$8((RealmResults) this.f$1, this.f$2, (List) obj);
                break;
            default:
                ((BaseTVActivity) this.f$0).lambda$getChannelModels$8((RealmResults) this.f$1, this.f$2, (List) obj);
                break;
        }
    }

    @Override // com.xplay.ibotv.dlgfragment.SubtitleTrackDlgFragment.ItemPositionListener, com.xplay.ibotv.dlgfragment.AudioTrackDlgFragment.ItemPositionListener
    public final void onItemPosition(int i) {
        switch (this.$r8$classId) {
            case 1:
                ((MoviePlayerActivity) this.f$0).lambda$showOpenSubtitleDlgFragment$6(this.f$2, this.f$1, i);
                break;
            default:
                ((SeriesPlayerActivity) this.f$0).lambda$showOpenSubtitleDlgFragment$5(this.f$2, this.f$1, i);
                break;
        }
    }

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda2(FragmentActivity fragmentActivity, RealmResults realmResults, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = fragmentActivity;
        this.f$1 = realmResults;
        this.f$2 = list;
    }
}
