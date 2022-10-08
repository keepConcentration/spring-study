package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    // 다대다 관계에서 category_item 다대다 관계를 풀어내기 위해 중간 테이블 category_item을 매핑. 다대다는 거의 사용하지 않음
    @ManyToMany
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),    // 중간 테이블에 있는 category_id
            inverseJoinColumns = @JoinColumn(name = "item_id")  // category_item 테이블에서 item으로 들어가는 컬럼 매핑
     )
    private List<Item> items = new ArrayList<>();

    // 셀프 양방향 괸계
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
