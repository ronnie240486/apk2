package androidx.media;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.media.MediaBrowserService;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.collection.ArrayMap;
import androidx.core.app.BundleCompat;
import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import androidx.core.util.Pair;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public abstract class MediaBrowserServiceCompat extends Service {
    public static final boolean DEBUG = Log.isLoggable("MBServiceCompat", 3);
    private static final float EPSILON = 1.0E-5f;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_MEDIA_ITEM = "media_item";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final String KEY_SEARCH_RESULTS = "search_results";

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_ERROR = -1;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_OK = 0;

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public static final int RESULT_PROGRESS_UPDATE = 1;
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    public ConnectionRecord mCurConnection;
    private MediaBrowserServiceImpl mImpl;
    public MediaSessionCompat.Token mSession;
    public final ConnectionRecord mConnectionFromFwk = new ConnectionRecord(MediaSessionManager.RemoteUserInfo.LEGACY_CONTROLLER, -1, -1, null, null);
    public final ArrayList<ConnectionRecord> mPendingConnections = new ArrayList<>();
    public final ArrayMap<IBinder, ConnectionRecord> mConnections = new ArrayMap<>();
    public final ServiceHandler mHandler = new ServiceHandler(this);

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";

        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        private final Bundle mExtras;
        private final String mRootId;

        public BrowserRoot(@NonNull String rootId, @Nullable Bundle extras) {
            if (rootId == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead");
            }
            this.mRootId = rootId;
            this.mExtras = extras;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public String getRootId() {
            return this.mRootId;
        }
    }

    /* JADX INFO: loaded from: classes2.dex */
    public class ConnectionRecord implements IBinder.DeathRecipient {
        public final MediaSessionManager.RemoteUserInfo browserInfo;
        public final ServiceCallbacks callbacks;
        public final int pid;
        public final String pkg;
        public BrowserRoot root;
        public final Bundle rootHints;
        public final HashMap<String, List<Pair<IBinder, Bundle>>> subscriptions = new HashMap<>();
        public final int uid;

        public ConnectionRecord(String pkg, int pid, int uid, Bundle rootHints, ServiceCallbacks callback) {
            this.pkg = pkg;
            this.pid = pid;
            this.uid = uid;
            this.browserInfo = new MediaSessionManager.RemoteUserInfo(pkg, pid, uid);
            this.rootHints = rootHints;
            this.callbacks = callback;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ConnectionRecord.1
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = ConnectionRecord.this;
                    MediaBrowserServiceCompat.this.mConnections.remove(connectionRecord.callbacks.asBinder());
                }
            });
        }
    }

    /* JADX INFO: loaded from: classes2.dex */
    public interface MediaBrowserServiceImpl {
        Bundle getBrowserRootHints();

        MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo();

        void notifyChildrenChanged(MediaSessionManager.RemoteUserInfo remoteUserInfo, String parentId, Bundle options);

        void notifyChildrenChanged(String parentId, Bundle options);

        IBinder onBind(Intent intent);

        void onCreate();

        void setSessionToken(MediaSessionCompat.Token token);
    }

    /* JADX INFO: loaded from: classes3.dex */
    @RequiresApi(23)
    public class MediaBrowserServiceImplApi23 extends MediaBrowserServiceImplApi21 {

        /* JADX INFO: loaded from: classes.dex */
        public class MediaBrowserServiceApi23 extends MediaBrowserServiceImplApi21.MediaBrowserServiceApi21 {
            public MediaBrowserServiceApi23(Context context) {
                super(context);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadItem(String itemId, MediaBrowserService.Result<MediaBrowser.MediaItem> result) {
                MediaBrowserServiceImplApi23.this.onLoadItem(itemId, new ResultWrapper<>(result));
            }
        }

        public MediaBrowserServiceImplApi23() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            MediaBrowserServiceApi23 mediaBrowserServiceApi23 = new MediaBrowserServiceApi23(MediaBrowserServiceCompat.this);
            this.mServiceFwk = mediaBrowserServiceApi23;
            mediaBrowserServiceApi23.onCreate();
        }

        public void onLoadItem(String itemId, final ResultWrapper<Parcel> resultWrapper) {
            Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(itemId) { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi23.1
                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    resultWrapper.detach();
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public final void onResultSent(@Nullable MediaBrowserCompat.MediaItem item) {
                    MediaBrowserCompat.MediaItem mediaItem = item;
                    if (mediaItem == null) {
                        resultWrapper.sendResult(null);
                        return;
                    }
                    Parcel parcelObtain = Parcel.obtain();
                    mediaItem.writeToParcel(parcelObtain, 0);
                    resultWrapper.sendResult(parcelObtain);
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            mediaBrowserServiceCompat.onLoadItem(itemId, result);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }
    }

    /* JADX INFO: loaded from: classes3.dex */
    @RequiresApi(26)
    public class MediaBrowserServiceImplApi26 extends MediaBrowserServiceImplApi23 {

        /* JADX INFO: loaded from: classes.dex */
        public class MediaBrowserServiceApi26 extends MediaBrowserServiceImplApi23.MediaBrowserServiceApi23 {
            public MediaBrowserServiceApi26(Context context) {
                super(context);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadChildren(String parentId, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result, Bundle options) {
                MediaSessionCompat.ensureClassLoader(options);
                MediaBrowserServiceImplApi26 mediaBrowserServiceImplApi26 = MediaBrowserServiceImplApi26.this;
                MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
                mediaBrowserServiceImplApi26.onLoadChildren(parentId, new ResultWrapper<>(result), options);
                MediaBrowserServiceCompat.this.mCurConnection = null;
            }
        }

        public MediaBrowserServiceImplApi26() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public Bundle getBrowserRootHints() {
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            ConnectionRecord connectionRecord = mediaBrowserServiceCompat.mCurConnection;
            if (connectionRecord == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            }
            if (connectionRecord == mediaBrowserServiceCompat.mConnectionFromFwk) {
                return this.mServiceFwk.getBrowserRootHints();
            }
            if (connectionRecord.rootHints == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21
        public final void notifyChildrenChangedForFramework(final String parentId, final Bundle options) {
            if (options != null) {
                this.mServiceFwk.notifyChildrenChanged(parentId, options);
            } else {
                this.mServiceFwk.notifyChildrenChanged(parentId);
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi23, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            MediaBrowserServiceApi26 mediaBrowserServiceApi26 = new MediaBrowserServiceApi26(MediaBrowserServiceCompat.this);
            this.mServiceFwk = mediaBrowserServiceApi26;
            mediaBrowserServiceApi26.onCreate();
        }

        public void onLoadChildren(String parentId, final ResultWrapper<List<Parcel>> resultWrapper, final Bundle options) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi26.1
                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    resultWrapper.detach();
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                    List<MediaBrowserCompat.MediaItem> listApplyOptions = list;
                    if (listApplyOptions == null) {
                        resultWrapper.sendResult(null);
                        return;
                    }
                    if ((getFlags() & 1) != 0) {
                        listApplyOptions = MediaBrowserServiceCompat.this.applyOptions(listApplyOptions, options);
                    }
                    ArrayList arrayList = new ArrayList();
                    for (MediaBrowserCompat.MediaItem mediaItem : listApplyOptions) {
                        Parcel parcelObtain = Parcel.obtain();
                        mediaItem.writeToParcel(parcelObtain, 0);
                        arrayList.add(parcelObtain);
                    }
                    resultWrapper.sendResult(arrayList);
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            mediaBrowserServiceCompat.onLoadChildren(parentId, result, options);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }
    }

    /* JADX INFO: loaded from: classes3.dex */
    @RequiresApi(28)
    public class MediaBrowserServiceImplApi28 extends MediaBrowserServiceImplApi26 {
        public MediaBrowserServiceImplApi28() {
            super();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21, androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            ConnectionRecord connectionRecord = mediaBrowserServiceCompat.mCurConnection;
            if (connectionRecord != null) {
                return connectionRecord == mediaBrowserServiceCompat.mConnectionFromFwk ? new MediaSessionManager.RemoteUserInfo(this.mServiceFwk.getCurrentBrowserInfo()) : connectionRecord.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }
    }

    /* JADX INFO: loaded from: classes2.dex */
    public class MediaBrowserServiceImplBase implements MediaBrowserServiceImpl {
        private Messenger mMessenger;
        public final /* synthetic */ MediaBrowserServiceCompat this$0;

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public Bundle getBrowserRootHints() {
            ConnectionRecord connectionRecord = this.this$0.mCurConnection;
            if (connectionRecord == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            }
            if (connectionRecord.rootHints == null) {
                return null;
            }
            return new Bundle(this.this$0.mCurConnection.rootHints);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            ConnectionRecord connectionRecord = this.this$0.mCurConnection;
            if (connectionRecord != null) {
                return connectionRecord.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(@NonNull final String parentId, final Bundle options) {
            this.this$0.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.2
                @Override // java.lang.Runnable
                public void run() {
                    Iterator<IBinder> it = MediaBrowserServiceImplBase.this.this$0.mConnections.keySet().iterator();
                    while (it.hasNext()) {
                        MediaBrowserServiceImplBase.this.notifyChildrenChangedOnHandler(MediaBrowserServiceImplBase.this.this$0.mConnections.get(it.next()), parentId, options);
                    }
                }
            });
        }

        public final void notifyChildrenChangedOnHandler(final ConnectionRecord connection, final String parentId, final Bundle options) {
            List<Pair<IBinder, Bundle>> list = connection.subscriptions.get(parentId);
            if (list != null) {
                for (Pair<IBinder, Bundle> pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(options, pair.second)) {
                        this.this$0.performLoadChildren(parentId, connection, pair.second, options);
                    }
                }
            }
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public IBinder onBind(Intent intent) {
            if (MediaBrowserServiceCompat.SERVICE_INTERFACE.equals(intent.getAction())) {
                return this.mMessenger.getBinder();
            }
            return null;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            this.mMessenger = new Messenger(this.this$0.mHandler);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void setSessionToken(final MediaSessionCompat.Token token) {
            this.this$0.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.1
                @Override // java.lang.Runnable
                public void run() {
                    Iterator<ConnectionRecord> it = MediaBrowserServiceImplBase.this.this$0.mConnections.values().iterator();
                    while (it.hasNext()) {
                        ConnectionRecord next = it.next();
                        try {
                            next.callbacks.onConnect(next.root.getRootId(), token, next.root.getExtras());
                        } catch (RemoteException unused) {
                            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Connection for ");
                            sbM26m.append(next.pkg);
                            sbM26m.append(" is no longer valid.");
                            Log.w("MBServiceCompat", sbM26m.toString());
                            it.remove();
                        }
                    }
                }
            });
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(@NonNull final MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull final String parentId, final Bundle options) {
            this.this$0.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplBase.3
                @Override // java.lang.Runnable
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceImplBase.this.this$0.mConnections.size(); i++) {
                        ConnectionRecord connectionRecordValueAt = MediaBrowserServiceImplBase.this.this$0.mConnections.valueAt(i);
                        if (connectionRecordValueAt.browserInfo.equals(remoteUserInfo)) {
                            MediaBrowserServiceImplBase.this.notifyChildrenChangedOnHandler(connectionRecordValueAt, parentId, options);
                            return;
                        }
                    }
                }
            });
        }
    }

    public static class Result<T> {
        private final Object mDebug;
        private boolean mDetachCalled;
        private int mFlags;
        private boolean mSendErrorCalled;
        private boolean mSendResultCalled;

        public Result(Object debug) {
            this.mDebug = debug;
        }

        private void checkExtraFields(@Nullable Bundle extras) {
            if (extras != null && extras.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                float f = extras.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS);
                if (f < -1.0E-5f || f > 1.00001f) {
                    throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0]");
                }
            }
        }

        public void detach() {
            if (this.mDetachCalled) {
                StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("detach() called when detach() had already been called for: ");
                sbM26m.append(this.mDebug);
                throw new IllegalStateException(sbM26m.toString());
            }
            if (this.mSendResultCalled) {
                StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("detach() called when sendResult() had already been called for: ");
                sbM26m2.append(this.mDebug);
                throw new IllegalStateException(sbM26m2.toString());
            }
            if (!this.mSendErrorCalled) {
                this.mDetachCalled = true;
            } else {
                StringBuilder sbM26m3 = Insets$$ExternalSyntheticOutline0.m26m("detach() called when sendError() had already been called for: ");
                sbM26m3.append(this.mDebug);
                throw new IllegalStateException(sbM26m3.toString());
            }
        }

        public final int getFlags() {
            return this.mFlags;
        }

        public final boolean isDone() {
            return this.mDetachCalled || this.mSendResultCalled || this.mSendErrorCalled;
        }

        public void onErrorSent(@Nullable Bundle extras) {
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("It is not supported to send an error for ");
            sbM26m.append(this.mDebug);
            throw new UnsupportedOperationException(sbM26m.toString());
        }

        public void onProgressUpdateSent(@Nullable Bundle extras) {
            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("It is not supported to send an interim update for ");
            sbM26m.append(this.mDebug);
            throw new UnsupportedOperationException(sbM26m.toString());
        }

        public void onResultSent(@Nullable T result) {
        }

        public void sendError(@Nullable Bundle extras) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("sendError() called when either sendResult() or sendError() had already been called for: ");
                sbM26m.append(this.mDebug);
                throw new IllegalStateException(sbM26m.toString());
            }
            this.mSendErrorCalled = true;
            onErrorSent(extras);
        }

        public void sendProgressUpdate(@Nullable Bundle extras) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: ");
                sbM26m.append(this.mDebug);
                throw new IllegalStateException(sbM26m.toString());
            }
            checkExtraFields(extras);
            onProgressUpdateSent(extras);
        }

        public void sendResult(@Nullable T result) {
            if (this.mSendResultCalled || this.mSendErrorCalled) {
                StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("sendResult() called when either sendResult() or sendError() had already been called for: ");
                sbM26m.append(this.mDebug);
                throw new IllegalStateException(sbM26m.toString());
            }
            this.mSendResultCalled = true;
            onResultSent(result);
        }

        public final void setFlags(int flags) {
            this.mFlags = flags;
        }
    }

    @RequiresApi(21)
    public static class ResultWrapper<T> {
        public MediaBrowserService.Result mResultFwk;

        public ResultWrapper(MediaBrowserService.Result result) {
            this.mResultFwk = result;
        }

        public void detach() {
            this.mResultFwk.detach();
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void sendResult(T result) {
            ArrayList arrayList = null;
            if (!(result instanceof List)) {
                if (!(result instanceof Parcel)) {
                    this.mResultFwk.sendResult(null);
                    return;
                }
                Parcel parcel = (Parcel) result;
                parcel.setDataPosition(0);
                this.mResultFwk.sendResult(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                parcel.recycle();
                return;
            }
            MediaBrowserService.Result result2 = this.mResultFwk;
            List<Parcel> list = (List) result;
            if (list != null) {
                arrayList = new ArrayList();
                for (Parcel parcel2 : list) {
                    parcel2.setDataPosition(0);
                    arrayList.add((MediaBrowser.MediaItem) MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel2));
                    parcel2.recycle();
                }
            }
            result2.sendResult(arrayList);
        }
    }

    public class ServiceBinderImpl {
        public ServiceBinderImpl() {
        }

        public void addSubscription(final String id, final IBinder token, final Bundle options, final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.3
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(callbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("addSubscription for callback that isn't registered id=");
                        sbM26m.append(id);
                        Log.w("MBServiceCompat", sbM26m.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str = id;
                    IBinder iBinder = token;
                    Bundle bundle = options;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    List<Pair<IBinder, Bundle>> arrayList = connectionRecord.subscriptions.get(str);
                    if (arrayList == null) {
                        arrayList = new ArrayList<>();
                    }
                    for (Pair<IBinder, Bundle> pair : arrayList) {
                        if (iBinder == pair.first && MediaBrowserCompatUtils.areSameOptions(bundle, pair.second)) {
                            return;
                        }
                    }
                    arrayList.add(new Pair<>(iBinder, bundle));
                    connectionRecord.subscriptions.put(str, arrayList);
                    mediaBrowserServiceCompat.performLoadChildren(str, connectionRecord, bundle, null);
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onSubscribe(str, bundle);
                    mediaBrowserServiceCompat.mCurConnection = null;
                }
            });
        }

        public void connect(final String pkg, final int pid, final int uid, final Bundle rootHints, final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            Objects.requireNonNull(mediaBrowserServiceCompat);
            boolean z = false;
            if (pkg != null) {
                for (String str : mediaBrowserServiceCompat.getPackageManager().getPackagesForUid(uid)) {
                    if (str.equals(pkg)) {
                        z = true;
                        break;
                    }
                }
            }
            if (z) {
                MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.1
                    @Override // java.lang.Runnable
                    public void run() {
                        IBinder iBinderAsBinder = callbacks.asBinder();
                        MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                        ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.new ConnectionRecord(pkg, pid, uid, rootHints, callbacks);
                        MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
                        mediaBrowserServiceCompat2.mCurConnection = connectionRecord;
                        BrowserRoot browserRootOnGetRoot = mediaBrowserServiceCompat2.onGetRoot(pkg, uid, rootHints);
                        connectionRecord.root = browserRootOnGetRoot;
                        MediaBrowserServiceCompat mediaBrowserServiceCompat3 = MediaBrowserServiceCompat.this;
                        mediaBrowserServiceCompat3.mCurConnection = null;
                        if (browserRootOnGetRoot == null) {
                            StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("No root for client ");
                            sbM26m.append(pkg);
                            sbM26m.append(" from service ");
                            sbM26m.append(getClass().getName());
                            Log.i("MBServiceCompat", sbM26m.toString());
                            try {
                                callbacks.onConnectFailed();
                                return;
                            } catch (RemoteException unused) {
                                StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("Calling onConnectFailed() failed. Ignoring. pkg=");
                                sbM26m2.append(pkg);
                                Log.w("MBServiceCompat", sbM26m2.toString());
                                return;
                            }
                        }
                        try {
                            mediaBrowserServiceCompat3.mConnections.put(iBinderAsBinder, connectionRecord);
                            iBinderAsBinder.linkToDeath(connectionRecord, 0);
                            if (MediaBrowserServiceCompat.this.mSession != null) {
                                callbacks.onConnect(connectionRecord.root.getRootId(), MediaBrowserServiceCompat.this.mSession, connectionRecord.root.getExtras());
                            }
                        } catch (RemoteException unused2) {
                            StringBuilder sbM26m3 = Insets$$ExternalSyntheticOutline0.m26m("Calling onConnect() failed. Dropping client. pkg=");
                            sbM26m3.append(pkg);
                            Log.w("MBServiceCompat", sbM26m3.toString());
                            MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                        }
                    }
                });
                return;
            }
            throw new IllegalArgumentException("Package/uid mismatch: uid=" + uid + " package=" + pkg);
        }

        public void disconnect(final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.2
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecordRemove = MediaBrowserServiceCompat.this.mConnections.remove(callbacks.asBinder());
                    if (connectionRecordRemove != null) {
                        connectionRecordRemove.callbacks.asBinder().unlinkToDeath(connectionRecordRemove, 0);
                    }
                }
            });
        }

        public void getMediaItem(final String mediaId, final ResultReceiver receiver, final ServiceCallbacks callbacks) {
            if (TextUtils.isEmpty(mediaId) || receiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.5
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(callbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("getMediaItem for callback that isn't registered id=");
                        sbM26m.append(mediaId);
                        Log.w("MBServiceCompat", sbM26m.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str = mediaId;
                    final ResultReceiver resultReceiver = receiver;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    Result<MediaBrowserCompat.MediaItem> result = new Result<MediaBrowserCompat.MediaItem>(str) { // from class: androidx.media.MediaBrowserServiceCompat.2
                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onResultSent(@Nullable MediaBrowserCompat.MediaItem item) {
                            MediaBrowserCompat.MediaItem mediaItem = item;
                            if ((getFlags() & 2) != 0) {
                                resultReceiver.send(-1, null);
                                return;
                            }
                            Bundle bundle = new Bundle();
                            bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, mediaItem);
                            resultReceiver.send(0, bundle);
                        }
                    };
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onLoadItem(str, result);
                    mediaBrowserServiceCompat.mCurConnection = null;
                    if (!result.isDone()) {
                        throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m19m("onLoadItem must call detach() or sendResult() before returning for id=", str));
                    }
                }
            });
        }

        public void registerCallbacks(final ServiceCallbacks callbacks, final String pkg, final int pid, final int uid, final Bundle rootHints) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.6
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord;
                    IBinder iBinderAsBinder = callbacks.asBinder();
                    MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                    Iterator<ConnectionRecord> it = MediaBrowserServiceCompat.this.mPendingConnections.iterator();
                    while (true) {
                        connectionRecord = null;
                        if (!it.hasNext()) {
                            break;
                        }
                        ConnectionRecord next = it.next();
                        if (next.uid == uid) {
                            connectionRecord = (TextUtils.isEmpty(pkg) || pid <= 0) ? MediaBrowserServiceCompat.this.new ConnectionRecord(next.pkg, next.pid, next.uid, rootHints, callbacks) : null;
                            it.remove();
                            break;
                        }
                    }
                    if (connectionRecord == null) {
                        connectionRecord = MediaBrowserServiceCompat.this.new ConnectionRecord(pkg, pid, uid, rootHints, callbacks);
                    }
                    MediaBrowserServiceCompat.this.mConnections.put(iBinderAsBinder, connectionRecord);
                    try {
                        iBinderAsBinder.linkToDeath(connectionRecord, 0);
                    } catch (RemoteException unused) {
                        Log.w("MBServiceCompat", "IBinder is already dead.");
                    }
                }
            });
        }

        public void removeSubscription(final String id, final IBinder token, final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.4
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(callbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("removeSubscription for callback that isn't registered id=");
                        sbM26m.append(id);
                        Log.w("MBServiceCompat", sbM26m.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str = id;
                    IBinder iBinder = token;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    boolean z = false;
                    try {
                        if (iBinder != null) {
                            List<Pair<IBinder, Bundle>> list = connectionRecord.subscriptions.get(str);
                            if (list != null) {
                                Iterator<Pair<IBinder, Bundle>> it = list.iterator();
                                while (it.hasNext()) {
                                    if (iBinder == it.next().first) {
                                        it.remove();
                                        z = true;
                                    }
                                }
                                if (list.size() == 0) {
                                    connectionRecord.subscriptions.remove(str);
                                }
                            }
                        } else if (connectionRecord.subscriptions.remove(str) != null) {
                            z = true;
                        }
                        mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                        mediaBrowserServiceCompat.onUnsubscribe(str);
                        mediaBrowserServiceCompat.mCurConnection = null;
                        if (z) {
                            return;
                        }
                        StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("removeSubscription called for ");
                        sbM26m2.append(id);
                        sbM26m2.append(" which is not subscribed");
                        Log.w("MBServiceCompat", sbM26m2.toString());
                    } catch (Throwable th) {
                        mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                        mediaBrowserServiceCompat.onUnsubscribe(str);
                        mediaBrowserServiceCompat.mCurConnection = null;
                        throw th;
                    }
                }
            });
        }

        public void search(final String query, final Bundle extras, final ResultReceiver receiver, final ServiceCallbacks callbacks) {
            if (TextUtils.isEmpty(query) || receiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.8
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(callbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("search for callback that isn't registered query=");
                        sbM26m.append(query);
                        Log.w("MBServiceCompat", sbM26m.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str = query;
                    Bundle bundle = extras;
                    final ResultReceiver resultReceiver = receiver;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(str) { // from class: androidx.media.MediaBrowserServiceCompat.3
                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> items) {
                            List<MediaBrowserCompat.MediaItem> list = items;
                            if ((getFlags() & 4) != 0 || list == null) {
                                resultReceiver.send(-1, null);
                                return;
                            }
                            Bundle bundle2 = new Bundle();
                            bundle2.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[]) list.toArray(new MediaBrowserCompat.MediaItem[0]));
                            resultReceiver.send(0, bundle2);
                        }
                    };
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onSearch(str, bundle, result);
                    mediaBrowserServiceCompat.mCurConnection = null;
                    if (!result.isDone()) {
                        throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m19m("onSearch must call detach() or sendResult() before returning for query=", str));
                    }
                }
            });
        }

        public void sendCustomAction(final String action, final Bundle extras, final ResultReceiver receiver, final ServiceCallbacks callbacks) {
            if (TextUtils.isEmpty(action) || receiver == null) {
                return;
            }
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.9
                @Override // java.lang.Runnable
                public void run() {
                    ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mConnections.get(callbacks.asBinder());
                    if (connectionRecord == null) {
                        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("sendCustomAction for callback that isn't registered action=");
                        sbM26m.append(action);
                        sbM26m.append(", extras=");
                        sbM26m.append(extras);
                        Log.w("MBServiceCompat", sbM26m.toString());
                        return;
                    }
                    MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
                    String str = action;
                    Bundle bundle = extras;
                    final ResultReceiver resultReceiver = receiver;
                    Objects.requireNonNull(mediaBrowserServiceCompat);
                    Result<Bundle> result = new Result<Bundle>(str) { // from class: androidx.media.MediaBrowserServiceCompat.4
                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onErrorSent(@Nullable Bundle data) {
                            resultReceiver.send(-1, data);
                        }

                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onProgressUpdateSent(@Nullable Bundle data) {
                            resultReceiver.send(1, data);
                        }

                        @Override // androidx.media.MediaBrowserServiceCompat.Result
                        public final void onResultSent(@Nullable Bundle result2) {
                            resultReceiver.send(0, result2);
                        }
                    };
                    mediaBrowserServiceCompat.mCurConnection = connectionRecord;
                    mediaBrowserServiceCompat.onCustomAction(str, bundle, result);
                    mediaBrowserServiceCompat.mCurConnection = null;
                    if (result.isDone()) {
                        return;
                    }
                    throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + str + " extras=" + bundle);
                }
            });
        }

        public void unregisterCallbacks(final ServiceCallbacks callbacks) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.ServiceBinderImpl.7
                @Override // java.lang.Runnable
                public void run() {
                    IBinder iBinderAsBinder = callbacks.asBinder();
                    ConnectionRecord connectionRecordRemove = MediaBrowserServiceCompat.this.mConnections.remove(iBinderAsBinder);
                    if (connectionRecordRemove != null) {
                        iBinderAsBinder.unlinkToDeath(connectionRecordRemove, 0);
                    }
                }
            });
        }
    }

    /* JADX INFO: loaded from: classes2.dex */
    public interface ServiceCallbacks {
        IBinder asBinder();

        void onConnect(String root, MediaSessionCompat.Token session, Bundle extras) throws RemoteException;

        void onConnectFailed() throws RemoteException;

        void onLoadChildren(String mediaId, List<MediaBrowserCompat.MediaItem> list, Bundle options, Bundle notifyChildrenChangedOptions) throws RemoteException;
    }

    public static class ServiceCallbacksCompat implements ServiceCallbacks {
        public final Messenger mCallbacks;

        public ServiceCallbacksCompat(Messenger callbacks) {
            this.mCallbacks = callbacks;
        }

        private void sendRequest(int what, Bundle data) throws RemoteException {
            Message messageObtain = Message.obtain();
            messageObtain.what = what;
            messageObtain.arg1 = 2;
            messageObtain.setData(data);
            this.mCallbacks.send(messageObtain);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public IBinder asBinder() {
            return this.mCallbacks.getBinder();
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onConnect(String root, MediaSessionCompat.Token session, Bundle extras) throws RemoteException {
            if (extras == null) {
                extras = new Bundle();
            }
            extras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
            Bundle bundle = new Bundle();
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, root);
            bundle.putParcelable(MediaBrowserProtocol.DATA_MEDIA_SESSION_TOKEN, session);
            bundle.putBundle(MediaBrowserProtocol.DATA_ROOT_HINTS, extras);
            sendRequest(1, bundle);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onConnectFailed() throws RemoteException {
            sendRequest(2, null);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.ServiceCallbacks
        public void onLoadChildren(String mediaId, List<MediaBrowserCompat.MediaItem> list, Bundle options, Bundle notifyChildrenChangedOptions) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID, mediaId);
            bundle.putBundle(MediaBrowserProtocol.DATA_OPTIONS, options);
            bundle.putBundle(MediaBrowserProtocol.DATA_NOTIFY_CHILDREN_CHANGED_OPTIONS, notifyChildrenChangedOptions);
            if (list != null) {
                bundle.putParcelableArrayList(MediaBrowserProtocol.DATA_MEDIA_ITEM_LIST, list instanceof ArrayList ? (ArrayList) list : new ArrayList<>(list));
            }
            sendRequest(3, bundle);
        }
    }

    public final class ServiceHandler extends Handler {
        private final ServiceBinderImpl mServiceBinderImpl;

        public ServiceHandler(final MediaBrowserServiceCompat this$0) {
            this.mServiceBinderImpl = this$0.new ServiceBinderImpl();
        }

        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            Bundle data = msg.getData();
            switch (msg.what) {
                case 1:
                    Bundle bundle = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(bundle);
                    this.mServiceBinderImpl.connect(data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), bundle, new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 2:
                    this.mServiceBinderImpl.disconnect(new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 3:
                    Bundle bundle2 = data.getBundle(MediaBrowserProtocol.DATA_OPTIONS);
                    MediaSessionCompat.ensureClassLoader(bundle2);
                    this.mServiceBinderImpl.addSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), bundle2, new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 4:
                    this.mServiceBinderImpl.removeSubscription(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), BundleCompat.getBinder(data, MediaBrowserProtocol.DATA_CALLBACK_TOKEN), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 5:
                    this.mServiceBinderImpl.getMediaItem(data.getString(MediaBrowserProtocol.DATA_MEDIA_ITEM_ID), (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 6:
                    Bundle bundle3 = data.getBundle(MediaBrowserProtocol.DATA_ROOT_HINTS);
                    MediaSessionCompat.ensureClassLoader(bundle3);
                    this.mServiceBinderImpl.registerCallbacks(new ServiceCallbacksCompat(msg.replyTo), data.getString(MediaBrowserProtocol.DATA_PACKAGE_NAME), data.getInt(MediaBrowserProtocol.DATA_CALLING_PID), data.getInt(MediaBrowserProtocol.DATA_CALLING_UID), bundle3);
                    break;
                case 7:
                    this.mServiceBinderImpl.unregisterCallbacks(new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 8:
                    Bundle bundle4 = data.getBundle(MediaBrowserProtocol.DATA_SEARCH_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(bundle4);
                    this.mServiceBinderImpl.search(data.getString(MediaBrowserProtocol.DATA_SEARCH_QUERY), bundle4, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                case 9:
                    Bundle bundle5 = data.getBundle(MediaBrowserProtocol.DATA_CUSTOM_ACTION_EXTRAS);
                    MediaSessionCompat.ensureClassLoader(bundle5);
                    this.mServiceBinderImpl.sendCustomAction(data.getString(MediaBrowserProtocol.DATA_CUSTOM_ACTION), bundle5, (ResultReceiver) data.getParcelable(MediaBrowserProtocol.DATA_RESULT_RECEIVER), new ServiceCallbacksCompat(msg.replyTo));
                    break;
                default:
                    Log.w("MBServiceCompat", "Unhandled message: " + msg + "\n  Service version: 2\n  Client version: " + msg.arg1);
                    break;
            }
        }

        public void postOrRun(Runnable r) {
            if (Thread.currentThread() == getLooper().getThread()) {
                r.run();
            } else {
                post(r);
            }
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
            Bundle data = msg.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt(MediaBrowserProtocol.DATA_CALLING_UID, Binder.getCallingUid());
            int callingPid = Binder.getCallingPid();
            if (callingPid > 0) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, callingPid);
            } else if (!data.containsKey(MediaBrowserProtocol.DATA_CALLING_PID)) {
                data.putInt(MediaBrowserProtocol.DATA_CALLING_PID, -1);
            }
            return super.sendMessageAtTime(msg, uptimeMillis);
        }
    }

    public final List<MediaBrowserCompat.MediaItem> applyOptions(List<MediaBrowserCompat.MediaItem> list, final Bundle options) {
        if (list == null) {
            return null;
        }
        int i = options.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i2 = options.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i == -1 && i2 == -1) {
            return list;
        }
        int i3 = i2 * i;
        int size = i3 + i2;
        if (i < 0 || i2 < 1 || i3 >= list.size()) {
            return Collections.emptyList();
        }
        if (size > list.size()) {
            size = list.size();
        }
        return list.subList(i3, size);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void attachToBaseContext(Context base) {
        attachBaseContext(base);
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fd, PrintWriter writer, String[] args) {
    }

    public final Bundle getBrowserRootHints() {
        return this.mImpl.getBrowserRootHints();
    }

    @NonNull
    public final MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
        return this.mImpl.getCurrentBrowserInfo();
    }

    @Nullable
    public MediaSessionCompat.Token getSessionToken() {
        return this.mSession;
    }

    public void notifyChildrenChanged(@NonNull String parentId) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.mImpl.notifyChildrenChanged(parentId, null);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.mImpl.onBind(intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        int i = Build.VERSION.SDK_INT;
        if (i >= 28) {
            this.mImpl = new MediaBrowserServiceImplApi28();
        } else if (i >= 26) {
            this.mImpl = new MediaBrowserServiceImplApi26();
        } else if (i >= 23) {
            this.mImpl = new MediaBrowserServiceImplApi23();
        } else {
            this.mImpl = new MediaBrowserServiceImplApi21();
        }
        this.mImpl.onCreate();
    }

    public void onCustomAction(@NonNull String action, Bundle extras, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String clientPackageName, int clientUid, @Nullable Bundle rootHints);

    public abstract void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result);

    public void onLoadChildren(@NonNull String parentId, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result, @NonNull Bundle options) {
        result.setFlags(1);
        onLoadChildren(parentId, result);
    }

    public void onLoadItem(String itemId, @NonNull Result<MediaBrowserCompat.MediaItem> result) {
        result.setFlags(2);
        result.sendResult(null);
    }

    public void onSearch(@NonNull String query, Bundle extras, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.setFlags(4);
        result.sendResult(null);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void onSubscribe(String id, Bundle option) {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void onUnsubscribe(String id) {
    }

    public final void performLoadChildren(final String parentId, final ConnectionRecord connection, final Bundle subscribeOptions, final Bundle notifyChildrenChangedOptions) {
        Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) { // from class: androidx.media.MediaBrowserServiceCompat.1
            @Override // androidx.media.MediaBrowserServiceCompat.Result
            public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                List<MediaBrowserCompat.MediaItem> listApplyOptions = list;
                if (MediaBrowserServiceCompat.this.mConnections.get(connection.callbacks.asBinder()) != connection) {
                    if (MediaBrowserServiceCompat.DEBUG) {
                        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
                        sbM26m.append(connection.pkg);
                        sbM26m.append(" id=");
                        sbM26m.append(parentId);
                        Log.d("MBServiceCompat", sbM26m.toString());
                        return;
                    }
                    return;
                }
                if ((getFlags() & 1) != 0) {
                    listApplyOptions = MediaBrowserServiceCompat.this.applyOptions(listApplyOptions, subscribeOptions);
                }
                try {
                    connection.callbacks.onLoadChildren(parentId, listApplyOptions, subscribeOptions, notifyChildrenChangedOptions);
                } catch (RemoteException unused) {
                    StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m("Calling onLoadChildren() failed for id=");
                    sbM26m2.append(parentId);
                    sbM26m2.append(" package=");
                    sbM26m2.append(connection.pkg);
                    Log.w("MBServiceCompat", sbM26m2.toString());
                }
            }
        };
        this.mCurConnection = connection;
        if (subscribeOptions == null) {
            onLoadChildren(parentId, result);
        } else {
            onLoadChildren(parentId, result, subscribeOptions);
        }
        this.mCurConnection = null;
        if (!result.isDone()) {
            throw new IllegalStateException(Insets$$ExternalSyntheticOutline0.m24m(Insets$$ExternalSyntheticOutline0.m26m("onLoadChildren must call detach() or sendResult() before returning for package="), connection.pkg, " id=", parentId));
        }
    }

    public void setSessionToken(MediaSessionCompat.Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null");
        }
        if (this.mSession != null) {
            throw new IllegalStateException("The session token has already been set");
        }
        this.mSession = token;
        this.mImpl.setSessionToken(token);
    }

    /* JADX INFO: loaded from: classes3.dex */
    @RequiresApi(21)
    public class MediaBrowserServiceImplApi21 implements MediaBrowserServiceImpl {
        public Messenger mMessenger;
        public final List<Bundle> mRootExtrasList = new ArrayList();
        public MediaBrowserService mServiceFwk;

        /* JADX INFO: loaded from: classes.dex */
        @RequiresApi(21)
        public class MediaBrowserServiceApi21 extends MediaBrowserService {
            public MediaBrowserServiceApi21(Context context) {
                attachBaseContext(context);
            }

            @Override // android.service.media.MediaBrowserService
            @SuppressLint({"SyntheticAccessor"})
            public MediaBrowserService.BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
                MediaSessionCompat.ensureClassLoader(rootHints);
                BrowserRoot browserRootOnGetRoot = MediaBrowserServiceImplApi21.this.onGetRoot(clientPackageName, clientUid, rootHints == null ? null : new Bundle(rootHints));
                if (browserRootOnGetRoot == null) {
                    return null;
                }
                return new MediaBrowserService.BrowserRoot(browserRootOnGetRoot.mRootId, browserRootOnGetRoot.mExtras);
            }

            @Override // android.service.media.MediaBrowserService
            public void onLoadChildren(String parentId, MediaBrowserService.Result<List<MediaBrowser.MediaItem>> result) {
                MediaBrowserServiceImplApi21.this.onLoadChildren(parentId, new ResultWrapper<>(result));
            }
        }

        public MediaBrowserServiceImplApi21() {
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public Bundle getBrowserRootHints() {
            if (this.mMessenger == null) {
                return null;
            }
            ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mCurConnection;
            if (connectionRecord == null) {
                throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
            }
            if (connectionRecord.rootHints == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.mCurConnection.rootHints);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public MediaSessionManager.RemoteUserInfo getCurrentBrowserInfo() {
            ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.mCurConnection;
            if (connectionRecord != null) {
                return connectionRecord.browserInfo;
            }
            throw new IllegalStateException("This should be called inside of onGetRoot, onLoadChildren, onLoadItem, onSearch, or onCustomAction methods");
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(final String parentId, final Bundle options) {
            notifyChildrenChangedForFramework(parentId, options);
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.3
                @Override // java.lang.Runnable
                public void run() {
                    Iterator<IBinder> it = MediaBrowserServiceCompat.this.mConnections.keySet().iterator();
                    while (it.hasNext()) {
                        MediaBrowserServiceImplApi21.this.notifyChildrenChangedForCompatOnHandler(MediaBrowserServiceCompat.this.mConnections.get(it.next()), parentId, options);
                    }
                }
            });
        }

        public final void notifyChildrenChangedForCompatOnHandler(final ConnectionRecord connection, final String parentId, final Bundle options) {
            List<Pair<IBinder, Bundle>> list = connection.subscriptions.get(parentId);
            if (list != null) {
                for (Pair<IBinder, Bundle> pair : list) {
                    if (MediaBrowserCompatUtils.hasDuplicatedItems(options, pair.second)) {
                        MediaBrowserServiceCompat.this.performLoadChildren(parentId, connection, pair.second, options);
                    }
                }
            }
        }

        public void notifyChildrenChangedForFramework(final String parentId, final Bundle options) {
            this.mServiceFwk.notifyChildrenChanged(parentId);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public IBinder onBind(Intent intent) {
            return this.mServiceFwk.onBind(intent);
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void onCreate() {
            MediaBrowserServiceApi21 mediaBrowserServiceApi21 = new MediaBrowserServiceApi21(MediaBrowserServiceCompat.this);
            this.mServiceFwk = mediaBrowserServiceApi21;
            mediaBrowserServiceApi21.onCreate();
        }

        /* JADX WARN: Type inference failed for: r3v9, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
        public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints) {
            Bundle extras;
            int i;
            if (rootHints == null || rootHints.getInt(MediaBrowserProtocol.EXTRA_CLIENT_VERSION, 0) == 0) {
                extras = null;
                i = -1;
            } else {
                rootHints.remove(MediaBrowserProtocol.EXTRA_CLIENT_VERSION);
                this.mMessenger = new Messenger(MediaBrowserServiceCompat.this.mHandler);
                extras = new Bundle();
                extras.putInt(MediaBrowserProtocol.EXTRA_SERVICE_VERSION, 2);
                BundleCompat.putBinder(extras, MediaBrowserProtocol.EXTRA_MESSENGER_BINDER, this.mMessenger.getBinder());
                MediaSessionCompat.Token token = MediaBrowserServiceCompat.this.mSession;
                if (token != null) {
                    IMediaSession extraBinder = token.getExtraBinder();
                    BundleCompat.putBinder(extras, MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder == null ? null : extraBinder.asBinder());
                } else {
                    this.mRootExtrasList.add(extras);
                }
                int i2 = rootHints.getInt(MediaBrowserProtocol.EXTRA_CALLING_PID, -1);
                rootHints.remove(MediaBrowserProtocol.EXTRA_CALLING_PID);
                i = i2;
            }
            ConnectionRecord connectionRecord = MediaBrowserServiceCompat.this.new ConnectionRecord(clientPackageName, i, clientUid, rootHints, null);
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = connectionRecord;
            BrowserRoot browserRootOnGetRoot = mediaBrowserServiceCompat.onGetRoot(clientPackageName, clientUid, rootHints);
            MediaBrowserServiceCompat mediaBrowserServiceCompat2 = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat2.mCurConnection = null;
            if (browserRootOnGetRoot == null) {
                return null;
            }
            if (this.mMessenger != null) {
                mediaBrowserServiceCompat2.mPendingConnections.add(connectionRecord);
            }
            if (extras == null) {
                extras = browserRootOnGetRoot.getExtras();
            } else if (browserRootOnGetRoot.getExtras() != null) {
                extras.putAll(browserRootOnGetRoot.getExtras());
            }
            return new BrowserRoot(browserRootOnGetRoot.getRootId(), extras);
        }

        public void onLoadChildren(String parentId, final ResultWrapper<List<Parcel>> resultWrapper) {
            Result<List<MediaBrowserCompat.MediaItem>> result = new Result<List<MediaBrowserCompat.MediaItem>>(parentId) { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.2
                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public void detach() {
                    resultWrapper.detach();
                }

                @Override // androidx.media.MediaBrowserServiceCompat.Result
                public final void onResultSent(@Nullable List<MediaBrowserCompat.MediaItem> list) {
                    ArrayList arrayList;
                    List<MediaBrowserCompat.MediaItem> list2 = list;
                    if (list2 != null) {
                        arrayList = new ArrayList();
                        for (MediaBrowserCompat.MediaItem mediaItem : list2) {
                            Parcel parcelObtain = Parcel.obtain();
                            mediaItem.writeToParcel(parcelObtain, 0);
                            arrayList.add(parcelObtain);
                        }
                    } else {
                        arrayList = null;
                    }
                    resultWrapper.sendResult(arrayList);
                }
            };
            MediaBrowserServiceCompat mediaBrowserServiceCompat = MediaBrowserServiceCompat.this;
            mediaBrowserServiceCompat.mCurConnection = mediaBrowserServiceCompat.mConnectionFromFwk;
            mediaBrowserServiceCompat.onLoadChildren(parentId, result);
            MediaBrowserServiceCompat.this.mCurConnection = null;
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void setSessionToken(final MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.mHandler.postOrRun(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.1
                /* JADX WARN: Type inference failed for: r2v0, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
                /* JADX WARN: Type inference failed for: r2v3, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
                /* JADX WARN: Type inference failed for: r3v0, types: [java.util.ArrayList, java.util.List<android.os.Bundle>] */
                @Override // java.lang.Runnable
                public void run() {
                    MediaBrowserServiceImplApi21 mediaBrowserServiceImplApi21 = MediaBrowserServiceImplApi21.this;
                    MediaSessionCompat.Token token2 = token;
                    if (!mediaBrowserServiceImplApi21.mRootExtrasList.isEmpty()) {
                        IMediaSession extraBinder = token2.getExtraBinder();
                        if (extraBinder != null) {
                            Iterator it = mediaBrowserServiceImplApi21.mRootExtrasList.iterator();
                            while (it.hasNext()) {
                                BundleCompat.putBinder((Bundle) it.next(), MediaBrowserProtocol.EXTRA_SESSION_BINDER, extraBinder.asBinder());
                            }
                        }
                        mediaBrowserServiceImplApi21.mRootExtrasList.clear();
                    }
                    mediaBrowserServiceImplApi21.mServiceFwk.setSessionToken((MediaSession.Token) token2.getToken());
                }
            });
        }

        @Override // androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImpl
        public void notifyChildrenChanged(final MediaSessionManager.RemoteUserInfo remoteUserInfo, final String parentId, final Bundle options) {
            MediaBrowserServiceCompat.this.mHandler.post(new Runnable() { // from class: androidx.media.MediaBrowserServiceCompat.MediaBrowserServiceImplApi21.4
                @Override // java.lang.Runnable
                public void run() {
                    for (int i = 0; i < MediaBrowserServiceCompat.this.mConnections.size(); i++) {
                        ConnectionRecord connectionRecordValueAt = MediaBrowserServiceCompat.this.mConnections.valueAt(i);
                        if (connectionRecordValueAt.browserInfo.equals(remoteUserInfo)) {
                            MediaBrowserServiceImplApi21.this.notifyChildrenChangedForCompatOnHandler(connectionRecordValueAt, parentId, options);
                        }
                    }
                }
            });
        }
    }

    public void notifyChildrenChanged(@NonNull String parentId, @NonNull Bundle options) {
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        if (options != null) {
            this.mImpl.notifyChildrenChanged(parentId, options);
            return;
        }
        throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
    public void notifyChildrenChanged(@NonNull MediaSessionManager.RemoteUserInfo remoteUserInfo, @NonNull String parentId, @NonNull Bundle options) {
        if (remoteUserInfo == null) {
            throw new IllegalArgumentException("remoteUserInfo cannot be null in notifyChildrenChanged");
        }
        if (parentId == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        if (options != null) {
            this.mImpl.notifyChildrenChanged(remoteUserInfo, parentId, options);
            return;
        }
        throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    }
}
