package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import java.io.Serializable;

/* JADX INFO: loaded from: classes2.dex */
public class TMDBVideoModel implements Serializable {

    /* JADX INFO: renamed from: id */
    public String f328id;
    public String key;
    public String name;
    public String published_at;
    public String site;
    public String type;

    public String getId() {
        return this.f328id;
    }

    public String getKey() {
        return this.key;
    }

    public String getName() {
        return this.name;
    }

    public String getPublished_at() {
        return this.published_at;
    }

    public String getSite() {
        return this.site;
    }

    public String getType() {
        return this.type;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("TMDBVideoModel{name='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.name, '\'', ", key='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.key, '\'', ", site='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.site, '\'', ", type='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.type, '\'', ", published_at='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.published_at, '\'', ", id='");
        sbM26m.append(this.f328id);
        sbM26m.append('\'');
        sbM26m.append('}');
        return sbM26m.toString();
    }
}
