package tobyspring.helloboot;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

// @Controller, @RestController가 붙으면
// 클래스 레벨의 RequestMapping을 넣지 않아도 DispatcherServlet이
// 해당 클래스에 매핑 정보가 있다고 판단함. 메서드 레벨에 URL정보를 넣을 수 있음
// @RequestMapping("/hello")
@RestController
public class HelloController {

    private final HelloService helloService;

    private final ApplicationContext applicationContext;

    public HelloController(HelloService helloService, ApplicationContext applicationContext) {
        this.helloService = helloService;
        this.applicationContext = applicationContext;
        System.out.println(applicationContext);
    }

    @GetMapping("/hello")
    // @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(String name) {
        // throw NullPointerException
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
