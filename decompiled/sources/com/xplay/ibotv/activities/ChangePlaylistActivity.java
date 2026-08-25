package com.xplay.ibotv.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.recyclerview.widget.RecyclerView;
import com.boxbrapks.BxAppSecurity;
import com.xplay.ibotv.adapter.PortalRecyclerAdapter;
import com.xplay.ibotv.apps.BaseActivity;
import com.xplay.ibotv.apps.Constants;
import com.xplay.ibotv.dlgfragment.AddPlaylistDlgFragment;
import com.xplay.ibotv.dlgfragment.ConnectDlgFragment;
import com.xplay.ibotv.dlgfragment.ExitDlgFragment;
import com.xplay.ibotv.dlgfragment.NoConnectionDlgFragment;
import com.xplay.ibotv.helper.PreferenceHelper;
import com.xplay.ibotv.models.AppInfoModel;
import com.xplay.ibotv.models.WordModels;
import com.xplay.ibotv.remote.GetDataRequest;
import com.xplay.ibotv.view.LiveVerticalGridView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import kotlin.Unit;
import org.json.JSONObject;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;
import p030.p031.p032.p033.p034.p035.C2859;
import pl.droidsonroids.gif.GifImageView;

/* JADX INFO: loaded from: classes3.dex */
public class ChangePlaylistActivity extends BaseActivity implements GetDataRequest.OnGetResponseListener {
    public String BoxBRURL;
    public AddPlaylistDlgFragment addPlaylistDlgFragment;
    public AppInfoModel appInfoModel;
    public Button btn_pay;
    public Button btn_web_site;
    public ConnectDlgFragment connectDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public ImageView image_qr;
    public NoConnectionDlgFragment noConnectionDlgFragment;
    public TextView notiContent;
    public TextView notiTitle;
    public PortalRecyclerAdapter portalAdapter;
    public PreferenceHelper preferenceHelper;
    public GifImageView progress_bar;
    public LiveVerticalGridView recycler_playlist;
    public TextView str_device_key;
    public TextView str_mac_address;
    public TextView str_mac_trial;
    public TextView str_scan_code;
    public TextView str_trial;
    public TextView str_upload;
    public TextView txt_description;
    public TextView txt_device_key;
    public TextView txt_mac_address;
    public TextView txt_version;
    public SimpleDateFormat expire_format = new SimpleDateFormat(C2859.m2370("ScKit-e01fcb47af844c74630193d4b6b7baad", "ScKit-b69148a81b48b4bb"));
    public long expired_mils = 0;
    public List<AppInfoModel.UrlModel> urlModelList = new ArrayList();
    public WordModels wordModels = new WordModels();
    public int playlist_position = 0;
    public int focused_position = 0;
    public AppInfoModel.UrlModel selectedModel = null;
    public boolean is_home = false;

    /* JADX INFO: renamed from: com.xplay.ibotv.activities.ChangePlaylistActivity$1 */
    /* JADX INFO: loaded from: classes.dex */
    public class C21231 implements ExitDlgFragment.OkButtonClickListener {
        public final /* synthetic */ ExitDlgFragment val$dlgFragment;
        public final /* synthetic */ AppInfoModel.UrlModel val$selectedModel;

        public C21231(ExitDlgFragment exitDlgFragment, AppInfoModel.UrlModel urlModel) {
            this.val$dlgFragment = exitDlgFragment;
            this.val$selectedModel = urlModel;
        }

        @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
        public void onCancelClick() {
        }

        @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
        public void onOkClick() {
            this.val$dlgFragment.dismiss();
            String deleteData = BxAppSecurity.getDeleteData(ChangePlaylistActivity.this.preferenceHelper.getSharedPreferenceMacAddress().toLowerCase(), this.val$selectedModel.getId());
            GetDataRequest getDataRequest = new GetDataRequest(ChangePlaylistActivity.this, 2000);
            getDataRequest.getResponse(BxAppSecurity.getJsonData(deleteData), Constants.second_delete_url);
            getDataRequest.setOnGetResponseListener(ChangePlaylistActivity.this);
        }
    }

    /* JADX INFO: renamed from: com.xplay.ibotv.activities.ChangePlaylistActivity$2 */
    /* JADX INFO: loaded from: classes.dex */
    public class C21242 implements AddPlaylistDlgFragment.SuccessAddedListener {
        public C21242() {
        }

        @Override // com.xplay.ibotv.dlgfragment.AddPlaylistDlgFragment.SuccessAddedListener
        public void onReload(int i) {
            ChangePlaylistActivity changePlaylistActivity = ChangePlaylistActivity.this;
            changePlaylistActivity.is_home = false;
            changePlaylistActivity.addPlaylistDlgFragment.dismiss();
            ChangePlaylistActivity.this.changePlaylistView();
        }

