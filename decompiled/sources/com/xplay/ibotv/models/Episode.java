package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class Episode implements Serializable {
    private String container_extension;
    private String episode_num;

    /* JADX INFO: renamed from: id */
    private String f320id;
    private Object info;
    private int season;
    private String title;

    public String getContainer_extension() {
        return this.container_extension;
    }

    public String getEpisode_num() {
        return this.episode_num;
    }

    public String getId() {
        return this.f320id;
    }

    public Object getInfo() {
        return this.info;
    }

    public int getSeason() {
        return this.season;
    }

    public String getTitle() {
        return this.title;
    }

    public void setContainer_extension(String str) {
        this.container_extension = str;
    }

    public void setEpisode_num(String str) {
        this.episode_num = str;
    }

    public void setId(String str) {
        this.f320id = str;
    }

    public void setInfo(Object obj) {
        this.info = obj;
    }

    public void setSeason(int i) {
        this.season = i;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Episode{id='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.f320id, '\'', ", episode_num='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.episode_num, '\'', ", title='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.title, '\'', ", container_extension='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.container_extension, '\'', ", season=");
        sbM26m.append(this.season);
        sbM26m.append(", info=");
        sbM26m.append(this.info);
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
