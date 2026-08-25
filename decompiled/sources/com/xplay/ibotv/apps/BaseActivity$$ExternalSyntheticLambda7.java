package com.xplay.ibotv.apps;

import io.realm.Realm;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class BaseActivity$$ExternalSyntheticLambda7 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ String f$0;

    public /* synthetic */ BaseActivity$$ExternalSyntheticLambda7(String str, int i) {
        this.$r8$classId = i;
        this.f$0 = str;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.lambda$getChannelModels$7(this.f$0, realm);
                break;
            case 1:
                BaseActivity.C221810.lambda$onResponse$1(this.f$0, realm);
                break;
            case 2:
                BaseActivity.C22225.lambda$onResponse$1(this.f$0, realm);
                break;
            case 3:
                BaseActivity.C22236.lambda$onResponse$1(this.f$0, realm);
                break;
            case 4:
                BaseActivity.C22247.lambda$onResponse$1(this.f$0, realm);
                break;
            case 5:
                BaseActivity.C22258.lambda$onResponse$1(this.f$0, realm);
                break;
            case 6:
                BaseActivity.C22269.lambda$onResponse$1(this.f$0, realm);
                break;
            case 7:
                BaseTVActivity.C222810.lambda$onResponse$1(this.f$0, realm);
                break;
            case 8:
                BaseTVActivity.C22325.lambda$onResponse$1(this.f$0, realm);
                break;
            case 9:
                BaseTVActivity.C22336.lambda$onResponse$1(this.f$0, realm);
                break;
            case 10:
                BaseTVActivity.C22347.lambda$onResponse$1(this.f$0, realm);
                break;
            case 11:
                BaseTVActivity.C22358.lambda$onResponse$1(this.f$0, realm);
                break;
            case 12:
                BaseTVActivity.C22369.lambda$onResponse$1(this.f$0, realm);
                break;
            case 13:
                BaseActivity.lambda$getSeriesFromEpisodes$19(this.f$0, realm);
                break;
            case 14:
                BaseActivity.lambda$getMovieModels$11(this.f$0, realm);
                break;
            case 15:
                BaseTVActivity.lambda$getSeriesFromEpisodes$19(this.f$0, realm);
                break;
            case 16:
                BaseTVActivity.lambda$getChannelModels$7(this.f$0, realm);
                break;
            default:
                BaseTVActivity.lambda$getMovieModels$11(this.f$0, realm);
                break;
        }
    }
}
