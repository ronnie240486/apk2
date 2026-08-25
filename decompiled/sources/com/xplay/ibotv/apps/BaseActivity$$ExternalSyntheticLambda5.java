package com.xplay.ibotv.apps;

import com.xplay.ibotv.models.ResumeModel;
import io.realm.Realm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda5 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ResumeModel f$0;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda5(ResumeModel resumeModel, int i) {
        this.$r8$classId = i;
        this.f$0 = resumeModel;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.lambda$getMovieModels$12(this.f$0, realm);
                break;
            case 1:
                BaseActivity.C22247.lambda$onResponse$2(this.f$0, realm);
                break;
            case 2:
                BaseActivity.C22258.lambda$onResponse$2(this.f$0, realm);
                break;
            case 3:
                BaseTVActivity.C22347.lambda$onResponse$2(this.f$0, realm);
                break;
            case 4:
                BaseTVActivity.C22358.lambda$onResponse$2(this.f$0, realm);
                break;
            default:
                BaseTVActivity.lambda$getMovieModels$12(this.f$0, realm);
                break;
        }
    }
}
