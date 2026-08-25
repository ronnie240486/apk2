package p029zz;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/* JADX INFO: loaded from: classes2.dex */
public class DownloadImageTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "MainActivity";
    private Context context;
    private String[] urls;

    public DownloadImageTask(Context context, String[] strArr) {
        this.context = context;
        this.urls = strArr;
    }

    private String getFileExtension(String str) {
        int iLastIndexOf = str.lastIndexOf(".");
        return (iLastIndexOf == -1 || iLastIndexOf >= str.length() + (-1)) ? "" : str.substring(iLastIndexOf);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public Boolean doInBackground(Void... voids) throws IOException {
        int i = 0;
        boolean z = true;
        while (true) {
            String[] strArr = this.urls;
            if (i >= strArr.length) {
                return Boolean.valueOf(z);
            }
            String str = strArr[i];
            if (str == null || str.trim().isEmpty()) {
                i++;
            } else {
                BufferedInputStream bufferedInputStream = null;
                FileOutputStream fileOutputStream = null;
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(str).openConnection();
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(15000);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                    fileOutputStream = new FileOutputStream(new File(this.context.getCacheDir(), "image" + i + ".tmp"));
                    byte[] bArr = new byte[1024];
                    while (true) {
                        int i2 = bufferedInputStream.read(bArr);
                        if (i2 == -1) {
                            break;
                        }
                        fileOutputStream.write(bArr, 0, i2);
                    }
                    fileOutputStream.flush();
                } else {
                    z = false;
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
                i++;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean bool) {
        if (!bool.booleanValue()) {
            Log.e(TAG, "Failed to download images.");
            return;
        }
        Log.d(TAG, "Images downloaded successfully!");
        File cacheDir = this.context.getCacheDir();
        for (int i = 0; i < this.urls.length; i++) {
            if (new File(cacheDir, "image" + i + ".tmp").renameTo(new File(cacheDir, "image" + i + ".jpg"))) {
                Log.d(TAG, "Image finalized successfully");
            } else {
                Log.e(TAG, "Failed to finalize image");
            }
        }
    }
}
