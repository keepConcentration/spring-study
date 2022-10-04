# 프로젝트 환경셜졍

## 목차
- 프로젝트 생성
- 라이브러리 살펴보기
- View 환경설정
- H2 데이터베이스 설치
- JPA와 DB 설정, 동작 확인

## 프로젝트 생성
-  스프링 부트 스타터(http://start.spring.io)
- 사용 기능: web, thymeleaf, jpa, h2, lombok
  - groupId: jpabook
  - artifactId: jpashop

Gradle 전체 설정
```groovy
plugin {
    id 'org.springframework.boot' version '2.1.6.RELEASE'
    id 'java'
}

apply plugin: 'io.spring.dependency-management'

group = 'jpabook'
version = '0.0.1-SNAPSHOT'
```