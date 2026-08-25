package p029zz;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.xplay.ibotv.apps.Constants;

/* JADX INFO: loaded from: classes.dex */
public class XplayBD2View extends WebView {
    private void init() {
        setBackgroundColor(0);
        setFocusable(false);
        setClickable(false);
        getSettings().setJavaScriptEnabled(true);
        setWebViewClient(new WebViewClient());
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(5000L);
        startAnimation(alphaAnimation);
        if (checkConnection()) {
            loadUrl(Constants.m292lp().concat(Constants.bd2()));
        } else {
            loadUrl("file:///android_asset/offline.html");
        }
    }

    public XplayBD2View(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private boolean checkConnection() {
        NetworkInfo activeNetworkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) getContext().getSystemService("connectivity");
        return (connectivityManager == null || (activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) == null || !activeNetworkInfo.isConnected()) ? false : true;
    }
}
