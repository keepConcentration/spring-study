package com.waw.core.web;

import com.waw.core.common.MyLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;

    // scope: request.
    // httpRequest가 없어서 MyLogger가 빈으로 생성이 안됨, 따라서 스프링 컨테이너에 컨트롤러를 싱글톤 빈으로 등록할 때 MyLogger 주입이 불가함.
    // private final MyLogger myLogger;

    private final ObjectProvider<MyLogger> myLoggerProvider;

    @RequestMapping("log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        MyLogger myLogger = myLoggerProvider.getObject();
        // MyLogger에 requestURL 세팅하는 부분은 스프링 인터셉터나 서블릿 필터에서 공통처리하는 것이 좋다.
        String requestURL = request.getRequestURL().toString();
        myLogger.setRequestURL(requestURL);

        myLogger.log("controller test");
        logDemoService.logic("testId");

        return "OK";
    }
}
