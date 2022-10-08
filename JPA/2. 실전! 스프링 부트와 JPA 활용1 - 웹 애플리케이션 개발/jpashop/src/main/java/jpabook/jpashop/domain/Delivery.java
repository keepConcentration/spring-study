package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Delivery {

    @Id
    @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)    // ORDINAL = 숫자, STRING = 문자    숫자를 쓰면 status 추가됐을 때 데이터가 밀릴 수 있음.
    private DeliveryStatus status;  // READY, COMP
}
