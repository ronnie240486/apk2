package com.xplay.ibotv.mac;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import org.json.JSONObject;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/** Mantém o aparelho online e sincroniza avisos sem usuário ou senha. */
public final class MacPanelHeartbeat {
    private static final long PERIOD_MS = 60_000L;
    private final Context context;
    private final String mac;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private String currentContent = "";
    private boolean running;

    public MacPanelHeartbeat(Context context, String mac) {
        this.context = context.getApplicationContext();
        this.mac = MacAddressProvider.normalize(mac);
        if (this.mac == null) {
            throw new IllegalArgumentException("MAC inválido");
        }
    }

    public void setCurrentContent(String title) {
        currentContent = title == null ? "" : title;
        if (running) {
            sendHeartbeat();
        }
    }

    public void start() {
        if (running) {
            return;
        }
        running = true;
        sendHeartbeat();
        handler.postDelayed(tick, PERIOD_MS);
    }

    public void stop() {
        running = false;
        handler.removeCallbacks(tick);
        executor.shutdownNow();
    }

    public void reportPlaybackFailure(int activeListNumber) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject payload = new JSONObject();
                    payload.put("mac", mac);
                    payload.put("active_list_number", activeListNumber);
                    MacPanelClient.postJson(MacPanelClient.BASE_URL + "/api/v5/playback-failure", payload);
                } catch (Exception ignored) {
                    // A próxima sincronização preserva o estado local mesmo sem rede.
                }
            }
        });
    }

    public void acknowledgeNotification(final String alertId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject payload = new JSONObject();
                    payload.put("mac", mac);
                    payload.put("alert_id", alertId);
                    MacPanelClient.postJson(MacPanelClient.BASE_URL + "/api/v5/list-notifications/ack", payload);
                } catch (Exception ignored) {
                }
            }
        });
    }

    public void acknowledgeCommand(final String commandId, final String status, final String resultMessage) {
        if (!"executed".equals(status) && !"failed".equals(status)) {
            throw new IllegalArgumentException("status deve ser executed ou failed");
        }
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject payload = new JSONObject();
                    payload.put("mac", mac);
                    payload.put("command_id", commandId);
                    payload.put("status", status);
                    if (resultMessage != null && !resultMessage.isEmpty()) {
                        payload.put("result_message", resultMessage);
                    }
                    MacPanelClient.postJson(MacPanelClient.BASE_URL + "/api/v5/remote-commands/ack", payload);
                } catch (Exception ignored) {
                }
            }
        });
    }

    private final Runnable tick = new Runnable() {
        @Override
        public void run() {
            if (!running) {
                return;
            }
            sendHeartbeat();
            handler.postDelayed(this, PERIOD_MS);
        }
    };

    private void sendHeartbeat() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder heartbeat = new StringBuilder(MacPanelClient.BASE_URL)
                            .append("/api/v5/heartbeat?mac=")
                            .append(URLEncoder.encode(mac, StandardCharsets.UTF_8.name()));
                    if (!currentContent.isEmpty()) {
                        heartbeat.append("&current_content=")
                                .append(URLEncoder.encode(currentContent, StandardCharsets.UTF_8.name()));
                    }
                    MacPanelClient.get(heartbeat.toString());

                    String notifications = MacPanelClient.BASE_URL
                            + "/api/v5/list-notifications?mac="
                            + URLEncoder.encode(mac, StandardCharsets.UTF_8.name());
                    MacPanelClient.get(notifications);
                } catch (Exception ignored) {
                    // Falha transitória: tenta novamente no próximo ciclo.
                }
            }
        });
    }
}
