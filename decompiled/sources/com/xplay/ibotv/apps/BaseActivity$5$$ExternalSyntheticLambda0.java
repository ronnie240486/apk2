package com.xplay.ibotv.apps;

import io.realm.Realm;
import retrofit2.Response;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class BaseActivity$5$$ExternalSyntheticLambda0 implements Realm.Transaction {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Response f$0;

    public /* synthetic */ BaseActivity$5$$ExternalSyntheticLambda0(Response response, int i) {
        this.$r8$classId = i;
        this.f$0 = response;
    }

    @Override // io.realm.Realm.Transaction
    public final void execute(Realm realm) {
        switch (this.$r8$classId) {
            case 0:
                BaseActivity.C22225.lambda$onResponse$0(this.f$0, realm);
                break;
            case 1:
                BaseActivity.C221810.lambda$onResponse$0(this.f$0, realm);
                break;
            case 2:
                BaseActivity.C22236.lambda$onResponse$0(this.f$0, realm);
                break;
            case 3:
                BaseActivity.C22247.lambda$onResponse$0(this.f$0, realm);
                break;
            case 4:
                BaseActivity.C22258.lambda$onResponse$0(this.f$0, realm);
                break;
            case 5:
                BaseActivity.C22269.lambda$onResponse$0(this.f$0, realm);
                break;
            case 6:
                BaseTVActivity.C222810.lambda$onResponse$0(this.f$0, realm);
                break;
            case 7:
                BaseTVActivity.C22325.lambda$onResponse$0(this.f$0, realm);
                break;
            case 8:
                BaseTVActivity.C22336.lambda$onResponse$0(this.f$0, realm);
                break;
            case 9:
                BaseTVActivity.C22347.lambda$onResponse$0(this.f$0, realm);
                break;
            case 10:
                BaseTVActivity.C22358.lambda$onResponse$0(this.f$0, realm);
                break;
            default:
                BaseTVActivity.C22369.lambda$onResponse$0(this.f$0, realm);
                break;
        }
    }
}
