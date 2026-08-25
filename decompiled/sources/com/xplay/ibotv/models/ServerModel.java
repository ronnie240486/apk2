package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class ServerModel implements Serializable {
    private String time_now;
    private long timestamp_now = 0;

    public String getTime_now() {
        return this.time_now;
    }

    public long getTimestamp_now() {
        return this.timestamp_now;
    }

    public void setTime_now(String str) {
        this.time_now = str;
    }

    public void setTimestamp_now(long j) {
        this.timestamp_now = j;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("ServerModel{timestamp_now='");
        sbM26m.append(this.timestamp_now);
        sbM26m.append('\'');
        sbM26m.append(", time_now='");
        sbM26m.append(this.time_now);
        sbM26m.append('\'');
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
