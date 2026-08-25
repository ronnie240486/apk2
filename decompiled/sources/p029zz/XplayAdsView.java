package p029zz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.webkit.WebSettings;
import android.webkit.WebView;
import com.xplay.ibotv.apps.Constants;
import com.xplay.ibotv.helper.PreferenceHelper;
import com.xplay.ibotv.models.AppInfoModel;

/* JADX INFO: loaded from: classes3.dex */
public class XplayAdsView extends WebView {
    public XplayAdsView(Context context) {
        super(context);
        init();
    }

    public XplayAdsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public XplayAdsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(0);
        setFocusable(false);
        setClickable(false);
        setLayerType(2, null);
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUserAgentString(Constants.m297ua());
        AppInfoModel sharedPreferenceAppInfo = new PreferenceHelper(getContext()).getSharedPreferenceAppInfo();
        if (checkConnection()) {
            loadUrl(sharedPreferenceAppInfo.getAds());
        } else {
            loadUrl("file:///android_asset/offline.html");
        }
    }

    private boolean checkConnection() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }
}
