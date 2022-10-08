package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable // 어딘가에 내장될 수 있다, 사용되는 곳에 @Embedded 나 @Embeddable 중 하나만 쓰거나 둘 다 쓰면 된다.
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;
}
