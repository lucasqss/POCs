package org.acme.interceptor;

import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.servlet.http.HttpServletRequest;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

@EventLog
@Interceptor
public class EventLogInterceptor implements Serializable {

    private static final long serialVersionUID = 1L;


     @AroundInvoke
     public Object log(InvocationContext context) throws Exception {
         long timestamp = System.currentTimeMillis();
         System.out.println("Timestamp: " + timestamp);
         System.out.println("Interceptando chamada ao método: " + context.getMethod().getName());
         System.out.println("Com os parâmetros: " + Arrays.toString(context.getParameters()));

         // Assuming the context has access to HTTP request details
         // This part may need adjustment based on your actual context and framework
         Object[] parameters = context.getParameters();
         for (Object param : parameters) {
             if (param instanceof HttpServletRequest) {
                 HttpServletRequest request = (HttpServletRequest) param;
                 System.out.println("Request Headers: " + Collections.list(request.getHeaderNames())
                         .stream()
                         .collect(Collectors.toMap(h -> h, request::getHeader)));
                 System.out.println("Request ID: " + request.getHeader("X-Request-ID"));
             }
         }

         return context.proceed();
     }
}
