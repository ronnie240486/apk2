package p029zz;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.tobi.playx.R;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class XplayView extends ImageView {
    public XplayView(Context context) {
        super(context);
        init();
    }

    public XplayView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public XplayView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        loadImage();
    }

    public void loadImage() {
        try {
            File file = new File(getContext().getCacheDir(), "image0.jpg");
            if (file.exists()) {
                setImageBitmap(BitmapFactory.decodeFile(file.getAbsolutePath()));
            } else {
                setImageResource(R.drawable.home_logo);
            }
        } catch (Exception unused) {
            setImageResource(R.drawable.home_logo);
        }
    }
}
