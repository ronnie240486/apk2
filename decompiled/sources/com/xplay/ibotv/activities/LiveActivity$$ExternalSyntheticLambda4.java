package com.xplay.ibotv.activities;

import com.xplay.ibotv.activities.mobile.LiveMobileActivity;
import com.xplay.ibotv.apps.SideMenu;
import com.xplay.ibotv.dlgfragment.ConnectDlgFragment;
import com.xplay.ibotv.models.AppInfoModel;
import com.xplay.ibotv.models.CastModel;
import com.xplay.ibotv.models.CategoryModel;
import com.xplay.ibotv.models.EpisodeModel;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class LiveActivity$$ExternalSyntheticLambda4 implements Function3 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ LiveActivity$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        switch (this.$r8$classId) {
            case 0:
                return ((LiveActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 1:
                return ((ChangePlaylistActivity) this.f$0).lambda$onCreate$0((AppInfoModel.UrlModel) obj, (Integer) obj2, (Boolean) obj3);
            case 2:
                return ((MovieActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 3:
                return ((MovieInfoActivity) this.f$0).lambda$initView$0((CastModel) obj, (Integer) obj2, (Boolean) obj3);
            case 4:
                return ((SeriesActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            case 5:
                return ((SeriesPlayerActivity) this.f$0).lambda$onCreate$0((EpisodeModel) obj, (Integer) obj2, (Boolean) obj3);
            case 6:
                return ((SettingActivity) this.f$0).lambda$onCreate$0((SideMenu) obj, (Integer) obj2, (Boolean) obj3);
            case 7:
                return ((LiveMobileActivity) this.f$0).lambda$onCreate$0((CategoryModel) obj, (Integer) obj2, (Boolean) obj3);
            default:
                return ((ConnectDlgFragment) this.f$0).lambda$onCreateView$0((SideMenu) obj, (Integer) obj2, (Boolean) obj3);
        }
    }
}
