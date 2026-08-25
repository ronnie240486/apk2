package com.boxbrapks;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.widget.EditText;

/* JADX INFO: loaded from: classes.dex */
public class PasswordEditText extends EditText {
    public PasswordEditText(Context context) {
        super(context);
        init(context);
    }

    public PasswordEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        String string;
        SharedPreferences sharedPreferences = context.getSharedPreferences("XpLoginPref", 0);
        if (!sharedPreferences.contains("BX.PASSWORD") || (string = sharedPreferences.getString("BX.PASSWORD", "")) == null) {
            string = "";
        }
        setText(string);
    }
}
