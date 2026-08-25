package com.xplay.ibotv.mac;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/** Entrada sem login: configura o aparelho pelo MAC antes de abrir o player. */
public final class MacGateActivity extends Activity {
    private TextView statusView;
    private EditText macInput;
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

        macInput = new EditText(this);
        macInput.setHint("MAC do dispositivo (AA:BB:CC:DD:EE:FF)");
        macInput.setSingleLine(true);
        macInput.setInputType(InputType.TYPE_CLASS_TEXT);
        macInput.setTextColor(Color.WHITE);
        macInput.setHintTextColor(Color.LTGRAY);
        macInput.setBackgroundColor(Color.DKGRAY);
        String savedMac = getSharedPreferences("PREF", MODE_PRIVATE)
                .getString("MAC_PANEL_MANUAL_MAC", "");
        if (savedMac.isEmpty()) {
            String detectedMac = MacAddressProvider.getMacAddress(this);
            if (detectedMac != null) {
                savedMac = detectedMac;
            }
        }
        macInput.setText(savedMac);

        retryButton = new Button(this);
        retryButton.setText("Conectar com MAC");
        retryButton.setVisibility(View.VISIBLE);
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String manualMac = macInput.getText().toString().trim();
                getSharedPreferences("PREF", MODE_PRIVATE).edit()
                        .putString("MAC_PANEL_MANUAL_MAC", manualMac)
                        .apply();
                loadPanelConfig(manualMac);
            }
        });

        root.addView(statusView, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        root.addView(macInput, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        root.addView(retryButton, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        setContentView(root);
    }

    private void loadPanelConfig() {
        String manualMac = macInput == null ? "" : macInput.getText().toString().trim();
        loadPanelConfig(manualMac);
    }

    private void loadPanelConfig(String manualMac) {
        String normalizedMac = MacAddressProvider.normalize(manualMac);
        statusView.setText(normalizedMac == null
                ? "MAC não detectado automaticamente. Informe o MAC do dispositivo."
                : "MAC detectado: " + normalizedMac + "\nConectando ao painel…");
        retryButton.setVisibility(View.GONE);
        String appId = getSharedPreferences("PREF", MODE_PRIVATE)
                .getString("MAC_PANEL_APP_ID", MacPanelClient.DEFAULT_APP_ID);
        MacPanelClient.fetchConfig(this, MacPanelClient.BASE_URL, appId, manualMac,
                new MacPanelClient.Callback() {
                    @Override
                    public void onSuccess(MacPanelClient.Config config) {
                        if (!config.isRegistered()) {
                            String error = config.getError();
                            showBlocked(error.isEmpty() ? "Aparelho não cadastrado no painel." : error);
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
                        String detail = error.getMessage();
                        if (detail == null || detail.trim().isEmpty()) {
                            detail = "Falha desconhecida";
                        }
                        showBlocked("Falha ao consultar o painel: " + detail);
                    }
                });
    }

    private void showBlocked(String message) {
        statusView.setText(message);
        retryButton.setVisibility(View.VISIBLE);
        if (macInput != null) {
            macInput.setVisibility(View.VISIBLE);
        }
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
