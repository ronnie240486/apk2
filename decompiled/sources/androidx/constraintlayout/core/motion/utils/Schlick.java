package androidx.constraintlayout.core.motion.utils;

/* JADX INFO: loaded from: classes.dex */
public class Schlick extends Easing {
    private static final boolean DEBUG = false;

    /* JADX INFO: renamed from: mS */
    public double f31mS;

    /* JADX INFO: renamed from: mT */
    public double f32mT;

    public Schlick(String str) {
        this.str = str;
        int iIndexOf = str.indexOf(40);
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        this.f31mS = Double.parseDouble(str.substring(iIndexOf + 1, iIndexOf2).trim());
        int i = iIndexOf2 + 1;
        this.f32mT = Double.parseDouble(str.substring(i, str.indexOf(44, i)).trim());
    }

    private double dfunc(double d) {
        double d2 = this.f32mT;
        if (d < d2) {
            double d3 = this.f31mS;
            return ((d3 * d2) * d2) / ((((d2 - d) * d3) + d) * (((d2 - d) * d3) + d));
        }
        double d4 = this.f31mS;
        return ((d2 - 1.0d) * ((d2 - 1.0d) * d4)) / (((((d2 - d) * (-d4)) - d) + 1.0d) * ((((d2 - d) * (-d4)) - d) + 1.0d));
    }

    private double func(double d) {
        double d2 = this.f32mT;
        if (d < d2) {
            return (d2 * d) / (((d2 - d) * this.f31mS) + d);
        }
        return ((d - 1.0d) * (1.0d - d2)) / ((1.0d - d) - ((d2 - d) * this.f31mS));
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double get(double d) {
        return func(d);
    }

    @Override // androidx.constraintlayout.core.motion.utils.Easing
    public double getDiff(double d) {
        return dfunc(d);
    }
}
