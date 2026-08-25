package com.xplay.ibotv.mac;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Integração MAC -> painel para a rota universal descrita em main(3).pdf.
 * Não envia username, password, Authorization ou qualquer segredo de login.
 */
public final class MacPanelClient {
    public static final String BASE_URL = "https://renciaapp.manus.space";
    public static final String DEFAULT_APP_ID = "nexus";
    public static final int CONNECT_TIMEOUT_MS = 15000;
    public static final int READ_TIMEOUT_MS = 20000;
    private static final String PREFS = "PREF";
    private static final ExecutorService EXECUTOR = Executors.newCachedThreadPool();
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private MacPanelClient() {
    }

    public interface Callback {
        void onSuccess(Config config);
        void onFailure(Exception error);
    }

    public static final class Config {
        private final JSONObject json;
        private final String mac;

        private Config(JSONObject json, String mac) {
            this.json = json;
            this.mac = mac;
        }

        public JSONObject raw() {
            return json;
        }

        public String getMac() {
            return mac;
        }

        public boolean isRegistered() {
            return json.optBoolean("registered", json.optBoolean("mac_registered", false));
        }

        public boolean isAllowed() {
            return json.optBoolean("allowed", false);
        }

        public String getStatus() {
            return json.optString("status", "");
        }

        public String getError() {
            return json.optString("error", "");
        }

        public boolean isPlaylistSyncRequired() {
            return json.optBoolean("playlist_sync_required", false);
        }

        public String getMessageTitle() {
            return json.optString("message_title", "");
        }

        public String getMessageText() {
            return json.optString("message_text", "");
        }

        public String getBlockTitle() {
            return json.optString("block_title", "");
        }

        public String getBlockMessage() {
            return json.optString("block_message", "");
        }

        public String getRenewButtonText() {
            return json.optString("renew_button_text", "Renovar");
        }

        public String getRenewButtonUrl() {
            return json.optString("renew_button_url", "");
        }

        public String getServerApiUrl() {
            return json.optString("server_api_url", "");
        }

        public String getLogoUrl() {
            return json.optString("logo_url", "");
        }

        public String getBannerUrl() {
            return json.optString("banner_url", "");
        }

        public String getBackgroundUrl() {
            return json.optString("background_url", "");
        }

        public String getMessageImageUrl() {
            return json.optString("message_image_url", "");
        }

        public String getUpdateUrl() {
            JSONObject update = json.optJSONObject("update");
            if (update == null) {
                return "";
            }
            String url = update.optString("url", "");
            return url.isEmpty() ? update.optString("apk_link", "") : url;
        }

        public List<String> getPlaylistUrls() {
            ArrayList<String> result = new ArrayList<>();
            JSONArray urls = json.optJSONArray("playlist_urls");
            if (urls == null) {
                return result;
            }
            for (int i = 0; i < urls.length(); i++) {
                Object value = urls.opt(i);
                if (value instanceof JSONObject) {
                    String url = ((JSONObject) value).optString("url", "");
                    if (!url.isEmpty()) {
                        result.add(url);
                    }
                } else if (value != null && !String.valueOf(value).isEmpty()) {
                    result.add(String.valueOf(value));
                }
            }
            return result;
        }
    }

    public static String buildConfigUrl(String baseUrl, String appId, String mac) throws Exception {
        String base = baseUrl == null ? BASE_URL : baseUrl.trim();
        if (base.endsWith("/")) {
            base = base.substring(0, base.length() - 1);
        }
        String family = TextUtils.isEmpty(appId) ? DEFAULT_APP_ID : appId.trim();
        String normalizedMac = MacAddressProvider.normalize(mac);
        if (normalizedMac == null) {
            throw new IllegalArgumentException("MAC inválido ou ausente");
        }
        return base + "/api/v5/apps/" +
                URLEncoder.encode(family, StandardCharsets.UTF_8.name()) +
                "/config?mac=" +
                URLEncoder.encode(normalizedMac, StandardCharsets.UTF_8.name());
    }

    public static void fetchConfig(final Context context, final String baseUrl, final String appId, final Callback callback) {
        fetchConfig(context, baseUrl, appId, null, callback);
    }

