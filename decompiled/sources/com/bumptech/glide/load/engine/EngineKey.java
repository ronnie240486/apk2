package com.bumptech.glide.load.engine;

import androidx.annotation.NonNull;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
class EngineKey implements Key {
    private int hashCode;
    private final int height;
    private final Object model;
    private final Options options;
    private final Class<?> resourceClass;
    private final Key signature;
    private final Class<?> transcodeClass;
    private final Map<Class<?>, Transformation<?>> transformations;
    private final int width;

    public EngineKey(Object obj, Key key, int i, int i2, Map<Class<?>, Transformation<?>> map, Class<?> cls, Class<?> cls2, Options options) {
        this.model = Preconditions.checkNotNull(obj);
        this.signature = (Key) Preconditions.checkNotNull(key, "Signature must not be null");
        this.width = i;
        this.height = i2;
        this.transformations = (Map) Preconditions.checkNotNull(map);
        this.resourceClass = (Class) Preconditions.checkNotNull(cls, "Resource class must not be null");
        this.transcodeClass = (Class) Preconditions.checkNotNull(cls2, "Transcode class must not be null");
        this.options = (Options) Preconditions.checkNotNull(options);
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        if (!(obj instanceof EngineKey)) {
            return false;
        }
        EngineKey engineKey = (EngineKey) obj;
        return this.model.equals(engineKey.model) && this.signature.equals(engineKey.signature) && this.height == engineKey.height && this.width == engineKey.width && this.transformations.equals(engineKey.transformations) && this.resourceClass.equals(engineKey.resourceClass) && this.transcodeClass.equals(engineKey.transcodeClass) && this.options.equals(engineKey.options);
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        if (this.hashCode == 0) {
            int iHashCode = this.model.hashCode();
            this.hashCode = iHashCode;
            int iHashCode2 = ((((this.signature.hashCode() + (iHashCode * 31)) * 31) + this.width) * 31) + this.height;
            this.hashCode = iHashCode2;
            int iHashCode3 = this.transformations.hashCode() + (iHashCode2 * 31);
            this.hashCode = iHashCode3;
            int iHashCode4 = this.resourceClass.hashCode() + (iHashCode3 * 31);
            this.hashCode = iHashCode4;
            int iHashCode5 = this.transcodeClass.hashCode() + (iHashCode4 * 31);
            this.hashCode = iHashCode5;
            this.hashCode = this.options.hashCode() + (iHashCode5 * 31);
        }
        return this.hashCode;
    }

    public String toString() {
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("EngineKey{model=");
        sbM26m.append(this.model);
        sbM26m.append(", width=");
        sbM26m.append(this.width);
        sbM26m.append(", height=");
        sbM26m.append(this.height);
        sbM26m.append(", resourceClass=");
        sbM26m.append(this.resourceClass);
        sbM26m.append(", transcodeClass=");
        sbM26m.append(this.transcodeClass);
        sbM26m.append(", signature=");
        sbM26m.append(this.signature);
        sbM26m.append(", hashCode=");
        sbM26m.append(this.hashCode);
        sbM26m.append(", transformations=");
        sbM26m.append(this.transformations);
        sbM26m.append(", options=");
        sbM26m.append(this.options);
        sbM26m.append('}');
        return sbM26m.toString();
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        throw new UnsupportedOperationException();
    }
}
