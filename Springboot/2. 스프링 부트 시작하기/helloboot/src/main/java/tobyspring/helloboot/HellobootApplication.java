package tobyspring.helloboot;


import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class HellobootApplication {

	public static void main(String[] args) {
		// 서블릿컨테이너 실행 (임베디드 톰캣)
		// new Tomcat().start();

		// 톰캣 외의 제티, 토우같은 다른 서블릿 컨테이너를 지원하기 위해 추상화된 인터페이스
		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		// ServletWebServerFactory serverFactory = new JettyServletWebServerFactory();
		// ServletWebServerFactory serverFactory = new UndertowServletWebServerFactory();


		// 요청을 수행할 서블릿
		Servlet httpServlet = new HttpServlet() {
			@Override
			protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
				res.setStatus(200);
				res.setHeader("Content-Type", "text/plain");
				res.getWriter().print("Hello Servlet");
			}
		};

		// ServletContextInitializer 에 위 서블릿 등록, 위 서블릿은 /hello URL 요청을 처리한다.
		WebServer webServer = serverFactory.getWebServer(new ServletContextInitializer() {
			@Override
			public void onStartup(ServletContext servletContext) throws ServletException {
				servletContext.addServlet("hello", httpServlet).addMapping("/hello");
			}
		});
		webServer.start();
	}

}
