package p029zz;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.tobi.playx.R;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class XplayViewBG extends ImageView {
    public XplayViewBG(Context context) {
        super(context);
        init();
    }

    public XplayViewBG(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public XplayViewBG(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        loadImage();
    }

    public void loadImage() {
        try {
            File file = new File(getContext().getCacheDir(), "image1.jpg");
            if (file.exists()) {
                setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            } else {
                setImageResource(R.drawable.background1);
            }
        } catch (Exception unused) {
            setImageResource(R.drawable.background1);
        }
    }
}
