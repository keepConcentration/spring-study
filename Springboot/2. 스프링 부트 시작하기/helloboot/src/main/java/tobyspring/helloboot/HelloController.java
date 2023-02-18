package tobyspring.helloboot;

import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequestMapping("/hello")
public class HelloController {

    private final HelloService helloService;

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    @ResponseBody
    // @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello(String name) {
        // throw NullPointerException
        return helloService.sayHello(Objects.requireNonNull(name));
    }
}
