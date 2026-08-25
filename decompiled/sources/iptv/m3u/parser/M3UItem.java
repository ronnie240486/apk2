package iptv.m3u.parser;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes3.dex */
public class M3UItem {
    private String mChannelId;
    private String mChannelName;
    private String mDLNAExtras;
    private int mDuration;
    private String mGroupTitle;
    private String mLogoURL;
    private String mPlugin;
    private String mStreamURL;
    private String mType;

    public String getChannelId() {
        return this.mChannelId;
    }

    public String getChannelName() {
        return this.mChannelName;
    }

    public String getDLNAExtras() {
        return this.mDLNAExtras;
    }

    public int getDuration() {
        return this.mDuration;
    }

    public String getGroupTitle() {
        String str = this.mGroupTitle;
        return str == null ? "" : str;
    }

    public String getLogoURL() {
        return this.mLogoURL;
    }

    public String getPlugin() {
        return this.mPlugin;
    }

    public String getStreamURL() {
        return this.mStreamURL;
    }

    public String getType() {
        return this.mType;
    }

    public void setChannelID(String str) {
        this.mChannelId = str;
    }

    public void setChannelName(String str) {
        this.mChannelName = str;
    }

    public void setDLNAExtras(String str) {
        this.mDLNAExtras = str;
    }

    public void setDuration(int i) {
        this.mDuration = i;
    }

    public void setGroupTitle(String str) {
        this.mGroupTitle = str;
    }

    public void setLogoURL(String str) {
        this.mLogoURL = str;
    }

    public void setPlugin(String str) {
        this.mPlugin = str;
    }

    public void setStreamURL(String str) {
        this.mStreamURL = str;
    }

    public void setType(String str) {
        this.mType = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[Item]");
        if (this.mChannelId != null) {
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("\n Channel Id: ");
            sbM26m.append(this.mChannelId);
            stringBuffer.append(sbM26m.toString());
        }
        if (this.mChannelName != null) {
            StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("\nChannel Name: ");
            sbM26m2.append(this.mChannelName);
            stringBuffer.append(sbM26m2.toString());
        }
        StringBuilder sbM26m3 = Insets$$ExternalSyntheticOutline0.m26m("\nDuration: ");
        sbM26m3.append(this.mDuration);
        stringBuffer.append(sbM26m3.toString());
        if (this.mStreamURL != null) {
            StringBuilder sbM26m4 = Insets$$ExternalSyntheticOutline0.m26m("\nStream URL: ");
            sbM26m4.append(this.mStreamURL);
            stringBuffer.append(sbM26m4.toString());
        }
        if (this.mGroupTitle != null) {
            StringBuilder sbM26m5 = Insets$$ExternalSyntheticOutline0.m26m("\nGroup: ");
            sbM26m5.append(this.mGroupTitle);
            stringBuffer.append(sbM26m5.toString());
        }
        if (this.mLogoURL != null) {
            StringBuilder sbM26m6 = Insets$$ExternalSyntheticOutline0.m26m("\nLogo: ");
            sbM26m6.append(this.mLogoURL);
            stringBuffer.append(sbM26m6.toString());
        }
        if (this.mType != null) {
            StringBuilder sbM26m7 = Insets$$ExternalSyntheticOutline0.m26m("\nType: ");
            sbM26m7.append(this.mType);
            stringBuffer.append(sbM26m7.toString());
        }
        if (this.mDLNAExtras != null) {
            StringBuilder sbM26m8 = Insets$$ExternalSyntheticOutline0.m26m("\nDLNA Extras: ");
            sbM26m8.append(this.mDLNAExtras);
            stringBuffer.append(sbM26m8.toString());
        }
        if (this.mPlugin != null) {
            StringBuilder sbM26m9 = Insets$$ExternalSyntheticOutline0.m26m("\nPlugin: ");
            sbM26m9.append(this.mPlugin);
            stringBuffer.append(sbM26m9.toString());
        }
        return stringBuffer.toString();
    }
}
