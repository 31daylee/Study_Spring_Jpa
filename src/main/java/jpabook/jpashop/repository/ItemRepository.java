package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){ // 아이템을 save하기 전까지는 id값이 없으므로,
            em.persist(item);    // 만약 null이라면 새롭게 생성한 객체라는 의미. 그래서persist해줌
        }else{
            em.merge(item); // 이미 있다면 강제 업데이트
        }

    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class).getResultList();
    }

}
