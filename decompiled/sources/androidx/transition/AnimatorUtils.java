package androidx.transition;

import android.animation.Animator;

/* JADX INFO: loaded from: classes2.dex */
class AnimatorUtils {

    /* JADX INFO: loaded from: classes.dex */
    public interface AnimatorPauseListenerCompat {
        void onAnimationPause(Animator animator);

        void onAnimationResume(Animator animator);
    }

    private AnimatorUtils() {
    }
}
