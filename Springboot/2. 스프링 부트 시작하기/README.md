# 스프링 부트 시작하기

## 스프링 부트 개발환경

SpringBoot 2.7.8 기준

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
- sdk install springboot 2.7.8
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

## Hello Boot 웹 프로젝트 작성

### 스프링 부트 프로젝트 생성

- 웹 Spring Initializr - https://start.spring.io
  - JVM 기반의 스프링 부트를 만들어주는 API 프로젝트임.
  - 해당 API를 이용하면 완성된 스프링부트 기반의 스프링 프로젝트 템플릿을 다운받을 수 있다.

- CLI
  - spring shell
  - init -b 2.7.8 -d web -g tobyspring -j 11 -n helloboot -x helloboot
    - -b: 버전, -g: 그룹명, -j: java 버전, -n: 프로젝트명, -x: 해당 폴더에 압축 해제
    - Spring Initializr 를 사용해서 다운로드받음
  - ./gradlew bootRun
