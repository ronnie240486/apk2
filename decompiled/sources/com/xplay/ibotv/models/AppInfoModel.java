package com.xplay.ibotv.models;

import com.evgenii.jsevaluator.BuildConfig;
import com.google.android.exoplayer2.text.ttml.TtmlNode;
import com.google.android.gms.common.internal.ImagesContract;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public class AppInfoModel implements Serializable {

    @SerializedName("ads")
    private String ads;

    @SerializedName("agent")
    private String agent;

    @SerializedName("apikey")
    private String apikey;

    @SerializedName("apk_link")
    private String apk_link;

    @SerializedName("app_version")
    private String app_version;

    /* JADX INFO: renamed from: bg */
    @SerializedName("bg")
    private String f313bg;

    @SerializedName("device_key")
    private String device_key;

    @SerializedName("expire_date")
    private String expiredDate;

    @SerializedName("images")
    private String images;
    private boolean is_google_pay;

    @SerializedName("is_trial")
    private int is_trial;

    @SerializedName("languages")
    private List<LanguageModel> languageModels;

    @SerializedName("lock")
    private int lock;

    @SerializedName("logo")
    private String logo;

    @SerializedName("mac_address")
    private String mac_address;

    @SerializedName("pin")
    private String pin_code;

    @SerializedName("plan_id")
    private String plan_id;

    @SerializedName("price")
    private String price;

    @SerializedName("urls")
    private List<UrlModel> result;

    @SerializedName("mac_registered")
    private boolean success;

    @SerializedName("theme")
    private String theme;

    /* JADX INFO: loaded from: classes.dex */
    public static class UrlModel implements Serializable {

        /* JADX INFO: renamed from: id */
        @SerializedName(TtmlNode.ATTR_ID)
        private String f314id;

        @SerializedName("is_protected")
        private String is_protected;

        @SerializedName("name")
        private String name;

        @SerializedName("type")
        private String type;

        @SerializedName(ImagesContract.URL)
        private String url;

        public String getId() {
            return this.f314id;
        }

        public String getIs_protected() {
            String str = this.is_protected;
            return str == null ? "0" : str;
        }

        public String getName() {
            return this.name;
        }

        public String getType() {
            return this.type;
        }

        public String getUrl() {
            String str = this.url;
            return str == null ? "" : str;
        }

        public void setId(String str) {
            this.f314id = str;
        }

        public void setName(String str) {
            this.name = str;
        }

        public void setType(String str) {
            this.type = str;
        }

        public void setUrl(String str) {
            this.url = str;
        }
    }

    public String getAds() {
        return this.ads;
    }

    public String getAgent() {
        return this.agent;
    }

    public String getApiKey() {
        return this.apikey;
    }

    public String getApk_link() {
        return this.apk_link;
    }

    public String getApp_version() {
        String str = this.app_version;
        return str == null ? BuildConfig.VERSION_NAME : str;
    }

    public String getBG() {
        String str = this.f313bg;
        return (str == null || str.isEmpty()) ? "" : this.f313bg;
    }

    public String getDevice_key() {
        return this.device_key;
    }

    public String getExpiredDate() {
        String str = this.expiredDate;
        return str == null ? "" : str;
    }

    public String getImages() {
        return this.images;
    }

    public int getIs_trial() {
        return this.is_trial;
    }

    public List<LanguageModel> getLanguageModels() {
        List<LanguageModel> list = this.languageModels;
        return list == null ? new ArrayList() : list;
    }

    public int getLock() {
        return this.lock;
    }

    public String getLogo() {
        String str = this.logo;
        return (str == null || str.isEmpty()) ? "" : this.logo;
    }

    public String getMac_address() {
        return this.mac_address;
    }

    public String getPin_code() {
        String str = this.pin_code;
        return str == null ? "0000" : str;
    }

    public String getPlan_id() {
        return this.plan_id;
    }

    public String getPrice() {
        String str = this.price;
        return (str == null || str.isEmpty()) ? "" : this.price;
    }

    public List<UrlModel> getResult() {
        List<UrlModel> list = this.result;
        return list == null ? new ArrayList() : list;
    }

    public boolean getSuccess() {
        return this.success;
    }

    public String getTheme() {
        String str = this.theme;
        return str == null ? "0" : str;
    }

    public boolean isIs_google_pay() {
        return this.is_google_pay;
    }

    public void setExpiredDate(String str) {
        this.expiredDate = str;
    }

    public void setIs_google_pay(boolean z) {
        this.is_google_pay = z;
    }

    public void setIs_trial(int i) {
        this.is_trial = i;
    }

    public void setLanguageModels(List<LanguageModel> list) {
        this.languageModels = list;
    }

    public void setLock(int i) {
        this.lock = i;
    }

    public void setPin_code(String str) {
        this.pin_code = str;
    }

    public void setResult(List<UrlModel> list) {
        this.result = list;
    }
}
