package com.waw.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

// proxyMode 가짜 프록시 클래스를 만들어서 다른 빈에 미리 주입해준다.
// ac.getBean(MyLogger.class)를 해도 가짜 프록시 객체가 조회된다.
// com.waw.core.common.MyLogger$$EnhancerBySpringCGLIB$$89e06348
// 가짜 proxy는 원본 객체를 상속받음.
// 실제 요청 시엔 가짜 proxy 가 진짜 logic을 호출한다.
// 웹 스코프가 아니어도 사용 가능.
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "][" + requestURL + "][ " + message + " ]");
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "] request scope bean create:" + this);
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "] request scope bean close:" + this);
    }
}
