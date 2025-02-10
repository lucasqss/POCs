package org.acme.interceptors;

import jakarta.annotation.Priority;
import jakarta.interceptor.Interceptor;
import java.util.Arrays;
import org.acme.annotations.EventLog;
import org.jboss.logging.Logger;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.InvocationContext;

@EventLog
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class EventLogInterceptor {

    private static final Logger LOGGER = Logger.getLogger(EventLogInterceptor.class);

    @AroundInvoke
    public Object log(InvocationContext context) throws Exception {
        // Log the request
        LOGGER.info("Request: " + context.getMethod().getName() + " with parameters: " + Arrays.toString(context.getParameters()));
        try {
            // Proceed with the method invocation
            Object result = context.proceed();
            // Log the response
            LOGGER.info("Response: " + result);
            return result;
        } catch (Exception e) {
            // Log the exception
            LOGGER.error("Exception: ", e);
            throw e;
        }
    }
}