package com.xplay.ibotv.models;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.xplay.ibotv.utils.Utils;
import java.io.Serializable;

/* JADX INFO: loaded from: classes.dex */
public class CatchUpEpg implements Serializable {
    private String channel_id;
    private String description;
    private String end;
    private long epg_id;
    private int has_archive;

    /* JADX INFO: renamed from: id */
    private long f317id;
    private String lang;
    private int now_playing;
    private String start;
    private long start_timestamp;
    private long stop_timestamp;
    private String title;

    public String getChannel_id() {
        return this.channel_id;
    }

    public String getDescription() {
        return this.description;
    }

    public int getDuration() {
        return (int) (((Utils.getDateFromString("yyyy-MM-dd HH:mm:ss", getEnd()).getTime() - Utils.getDateFromString("yyyy-MM-dd HH:mm:ss", getStart()).getTime()) / 1000) / 60);
    }

    public String getEnd() {
        return this.end;
    }

    public long getEpg_id() {
        return this.epg_id;
    }

    public int getHas_archive() {
        return this.has_archive;
    }

    public long getId() {
        return this.f317id;
    }

    public String getLang() {
        return this.lang;
    }

    public int getNow_playing() {
        return this.now_playing;
    }

    public int getProgress() {
        return (int) ((((System.currentTimeMillis() / 1000) - getStart_timestamp()) * 100) / (getStop_timestamp() - getStart_timestamp()));
    }

    public String getStart() {
        return this.start;
    }

    public String getStartForUrl() {
        return Utils.formatDateFromString("yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd:HH-mm", getStart());
    }

    public long getStart_timestamp() {
        return this.start_timestamp;
    }

    public long getStop_timestamp() {
        return this.stop_timestamp;
    }

    public String getTitle() {
        return this.title;
    }

    public String getUrl(String str, String str2, String str3, String str4) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("/timeshift/");
        sb.append(str2);
        sb.append("/");
        sb.append(str3);
        sb.append("/");
        sb.append(getDuration());
        sb.append("/");
        sb.append(getStartForUrl());
        sb.append("/");
        return Insets$$ExternalSyntheticOutline0.m23m(sb, str4, ".ts");
    }

    public void setChannel_id(String str) {
        this.channel_id = str;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public void setEnd(String str) {
        this.end = str;
    }

    public void setEpg_id(long j) {
        this.epg_id = j;
    }

    public void setHas_archive(int i) {
        this.has_archive = i;
    }

    public void setId(long j) {
        this.f317id = j;
    }

    public void setLang(String str) {
        this.lang = str;
    }

    public void setNow_playing(int i) {
        this.now_playing = i;
    }

    public void setStart(String str) {
        this.start = str;
    }

    public void setStart_timestamp(long j) {
        this.start_timestamp = j;
    }

    public void setStop_timestamp(long j) {
        this.stop_timestamp = j;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Epg{id=");
        sbM26m.append(this.f317id);
        sbM26m.append(", epg_id=");
        sbM26m.append(this.epg_id);
        sbM26m.append(", title='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.title, '\'', ", lang='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.lang, '\'', ", start='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.start, '\'', ", end='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.end, '\'', ", description='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.description, '\'', ", channel_id='");
        Insets$$ExternalSyntheticOutline0.m36m(sbM26m, this.channel_id, '\'', ", start_timestamp=");
        sbM26m.append(this.start_timestamp);
        sbM26m.append(", stop_timestamp=");
        sbM26m.append(this.stop_timestamp);
        sbM26m.append(", now_playing=");
        sbM26m.append(this.now_playing);
        sbM26m.append(", has_archive=");
        return Insets$$ExternalSyntheticOutline0.m21m(sbM26m, this.has_archive, '}');
    }
}
