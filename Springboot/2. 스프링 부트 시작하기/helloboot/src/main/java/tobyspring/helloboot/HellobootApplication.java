package tobyspring.helloboot;


import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServer;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;

public class HellobootApplication {

	public static void main(String[] args) {
		// 서블릿컨테이너 실행 (임베디드 톰캣)
		// new Tomcat().start();

		ServletWebServerFactory serverFactory = new TomcatServletWebServerFactory();
		// ServletWebServerFactory serverFactory = new JettyServletWebServerFactory();

		// 톰캣 외의 제티같은 다른 서블릿 컨테이너를 지원하기 위해 추상화.
		WebServer webServer = serverFactory.getWebServer();
		webServer.start();
	}

}
