package tobyspring.helloboot;


import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

    public static void main(String[] args) {
        // 서블릿컨테이너 실행 (임베디드 톰캣)
        // new Tomcat().start();

        // ServletWebServerFactory: 톰캣 외의 제티, 토우같은 다른 서블릿 컨테이너를 지원하기 위해 추상화된 인터페이스
        ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        // ServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
        // ServletWebServerFactory serverFactory = new UndertowServletWebServerFactory();

        // ServletContextInitializer 에 위 서블릿 등록, 위 서블릿은 /hello URL 요청을 처리한다.
        WebServer webServer = serverFactory.getWebServer(servletContext ->
                servletContext.addServlet("frontcontroller", new HttpServlet() {
					// 요청을 수행할 서블릿
                    @Override
                    protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
                        // 인증, 보안, 다국어, 공통 기능 처리 서블릿

                        // GET /hello
                        if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                            String name = req.getParameter("name");

                            res.setStatus(HttpStatus.OK.value());
                            res.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN_VALUE);
                            res.getWriter().println("Hello " + name);
                        } else if (req.getRequestURI().equals("/user")) {

                        } else {
                            res.setStatus(HttpStatus.NOT_FOUND.value());
                        }
                    }
                }).addMapping("/*"));
        // 요청: http -v ":8080/hello?name=Spring"
        webServer.start();
    }

}
