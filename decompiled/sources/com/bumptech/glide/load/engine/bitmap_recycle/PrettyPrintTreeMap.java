package com.bumptech.glide.load.engine.bitmap_recycle;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.util.Map;
import java.util.TreeMap;

/* JADX INFO: loaded from: classes.dex */
class PrettyPrintTreeMap<K, V> extends TreeMap<K, V> {
    @Override // java.util.AbstractMap
    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("( ");
        for (Map.Entry<K, V> entry : entrySet()) {
            sbM26m.append('{');
            sbM26m.append(entry.getKey());
            sbM26m.append(':');
            sbM26m.append(entry.getValue());
            sbM26m.append("}, ");
        }
        if (!isEmpty()) {
            sbM26m.replace(sbM26m.length() - 2, sbM26m.length(), "");
        }
        sbM26m.append(" )");
        return sbM26m.toString();
    }
}
