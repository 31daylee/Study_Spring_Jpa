package jpabook.jpashop.domain.item;

import jakarta.persistence.*;
import jpabook.jpashop.domain.Category;
import jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories= new ArrayList<>();

    // == 비즈니스 로직 ==//
    /*
    * stock 증가
    * */
    public void addStock(int quantity){
        this.stockQuantity += quantity;
    }

    /*
     * stock 감소
     * */
    public void removeStock(int quantity){
        int restStock = this.stockQuantity -= quantity;
        if(restStock < 0){
            throw new NotEnoughStockException("need more stock!@");
        }
        this.stockQuantity = restStock;
    }
}
