package androidx.leanback.widget;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.leanback.C0398R;

/* JADX INFO: loaded from: classes2.dex */
public final class ListRowHoverCardView extends LinearLayout {
    private final TextView mDescriptionView;
    private final TextView mTitleView;

    public ListRowHoverCardView(Context context) {
        this(context, null);
    }

    public final CharSequence getDescription() {
        return this.mDescriptionView.getText();
    }

    public final CharSequence getTitle() {
        return this.mTitleView.getText();
    }

    public final void setDescription(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mDescriptionView.setVisibility(8);
        } else {
            this.mDescriptionView.setText(charSequence);
            this.mDescriptionView.setVisibility(0);
        }
    }

    public final void setTitle(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.mTitleView.setVisibility(8);
        } else {
            this.mTitleView.setText(charSequence);
            this.mTitleView.setVisibility(0);
        }
    }

    public ListRowHoverCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ListRowHoverCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        LayoutInflater.from(context).inflate(C0398R.layout.lb_list_row_hovercard, this);
        this.mTitleView = (TextView) findViewById(C0398R.id.title);
        this.mDescriptionView = (TextView) findViewById(C0398R.id.description);
    }
}
