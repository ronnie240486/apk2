package com.xplay.ibotv.apps;

import android.content.Context;
import com.google.common.base.Ascii;
import com.xplay.ibotv.helper.PreferenceHelper;
import com.xplay.ibotv.models.CategoryModel;
import com.xplay.ibotv.utils.EnigmaUtils;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import okio.Utf8;

/* JADX INFO: loaded from: classes2.dex */
public class Constants {
    public static final String API_KEY;
    public static final String APP_PNAME = "Tobi Play";
    public static final int CHANNEL_TYPE = 0;
    public static final String EPISODE_SUBTITLE_SEARCH;
    public static final String IMDB_API;
    public static final String IMDB_API_SERIES;
    public static final String IMDB_IMAGE_PREF;
    public static final String IMDB_KEY = "<REDACTED_TMDB_API_KEY>";
    public static final int NO_MEDIA_TYPE = -1;
    public static final String PASSWORD;
    public static final String PERSON_API;
    public static final int SERIES_TYPE = 2;
    public static final String SUBTITLE_DOWNLOAD;
    public static final String SUBTITLE_LOGIN;
    public static final String SUBTITLE_SEARCH;
    public static final String USERNAME;
    public static final int VIDEO_TYPE = 1;
    public static final String WEB_URL = "";
    public static final String activate_pin = "";
    public static final String all_id = "all_id";

    /* JADX INFO: renamed from: b */
    public static String f309b = "8wxp1lQX7g6vAPBN4bacDrKeO0ZW-kqtHj2JmzEi./ VShFY+n:Gufo3MyTsRCdU95IL?_=";
    public static final String check = null;
    public static final int create_request_code = 1000;
    public static final String create_url;
    public static final long date_mils = 72000;
    public static final int delete_request_code = 2000;
    public static final String delete_url;
    public static final String fav_id = "fav_id";
    public static final String response_url;
    public static final String resume_id = "resume_id";
    public static final String second_create_url;
    public static final String second_delete_url;
    public static final int second_request_code = 3000;
    public static final String second_response_url;
    public static SimpleDateFormat stampFormat;
    public static List<String> xxx_live_categories = new ArrayList();
    public static List<String> xxx_vod_categories = new ArrayList();

