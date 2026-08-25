package com.xplay.ibotv.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import com.xplay.ibotv.dlgfragment.AccountDlgFragment;
import com.xplay.ibotv.dlgfragment.NoConnectionDlgFragment;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class HomeActivity$$ExternalSyntheticLambda0 implements NoConnectionDlgFragment.OnRetryClickListener, ActivityResultCallback, AccountDlgFragment.PayButtonClickListener {
    public final /* synthetic */ HomeActivity f$0;

    public /* synthetic */ HomeActivity$$ExternalSyntheticLambda0(HomeActivity homeActivity) {
        this.f$0 = homeActivity;
    }

    @Override // androidx.activity.result.ActivityResultCallback
    public final void onActivityResult(Object obj) {
        this.f$0.lambda$new$0((ActivityResult) obj);
    }

    @Override // com.xplay.ibotv.dlgfragment.AccountDlgFragment.PayButtonClickListener
    public final void onPayButtonClicked() {
        this.f$0.lambda$showAccountDlgFragment$1();
    }

    @Override // com.xplay.ibotv.dlgfragment.NoConnectionDlgFragment.OnRetryClickListener
    public final void onRetryClick() {
        this.f$0.lambda$showNoConnectionDlgFragment$2();
    }
}
