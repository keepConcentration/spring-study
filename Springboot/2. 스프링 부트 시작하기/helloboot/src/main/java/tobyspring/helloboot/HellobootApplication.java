package tobyspring.helloboot;


import org.springframework.boot.SpringApplication;
import tobyspring.config.MySpringBootApplication;


// 스프링 컨테이너가 해당 클래스가 팩토리 메서드를 가진 클래스인 것을 인지하기 위해
@MySpringBootApplication
public class HellobootApplication {
    public static void main(String[] args) {
        //MySpringApplication.run(HellobootApplication.class, args);
        SpringApplication.run(HellobootApplication.class, args);
    }
}