    public static void fetchConfig(final Context context, final String baseUrl, final String appId, final String macOverride, final Callback callback) {
        if (context == null || callback == null) {
            return;
        }
        EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    String mac = MacAddressProvider.normalize(macOverride);
                    if (mac == null) {
                        mac = MacAddressProvider.getMacAddress(context.getApplicationContext());
                    }
                    if (mac == null) {
                        throw new IllegalStateException("MAC não disponível neste Android; informe o MAC do dispositivo");
                    }
                    String url = buildConfigUrl(baseUrl, appId, mac);
                    String body = getConfig(url);
                    final Config config = new Config(new JSONObject(body), mac);
                    persist(context.getApplicationContext(), config);
                    MAIN.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onSuccess(config);
                        }
                    });
                } catch (final Exception error) {
                    MAIN.post(new Runnable() {
                        @Override
                        public void run() {
                            callback.onFailure(error);
                        }
                    });
                }
            }
        });
    }

    static String getConfig(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        connection.setReadTimeout(READ_TIMEOUT_MS);
        connection.setUseCaches(false);
        connection.setRequestProperty("Accept", "application/json");
        try {
            int status = connection.getResponseCode();
            InputStream stream = status >= 200 && status < 300
                    ? connection.getInputStream() : connection.getErrorStream();
            String body = readUtf8(stream);
            // O painel usa 404 para MAC não cadastrado, mas o JSON ainda é uma resposta de negócio.
            if (status == 404 && body.trim().startsWith("{")) {
                JSONObject json = new JSONObject(body);
                if (json.has("registered")) {
                    return body;
                }
            }
            if (status < 200 || status >= 300) {
                throw new IOException("Painel respondeu HTTP " + status + ": " + body);
            }
            return body;
        } finally {
            connection.disconnect();
        }
    }

    static String get(String url) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        connection.setReadTimeout(READ_TIMEOUT_MS);
        connection.setUseCaches(false);
        connection.setRequestProperty("Accept", "application/json");
        try {
            int status = connection.getResponseCode();
            InputStream stream = status >= 200 && status < 300
                    ? connection.getInputStream() : connection.getErrorStream();
            String body = readUtf8(stream);
            if (status < 200 || status >= 300) {
                throw new IOException("Painel respondeu HTTP " + status + ": " + body);
            }
            return body;
        } finally {
            connection.disconnect();
        }
    }

    static String postJson(String url, JSONObject payload) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
        connection.setRequestMethod("POST");
        connection.setConnectTimeout(CONNECT_TIMEOUT_MS);
        connection.setReadTimeout(READ_TIMEOUT_MS);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setRequestProperty("Accept", "application/json");
        connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        byte[] bytes = payload.toString().getBytes(StandardCharsets.UTF_8);
        connection.setFixedLengthStreamingMode(bytes.length);
        try (OutputStream output = connection.getOutputStream()) {
            output.write(bytes);
        }
        try {
            int status = connection.getResponseCode();
            InputStream stream = status >= 200 && status < 300
                    ? connection.getInputStream() : connection.getErrorStream();
            String body = readUtf8(stream);
            if (status < 200 || status >= 300) {
                throw new IOException("Painel respondeu HTTP " + status + ": " + body);
            }
            return body;
        } finally {
            connection.disconnect();
        }
    }

    static String readUtf8(InputStream stream) throws IOException {
        if (stream == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        }
        return result.toString();
    }

    public static void persist(Context context, Config config) throws Exception {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit();
        editor.putString("MAC_PANEL_CONFIG", config.raw().toString());
        editor.putString("MAC_PANEL_ADDRESS", config.getMac());
        editor.putBoolean("MAC_PANEL_REGISTERED", config.isRegistered());
        editor.putBoolean("MAC_PANEL_ALLOWED", config.isAllowed());
        editor.putString("MAC_PANEL_STATUS", config.getStatus());
        editor.putString("MAC_PANEL_PLAYLIST_URLS", new JSONArray(config.getPlaylistUrls()).toString());
        if (!config.getServerApiUrl().isEmpty()) {
            editor.putString("server_url", config.getServerApiUrl());
        }
        editor.putString("mac_address", config.getMac());
        editor.putBoolean("is_m3u", true);
        editor.apply();

        // Compatibilidade com a tela legada: transforma apenas URLs em AppInfoModel.
        if (config.isRegistered() && config.isAllowed() && !config.getPlaylistUrls().isEmpty()) {
            JSONObject legacy = new JSONObject();
            legacy.put("mac_registered", true);
            legacy.put("mac_address", config.getMac());
            legacy.put("logo", config.getLogoUrl());
            legacy.put("bg", config.getBackgroundUrl());
            legacy.put("apk_link", config.getUpdateUrl());
            legacy.put("theme", "0");
            JSONArray urls = new JSONArray();
            List<String> playlistUrls = config.getPlaylistUrls();
            for (int i = 0; i < playlistUrls.size(); i++) {
                JSONObject item = new JSONObject();
                item.put("id", String.valueOf(i + 1));
                item.put("name", "Lista " + (i + 1));
                item.put("type", "m3u");
                item.put("is_protected", "0");
                item.put("url", playlistUrls.get(i));
                urls.put(item);
            }
            legacy.put("urls", urls);
            editor.putString("APP_INFO_MODEL", legacy.toString());
            editor.apply();
        }
    }
}
