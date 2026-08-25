package com.xplay.ibotv.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Process;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.boxbrapks.BxLoginActivity;
import com.tobi.playx.R;
import com.xplay.ibotv.MainActivity;
import com.xplay.ibotv.activities.mobile.LiveMobileActivity;
import com.xplay.ibotv.apps.BaseActivity;
import com.xplay.ibotv.apps.Constants;
import com.xplay.ibotv.apps.HomeType;
import com.xplay.ibotv.apps.LTVApp;
import com.xplay.ibotv.dlgfragment.AccountDlgFragment;
import com.xplay.ibotv.dlgfragment.ExitDlgFragment;
import com.xplay.ibotv.dlgfragment.NoConnectionDlgFragment;
import com.xplay.ibotv.helper.GetSharedInfo;
import com.xplay.ibotv.helper.PreferenceHelper;
import com.xplay.ibotv.models.AppInfoModel;
import com.xplay.ibotv.models.LoginModel;
import com.xplay.ibotv.models.WordModels;
import com.xplay.ibotv.utils.Utils;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import p029zz.XplayComboView;
import p029zz.XplaySports;
import p030.p031.p032.p033.p034.p035.C2859;
import pl.droidsonroids.gif.GifImageView;

/* JADX INFO: loaded from: classes.dex */
public class HomeActivity extends BaseActivity implements View.OnClickListener {
    public String BoxBRURL;
    public AccountDlgFragment accountDlgFragment;
    public ExitDlgFragment exitDlgFragment;
    public ConstraintLayout ly_account;
    public ConstraintLayout ly_change;
    public ConstraintLayout ly_exit;
    public ConstraintLayout ly_live;
    public ConstraintLayout ly_movie;
    public ConstraintLayout ly_reload;
    public ConstraintLayout ly_series;
    public ConstraintLayout ly_setting;
    public NoConnectionDlgFragment noConnectionDlgFragment;
    public PreferenceHelper preferenceHelper;
    public GifImageView progressBar;
    public TextView txt_account;
    public TextView txt_change;
    public TextView txt_exit;
    public TextView txt_live;
    public TextView txt_movie;
    public TextView txt_reload;
    public TextView txt_series;
    public TextView txt_setting;
    public TextView txt_time;
    public TextView txt_version;
    public WordModels wordModels = new WordModels();
    public ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new HomeActivity$$ExternalSyntheticLambda0(this));

    private void changeStringsInApp() {
        WordModels wordModel = GetSharedInfo.getWordModel(this);
        this.wordModels = wordModel;
        this.txt_live.setText(wordModel.getLive_tv());
        this.txt_movie.setText(this.wordModels.getMovies());
        this.txt_series.setText(this.wordModels.getSeries());
        this.txt_account.setText(this.wordModels.getAccount());
        this.txt_change.setText(this.wordModels.getChange_playlist());
        this.txt_setting.setText(this.wordModels.getSettings());
        this.txt_reload.setText(this.wordModels.getReload_portal());
        this.txt_exit.setText(this.wordModels.getExit());
    }

    public static void copyBxLoginToXp(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("BxLoginPref", 0);
        String string = sharedPreferences.getString("BX.USERNAME", "");
        String string2 = sharedPreferences.getString("BX.PASSWORD", "");
        SharedPreferences.Editor editorEdit = context.getSharedPreferences("XpLoginPref", 0).edit();
        editorEdit.putString("BX.USERNAME", string);
        editorEdit.putString("BX.PASSWORD", string2);
        editorEdit.apply();
    }

    private String getCurrentPlaylistExpiredDate() {
        LoginModel sharedPreferenceLoginModel;
        boolean sharedPreferenceISM3U = this.preferenceHelper.getSharedPreferenceISM3U();
        String strM2370 = C2859.m2370("ScKit-4068cd00fde9ff493fda707087f6ca2e", "ScKit-db84906f9cf1430a");
        return (sharedPreferenceISM3U || (sharedPreferenceLoginModel = this.preferenceHelper.getSharedPreferenceLoginModel()) == null) ? strM2370 : Utils.getDate(sharedPreferenceLoginModel.getExp_date());
    }

    private void initView() {
        this.txt_time = (TextView) findViewById(R.id.txt_time);
        this.ly_live = (ConstraintLayout) findViewById(R.id.ly_live);
        this.ly_movie = (ConstraintLayout) findViewById(R.id.ly_movie);
        this.ly_series = (ConstraintLayout) findViewById(R.id.ly_series);
        this.ly_account = (ConstraintLayout) findViewById(R.id.ly_account);
        this.ly_change = (ConstraintLayout) findViewById(R.id.ly_change);
        this.ly_setting = (ConstraintLayout) findViewById(R.id.ly_setting);
        this.ly_reload = (ConstraintLayout) findViewById(R.id.ly_reload);
        this.ly_exit = (ConstraintLayout) findViewById(R.id.ly_exit);
        this.txt_live = (TextView) findViewById(R.id.txt_live);
        this.txt_movie = (TextView) findViewById(R.id.txt_movie);
        this.txt_series = (TextView) findViewById(R.id.txt_series);
        this.txt_account = (TextView) findViewById(R.id.txt_account);
        this.txt_change = (TextView) findViewById(R.id.txt_change);
        this.txt_setting = (TextView) findViewById(R.id.txt_setting);
        this.txt_reload = (TextView) findViewById(R.id.txt_reload);
        this.txt_exit = (TextView) findViewById(R.id.txt_exit);
        this.txt_version = (TextView) findViewById(R.id.txt_version);
        this.progressBar = (GifImageView) findViewById(R.id.progress_bar);
        this.ly_live.setOnClickListener(this);
        this.ly_movie.setOnClickListener(this);
        this.ly_series.setOnClickListener(this);
        this.ly_account.setOnClickListener(this);
        this.ly_change.setOnClickListener(this);
        this.ly_setting.setOnClickListener(this);
        this.ly_reload.setOnClickListener(this);
        this.ly_exit.setOnClickListener(this);
    }

    public static void initializeAgent(Context context) {
        MainActivity.f307UA = new PreferenceHelper(context).getSharedPreferenceAppInfo().getAgent();
    }

    public static void initializeApikey(Context context) {
        XplayComboView.TMDB = new PreferenceHelper(context).getSharedPreferenceAppInfo().getApiKey();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(ActivityResult activityResult) {
        if (activityResult.getResultCode() != -1 || activityResult.getData() == null) {
            return;
        }
        changeStringsInApp();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showAccountDlgFragment$1() {
        this.accountDlgFragment.dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$showNoConnectionDlgFragment$2() {
        Intent intent = new Intent(this, (Class<?>) ChangePlaylistActivity.class);
        intent.putExtra(C2859.m2370("ScKit-c9d5300644245fcc6d079ad88d5bc6fb", "ScKit-db84906f9cf1430a"), false);
        startActivity(intent);
        finish();
    }

    private void reloadPortal() {
        this.progressBar.setVisibility(0);
        int playlistPosition = GetSharedInfo.getPlaylistPosition(this);
        AppInfoModel sharedPreferenceAppInfo = this.preferenceHelper.getSharedPreferenceAppInfo();
        if (sharedPreferenceAppInfo.getResult().size() > 0) {
            sharedPreferenceAppInfo.getResult().get(playlistPosition);
            this.preferenceHelper.setSharedPreferenceLastPlaylistDate(0L);
            if (this.BoxBRURL.contains(C2859.m2370("ScKit-aa26f439d66e59eaec78cb7efdb6f648", "ScKit-db84906f9cf1430a"))) {
                this.preferenceHelper.setSharedPreferenceISM3U(false);
                goToLogin(this.BoxBRURL, this.wordModels);
            } else if (GetSharedInfo.checkXUILink(this.BoxBRURL)) {
                this.preferenceHelper.setSharedPreferenceISM3U(false);
                goToXUILogin(this.BoxBRURL, this.wordModels);
            } else {
                this.preferenceHelper.setSharedPreferenceISM3U(true);
                reloadM3UData(this.BoxBRURL, this.wordModels);
            }
        }
    }

    private void showAccountDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        String strM2370 = C2859.m2370("ScKit-249aa24bb92c7cdb844c27170747169b1a26c7b6fbe04cfb6dc7e25401bdf65d", "ScKit-db84906f9cf1430a");
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(strM2370);
        if (fragmentFindFragmentByTag != null) {
            Insets$$ExternalSyntheticOutline0.m33m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        AccountDlgFragment accountDlgFragmentNewInstance = AccountDlgFragment.newInstance(this);
        this.accountDlgFragment = accountDlgFragmentNewInstance;
        accountDlgFragmentNewInstance.setOnPayButtonClickListener(new HomeActivity$$ExternalSyntheticLambda0(this));
        this.accountDlgFragment.show(supportFragmentManager, strM2370);
    }

    private void showExitDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        String strM2370 = C2859.m2370("ScKit-1bd516036d0eff3f20a7ee79d0753196", "ScKit-db84906f9cf1430a");
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(strM2370);
        if (fragmentFindFragmentByTag != null) {
            Insets$$ExternalSyntheticOutline0.m33m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        ExitDlgFragment exitDlgFragmentNewInstance = ExitDlgFragment.newInstance(this.wordModels.getExit(), this.wordModels.getExit_description(), this.wordModels.getStr_yes(), this.wordModels.getNo());
        this.exitDlgFragment = exitDlgFragmentNewInstance;
        exitDlgFragmentNewInstance.setOkButtonClickListener(new ExitDlgFragment.OkButtonClickListener() { // from class: com.xplay.ibotv.activities.HomeActivity.1
            @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onCancelClick() {
            }

            @Override // com.xplay.ibotv.dlgfragment.ExitDlgFragment.OkButtonClickListener
            public void onOkClick() {
                HomeActivity.this.finishAffinity();
                System.exit(0);
            }
        });
        this.exitDlgFragment.show(supportFragmentManager, strM2370);
    }

    private void showNoConnectionDlgFragment() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransactionBeginTransaction = supportFragmentManager.beginTransaction();
        String strM2370 = C2859.m2370("ScKit-db249289988b6c0aeb2aefebf6684a3c9730633fa6e295a999b7f78f937becb2", "ScKit-db84906f9cf1430a");
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(strM2370);
        if (fragmentFindFragmentByTag != null) {
            Insets$$ExternalSyntheticOutline0.m33m(fragmentTransactionBeginTransaction, fragmentFindFragmentByTag, (String) null);
            return;
        }
        NoConnectionDlgFragment noConnectionDlgFragmentNewInstance = NoConnectionDlgFragment.newInstance(this, this.wordModels.getPlaylist_is_not_working());
        this.noConnectionDlgFragment = noConnectionDlgFragmentNewInstance;
        noConnectionDlgFragmentNewInstance.setOnRetryClickListener(new HomeActivity$$ExternalSyntheticLambda0(this));
        this.noConnectionDlgFragment.show(supportFragmentManager, strM2370);
    }

    private void showWaitToast() {
        Toast.makeText(this, this.wordModels.getPlaylist_is_loading(), 0).show();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.core.app.ComponentActivity, android.app.Activity, android.view.Window.Callback
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getAction() != 0 || keyEvent.getKeyCode() != 4) {
            return super.dispatchKeyEvent(keyEvent);
        }
        showExitDlgFragment();
        return true;
    }

    @Override // com.xplay.ibotv.apps.BaseActivity
    public final void doNextTask(boolean z) {
        if (!z) {
            this.progressBar.setVisibility(8);
            showNoConnectionDlgFragment();
        } else {
            this.preferenceHelper.setSharedPreferenceLastPlaylistDate(System.currentTimeMillis() / 1000);
            this.progressBar.setVisibility(8);
            Toast.makeText(this, this.wordModels.getPortal_loaded_successfully(), 0).show();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ly_account /* 2131427885 */:
                startActivity(new Intent(this, (Class<?>) XplaySports.class));
                break;
            case R.id.ly_change /* 2131427893 */:
                setStop(true);
                SharedPreferences pref = getSharedPreferences(BxLoginActivity.BX_LOGIN_PREF, 0);
                pref.edit().clear().commit();
                Toast.makeText(this, C2859.m2370("ScKit-2900c447518367fe023f8abd34f8e80c5011aa760cc7d6f450b36c4a795854bf549b1715300021b22688046dccb674a7", "ScKit-db84906f9cf1430a"), 1);
                Intent intent = new Intent(this, (Class<?>) BxLoginActivity.class);
                intent.addFlags(67108864);
                intent.putExtra(C2859.m2370("ScKit-f77026f754f65da9e5eb974beae67d8b", "ScKit-63eafea907d6f67a"), true);
                startActivity(intent);
                finish();
                break;
            case R.id.ly_exit /* 2131427898 */:
                showExitDlgFragment();
                break;
            case R.id.ly_live /* 2131427903 */:
                if (this.progressBar.getVisibility() == 0) {
                    showWaitToast();
                } else if (this.preferenceHelper.getSharedPreferenceIsGrid()) {
                    LTVApp.homeType = HomeType.live;
                    startActivity(new Intent(this, (Class<?>) CategoryActivity.class));
                } else if (!GetSharedInfo.isTVDevice(this)) {
                    startActivity(new Intent(this, (Class<?>) LiveMobileActivity.class));
                } else {
                    startActivity(new Intent(this, (Class<?>) LiveActivity.class));
                }
                break;
            case R.id.ly_movie /* 2131427906 */:
                if (this.progressBar.getVisibility() == 0) {
                    showWaitToast();
                } else if (!this.preferenceHelper.getSharedPreferenceIsGrid()) {
                    startActivity(new Intent(this, (Class<?>) MovieActivity.class));
                } else {
                    LTVApp.homeType = HomeType.movies;
                    startActivity(new Intent(this, (Class<?>) CategoryActivity.class));
                }
                break;
            case R.id.ly_reload /* 2131427909 */:
                if (this.progressBar.getVisibility() != 0) {
                    reloadPortal();
                } else {
                    showWaitToast();
                }
                break;
            case R.id.ly_series /* 2131427913 */:
                if (this.progressBar.getVisibility() == 0) {
                    showWaitToast();
                } else if (!this.preferenceHelper.getSharedPreferenceIsGrid()) {
                    startActivity(new Intent(this, (Class<?>) SeriesActivity.class));
                } else {
                    LTVApp.homeType = HomeType.series;
                    startActivity(new Intent(this, (Class<?>) CategoryActivity.class));
                }
                break;
            case R.id.ly_setting /* 2131427914 */:
                if (this.progressBar.getVisibility() != 0) {
                    this.someActivityResultLauncher.launch(new Intent(this, (Class<?>) SettingActivity.class));
                } else {
                    showWaitToast();
                }
                break;
        }
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.xplay.ibotv.activities.HomeActivity$2] */
    @Override // com.xplay.ibotv.apps.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) throws PackageManager.NameNotFoundException {
        int i;
        super.onCreate(bundle);
        initializeApikey(this);
        initializeAgent(this);
        copyBxLoginToXp(this);
        new Object() { // from class: com.xplay.ibotv.activities.HomeActivity.2
            private String SHA = Constants.sha();

            public String bytes_To_Hex_(byte[] bArr) {
                StringBuilder sb = new StringBuilder(bArr.length * 2);
                for (byte b2 : bArr) {
                    int i2 = b2 & 255;
                    sb.append("0123456789ABCDEF".charAt(i2 >> 4));
                    sb.append("0123456789ABCDEF".charAt(i2 & 15));
                }
                return sb.toString();
            }

            public void check(Activity activity) {
                if (getSha(activity).equals(this.SHA)) {
                    return;
                }
                end(activity);
            }

            public void end(Activity activity) {
                if (activity != null) {
                    try {
                        activity.finishAffinity();
                        Process.killProcess(Process.myPid());
                    } catch (Exception unused) {
                    }
                }
            }

            public String getSHA1_(byte[] bArr) {
                try {
                    MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
                    messageDigest.update(bArr);
                    return bytes_To_Hex_(messageDigest.digest());
                } catch (NoSuchAlgorithmException unused) {
                    return "";
                }
            }

            public String getSha(Context context) {
                try {
                    Signature[] signatureArr = context.getPackageManager().getPackageInfo(context.getPackageName(), 64).signatures;
                    return signatureArr.length > 0 ? getSHA1_(signatureArr[0].toByteArray()) : "";
                } catch (PackageManager.NameNotFoundException unused) {
                    return "";
                }
            }
        }.check(this);
        String theme = new PreferenceHelper(this).getSharedPreferenceAppInfo().getTheme();
        if (theme.equals(C2859.m2370("ScKit-54bd7e4bbcd4b9b9d29e6f4d404ade9f", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home;
        } else if (theme.equals(C2859.m2370("ScKit-247cd9a8830ee16389f540a1665390b7", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home_1;
        } else if (theme.equals(C2859.m2370("ScKit-547f459d48c229849a248e148847005a", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home_2;
        } else if (theme.equals(C2859.m2370("ScKit-2703ef6ebdb0ed960de6f28ebcc4edb9", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home_3;
        } else if (theme.equals(C2859.m2370("ScKit-63af790b1d2f2398e0353af2b2deb2ba", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home_4;
        } else if (theme.equals(C2859.m2370("ScKit-e61bab24c26e42146c21e56aeabead86", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home_5;
        } else if (theme.equals(C2859.m2370("ScKit-04e650d948aaf44a0cadbdc9c6e5dc51", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home_6;
        } else if (theme.equals(C2859.m2370("ScKit-be88bcee8d0cac47af32729b936e4467", "ScKit-63eafea907d6f67a"))) {
            i = R.layout.activity_home_7;
        } else {
            i = !theme.equals(C2859.m2370("ScKit-a2b09b5222b0afeaa879a64867026808", "ScKit-63eafea907d6f67a")) ? R.layout.activity_home : R.layout.activity_home_8;
        }
        setContentView(i);
        Utils.FullScreenCall(this);
        StringBuilder sb = new StringBuilder();
        SharedPreferences sharedPreferences = getSharedPreferences(BxLoginActivity.BX_LOGIN_PREF, 0);
        sb.append(sharedPreferences.getString(BxLoginActivity.BX_AUTH_URL, ""));
        sb.append(C2859.m2370("ScKit-940bfda41320d97f7ecb75d160ed1b4680362c0a413a997b8d6590b6b9c26329", "ScKit-63eafea907d6f67a"));
        sb.append(sharedPreferences.getString(BxLoginActivity.BX_USERNAME, ""));
        sb.append(C2859.m2370("ScKit-e2ebd39a88ec60400021b90911b4651c", "ScKit-63eafea907d6f67a"));
        sb.append(sharedPreferences.getString(BxLoginActivity.BX_PASSWORD, ""));
        sb.append(C2859.m2370("ScKit-2a019567b5eb64feaf8438099b17760b37056e99accb1a9ea76915c4cfb8d7e1", "ScKit-63eafea907d6f67a"));
        this.BoxBRURL = sb.toString();
        this.preferenceHelper = new PreferenceHelper(this);
        initView();
        changeStringsInApp();
        this.txt_time.setText(this.wordModels.getCurrent_expired() + C2859.m2370("ScKit-22b66fcfffd42c077d648b4b125560e0", "ScKit-63eafea907d6f67a") + getCurrentPlaylistExpiredDate());
        LTVApp.instance.versionCheck();
        LTVApp.instance.loadVersion();
        LTVApp.instance.loadApk();
        TextView textView = this.txt_version;
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(C2859.m2370("ScKit-d3ff2122ecaea3e0973e477ed7ca9001", "ScKit-63eafea907d6f67a"));
        sbM26m.append(LTVApp.version_name);
        textView.setText(sbM26m.toString());
        this.ly_live.requestFocus();
    }
}
