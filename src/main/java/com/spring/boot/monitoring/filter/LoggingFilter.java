package com.spring.boot.monitoring.filter;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class LoggingFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //traceId
        String traceId = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmss")); //20260410065245
        MDC.put("traceId", traceId);

        //to controller
        try{
            //이 filter를 적용한 application 측에서만 확인 가능한 내용
            //Hiberante, Database Driver 등의 로그에서는 trace id가 정상적으로 찍히지 않을 수 있음에 유의
            chain.doFilter(request, response);
        } finally {
            //톰캣에 메시지를 누적하지 않고 비워야 한다.
            MDC.clear();
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
