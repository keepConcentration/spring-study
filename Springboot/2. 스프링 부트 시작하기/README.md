# 스프링 부트 시작하기

## 스프링 부트 개발환경

SpringBoot 2.7.6 기준

## JDK

JDK 8, 11, 17 설치
- 공개 JDK 다운로드 후 설치
  - Eclipse Temurin
  - Microsoft OpenJDK
  - Amazon Corretto
  - Azul JDK
  - Oracle JDK
- https://sdkman.io

## IDE

- IntelliJ IDEA : https://www.jetbrains.com/idea/download
  - Ultimate
  - Community
- STS : https://spring.io.tools
- Visual Studio Code : https://code.visualstudio.com

## SpringBoot

CLI 버전 스프링 부트 설치
- Spring Boot CLI : https://docs.spring.io/spring-boot/docs/2.7.x/reference/htmlsingle/#getting-started.installing.cli

sdkman으로 스프링 부트 구동하기
- sdk install springboot 2.7.6
- mkdir myproject
- cd myproject
- vi hello.groovy
- i
```groovy
@RestController
class HelloController {
    @GetMapping("/")
    def hello() {
        return "Hello Spring Boot"
    }
}

```
- :wq
- spring run hello.groovy
