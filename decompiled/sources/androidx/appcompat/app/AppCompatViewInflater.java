package androidx.appcompat.app;

import android.R;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.C0044R;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.collection.SimpleArrayMap;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private final Object[] mConstructorArgs = new Object[2];
    private static final Class<?>[] sConstructorSignature = {Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = {R.attr.onClick};
    private static final int[] sAccessibilityHeading = {R.attr.accessibilityHeading};
    private static final int[] sAccessibilityPaneTitle = {R.attr.accessibilityPaneTitle};
    private static final int[] sScreenReaderFocusable = {R.attr.screenReaderFocusable};
    private static final String[] sClassPrefixList = {"android.widget.", "android.view.", "android.webkit."};
    private static final SimpleArrayMap<String, Constructor<? extends View>> sConstructorMap = new SimpleArrayMap<>();

    /* JADX INFO: loaded from: classes2.dex */
    public static class DeclaredOnClickListener implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View view, @NonNull String str) {
            this.mHostView = view;
            this.mMethodName = str;
        }

        private void resolveMethod(@Nullable Context context) {
            String string;
            Method method;
            while (context != null) {
                try {
                    if (!context.isRestricted() && (method = context.getClass().getMethod(this.mMethodName, View.class)) != null) {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = context;
                        return;
                    }
                } catch (NoSuchMethodException unused) {
                }
                context = context instanceof ContextWrapper ? ((ContextWrapper) context).getBaseContext() : null;
            }
            int id = this.mHostView.getId();
            if (id == -1) {
                string = "";
            } else {
                StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m(" with id '");
                sbM26m.append(this.mHostView.getContext().getResources().getResourceEntryName(id));
                sbM26m.append("'");
                string = sbM26m.toString();
            }
            StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("Could not find method ");
            sbM26m2.append(this.mMethodName);
            sbM26m2.append("(View) in a parent or ancestor Context for android:onClick attribute defined on view ");
            sbM26m2.append(this.mHostView.getClass());
            sbM26m2.append(string);
            throw new IllegalStateException(sbM26m2.toString());
        }

        @Override // android.view.View.OnClickListener
        public void onClick(@NonNull View view) {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext());
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, view);
            } catch (IllegalAccessException e) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", e);
            } catch (InvocationTargetException e2) {
                throw new IllegalStateException("Could not execute method for android:onClick", e2);
            }
        }
    }

    private void backportAccessibilityAttributes(@NonNull Context context, @NonNull View view, @NonNull AttributeSet attributeSet) {
        if (Build.VERSION.SDK_INT > 28) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, sAccessibilityHeading);
        if (typedArrayObtainStyledAttributes.hasValue(0)) {
            ViewCompat.setAccessibilityHeading(view, typedArrayObtainStyledAttributes.getBoolean(0, false));
        }
        typedArrayObtainStyledAttributes.recycle();
        TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, sAccessibilityPaneTitle);
        if (typedArrayObtainStyledAttributes2.hasValue(0)) {
            ViewCompat.setAccessibilityPaneTitle(view, typedArrayObtainStyledAttributes2.getString(0));
        }
        typedArrayObtainStyledAttributes2.recycle();
        TypedArray typedArrayObtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, sScreenReaderFocusable);
        if (typedArrayObtainStyledAttributes3.hasValue(0)) {
            ViewCompat.setScreenReaderFocusable(view, typedArrayObtainStyledAttributes3.getBoolean(0, false));
        }
        typedArrayObtainStyledAttributes3.recycle();
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        Context context = view.getContext();
        if ((context instanceof ContextWrapper) && ViewCompat.hasOnClickListeners(view)) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, sOnClickAttrs);
            String string = typedArrayObtainStyledAttributes.getString(0);
            if (string != null) {
                view.setOnClickListener(new DeclaredOnClickListener(view, string));
            }
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    private View createViewByPrefix(Context context, String str, String str2) throws InflateException, ClassNotFoundException {
        String str3;
        SimpleArrayMap<String, Constructor<? extends View>> simpleArrayMap = sConstructorMap;
        Constructor<? extends View> constructor = simpleArrayMap.get(str);
        if (constructor == null) {
            if (str2 != null) {
                try {
                    str3 = str2 + str;
                } catch (Exception unused) {
                    return null;
                }
            } else {
                str3 = str;
            }
            constructor = Class.forName(str3, false, context.getClassLoader()).asSubclass(View.class).getConstructor(sConstructorSignature);
            simpleArrayMap.put(str, constructor);
        }
        constructor.setAccessible(true);
        return constructor.newInstance(this.mConstructorArgs);
    }

    private View createViewFromTag(Context context, String str, AttributeSet attributeSet) {
        if (str.equals("view")) {
            str = attributeSet.getAttributeValue(null, "class");
        }
        try {
            Object[] objArr = this.mConstructorArgs;
            objArr[0] = context;
            objArr[1] = attributeSet;
            if (-1 != str.indexOf(46)) {
                return createViewByPrefix(context, str, null);
            }
            int i = 0;
            while (true) {
                String[] strArr = sClassPrefixList;
                if (i >= strArr.length) {
                    return null;
                }
                View viewCreateViewByPrefix = createViewByPrefix(context, str, strArr[i]);
                if (viewCreateViewByPrefix != null) {
                    return viewCreateViewByPrefix;
                }
                i++;
            }
        } catch (Exception unused) {
            return null;
        } finally {
            Object[] objArr2 = this.mConstructorArgs;
            objArr2[0] = null;
            objArr2[1] = null;
        }
    }

    private static Context themifyContext(Context context, AttributeSet attributeSet, boolean z, boolean z2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, C0044R.styleable.View, 0, 0);
        int resourceId = z ? typedArrayObtainStyledAttributes.getResourceId(C0044R.styleable.View_android_theme, 0) : 0;
        if (z2 && resourceId == 0 && (resourceId = typedArrayObtainStyledAttributes.getResourceId(C0044R.styleable.View_theme, 0)) != 0) {
            Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
        }
        typedArrayObtainStyledAttributes.recycle();
        if (resourceId != 0) {
            return ((context instanceof ContextThemeWrapper) && ((ContextThemeWrapper) context).getThemeResId() == resourceId) ? context : new ContextThemeWrapper(context, resourceId);
        }
        return context;
    }

    private void verifyNotNull(View view, String str) {
        if (view != null) {
            return;
        }
        throw new IllegalStateException(getClass().getName() + " asked to inflate view for <" + str + ">, but returned null");
    }

    @NonNull
    public AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatAutoCompleteTextView(context, attributeSet);
    }

    @NonNull
    public AppCompatButton createButton(Context context, AttributeSet attributeSet) {
        return new AppCompatButton(context, attributeSet);
    }

    @NonNull
    public AppCompatCheckBox createCheckBox(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    @NonNull
    public AppCompatRadioButton createRadioButton(Context context, AttributeSet attributeSet) {
        return new AppCompatRadioButton(context, attributeSet);
    }

    @NonNull
    public AppCompatTextView createTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:11:0x0020  */
    public final View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet, boolean z, boolean z2, boolean z3) {
        View appCompatRatingBar;
        Context context2 = (!z || view == null) ? context : view.getContext();
        byte b2 = 1;
        Context contextThemifyContext = themifyContext(context2, attributeSet, z2, true);
        if (z3) {
            contextThemifyContext = TintContextWrapper.wrap(contextThemifyContext);
        }
        Objects.requireNonNull(str);
        switch (str.hashCode()) {
            case -1946472170:
                if (!str.equals("RatingBar")) {
                    b2 = -1;
                } else {
                    b2 = 0;
                }
                break;
            case -1455429095:
                if (!str.equals("CheckedTextView")) {
                    b2 = -1;
                }
                break;
            case -1346021293:
                if (!str.equals("MultiAutoCompleteTextView")) {
                    b2 = -1;
                } else {
                    b2 = 2;
                }
                break;
            case -938935918:
                if (!str.equals("TextView")) {
                    b2 = -1;
                } else {
                    b2 = 3;
                }
                break;
            case -937446323:
                if (!str.equals("ImageButton")) {
                    b2 = -1;
                } else {
                    b2 = 4;
                }
                break;
            case -658531749:
                if (!str.equals("SeekBar")) {
                    b2 = -1;
                } else {
                    b2 = 5;
                }
                break;
            case -339785223:
                if (!str.equals("Spinner")) {
                    b2 = -1;
                } else {
                    b2 = 6;
                }
                break;
            case 776382189:
                if (!str.equals("RadioButton")) {
                    b2 = -1;
                } else {
                    b2 = 7;
                }
                break;
            case 799298502:
                if (!str.equals("ToggleButton")) {
                    b2 = -1;
                } else {
                    b2 = 8;
                }
                break;
            case 1125864064:
                if (!str.equals("ImageView")) {
                    b2 = -1;
                } else {
                    b2 = 9;
                }
                break;
            case 1413872058:
                if (!str.equals("AutoCompleteTextView")) {
                    b2 = -1;
                } else {
                    b2 = 10;
                }
                break;
            case 1601505219:
                if (!str.equals("CheckBox")) {
                    b2 = -1;
                } else {
                    b2 = 11;
                }
                break;
            case 1666676343:
                if (!str.equals("EditText")) {
                    b2 = -1;
                } else {
                    b2 = 12;
                }
                break;
            case 2001146706:
                if (!str.equals("Button")) {
                    b2 = -1;
                } else {
                    b2 = 13;
                }
                break;
            default:
                b2 = -1;
                break;
        }
        switch (b2) {
            case 0:
                appCompatRatingBar = new AppCompatRatingBar(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 1:
                appCompatRatingBar = new AppCompatCheckedTextView(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 2:
                appCompatRatingBar = new AppCompatMultiAutoCompleteTextView(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 3:
                appCompatRatingBar = createTextView(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 4:
                appCompatRatingBar = new AppCompatImageButton(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 5:
                appCompatRatingBar = new AppCompatSeekBar(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 6:
                appCompatRatingBar = new AppCompatSpinner(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 7:
                appCompatRatingBar = createRadioButton(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 8:
                appCompatRatingBar = new AppCompatToggleButton(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 9:
                appCompatRatingBar = new AppCompatImageView(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 10:
                appCompatRatingBar = createAutoCompleteTextView(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 11:
                appCompatRatingBar = createCheckBox(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 12:
                appCompatRatingBar = new AppCompatEditText(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            case 13:
                appCompatRatingBar = createButton(contextThemifyContext, attributeSet);
                verifyNotNull(appCompatRatingBar, str);
                break;
            default:
                appCompatRatingBar = null;
                break;
        }
        if (appCompatRatingBar == null && context != contextThemifyContext) {
            appCompatRatingBar = createViewFromTag(contextThemifyContext, str, attributeSet);
        }
        if (appCompatRatingBar != null) {
            checkOnClickListener(appCompatRatingBar, attributeSet);
            backportAccessibilityAttributes(contextThemifyContext, appCompatRatingBar, attributeSet);
        }
        return appCompatRatingBar;
    }
}
