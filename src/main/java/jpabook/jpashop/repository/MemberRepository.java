package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    // 회원 저장
    public void save(Member member){
        em.persist(member);
    }

    // 회원 조회_단건 조회
    public Member findOne(Long id){
        return em.find(Member.class,id); // 첫 번째는 타입, 두 번째는 PK
    }

    // 회원 리스트 조회
    public List<Member> findAll(){
        return em.createQuery("select m from Member m", Member.class) // 조회타입은 Member.class
                .getResultList();
    }

    // 회원 리스트 이름(파라미터)통해서 조회
    public List<Member> findByName(String name){
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name) // 파라미터 통해서 조회
                .getResultList();

    }



}
