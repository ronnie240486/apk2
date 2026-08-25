package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class CatchUpEpgResponse implements Serializable {
    public List<CatchUpEpg> epg_listings = new ArrayList();

    public List<CatchUpEpg> getEpg_listings() {
        return this.epg_listings;
    }

    public void setEpg_listings(List<CatchUpEpg> list) {
        this.epg_listings = list;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("CatchUpEpgResponse{epg_listings=");
        sbM26m.append(this.epg_listings);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
