package jpabook.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") // single_table로 들어가면 구분하는 것이 필요하는데 인덱스 역할처럼 한다고 보면 된다.
@Getter
@Setter
public class Book extends Item{

    private String author;
    private String isbn;

}
