package com.xplay.ibotv.apps;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import androidx.multidex.MultiDexApplication;
import com.xplay.ibotv.helper.PreferenceHelper;
import com.xplay.ibotv.models.AppInfoModel;
import com.xplay.ibotv.models.CategoryModel;
import iptv.m3u.parser.M3UItem;
import java.util.ArrayList;
import java.util.List;
import p029zz.DownloadImageTask;
import p030.p031.p032.p033.p034.p035.C2859;

/* JADX INFO: loaded from: classes3.dex */
public class LTVApp extends MultiDexApplication {
    public static float SCREEN_HEIGHT = 1080.0f;
    public static float SCREEN_WIDTH = 1920.0f;
    public static long SEVER_OFFSET;
    public static String channelName;
    public static LTVApp instance;

    /* JADX INFO: renamed from: P */
    public AppInfoModel f310P;
    public static List<CategoryModel> vod_categories_filter = new ArrayList();
    public static List<CategoryModel> live_categories_filter = new ArrayList();
    public static List<CategoryModel> series_categories_filter = new ArrayList();
    public static String version_name = C2859.m2370("ScKit-a659d8e0a0a99e5d23afe7eab72631eb", "ScKit-0d8cb60c470737d1");
    public static HomeType homeType = HomeType.live;
    private List<M3UItem> m3UChannelsItems = new ArrayList();
    private List<M3UItem> m3UVideosItems = new ArrayList();
    private List<M3UItem> m3USeriesItems = new ArrayList();

    public static LTVApp getInstance() {
        if (instance == null) {
            instance = new LTVApp();
        }
        return instance;
    }

    public List<M3UItem> getM3UChannelsItems() {
        return this.m3UChannelsItems;
    }

    public List<M3UItem> getM3USeriesItems() {
        return this.m3USeriesItems;
    }

    public List<M3UItem> getM3UVideosItems() {
        return this.m3UVideosItems;
    }

    public String getM3uDate() {
        return new PreferenceHelper(this).getSharedPreferenceLastM3uDate();
    }

    public void getNavigationBarHeight(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
        float f = displayMetrics.heightPixels;
        SCREEN_HEIGHT = f;
        float f2 = displayMetrics.widthPixels;
        SCREEN_WIDTH = f2;
        if (f2 < f) {
            SCREEN_WIDTH = f;
            SCREEN_HEIGHT = f2;
        }
    }

    public void loadApk() {
        String str = Constants.check;
        if (str == null || !str.contains("djb")) {
            System.exit(0);
        }
    }

    public void loadVersion() throws PackageManager.NameNotFoundException {
        PackageManager packageManager = getPackageManager();
        String packageName = getPackageName();
        PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
        if (!packageName.equals(Constants.m294pk())) {
            System.exit(0);
        }
        if (packageInfo != null) {
            version_name = packageInfo.versionName;
        }
    }

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public void setM3UChannelsItems(List<M3UItem> list) {
        this.m3UChannelsItems = list;
    }

    public void setM3USeriesItems(List<M3UItem> list) {
        this.m3USeriesItems = list;
    }

    public void setM3UVideosItems(List<M3UItem> list) {
        this.m3UVideosItems = list;
    }

    public void setM3uDate(String str) {
        new PreferenceHelper(this).setSharedPreferenceLastM3uDate(str);
    }

    public void versionCheck() {
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build());
    }

    public void downImage() {
        this.f310P = new PreferenceHelper(this).getSharedPreferenceAppInfo();
        new DownloadImageTask(this, buildUrls()).execute(new Void[0]);
    }

    private String[] buildUrls() {
        return new String[]{this.f310P.getLogo(), this.f310P.getBG()};
    }
}
