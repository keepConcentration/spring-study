package tobyspring.helloboot;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

public class HelloController {

    public String hello(String name) {
        SimpleHelloService simpleHelloService = new SimpleHelloService();

        // throw NullPointerException
        return simpleHelloService.sayHello(Objects.requireNonNull(name));
    }
}
