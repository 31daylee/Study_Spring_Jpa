package jpabook.jpashop.service;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class) // Junit 실행할 때 스프링이랑 엮어서 실행할래
@SpringBootTest // 스프링 컨테이너 안에서 테스트를 돌린다.
@Transactional // 기본적으로 롤백함. (false)라고 설정해줘야지 롤백 안함
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    EntityManager em;


    @Test
    public void 회원가입() throws Exception{
        // given
        Member member = new Member();
        member.setName("kim");

        // when
        Long savedId = memberService.join(member);

        // then
        em.flush(); // 영속성 컨텍스트에 있는 어떤 변경이나 등록내용을 데이터베이스에 반영하는 역할 -> insert문 볼 수 있다.
        assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test
    @DisplayName("중복 회원 예외가 될까요?")
    public void 중복_회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");
        // when
        memberService.join(member1);
        Assertions.assertThrows(IllegalStateException.class,() -> memberService.join(member2));// 똑같은 이름이므로 예외가 발생한다!!


        // then
        Assertions.fail("예외가 발생해야 한다.");
    }

}