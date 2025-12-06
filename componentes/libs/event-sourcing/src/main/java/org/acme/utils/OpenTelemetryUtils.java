package org.acme.utils;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.ContextKey;

public class OpenTelemetryUtils {

    private static final ContextKey<String> USER_ID_KEY = ContextKey.named("user.id");

    public static void setUserId(String userId) {
        Span currentSpan = Span.current();
        if (currentSpan != null && currentSpan.getSpanContext().isValid()) {
            currentSpan.setAttribute("user.id", userId);
        } else {
            throw new IllegalStateException("No active span found to add the key.");
        }
    }

    public static String getUserId() {
        return Context.current().get(USER_ID_KEY);
    }
}