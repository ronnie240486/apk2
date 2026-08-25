package p029zz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/* JADX INFO: loaded from: classes.dex */
public class XplayComboView extends WebView {
    public static String TMDB;

    private void init() {
        setBackgroundColor(0);
        setFocusable(false);
        setClickable(false);
        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient());
        if (checkConnection()) {
            loadUrl("file:///android_asset/ads/combo.html?url=".concat(TMDB));
        } else {
            loadUrl("file:///android_asset/ads/combo.html?url=".concat(TMDB));
        }
    }

    public XplayComboView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private boolean checkConnection() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }
}
