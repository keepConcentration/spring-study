
### 프로젝트 생성
- 사전 준비물
  - Java 11
  - IDE: IntteliJ 또는 Eclipse 설치

**[스프링 부트 스타터](https://start.spring.io) 사이트로 이동해서 스프링 프로젝트 생성**

- 프로젝트 선택
  - Project: Gradle Project
  - Spring Boot: 2.6.x (강의 영상에선 2.3.x 선택함. SNAPSHOT, M2 등은 정식 릴리즈 버전이 아님)
  - Language: Java
  - Packaging: Jar
  - Java: 11
- Project Metedata
  - groupid: com.waw(강의 영상에선 hello 입력)
  - artifactId: core
  - Dependencies: 선택하지 않는다.(아무 것도 선택하지 않아도 스프링 코어쪽 라이브러리만 가져온다.)

### Gradle 전체 설정
```groovy
plugins {
	id 'org.springframework.boot' version '2.6.9'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
}

group = 'com.waw'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

tasks.named('test') {
	useJUnitPlatform()
}

```

아래 사진처럼 CoreApplication을 실행해보자.

![CoreApplicationRun](./image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-07-15%20%EC%98%A4%EC%A0%84%207.22.01.png)

![CoreApplicationRunResult](./image/%EC%8A%A4%ED%81%AC%EB%A6%B0%EC%83%B7%202022-07-15%20%EC%98%A4%EC%A0%84%207.28.28.png)

실행 후 끝나면 정상작동한 거다.





## 비즈니스 요구사항과 설계
- 회원
  - 회원을 가입하고 조회할 수 있다.
  - 회원은 일반과 VIP 두 가지 등급이 있다.
  - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다(미확정)

- 주문과 할인 정책
  - 회원은 상품을 주문할 수 있다.
  - 회원 등급에 따라 할인 정책을 적용할 수 있다.
  - 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라.(나중에 변경될 수 있다.)
  - 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수도 있다.(미확정)

요구사항을 보면 회원 데이터, 할인 정책 같은 부분은 지금 결정하기 어려운 부분이다. 그렇다고 이런 정책이 결정될 때까지 개발을 무기한 기다릴 수도 없다. 우리는 앞에서 배운 객체 지향 설계 방법이 있지 않은가!

인터페이스를 만들고 구현체를 언제든지 갈이끼울 수 있도록 설계하면 된다. 그럼 시작해보자

> **참고**: 프로젝트 환경설정을 편리하게 하려고 스프링 부트를 사용한 것이다. 지금은 스프링 없는 순수한 자바로만 개발을 진행한다는 점을 꼭 기억하자! 스프링 관련은 한참 뒤에 등장한다.

## 회원 도메인 설계
- 회원 도메인 요구사항
  - 회원을 가입하고 조회할 수 있다.
  - 회원은 일반과 VIP 두 가지 등급이 있다.
  - 회원 데이터는 자체 DB를 구축할 수 있고, 외부 시스템과 연동할 수 있다(미확정)

**회원 도메인 협력관계**

![회원 도메인 협력관계](./image/%ED%9A%8C%EC%9B%90%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%ED%98%91%EB%A0%A5%EA%B4%80%EA%B3%84.png)

- 클라이언트는 회원가입, 회원 조회를 하기 위해 회원서비스를 호출한다.
- 회원 저장소는 정해지지 않았기 때문에 인터페이스로 생성한다.
- 회원 저장소는 메모리, DB, 외부 시스템으로 구현한다.

**회원 클래스 다이어그램**

![회원 클래스 다이어그램](./image/%ED%9A%8C%EC%9B%90%20%ED%81%B4%EB%9E%98%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)

**회원 객체 다이어그램**

![회원 객체 다이어그램](./image/%ED%9A%8C%EC%9B%90%20%EA%B0%9D%EC%B2%B4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)

- 회원서비스: **MemberServiceImpl**

### 회원 도메인 개발

#### 회원 엔티티

**회원 등급**
```java
package com.waw.core.member;

public enum Grade {
  BASIC,
  VIP
}

```

**회원 엔티티**

```java
package com.waw.core.member;

public class Member {
  private Long id;
  private String name;
  private Grade grade;

  public Member(Long id, String name, Grade grade) {
    this.id = id;
    this.name = name;
    this.grade = grade;
  }

  ...

  // Getter, Setter 생략
}

```

#### 회원 저장소

**회원 저장소 인터페이스**

```java
package com.waw.core.member;

public interface MemberRepository {
  void save(Member member);
  Member findById(Long memberId);
}

```

**메모리 회원 저장소 구현체**

```java
package com.waw.core.member;

import java.util.HashMap;
import java.util.Map;

public MemoryMemberRepository implements MemberRepository {

  private static Map<Long, Member> store = new HashMap<>();

  @Override
  public void save(Member member) {
    store.put(member.getId(), member);
  }

  @Override
  public Member findById(Long memberId) {
    return store.get(memberId);
  }

}

```

인터페이스가 아직 확정이 안되었다. 그래도 개발은 진행해야 하니 가장 단순한, 메모리 회원 저장소를
구현해서 우선 개발을 진행하자.
> 참고: `HashMap` 은 동시성 이슈가 발생할 수 있다. 이런 경우 
`ConcurrentHashMap` 을 사용하자.

#### 회원 서비스

**회원 서비스 인터페이스**

```java
package com.waw.waw.core.member;

public interface MemberService {
  void join(Member member);
  Member findMember(Long memberId);
}

```

**회원 서비스 구현체**
```java
package com.waw.core.member;

public class MemberServiceImpl implements MemberService {

  private final MemberRepository memberRepository = new MemoryMemberRepository();

  public void join(Member member) {
    memberRepository.save(member);
  }

  public Member findMember(Long memberId) {
    return memberRepository.findById(memberId);
  }
}

```


**회원 도메인 설계의 문제점**

위 설계는 의존관계가 인터페이스 뿐만 아니라 구현까지 모두 의존하는 문제점이 있음
- OCP, DIP를 지키지 않음.
```java
// MemberServiceImpl 내부
private final MemberRepository memberRepository = new MemoryMemberRepository();
```


#### 회원 도메인 실행과 테스트

**회원 도메인 - 회원 가입 main**

```java
package com.waw.core;

import com.waw.core.member.Grade;
import com.waw.core.member.Member;
import com.waw.core.member.MemberService;
import com.waw.core.member.MemberServiceImpl;

public class MemberApp {

  public static void main(String[] args) {
    MemberService memberService = new MemberServiceImpl();
    Member member = new Member(1L, "memberA", Grade.VIP);
    memberService.join(member);

    Member findMember = memberService.findMember(1L);
    System.out.println("new member = " + member.getName());
    System.out.println("find Member = " + findMember.getName());
  }
}

```
애플리케이션 로직으로 이렇게 테스트 하는 것은 좋은 방법이 아니다. JUnit 테스트를 사용하자.

**회원 도메인 - 회원 가입 테스트**

```java
package com.waw.core.member;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

  MemberService memberService = new MemberServiceImpl();

  @Test
  void join() {
    //given 어떤 환경에서
    Member member = new Member(1L, "memberA", Grade.VIP);

    //when 주어졌을 때
    memberService.join(member);
    Member findMember = memberService.findMember(1L);

    //then 이렇게 된다.
    assertThat(member).isEqualTo(findMember);
  }
}

```

### 주문과 할인 도메인 설계

- 주문과 할인 정책
  + 회원은 상품을 주문할 수 있다.
  + 회원 등급에 따라 할인 정책을 적용할 수 있다.
  + 할인 정책은 모든 VIP는 1000원을 할인해주는 고정 금액 할인을 적용해달라. (나중에 변경 될 수 있다.)
  + 할인 정책은 변경 가능성이 높다. 회사의 기본 할인 정책을 아직 정하지 못했고, 오픈 직전까지 고민을 미루고 싶다. 최악의 경우 할인을 적용하지 않을 수 도 있다. (미확정)
**주문 도메인 협력, 역할, 책임**

![주문 도메인 협력, 역할, 책임](./image/%EC%A3%BC%EB%AC%B8%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%ED%98%91%EB%A0%A5%2C%20%EC%97%AD%ED%95%A0%2C%20%EC%B1%85%EC%9E%84.png)


**1. 주문 생성**: 클라이언트는 주문 서비스에 주문 생성을 요청한다.

**2. 회원 조회**: 할인을 위해서는 회원 등급이 필요하다. 그래서 주문 서비스는 회원 저장소에서 회원을 조회한다.

**3. 할인 적용**: 주문 서비스는 회원 등급에 따른 할인 여부를 할인 정책에 위임한다.

**4. 주문 결과 반환**: 주문 서비스는 할인 결과를 포함한 주문 결과를 반환한다.

> 참고: 실제로는 주문 데이터를 DB에 저장하겠지만, 예제가 너무 복잡해 질 수 있어서 생략하고, 단순히 주문 결과를 반환한다.

**주문 도메인 전체**

![주문 도메인 전체](./image/%EC%A3%BC%EB%AC%B8%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%EC%A0%84%EC%B2%B4.png)

**역할과 구현을 분리**해서 자유롭게 구현 객체를 조립할 수 있게 설계했다. 덕분에 회원 저장소는 물론이고, 할인 정책도 유연하게 변경할 수 있다.

**주문 도메인 클래스 다이어그램**

![주문 도메인 클래스 다이어그램](./image/%EC%A3%BC%EB%AC%B8%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%ED%81%B4%EB%9E%98%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8.png)

**주문 도메인 객체 다이어그램1**

![주문 도메인 객체 다이어그램1](./image/%EC%A3%BC%EB%AC%B8%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%EA%B0%9D%EC%B2%B4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A81.png)

회원을 메모리에서 조회하고, 정액 할인 정책(고정 금액)을 지원해도 주문 서비스를 변경하지 않아도 된다. 역할들의 협력 관계를 그대로 재사용할 수 있다.

**주문 도메인 객체 다이어그램2**

![주문 도메인 클래스 다이어그램](./image/%EC%A3%BC%EB%AC%B8%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%EA%B0%9D%EC%B2%B4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A82.png)

회원을 메모리가 아닌 실제 DB에서 조회하고, 정률 할인 정책(주문 금액에 따라 % 할인)을 지원해도 주문 서비스를 변경하지 않아도 된다.
협력 관계를 그대로 재사용 할 수 있다.



### 주문과 할인 도메인 개발

**할인 정책 인터페이스**

```java

package com.waw.core.discount;

import com.waw.core.member.Member;

  public interface DiscountPolicy {

  /**
   * @return 할인 대상 금액
   */
  int discount(Member member, int price);

}

```


**정액 할인 정책 구현체**
```java

package com.waw.core.discount;

import com.waw.core.member.Grade;
import com.waw.core.member.Member;

public class FixDiscountPolicy implements DiscountPolicy {
  private int discountFixAmount = 1000; //1000원 할인

  @Override
  public int discount(Member member, int price) {
    if (member.getGrade() == Grade.VIP) {
        return discountFixAmount;
    }
    return 0;
  }

}

```

VIP면 1000원 할인, 아니면 할인 없음

**주문 엔티티**
```java

package com.waw.core.order;

public class Order {

  private Long memberId;
  private String itemName;
  private int itemPrice;
  private int discountPrice;

  public Order(Long memberId, String itemName, int itemPrice, int
discountPrice) {
    this.memberId = memberId;
    this.itemName = itemName;
    this.itemPrice = itemPrice;
    this.discountPrice = discountPrice;
  }

  public int calculatePrice() {
      return itemPrice - discountPrice;
  }

  @Override
  public String toString() {
    return "Order{" +
        "memberId=" + memberId +
        ", itemName='" + itemName + '\'' +
        ", itemPrice=" + itemPrice +
        ", discountPrice=" + discountPrice +
        "}";
  }

  ...

  // Getter, Setter 생략
}

```

**주문 서비스 인터페이스**
```java
package com.waw.core.order;

public interface OrderService {

  Order createOrder(Long memberId, String itemName, int itemPrice);
}

```


**주문 서비스 구현체**

```java
package com.waw.core.order;

import com.waw.core.discount.DiscountPolicy;
import com.waw.core.discount.FixDiscountPolicy;
import com.waw.core.member.Member;
import com.waw.core.member.MemberRepository;
import com.waw.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {
  private final MemberRepository memberRepository = new
MemoryMemberRepository();
  private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
  
  @Override
  public Order createOrder(Long memberId, String itemName, int itemPrice) {
    Member member = memberRepository.findById(memberId);
    int discountPrice = discountPolicy.discount(member, itemPrice);

    return new Order(memberId, itemName, itemPrice, discountPrice);
  }
}
 
```

주문 생성 요청이 오면, 회원 정보를 조회하고, 할인 정책을 적용한 다음 주문 객체를 생성해서 반환한다. **메모리 회원 리포지토리와, 고정 금액 할인 정책을 구현체로 생성한다.**

### 주문과 할인 도메인 실행과 테스트

**주문과 할인 정책 실행**

```java
package com.waw.core;

import com.waw.core.member.Grade;
import com.waw.core.member.Member;
import com.waw.core.member.MemberService;
import com.waw.core.member.MemberServiceImpl;
import com.waw.core.order.Order;
import com.waw.core.order.OrderService;
import com.waw.core.order.OrderServiceImpl;

public class OrderApp {

  public static void main(String[] args) {

    MemberService memberService = new MemberServiceImpl();
    OrderService orderService = new OrderServiceImpl();
    long memberId = 1L;

    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);

    Order order = orderService.createOrder(memberId, "itemA", 10000);
    System.out.println("order = " + order);
  }
}

```

**결과**

>  order = Order{memberId=1, itemName='itemA', itemPrice=10000,
  discountPrice=1000}

할인 금액이 잘 출력되는 것을 확인할 수 있다.
애플리케이션 로직으로 이렇게 테스트 하는 것은 좋은 방법이 아니다. JUnit 테스트를 사용하자.

**주문과 할인 정책 테스트**

```java
package com.waw.core.order;

import com.waw.core.member.Grade;
import com.waw.core.member.Member;
import com.waw.core.member.MemberService;
import com.waw.core.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderServiceTest {
  MemberService memberService = new MemberServiceImpl();
  OrderService orderService = new OrderServiceImpl();

  @Test
  void createOrder() {
    long memberId = 1L;
    Member member = new Member(memberId, "memberA", Grade.VIP);
    memberService.join(member);
    
    Order order = orderService.createOrder(memberId, "itemA", 10000);
    Assertions.assertThat(order.getDiscountPrice()).isEqualTo(1000);
  }
}

```