package jpabook.jpashop.domain;

// 실무에선 Setter는 꼭 필요한 경우에만 사용하는 것을 추천

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 일대다 관계
    @OneToMany(mappedBy = "member")  // order 필드에 있는 member 에 매핑됨(읽기 전용)
    private List<Order> orders = new ArrayList<>();
}
