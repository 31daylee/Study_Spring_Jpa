package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

 /*   @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
        => @RequiredArgsConstructor 으로 인해 생성자 생성 및 memberRepository에 final 까지 한 번에 잡아준다.
    }*/

    // 회원 가입
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    // 중복 회원 검증
    private void validateDuplicateMember(Member member) {
        // Exception
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

    }

    // 회원 전체 조회
    @Transactional(readOnly = true) // 읽기에는 readOnly-true 넣어주면 약간의 성능향상 가능
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    // 한 회원 조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }

}
