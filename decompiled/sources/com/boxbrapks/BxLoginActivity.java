package com.boxbrapks;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.boxbrapks.model.LoginTaskResponse;
import com.boxbrapks.model.UserAccountModel;
import java.util.ArrayList;
import p023x.p024b.p025o.p026x.p027b.p028r.pro;
import p030.p031.p032.p033.p034.p035.C2859;

/* JADX INFO: loaded from: classes2.dex */
public class BxLoginActivity extends Activity implements View.OnFocusChangeListener, View.OnClickListener, LoginTaskResponse {
    public static String BX_ACTIVE_CONNECTIONS = null;
    public static String BX_AUTH_URL = null;
    public static String BX_AUTO_LOGIN = null;
    public static String BX_CREATED_AT = null;
    public static String BX_EXPIRY = null;
    public static String BX_IS_TRIAL = null;
    public static String BX_LOGIN_PREF = null;
    public static String BX_MAX_CONNECTIONS = null;
    public static String BX_PASSWORD = null;
    public static String BX_STATUS = null;
    public static String BX_USERNAME = null;
    private static final int MESSAGE_TYPE_ERROR = 1;
    private static final int MESSAGE_TYPE_NORMAL = 0;
    private static final int MESSAGE_TYPE_SUCCESS = 2;
    Button btn_login;
    EditText et_password;
    EditText et_username;
    TextView mac;
    FrameLayout messageContainer;
    TextView messageText;
    private String Webnet = getClass().getName();
    boolean isAutoLogin = false;

    private native void Ativador();

    public static native void BxClearLoginPref(Activity activity);

    public static native void FullScreenCall(Activity activity);

    private native void WebScreen();

    @SuppressLint({"HardwareIds"})
    public static native String getDeviceId(Context context);

    /* JADX INFO: Access modifiers changed from: private */
    public native void hideMessageLater();

    /* JADX INFO: Access modifiers changed from: private */
    public native void showMessage(int i, String str);

    public native void downImage();

    @Override // android.view.View.OnClickListener
    public native void onClick(View view);

    @Override // android.app.Activity
    protected native void onCreate(Bundle bundle);

    @Override // android.view.View.OnFocusChangeListener
    public native void onFocusChange(View view, boolean z);

    @Override // com.boxbrapks.model.LoginTaskResponse
    public native void onLoginFailed(String str);

    @Override // com.boxbrapks.model.LoginTaskResponse
    public native void onLoginSuccess(String str, String str2, String str3);

    @Override // com.boxbrapks.model.LoginTaskResponse
    public native void onLoginSuccess(ArrayList<UserAccountModel> arrayList);

    public native void scaleView(View view, float f, float f2);

    native void toggleInputFields(boolean z);

    static {
        pro.classes2Init0(1);
        BX_LOGIN_PREF = C2859.m2370("ScKit-ad82a476808099cdfebd3e7c049cda31", "ScKit-97ebfc7fdc8e67c4");
        BX_USERNAME = C2859.m2370("ScKit-5be44aa8839dc18cacf500802c7031de", "ScKit-97ebfc7fdc8e67c4");
        BX_PASSWORD = C2859.m2370("ScKit-36f37701917aeccd668fa4595e78e117", "ScKit-97ebfc7fdc8e67c4");
        BX_STATUS = C2859.m2370("ScKit-57b898acc8d3b411f43a7689881a5b597b3d363ef0f727cf940e7c92bb18e192", "ScKit-97ebfc7fdc8e67c4");
        BX_EXPIRY = C2859.m2370("ScKit-8136f52e651ffebccfcd8f8b67e19783", "ScKit-8c216c0b037d1d52");
        BX_IS_TRIAL = C2859.m2370("ScKit-5893f7bf955385203a4314fbf3849bb5", "ScKit-8c216c0b037d1d52");
        BX_ACTIVE_CONNECTIONS = C2859.m2370("ScKit-008d0c69461bce0da65c92a0a63397b478ab885782373ae02fd99cc010cffd83", "ScKit-8c216c0b037d1d52");
        BX_CREATED_AT = C2859.m2370("ScKit-e0475762dbdc6bc9cb2f7a08fc00072c", "ScKit-8c216c0b037d1d52");
        BX_MAX_CONNECTIONS = C2859.m2370("ScKit-6f678d2111fcd2fe7825df1ba9b48bfc1e7f9b59afca5e4d668710bd7d24dd41", "ScKit-8c216c0b037d1d52");
        BX_AUTH_URL = C2859.m2370("ScKit-a5d7d5ceccc0e65dfb77674d75c11565", "ScKit-8c216c0b037d1d52");
        BX_AUTO_LOGIN = C2859.m2370("ScKit-26331b9820e698b04a98e823f728c3ed", "ScKit-8c216c0b037d1d52");
    }

    /* JADX INFO: renamed from: com.boxbrapks.BxLoginActivity$1 */
    /* JADX INFO: loaded from: classes.dex */
    class RunnableC09441 implements Runnable {
        static {
            pro.classesInit0(4);
        }

        @Override // java.lang.Runnable
        public native void run();

        RunnableC09441() {
        }

        /* JADX INFO: renamed from: com.boxbrapks.BxLoginActivity$1$1, reason: invalid class name */
        /* JADX INFO: loaded from: classes3.dex */
        class AnonymousClass1 implements Animation.AnimationListener {
            static {
                pro.classes3Init0(1);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public native void onAnimationEnd(Animation animation);

            @Override // android.view.animation.Animation.AnimationListener
            public native void onAnimationRepeat(Animation animation);

            @Override // android.view.animation.Animation.AnimationListener
            public native void onAnimationStart(Animation animation);

            AnonymousClass1() {
            }
        }
    }

    /* JADX INFO: renamed from: com.boxbrapks.BxLoginActivity$2 */
    /* JADX INFO: loaded from: classes.dex */
    class RunnableC09452 implements Runnable {
        final /* synthetic */ String val$message;

        static {
            pro.classesInit0(5);
        }

        @Override // java.lang.Runnable
        public native void run();

        RunnableC09452(String str) {
            this.val$message = str;
        }
    }

    /* JADX INFO: renamed from: com.boxbrapks.BxLoginActivity$3 */
    /* JADX INFO: loaded from: classes.dex */
    class AnimationAnimationListenerC09463 implements Animation.AnimationListener {
        static {
            pro.classesInit0(6);
        }

        @Override // android.view.animation.Animation.AnimationListener
        public native void onAnimationEnd(Animation animation);

        @Override // android.view.animation.Animation.AnimationListener
        public native void onAnimationRepeat(Animation animation);

        @Override // android.view.animation.Animation.AnimationListener
        public native void onAnimationStart(Animation animation);

        AnimationAnimationListenerC09463() {
        }
    }

    /* JADX INFO: renamed from: com.boxbrapks.BxLoginActivity$4 */
    /* JADX INFO: loaded from: classes.dex */
    class RunnableC09474 implements Runnable {
        final /* synthetic */ ArrayList val$validUsers;

        static {
            pro.classesInit0(7);
        }

        @Override // java.lang.Runnable
        public native void run();

        RunnableC09474(ArrayList arrayList) {
            this.val$validUsers = arrayList;
        }
    }

    /* JADX INFO: renamed from: com.boxbrapks.BxLoginActivity$5 */
    /* JADX INFO: loaded from: classes.dex */
    class RunnableC09485 implements Runnable {
        final /* synthetic */ String val$message;

        static {
            pro.classesInit0(1);
        }

        @Override // java.lang.Runnable
        public native void run();

        RunnableC09485(String str) {
            this.val$message = str;
        }
    }
}
