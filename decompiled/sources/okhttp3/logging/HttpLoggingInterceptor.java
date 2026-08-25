package okhttp3.logging;

import androidx.core.graphics.Insets$$ExternalSyntheticOutline0;
import com.google.common.net.HttpHeaders;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.JvmField;
import kotlin.jvm.JvmName;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.p018io.CloseableKt;
import kotlin.text.StringsKt;
import kotlin.text.StringsKt__StringsJVMKt;
import okhttp3.Connection;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.internal.Utf8Kt;
import okio.Buffer;
import okio.BufferedSource;
import okio.GzipSource;
import org.jetbrains.annotations.NotNull;

/* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(m313bv = {1, 0, 3}, m314d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\u0018\u00002\u00020\u0001:\u0002\u001e\u001fB\u0011\b\u0007\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\r\u0010\u000b\u001a\u00020\tH\u0007¢\u0006\u0002\b\u0012J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0019\u001a\u00020\u001aH\u0002J\u000e\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u0007J\u000e\u0010\u001d\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\tR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R$\u0010\n\u001a\u00020\t2\u0006\u0010\b\u001a\u00020\t@GX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\n\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, m315d2 = {"Lokhttp3/logging/HttpLoggingInterceptor;", "Lokhttp3/Interceptor;", "logger", "Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "(Lokhttp3/logging/HttpLoggingInterceptor$Logger;)V", "headersToRedact", "", "", "<set-?>", "Lokhttp3/logging/HttpLoggingInterceptor$Level;", "level", "getLevel", "()Lokhttp3/logging/HttpLoggingInterceptor$Level;", "(Lokhttp3/logging/HttpLoggingInterceptor$Level;)V", "bodyHasUnknownEncoding", "", "headers", "Lokhttp3/Headers;", "-deprecated_level", "intercept", "Lokhttp3/Response;", "chain", "Lokhttp3/Interceptor$Chain;", "logHeader", "", "i", "", "redactHeader", "name", "setLevel", "Level", "Logger", "okhttp-logging-interceptor"}, m316k = 1, m317mv = {1, 4, 1})
public final class HttpLoggingInterceptor implements Interceptor {
    private volatile Set<String> headersToRedact;

    @NotNull
    private volatile Level level;
    private final Logger logger;

    /* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
    /* JADX INFO: loaded from: classes2.dex */
    @Metadata(m313bv = {1, 0, 3}, m314d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006¨\u0006\u0007"}, m315d2 = {"Lokhttp3/logging/HttpLoggingInterceptor$Level;", "", "(Ljava/lang/String;I)V", "NONE", "BASIC", "HEADERS", "BODY", "okhttp-logging-interceptor"}, m316k = 1, m317mv = {1, 4, 1})
    public enum Level {
        NONE,
        BASIC,
        HEADERS,
        BODY
    }

    /* JADX INFO: compiled from: HttpLoggingInterceptor.kt */
    /* JADX INFO: loaded from: classes2.dex */
    @Metadata(m313bv = {1, 0, 3}, m314d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\bæ\u0080\u0001\u0018\u0000 \u00062\u00020\u0001:\u0001\u0006J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&¨\u0006\u0007"}, m315d2 = {"Lokhttp3/logging/HttpLoggingInterceptor$Logger;", "", "log", "", "message", "", "Companion", "okhttp-logging-interceptor"}, m316k = 1, m317mv = {1, 4, 1})
    public interface Logger {

        @JvmField
        @NotNull
        public static final Logger DEFAULT = new Companion.DefaultLogger();

        void log(@NotNull String message);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @JvmOverloads
    public HttpLoggingInterceptor() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @JvmOverloads
    public HttpLoggingInterceptor(@NotNull Logger logger) {
        Intrinsics.checkNotNullParameter(logger, "logger");
        this.logger = logger;
        this.headersToRedact = SetsKt.emptySet();
        this.level = Level.NONE;
    }

    private final boolean bodyHasUnknownEncoding(Headers headers) {
        String str = headers.get(HttpHeaders.CONTENT_ENCODING);
        return (str == null || StringsKt__StringsJVMKt.equals(str, "identity", true) || StringsKt__StringsJVMKt.equals(str, "gzip", true)) ? false : true;
    }

    private final void logHeader(Headers headers, int i) {
        String strValue = this.headersToRedact.contains(headers.name(i)) ? "██" : headers.value(i);
        this.logger.log(headers.name(i) + ": " + strValue);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "moved to var", replaceWith = @ReplaceWith(expression = "level", imports = {}))
    @JvmName(name = "-deprecated_level")
    @NotNull
    /* JADX INFO: renamed from: -deprecated_level, reason: not valid java name and from getter */
    public final Level getLevel() {
        return this.level;
    }

    @NotNull
    public final Level getLevel() {
        return this.level;
    }

    @Override // okhttp3.Interceptor
    @NotNull
    public Response intercept(@NotNull Interceptor.Chain chain) throws Exception {
        String string;
        Charset UTF_8;
        Charset UTF_9;
        Intrinsics.checkNotNullParameter(chain, "chain");
        Level level = this.level;
        Request request = chain.request();
        if (level == Level.NONE) {
            return chain.proceed(request);
        }
        boolean z = level == Level.BODY;
        boolean z2 = z || level == Level.HEADERS;
        RequestBody requestBodyBody = request.body();
        Connection connection = chain.connection();
        StringBuilder sbM26m = Insets$$ExternalSyntheticOutline0.m26m("--> ");
        sbM26m.append(request.method());
        sbM26m.append(' ');
        sbM26m.append(request.url());
        if (connection != null) {
            StringBuilder sbM26m2 = Insets$$ExternalSyntheticOutline0.m26m(" ");
            sbM26m2.append(connection.protocol());
            string = sbM26m2.toString();
        } else {
            string = "";
        }
        sbM26m.append(string);
        String string2 = sbM26m.toString();
        if (!z2 && requestBodyBody != null) {
            StringBuilder sbM28m = Insets$$ExternalSyntheticOutline0.m28m(string2, " (");
            sbM28m.append(requestBodyBody.contentLength());
            sbM28m.append("-byte body)");
            string2 = sbM28m.toString();
        }
        this.logger.log(string2);
        if (z2) {
            Headers headers = request.headers();
            if (requestBodyBody != null) {
                MediaType contentType = requestBodyBody.getContentType();
                if (contentType != null && headers.get("Content-Type") == null) {
                    this.logger.log("Content-Type: " + contentType);
                }
                if (requestBodyBody.contentLength() != -1 && headers.get(HttpHeaders.CONTENT_LENGTH) == null) {
                    Logger logger = this.logger;
                    StringBuilder sbM26m3 = Insets$$ExternalSyntheticOutline0.m26m("Content-Length: ");
                    sbM26m3.append(requestBodyBody.contentLength());
                    logger.log(sbM26m3.toString());
                }
            }
            int size = headers.size();
            for (int i = 0; i < size; i++) {
                logHeader(headers, i);
            }
            if (!z || requestBodyBody == null) {
                Logger logger2 = this.logger;
                StringBuilder sbM26m4 = Insets$$ExternalSyntheticOutline0.m26m("--> END ");
                sbM26m4.append(request.method());
                logger2.log(sbM26m4.toString());
            } else if (bodyHasUnknownEncoding(request.headers())) {
                Logger logger3 = this.logger;
                StringBuilder sbM26m5 = Insets$$ExternalSyntheticOutline0.m26m("--> END ");
                sbM26m5.append(request.method());
                sbM26m5.append(" (encoded body omitted)");
                logger3.log(sbM26m5.toString());
            } else if (requestBodyBody.isDuplex()) {
                Logger logger4 = this.logger;
                StringBuilder sbM26m6 = Insets$$ExternalSyntheticOutline0.m26m("--> END ");
                sbM26m6.append(request.method());
                sbM26m6.append(" (duplex request body omitted)");
                logger4.log(sbM26m6.toString());
            } else if (requestBodyBody.isOneShot()) {
                Logger logger5 = this.logger;
                StringBuilder sbM26m7 = Insets$$ExternalSyntheticOutline0.m26m("--> END ");
                sbM26m7.append(request.method());
                sbM26m7.append(" (one-shot body omitted)");
                logger5.log(sbM26m7.toString());
            } else {
                Buffer buffer = new Buffer();
                requestBodyBody.writeTo(buffer);
                MediaType contentType2 = requestBodyBody.getContentType();
                if (contentType2 == null || (UTF_9 = contentType2.charset(StandardCharsets.UTF_8)) == null) {
                    UTF_9 = StandardCharsets.UTF_8;
                    Intrinsics.checkNotNullExpressionValue(UTF_9, "UTF_8");
                }
                this.logger.log("");
                if (Utf8Kt.isProbablyUtf8(buffer)) {
                    this.logger.log(buffer.readString(UTF_9));
                    Logger logger6 = this.logger;
                    StringBuilder sbM26m8 = Insets$$ExternalSyntheticOutline0.m26m("--> END ");
                    sbM26m8.append(request.method());
                    sbM26m8.append(" (");
                    sbM26m8.append(requestBodyBody.contentLength());
                    sbM26m8.append("-byte body)");
                    logger6.log(sbM26m8.toString());
                } else {
                    Logger logger7 = this.logger;
                    StringBuilder sbM26m9 = Insets$$ExternalSyntheticOutline0.m26m("--> END ");
                    sbM26m9.append(request.method());
                    sbM26m9.append(" (binary ");
                    sbM26m9.append(requestBodyBody.contentLength());
                    sbM26m9.append("-byte body omitted)");
                    logger7.log(sbM26m9.toString());
                }
            }
        }
        long jNanoTime = System.nanoTime();
        try {
            Response responseProceed = chain.proceed(request);
            long millis = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - jNanoTime);
            ResponseBody responseBodyBody = responseProceed.body();
            Intrinsics.checkNotNull(responseBodyBody);
            long contentLength = responseBodyBody.getContentLength();
            String str = contentLength != -1 ? contentLength + "-byte" : "unknown-length";
            Logger logger8 = this.logger;
            StringBuilder sbM26m10 = Insets$$ExternalSyntheticOutline0.m26m("<-- ");
            sbM26m10.append(responseProceed.code());
            sbM26m10.append(responseProceed.message().length() == 0 ? "" : String.valueOf(' ') + responseProceed.message());
            sbM26m10.append(' ');
            sbM26m10.append(responseProceed.request().url());
            sbM26m10.append(" (");
            sbM26m10.append(millis);
            sbM26m10.append("ms");
            sbM26m10.append(!z2 ? Insets$$ExternalSyntheticOutline0.m20m(", ", str, " body") : "");
            sbM26m10.append(')');
            logger8.log(sbM26m10.toString());
            if (z2) {
                Headers headers2 = responseProceed.headers();
                int size2 = headers2.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    logHeader(headers2, i2);
                }
                if (!z || !okhttp3.internal.http.HttpHeaders.promisesBody(responseProceed)) {
                    this.logger.log("<-- END HTTP");
                } else if (bodyHasUnknownEncoding(responseProceed.headers())) {
                    this.logger.log("<-- END HTTP (encoded body omitted)");
                } else {
                    BufferedSource source = responseBodyBody.getSource();
                    source.request(Long.MAX_VALUE);
                    Buffer buffer2 = source.getBuffer();
                    Long l = null;
                    if (StringsKt__StringsJVMKt.equals("gzip", headers2.get(HttpHeaders.CONTENT_ENCODING), true)) {
                        Long lValueOf = Long.valueOf(buffer2.size());
                        GzipSource gzipSource = new GzipSource(buffer2.clone());
                        try {
                            buffer2 = new Buffer();
                            buffer2.writeAll(gzipSource);
                            CloseableKt.closeFinally(gzipSource, null);
                            l = lValueOf;
                        } catch (Throwable th) {
                            try {
                                throw th;
                            } catch (Throwable th2) {
                                CloseableKt.closeFinally(gzipSource, th);
                                throw th2;
                            }
                        }
                    }
                    MediaType mediaType = responseBodyBody.get$contentType();
                    if (mediaType == null || (UTF_8 = mediaType.charset(StandardCharsets.UTF_8)) == null) {
                        UTF_8 = StandardCharsets.UTF_8;
                        Intrinsics.checkNotNullExpressionValue(UTF_8, "UTF_8");
                    }
                    if (!Utf8Kt.isProbablyUtf8(buffer2)) {
                        this.logger.log("");
                        Logger logger9 = this.logger;
                        StringBuilder sbM26m11 = Insets$$ExternalSyntheticOutline0.m26m("<-- END HTTP (binary ");
                        sbM26m11.append(buffer2.size());
                        sbM26m11.append("-byte body omitted)");
                        logger9.log(sbM26m11.toString());
                        return responseProceed;
                    }
                    if (contentLength != 0) {
                        this.logger.log("");
                        this.logger.log(buffer2.clone().readString(UTF_8));
                    }
                    if (l != null) {
                        Logger logger10 = this.logger;
                        StringBuilder sbM26m12 = Insets$$ExternalSyntheticOutline0.m26m("<-- END HTTP (");
                        sbM26m12.append(buffer2.size());
                        sbM26m12.append("-byte, ");
                        sbM26m12.append(l);
                        sbM26m12.append("-gzipped-byte body)");
                        logger10.log(sbM26m12.toString());
                    } else {
                        Logger logger11 = this.logger;
                        StringBuilder sbM26m13 = Insets$$ExternalSyntheticOutline0.m26m("<-- END HTTP (");
                        sbM26m13.append(buffer2.size());
                        sbM26m13.append("-byte body)");
                        logger11.log(sbM26m13.toString());
                    }
                }
            }
            return responseProceed;
        } catch (Exception e) {
            this.logger.log("<-- HTTP FAILED: " + e);
            throw e;
        }
    }

    @JvmName(name = "level")
    public final void level(@NotNull Level level) {
        Intrinsics.checkNotNullParameter(level, "<set-?>");
        this.level = level;
    }

    public final void redactHeader(@NotNull String name) {
        Intrinsics.checkNotNullParameter(name, "name");
        TreeSet treeSet = new TreeSet(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        CollectionsKt.addAll(treeSet, this.headersToRedact);
        treeSet.add(name);
        this.headersToRedact = treeSet;
    }

    @NotNull
    public final HttpLoggingInterceptor setLevel(@NotNull Level level) {
        Intrinsics.checkNotNullParameter(level, "level");
        this.level = level;
        return this;
    }

    public /* synthetic */ HttpLoggingInterceptor(Logger logger, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Logger.DEFAULT : logger);
    }
}
