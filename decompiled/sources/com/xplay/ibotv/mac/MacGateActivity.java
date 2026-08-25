package com.xplay.ibotv.mac;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xplay.ibotv.MainActivity;
import com.xplay.ibotv.MainTVActivity;

/** Entrada sem login: configura o aparelho pelo MAC antes de abrir o player. */
public final class MacGateActivity extends Activity {
    private TextView statusView;
    private Button retryButton;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        setTitle("Tobi Play");
        buildView();
        loadPanelConfig();
    }

    private void buildView() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER);
        root.setBackgroundColor(Color.BLACK);
        root.setPadding(48, 48, 48, 48);

        statusView = new TextView(this);
        statusView.setTextColor(Color.WHITE);
        statusView.setTextSize(18.0f);
        statusView.setGravity(Gravity.CENTER);

        retryButton = new Button(this);
        retryButton.setText("Tentar novamente");
        retryButton.setVisibility(View.GONE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadPanelConfig();
            }
        });

        root.addView(statusView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        root.addView(retryButton, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        setContentView(root);
    }

    private void loadPanelConfig() {
        statusView.setText("Conectando ao painel…");
        retryButton.setVisibility(View.GONE);
        String appId = getSharedPreferences("PREF", MODE_PRIVATE)
                .getString("MAC_PANEL_APP_ID", MacPanelClient.DEFAULT_APP_ID);
        MacPanelClient.fetchConfig(this, MacPanelClient.BASE_URL, appId,
                new MacPanelClient.Callback() {
                    @Override
                    public void onSuccess(MacPanelClient.Config config) {
                        if (!config.isRegistered()) {
                            showBlocked("Aparelho não cadastrado no painel.");
                            return;
                        }
                        if (!config.isAllowed()) {
                            String message = config.getBlockMessage();
                            showBlocked(message.isEmpty() ? "Aparelho bloqueado ou expirado." : message);
                            return;
                        }
                        openPlayer(config);
                    }

                    @Override
                    public void onFailure(Exception error) {
                        showBlocked("Não foi possível consultar o painel. Verifique a rede.");
                    }
                });
    }

    private void showBlocked(String message) {
        statusView.setText(message);
        retryButton.setVisibility(View.VISIBLE);
    }

    private void openPlayer(MacPanelClient.Config config) {
        if (config.getPlaylistUrls().isEmpty()) {
            showBlocked("O painel não devolveu nenhuma playlist ativa.");
            return;
        }
        Intent next = new Intent(this, MacHomeActivity.class);
        next.putExtra("MAC_PANEL_FIRST_PLAYLIST", config.getPlaylistUrls().get(0));
        next.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(next);
        finish();
    }
}
