package com.xplay.ibotv.net;

import com.xplay.ibotv.models.EpisodeModel;
import java.io.IOException;
import java.util.List;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class FetchEpisodeTask extends NetworkTask<Void, Void, List<EpisodeModel>> {
    private LoadEpisodeCommand command;

    @Override // com.xplay.ibotv.net.NetworkTask
    public final List<EpisodeModel> doNetworkAction() throws JSONException, IOException {
        LoadEpisodeCommand loadEpisodeCommand = new LoadEpisodeCommand();
        this.command = loadEpisodeCommand;
        return loadEpisodeCommand.execute();
    }

    @Override // com.xplay.ibotv.net.NetworkTask, android.os.AsyncTask
    public final void onPreExecute() {
    }
}
