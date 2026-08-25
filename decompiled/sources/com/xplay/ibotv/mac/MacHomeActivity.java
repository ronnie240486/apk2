package com.xplay.ibotv.mac;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

import com.xplay.ibotv.activities.HomeActivity;
import com.xplay.ibotv.apps.BaseActivity;
import com.xplay.ibotv.models.WordModels;

/** Carrega a lista devolvida pelo painel e reaproveita o pipeline M3U já existente. */
public final class MacHomeActivity extends BaseActivity {
    private TextView statusView;

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        statusView = new TextView(this);
        statusView.setTextColor(Color.WHITE);
        statusView.setTextSize(18.0f);
        statusView.setGravity(Gravity.CENTER);
        setContentView(statusView);

        String playlist = getIntent().getStringExtra("MAC_PANEL_FIRST_PLAYLIST");
        if (playlist == null || playlist.trim().isEmpty()) {
            statusView.setText("Nenhuma playlist foi recebida do painel.");
            return;
        }
        statusView.setText("Carregando playlist…");
        preferenceHelper = new com.xplay.ibotv.helper.PreferenceHelper(this);
        preferenceHelper.setSharedPreferenceISM3U(true);
        reloadM3UData(playlist, new WordModels());
    }

    @Override
    public void doNextTask(boolean success) {
        if (success) {
            startActivity(new Intent(this, HomeActivity.class));
            finish();
            return;
        }
        if (statusView != null) {
            statusView.setText("Não foi possível carregar a playlist do painel.");
        }
    }
}
