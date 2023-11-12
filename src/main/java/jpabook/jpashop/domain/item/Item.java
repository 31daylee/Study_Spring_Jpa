package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //single_table 전략은 한 테이블에 다 때려 넣는것
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item { // 구현체를 가지고 할 것이기 때문에 추상클래스로 설정

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantitiy;
}
