package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    // @Rollback(false)    // @Transactional와 @Test가 함께 있을 땐 스프링에서 기본적으로 rollback됨(정확히는 영속성 컨텍스트를 flush하지 않음).
    void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");
        
        // when
        Long savedId = memberService.join(member);

        // then
        //em.flush(); // 강제로 쿼리 실행
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    void 중복_회원_예약() throws Exception {
        // given
        Member member1 = new Member();
        member1.setName("kim");
        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);    // 중복 이름으로 인해 예외 발생
        });

        // then
        // fail("예외가 발생해야 한다.");   // fail: 실행되면 실패한 테스트.
    }
}