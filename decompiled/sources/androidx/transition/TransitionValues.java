package androidx.transition;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TransitionValues {
    public View view;
    public final Map<String, Object> values = new HashMap();
    public final ArrayList<Transition> mTargetedTransitions = new ArrayList<>();

    @Deprecated
    public TransitionValues() {
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TransitionValues)) {
            return false;
        }
        TransitionValues transitionValues = (TransitionValues) obj;
        return this.view == transitionValues.view && this.values.equals(transitionValues.values);
    }

    public int hashCode() {
        return this.values.hashCode() + (this.view.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("TransitionValues@");
        sbM26m.append(Integer.toHexString(hashCode()));
        sbM26m.append(":\n");
        StringBuilder sbM28m = Insets$$ExternalSyntheticOutline0.m28m(sbM26m.toString(), "    view = ");
        sbM28m.append(this.view);
        sbM28m.append("\n");
        String strM19m = Insets$$ExternalSyntheticOutline0.m19m(sbM28m.toString(), "    values:");
        for (String str : this.values.keySet()) {
            strM19m = strM19m + "    " + str + ": " + this.values.get(str) + "\n";
        }
        return strM19m;
    }

    public TransitionValues(@NonNull View view) {
        this.view = view;
    }
}
