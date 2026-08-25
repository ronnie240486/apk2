package com.xplay.ibotv.apps;

import com.xplay.ibotv.models.ResumeSeriesModel;
import io.realm.Realm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda6 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ResumeSeriesModel f$0;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda6(ResumeSeriesModel resumeSeriesModel, int i) {
        this.$r8$classId = i;
        this.f$0 = resumeSeriesModel;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.lambda$getSeriesFromEpisodes$20(this.f$0, realm);
                break;
            case 1:
                BaseActivity.C221810.lambda$onResponse$2(this.f$0, realm);
                break;
            case 2:
                BaseActivity.C22269.lambda$onResponse$2(this.f$0, realm);
                break;
            case 3:
                BaseTVActivity.C222810.lambda$onResponse$2(this.f$0, realm);
                break;
            case 4:
                BaseTVActivity.C22369.lambda$onResponse$2(this.f$0, realm);
                break;
            default:
                BaseTVActivity.lambda$getSeriesFromEpisodes$20(this.f$0, realm);
                break;
        }
    }
}