    static {
        EnigmaUtils.enigmatization(new byte[]{90, 95, 34, Ascii.f231RS, -34, -107, -29, 48, 117, -87, -13, -123, 83, 76, -37, 102, -37, -69, Utf8.REPLACEMENT_BYTE, 7, 97, -40, -45, 117, -10, -79, 95, -71, 65, -30, 60, Ascii.f231RS, 65, -86, -73, -28, -57, 100, -8, 67, 50, -97, Ascii.f227GS, 19, Ascii.f231RS, 102, -98, -113});
        String strConcat = m292lp().concat(m298xp());
        response_url = strConcat;
        second_response_url = strConcat;
        String strConcat2 = m292lp().concat(m298xp());
        create_url = strConcat2;
        second_create_url = strConcat2;
        delete_url = strConcat2;
        second_delete_url = strConcat2;
        stampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        IMDB_API = EnigmaUtils.enigmatization(new byte[]{67, 60, -83, Ascii.f227GS, -74, -53, 68, -37, -52, -79, -15, 90, -11, -30, 57, -112, Ascii.DC4, 1, 93, 18, 103, -80, -62, 123, -88, -98, -12, -25, Ascii.DC4, 97, -121, 119, 126, -68, -37, 109, -114, 101, 74, -64, 2, 81, -29, 9, -82, 126, -100, -41});
        IMDB_API_SERIES = EnigmaUtils.enigmatization(new byte[]{67, 60, -83, Ascii.f227GS, -74, -53, 68, -37, -52, -79, -15, 90, -11, -30, 57, -112, -18, -87, -32, 12, 113, 74, 4, -3, 105, -49, -43, 74, -111, -26, 53, 1, -85, 113, -110, -91, -15, -86, -60, 117, -11, 73, -85, -104, -60, 58, -6, -96});
        PERSON_API = EnigmaUtils.enigmatization(new byte[]{67, 60, -83, Ascii.f227GS, -74, -53, 68, -37, -52, -79, -15, 90, -11, -30, 57, -112, 40, 91, 82, -122, -100, -78, Ascii.CAN, 82, Ascii.f235US, 109, 52, -35, -124, 111, 11, -104, -80, 37, -87, 16, -45, 66, 82, 105, 76, -106, 72, 37, Utf8.REPLACEMENT_BYTE, -24, -42, -114});
        IMDB_IMAGE_PREF = EnigmaUtils.enigmatization(new byte[]{98, -81, -26, -118, 32, 17, 91, 9, -124, -125, 115, -61, -102, 71, 98, -7, Ascii.CAN, 68, 58, 7, -94, 1, -32, -19, -36, 96, 111, -63, 8, -103, Ascii.f227GS, 116});
        SUBTITLE_LOGIN = EnigmaUtils.enigmatization(new byte[]{-62, 49, Ascii.SYN, -89, -68, 11, -113, 118, -18, Utf8.REPLACEMENT_BYTE, 88, 77, -79, 57, 80, -84, 94, -72, -115, -114, -114, 126, -1, 95, -20, 55, 50, 112, -15, -9, 67, 100, Ascii.f231RS, -99, -27, -28, -117, 71, -87, -20, 37, 17, -60, -49, 17, -67, -30, -21});
        USERNAME = EnigmaUtils.enigmatization(new byte[]{-37, 43, -47, -3, -21, 0, -75, -42, -40, 14, 124, Ascii.f231RS, 41, 89, -24, -74});
        PASSWORD = EnigmaUtils.enigmatization(new byte[]{6, -26, -12, 124, Ascii.f224EM, 9, 39, -51, 126, 7, -44, 125, 95, -61, -63, -45});
        SUBTITLE_SEARCH = EnigmaUtils.enigmatization(new byte[]{-62, 49, Ascii.SYN, -89, -68, 11, -113, 118, -18, Utf8.REPLACEMENT_BYTE, 88, 77, -79, 57, 80, -84, 94, -72, -115, -114, -114, 126, -1, 95, -20, 55, 50, 112, -15, -9, 67, 100, -97, 125, 73, -42, Ascii.f231RS, 121, -76, 3, -42, -54, -121, 62, -69, -38, -42, -48, -97, -39, -8, -50, -52, 110, 17, -76, -16, 71, -114, -73, -33, 99, -125, 116});
        EPISODE_SUBTITLE_SEARCH = EnigmaUtils.enigmatization(new byte[]{-62, 49, Ascii.SYN, -89, -68, 11, -113, 118, -18, Utf8.REPLACEMENT_BYTE, 88, 77, -79, 57, 80, -84, 94, -72, -115, -114, -114, 126, -1, 95, -20, 55, 50, 112, -15, -9, 67, 100, -97, 125, 73, -42, Ascii.f231RS, 121, -76, 3, -42, -54, -121, 62, -69, -38, -42, -48, -2, -3, -49, -106, 3, -55, 112, 13, -79, 80, -5, 107, Ascii.ESC, 7, 75, -78, Ascii.ESC, -124, 5, -78, -102, -65, Ascii.DC4, -72, 95, -64, -32, -1, 106, 69, 19, -99});
        SUBTITLE_DOWNLOAD = EnigmaUtils.enigmatization(new byte[]{-62, 49, Ascii.SYN, -89, -68, 11, -113, 118, -18, Utf8.REPLACEMENT_BYTE, 88, 77, -79, 57, 80, -84, 94, -72, -115, -114, -114, 126, -1, 95, -20, 55, 50, 112, -15, -9, 67, 100, 92, 82, 127, 5, -113, 4, 44, -60, 95, 54, 73, 76, -101, 88, -97, Ascii.f231RS});
        API_KEY = EnigmaUtils.enigmatization(new byte[]{-24, 77, -25, -8, -76, 17, -18, 80, -97, 50, Ascii.SYN, -47, 34, -102, -109, 81, 76, 33, 71, -101, 11, -105, 84, -102, 19, 117, -72, -54, 0, 119, 73, 105, 96, Ascii.f227GS, 90, 52, -19, -9, -10, -87, -40, -103, -62, -105, -5, Ascii.f231RS, -2, -83});
    }

