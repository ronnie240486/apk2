package p029zz;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.tobi.playx.R;
import com.xplay.ibotv.apps.Constants;
import com.xplay.ibotv.utils.Utils;

/* JADX INFO: loaded from: classes.dex */
public class XplaySports extends Activity {

    /* JADX INFO: renamed from: a */
    public WebView f391a;

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.sport);
        Utils.FullScreenCall(this);
        WebView webView = (WebView) findViewById(R.id.webview);
        webView.setBackgroundColor(0);
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setJavaScriptEnabled(true);
        settings.setUserAgentString(Constants.m297ua());
        this.f391a = webView;
        if (checkConnection()) {
            webView.loadUrl(Constants.m292lp().concat(Constants.m296sp()));
        } else {
            webView.loadUrl("file:///android_asset/offline.html");
        }
    }

    private boolean checkConnection() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }
}
