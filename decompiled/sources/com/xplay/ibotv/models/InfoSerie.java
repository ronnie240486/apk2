package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class InfoSerie implements Serializable {
    public List<Season> seasons = new ArrayList();
    public ListEpisode episodes = new ListEpisode();

    public ListEpisode getEpisodes() {
        return this.episodes;
    }

    public List<Season> getSeasons() {
        return this.seasons;
    }

    public void setEpisodes(ListEpisode listEpisode) {
        this.episodes = listEpisode;
    }

    public void setSeasons(List<Season> list) {
        this.seasons = list;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("InfoSerie{seasons=");
        sbM26m.append(this.seasons);
        sbM26m.append("episodes=");
        sbM26m.append(this.episodes);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