        @Override // com.xplay.ibotv.dlgfragment.AddPlaylistDlgFragment.SuccessAddedListener
        public void onSkip() {
            ChangePlaylistActivity changePlaylistActivity = ChangePlaylistActivity.this;
            changePlaylistActivity.is_home = false;
            changePlaylistActivity.addPlaylistDlgFragment.dismiss();
            ChangePlaylistActivity.this.changePlaylistView();
        }
    }

    /* JADX INFO: renamed from: com.xplay.ibotv.activities.ChangePlaylistActivity$3 */
    /* JADX INFO: loaded from: classes.dex */
    public class C21253 extends OnChildViewHolderSelectedListener {
        public final /* synthetic */ View[] val$previousSelectedView0;

        public C21253(View[] viewArr) {
            this.val$previousSelectedView0 = viewArr;
        }

        @Override // androidx.leanback.widget.OnChildViewHolderSelectedListener
        public void onChildViewHolderSelected(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, int i2) {
            super.onChildViewHolderSelected(recyclerView, viewHolder, i, i2);
            View[] viewArr = this.val$previousSelectedView0;
            if (viewArr[0] != null) {
                viewArr[0].setSelected(false);
                View[] viewArr2 = this.val$previousSelectedView0;
                viewArr2[0] = viewHolder.itemView;
                viewArr2[0].setSelected(true);
            }
        }
    }

    /* JADX INFO: renamed from: com.xplay.ibotv.activities.ChangePlaylistActivity$4 */
    /* JADX INFO: loaded from: classes.dex */
    public class C21264 implements ExitDlgFragment.OkButtonClickListener {
        public C21264() {
        }

        @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
        public void onCancelClick() {
        }

        @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
        public void onOkClick() {
            ChangePlaylistActivity.this.finishAffinity();
            System.exit(0);
        }
    }

    static {
        pro.classes3Init0(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public native void changePlaylistView();

    private native void checkDescription();

    private native void deletePlaylist(AppInfoModel.UrlModel urlModel);

    private native void initView();

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initView$3(View view) {
        try {
            Intent intent = new Intent(C2859.m2370("ScKit-2203bf7f30c416c440b095828ceaca560ec5536de843f84c61cc0ccfbb444684", "ScKit-43372371bda458eb"), Uri.parse(""));
            intent.addFlags(276856832);
            startActivity(intent);
        } catch (Exception unused) {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ Unit lambda$onCreate$0(AppInfoModel.UrlModel urlModel, Integer num, Boolean bool) {
        if (!bool.booleanValue()) {
            this.focused_position = num.intValue();
            return null;
        }
        if (num.intValue() == this.urlModelList.size()) {
            showAddPlaylistDlgFragment(-1);
            return null;
        }
        this.selectedModel = urlModel;
        if (urlModel.getId().equalsIgnoreCase(C2859.m2370("ScKit-209181e8a5c0249d8165f5775e0383fa", "ScKit-43372371bda458eb"))) {
            loadingData(this.selectedModel);
            return null;
        }
        showConnectDlgFragment(this.selectedModel, num.intValue());
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showConnectDlgFragment$1(int i, AppInfoModel.UrlModel urlModel, int i2) {
        if (1 == 0) {
            this.playlist_position = 1;
            loadingData(urlModel);
            return;
        }
        String strM2370 = C2859.m2370("ScKit-a4d658114e4a64a05b939894a731f2f6", "ScKit-43372371bda458eb");
        if (1 == 1) {
            if (urlModel.getIs_protected().equalsIgnoreCase(strM2370)) {
                Toast.makeText(this, this.wordModels.getPlaylist_protected(), 0).show();
                return;
            } else {
                showAddPlaylistDlgFragment(1);
                return;
            }
        }
        if (1 != 2) {
            return;
        }
        if (urlModel.getIs_protected().equalsIgnoreCase(strM2370)) {
            Toast.makeText(this, this.wordModels.getPlaylist_protected(), 0).show();
        } else {
            deletePlaylist(urlModel);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNoConnectionDlgFragment$2() {
        this.playlist_position = -1;
    }

    private native void loadingData(AppInfoModel.UrlModel urlModel);

    private native void showAddPlaylistDlgFragment(int i);

    private native void showConnectDlgFragment(AppInfoModel.UrlModel urlModel, int i);

    private native void showExitDlgFragment();

    private native void showNoConnectionDlgFragment();

    @Override // com.xplay.ibotv.remote.GetDataRequest.OnGetResponseListener
    public native void OnGetResponseResult(JSONObject jSONObject, int i);

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public native boolean dispatchKeyEvent(KeyEvent keyEvent);

    @Override // com.xplay.ibotv.apps.BaseActivity
    public final native void doNextTask(boolean z);

    @Override // com.xplay.ibotv.apps.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final native void onCreate(Bundle bundle);
}
