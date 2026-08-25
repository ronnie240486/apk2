package iptv.m3u.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
public class M3UHead {
    private String mDLNAExtras;
    private String mName;
    private String mPlugin;
    private String mType;

    public String getDLNAExtras() {
        return this.mDLNAExtras;
    }

    public String getName() {
        return this.mName;
    }

    public String getPlugin() {
        return this.mPlugin;
    }

    public String getType() {
        return this.mType;
    }

    public void setDLNAExtras(String str) {
        this.mDLNAExtras = str;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setPlugin(String str) {
        this.mPlugin = str;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[Head]");
        if (this.mName != null) {
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("\nName: ");
            sbM26m.append(this.mName);
            stringBuffer.append(sbM26m.toString());
        }
        if (this.mType != null) {
            StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("\nType: ");
            sbM26m2.append(this.mType);
            stringBuffer.append(sbM26m2.toString());
        }
        if (this.mDLNAExtras != null) {
            StringBuilder sbM26m3 = Insets$$ExternalSyntheticOutline0.m26m("\nDLNA Extras: ");
            sbM26m3.append(this.mDLNAExtras);
            stringBuffer.append(sbM26m3.toString());
        }
        if (this.mPlugin != null) {
            StringBuilder sbM26m4 = Insets$$ExternalSyntheticOutline0.m26m("\nPlugin: ");
            sbM26m4.append(this.mPlugin);
            stringBuffer.append(sbM26m4.toString());
        }
        return stringBuffer.toString();
    }
}
