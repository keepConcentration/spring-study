package tobyspring.helloboot;


import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.cglib.proxy.Dispatcher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.GenericWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


// 스프링 컨테이너가 해당 클래스가 팩토리 메서드를 가진 클래스인 것을 인지하기 위해
@Configuration
@ComponentScan // 해당 클래스의 패키지, 하위 패키지에 @Component가 붙은 클래스를 빈으로 등록함
public class HellobootApplication {

    /*
    // 팩토리 메서드
    @Bean
    public HelloController helloController(HelloService helloService) {
        return new HelloController(helloService);
    }

    // 팩토리 메서드
    @Bean
    public HelloService helloService() {
        return new SimpleHelloService();
    }*/

    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }

    @Bean
    public DispatcherServlet dispatcherServlet() {
        return new DispatcherServlet();
    }

    public static void main(String[] args) {

        // 스프링 컨테이너
        AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext() {
            // 스프링 컨테이너 초기화 후 서블릿 컨테이너 초기화 작업을 스프링 컨테이너 초기화 시 서블릿 컨테이너도 함께 초기화하는 작업을 위해
            // onRefresh()를 재정의함.
            @Override
            protected void onRefresh() {
                super.onRefresh();

                ServletWebServerFactory serverFactory = this.getBean(ServletWebServerFactory.class);
                DispatcherServlet dispatcherServlet = this.getBean(DispatcherServlet.class);
                // dispatcherServlet.setApplicationContext(this);


                WebServer webServer = serverFactory.getWebServer(servletContext ->
                        servletContext.addServlet("dispatcherServlet",
                                // DispatcherServlet은 "Web"ApplicationContext를 파라미터로 넣어줘야한다.
                                // 각 URL, Method에 대해 처리할 서블릿에 대한 힌트가 없어 어떤 요청이라도 404.
                                // 해결 방법은 해당 URL, Method 요청을 처리할 컨트롤러에 매핑정보를 입력.
                                // @RequestMapping, @GetMapping과 같은 URL 매핑 정보를 사용해서 웹 요청을 위임함.
                                // Controller가 String을 리턴하면 View로 인식함.
                                // String을 웹 응답 Body에 넣기 위해선 @ResponseBody를 사용.
                                // @RestController를 사용하면 클래스 아래 메소드에 모두 @ResponseBody를 붙임
                                dispatcherServlet
                        ).addMapping("/*"));
                webServer.start();
            }
        };

        // AnnotationConfigWebApplicationContext 에서 사용됨
        applicationContext.register(HellobootApplication.class);

        // GenericWebApplicationContext 에서 사용됨.
        // applicationContext.registerBean(HelloController.class);
        // applicationContext.registerBean(SimpleHelloService.class);
        applicationContext.refresh();  // 구성정보를 이용해 컨테이너 초기화

        // 서블릿컨테이너 실행 (임베디드 톰캣)
        // new Tomcat().start();

        // 서블릿 컨테이너 초기화
        // ServletWebServerFactory: 톰캣 외의 제티, 토우같은 다른 서블릿 컨테이너를 지원하기 위해 추상화된 인터페이스
        // ServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
        // ServletWebServerFactory serverFactory = new UndertowServletWebServerFactory();
        /*ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
        WebServer webServer = serverFactory.getWebServer(servletContext ->
                servletContext.addServlet("frontcontroller",
                        // DispatcherServlet은 "Web"ApplicationContext를 파라미터로 넣어줘야한다.
                        // 각 URL, Method에 대해 처리할 서블릿에 대한 힌트가 없어 어떤 요청이라도 404.
                        // 해결 방법은 해당 URL, Method 요청을 처리할 컨트롤러에 매핑정보를 입력.
                        // @RequestMapping, @GetMapping과 같은 URL 매핑 정보를 사용해서 웹 요청을 위임함.
                        // Controller가 String을 리턴하면 View로 인식함.
                        // String을 웹 응답 Body에 넣기 위해선 @ResponseBody를 사용.
                        // @RestController를 사용하면 클래스 아래 메소드에 모두 @ResponseBody를 붙임
                        new DispatcherServlet(applicationContext)
                ).addMapping("/*"));
        webServer.start();*/


        /*
        // 요청을 수행할 서블릿
        // 요청: http -v ":8080/hello?name=Spring"
        // ServletContextInitializer 에 아래 서블릿 등록, 아래 서블릿은 /hello URL 요청을 처리한다.
        new HttpServlet() {
            @Override
            protected void service(HttpServletRequest req, HttpServletResponse res) throws IOException {
                // 인증, 보안, 다국어, 공통 기능 처리 서블릿
                if (req.getRequestURI().equals("/hello") && req.getMethod().equals(HttpMethod.GET.name())) {
                    String name = req.getParameter("name");

                    HelloController helloController = applicationContext.getBean(HelloController.class);
                    String ret = helloController.hello(name);

                    res.setContentType(MediaType.TEXT_PLAIN_VALUE);
                    res.getWriter().println(ret);

                } else {
                    res.setStatus(HttpStatus.NOT_FOUND.value());
                }
            }
        };
         */
    }

}
