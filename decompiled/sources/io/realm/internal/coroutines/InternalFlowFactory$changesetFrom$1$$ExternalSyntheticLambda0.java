package io.realm.internal.coroutines;

import io.realm.OrderedCollectionChangeSet;
import io.realm.OrderedRealmCollectionChangeListener;
import io.realm.RealmList;
import io.realm.RealmResults;
import kotlinx.coroutines.channels.ProducerScope;

/* JADX INFO: compiled from: R8$$SyntheticClass */
/* JADX INFO: loaded from: classes2.dex */
public final /* synthetic */ class InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0 implements OrderedRealmCollectionChangeListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ProducerScope f$0;
    public final /* synthetic */ InternalFlowFactory f$1;

    public /* synthetic */ InternalFlowFactory$changesetFrom$1$$ExternalSyntheticLambda0(ProducerScope producerScope, InternalFlowFactory internalFlowFactory, int i) {
        this.$r8$classId = i;
        this.f$0 = producerScope;
        this.f$1 = internalFlowFactory;
    }

    @Override // io.realm.OrderedRealmCollectionChangeListener
    public final void onChange(Object obj, OrderedCollectionChangeSet orderedCollectionChangeSet) {
        switch (this.$r8$classId) {
            case 0:
                InternalFlowFactory.C23231.m642invokeSuspend$lambda0(this.f$0, this.f$1, (RealmResults) obj, orderedCollectionChangeSet);
                break;
            case 1:
                InternalFlowFactory.C23242.m643invokeSuspend$lambda0(this.f$0, this.f$1, (RealmResults) obj, orderedCollectionChangeSet);
                break;
            case 2:
                InternalFlowFactory.C23253.m644invokeSuspend$lambda0(this.f$0, this.f$1, (RealmList) obj, orderedCollectionChangeSet);
                break;
            default:
                InternalFlowFactory.C23264.m646invokeSuspend$lambda0(this.f$0, this.f$1, (RealmList) obj, orderedCollectionChangeSet);
                break;
        }
    }
}
