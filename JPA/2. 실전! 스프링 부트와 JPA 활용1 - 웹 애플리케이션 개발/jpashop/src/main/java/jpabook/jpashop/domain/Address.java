package jpabook.jpashop.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Embeddable;

// 값 타입
// 어딘가에 내장될 수 있다, 사용되는 곳에 @Embedded 나 @Embeddable 중 하나만 쓰거나 둘 다 쓰면 된다.
// 변경 불가능한 클래스
// JPA 구현 라이브러리가 객체를 생성할 때 리플렉션이나 프록시같은 기술을 사용할 수 있도록 지원해야 하기 때문에 생성자를 public, protected로 설정해야한다.
@Embeddable
@Getter
public class Address {

    protected Address() {}

    private String city;
    private String street;
    private String zipcode;

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