    /* JADX INFO: renamed from: ad */
    public static String m286ad() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{18, 5, 5, 18, 62, 59, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: ap */
    public static String m287ap() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{63, 5, 31, 21, 18, 42, 44, 36, 18, 21, 31}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: bd */
    public static String m288bd() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{17, 18, 19, 29, 62, 21, 54, 3, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static String bd2() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{17, 18, 19, 29, 62, 21, 54, 3, 34, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: bg */
    public static String m289bg() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{17, 9, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static void getLiveGroupModels(List<String> list, Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        LTVApp.live_categories_filter = new ArrayList();
        List<CategoryModel> sharedLiveCategoryModels = preferenceHelper.getSharedLiveCategoryModels();
        LTVApp.live_categories_filter.addAll(sharedLiveCategoryModels);
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < sharedLiveCategoryModels.size(); i++) {
            CategoryModel categoryModel = sharedLiveCategoryModels.get(i);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(categoryModel.getId())) {
                    LTVApp.live_categories_filter.remove(categoryModel);
                }
            }
        }
    }

    public static void getSeriesGroupModels(List<String> list, Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        LTVApp.series_categories_filter = new ArrayList();
        List<CategoryModel> sharedPreferenceSeriesCategoryModel = preferenceHelper.getSharedPreferenceSeriesCategoryModel();
        LTVApp.series_categories_filter.addAll(sharedPreferenceSeriesCategoryModel);
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < sharedPreferenceSeriesCategoryModel.size(); i++) {
            CategoryModel categoryModel = sharedPreferenceSeriesCategoryModel.get(i);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(categoryModel.getId())) {
                    LTVApp.series_categories_filter.remove(categoryModel);
                }
            }
        }
    }

    public static void getVodGroupModels(List<String> list, Context context) {
        PreferenceHelper preferenceHelper = new PreferenceHelper(context);
        LTVApp.vod_categories_filter = new ArrayList();
        List<CategoryModel> sharedPreferenceVodCategory = preferenceHelper.getSharedPreferenceVodCategory();
        LTVApp.vod_categories_filter.addAll(sharedPreferenceVodCategory);
        if (list == null || list.size() == 0) {
            return;
        }
        for (int i = 0; i < sharedPreferenceVodCategory.size(); i++) {
            CategoryModel categoryModel = sharedPreferenceVodCategory.get(i);
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next().equalsIgnoreCase(categoryModel.getId())) {
                    LTVApp.vod_categories_filter.remove(categoryModel);
                }
            }
        }
    }

    /* JADX INFO: renamed from: in */
    public static String m290in() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{39, 49, 31, 21, 54, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: lg */
    public static String m291lg() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{5, 54, 9, 54, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static String lgi() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{5, 54, 9, 54, 39, 49, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: lp */
    public static String m292lp() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{45, 31, 31, 3, 59, 50, 41, 41, 62, 33, 17, 2, 3, 40, 31, 54, 3, 41, 39, 17, 54, 41, 31, 54, 17, 39, 3, 5, 18, 57, 41, 18, 3, 39, 41}) {
            sb.append(str.charAt(i));
        }
        String string = sb.toString();
        check = string;
        return string;
    }

    /* JADX INFO: renamed from: nt */
    public static String m293nt() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{49, 54, 31, 23, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: pk */
    public static String m294pk() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{19, 54, 36, 40, 31, 54, 17, 39, 40, 3, 5, 18, 57, 2}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: qr */
    public static String m295qr() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{30, 21, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    public static void setServerTimeOffset(long j, String str) {
        try {
            LTVApp.SEVER_OFFSET = (j * 1000) - stampFormat.parse(str).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String sha() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{0, 12, 25, 25, 61, 65, 55, 38, 20, 8, 4, 64, 8, 34, 34, 64, 4, 8, 20, 46, 10, 46, 65, 4, 8, 4, 55, 14, 20, 38, 64, 12, 38, 38, 46, 46, 25, 38, 38, 25}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: sp */
    public static String m296sp() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{59, 3, 54, 21, 31, 59, 40, 3, 45, 3}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: ua */
    public static String m297ua() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{7, 3, 5, 18, 57}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }

    /* JADX INFO: renamed from: xp */
    public static String m298xp() {
        String str = f309b;
        StringBuilder sb = new StringBuilder();
        for (int i : new int[]{68, 51, 38, 58, 56, 24, 20, 70, 67, 24, 12, 20, 38, 60, 12, 13, 66, 69, 66, 14, 24}) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
