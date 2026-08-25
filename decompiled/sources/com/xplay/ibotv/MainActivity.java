package com.xplay.ibotv;

import android.app.ActivityManager;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import androidx.annotation.RequiresApi;
import com.google.gson.reflect.TypeToken;
import com.xplay.ibotv.apps.BaseActivity;
import com.xplay.ibotv.dlgfragment.DescriptionDlgFragment;
import com.xplay.ibotv.dlgfragment.ExitDlgFragment;
import com.xplay.ibotv.helper.PreferenceHelper;
import com.xplay.ibotv.models.AppInfoModel;
import com.xplay.ibotv.models.WordModels;
import com.xplay.ibotv.remote.GetDataRequest;
import java.util.List;
import org.json.JSONObject;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;
import p030.p031.p032.p033.p034.p035.C2859;
import pl.droidsonroids.gif.GifImageView;

/* JADX INFO: loaded from: classes2.dex */
public class MainActivity extends BaseActivity implements GetDataRequest.OnGetResponseListener {

    /* JADX INFO: renamed from: UA */
    public static String f307UA;
    public String BoxBRURL;
    public AppInfoModel appInfoModel;
    public AppInfoModel.UrlModel currentUrlModel;
    public DescriptionDlgFragment descriptionDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public GifImageView image_loader;
    public PreferenceHelper preferenceHelper;
    public String subscription = "";
    public String description = "";
    public WordModels wordModels = new WordModels();
    public int failed_count = 0;
    public int playlist_position = 0;
    public String device_type = C2859.m2370("ScKit-644edd31827da77094d2e966ca273c1d", "ScKit-65df115d343b2f65");

    /* JADX INFO: renamed from: com.xplay.ibotv.MainActivity$1 */
    /* JADX INFO: loaded from: classes.dex */
    public class C21021 extends TypeToken<AppInfoModel> {
    }

    /* JADX INFO: renamed from: com.xplay.ibotv.MainActivity$2 */
    /* JADX INFO: loaded from: classes.dex */
    public class C21032 implements DescriptionDlgFragment.ButtonClickListener {
        public final /* synthetic */ int val$playlist_size;

        public C21032(int i) {
            this.val$playlist_size = i;
        }

        @Override // com.xplay.ibotv.dlgfragment.DescriptionDlgFragment.ButtonClickListener
        public void onCancelClick() {
            MainActivity.this.finishApp();
        }

        @Override // com.xplay.ibotv.dlgfragment.DescriptionDlgFragment.ButtonClickListener
        public void onContinueClick() {
            if (this.val$playlist_size == -1) {
                MainActivity.this.goToWebsite();
            } else {
                MainActivity.this.loadingData();
            }
        }
    }

    /* JADX INFO: renamed from: com.xplay.ibotv.MainActivity$3 */
    /* JADX INFO: loaded from: classes.dex */
    public class C21043 implements ExitDlgFragment.OkButtonClickListener {
        public C21043() {
        }

        @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
        public void onCancelClick() {
        }

        @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
        public void onOkClick() {
            List<ActivityManager.AppTask> appTasks;
            System.exit(0);
            Process.killProcess(Process.myPid());
            ActivityManager activityManager = (ActivityManager) MainActivity.this.getSystemService("activity");
            if (activityManager != null && (appTasks = activityManager.getAppTasks()) != null && appTasks.size() > 0) {
                appTasks.get(0).setExcludeFromRecents(true);
            }
            MainActivity.this.finishAndRemoveTask();
        }
    }

    static {
        pro.classes2Init0(3);
    }

    @RequiresApi(api = 23)
    private native void CheckSDK23Permission();

    @RequiresApi(api = 23)
    private native boolean addPermission(List<String> list, String str);

    private native void checkAppInfoModel(AppInfoModel appInfoModel);

    private native void checkLocalStorageAccount();

    /* JADX INFO: Access modifiers changed from: private */
    public native void finishApp();

    private native void getMacAddress();

    private native void getUserInfoModel();

    /* JADX INFO: Access modifiers changed from: private */
    public native void goToWebsite();

    /* JADX INFO: Access modifiers changed from: private */
    public native void loadingData();

    private native void showDescriptionDlgFragment(String str, String str2, int i);

    private native void showExitDlgFragment();

    @Override // com.xplay.ibotv.remote.GetDataRequest.OnGetResponseListener
    public native void OnGetResponseResult(JSONObject jSONObject, int i);

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public native boolean dispatchKeyEvent(KeyEvent keyEvent);

    @Override // com.xplay.ibotv.apps.BaseActivity
    public final native void doNextTask(boolean z);

    @Override // com.xplay.ibotv.apps.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final native void onCreate(Bundle bundle);

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public native void onRequestPermissionsResult(int i, String[] strArr, int[] iArr);
}
