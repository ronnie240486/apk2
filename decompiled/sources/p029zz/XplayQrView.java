package p029zz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xplay.ibotv.apps.Constants;

/* JADX INFO: loaded from: classes2.dex */
public class XplayQrView extends WebView {
    private void init() {
        setBackgroundColor(0);
        setFocusable(false);
        setClickable(false);
        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient());
        if (checkConnection()) {
            loadUrl(Constants.m292lp().concat(Constants.m295qr()));
        } else {
            loadUrl("file:///android_asset/offline.html");
        }
    }

    public XplayQrView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private boolean checkConnection() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }
}
