package io.realm.mongodb.sync;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;

/* JADX INFO: loaded from: classes.dex */
public enum SubscriptionSet$State {
    UNCOMMITTED((byte) 0),
    PENDING((byte) 1),
    BOOTSTRAPPING((byte) 2),
    COMPLETE((byte) 3),
    ERROR((byte) 4),
    SUPERSEDED((byte) 5);

    private final byte value;

    SubscriptionSet$State(byte b2) {
        this.value = b2;
    }

    public static SubscriptionSet$State fromNativeValue(long j) {
        for (SubscriptionSet$State subscriptionSet$State : values()) {
            if (subscriptionSet$State.value == j) {
                return subscriptionSet$State;
            }
        }
        throw new IllegalArgumentException(Insets$$ExternalSyntheticOutline0.m17m("Unknown SubscriptionSetState code: ", j));
    }
}
