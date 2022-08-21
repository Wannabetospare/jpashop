package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

// 테스트 - Junit4 설정
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired // 의존성 주입
    MemberService memberService;

    @Autowired // 의존성 주입
    MemberRepository memberRepository;

   // name - 회원가입 테스트
   // give - 멤버를 생성하고, 이름을 세팅
   // when - 생성한 멤버를 회원가입 시킨다.
   // then - 생성한 멤버와 가입시킨 멤버의 id 값이 같은지 확인
    @Test
    public void 회원가입() throws Exception {
        // given
        Member member = new Member();
        member.setName("kim");
        // when
        Long saveId = memberService.join(member);
        // then
        assertEquals(member, memberRepository.findOne(saveId));
    }

    // name - 중복 회원 검증 테스트
    // give - 멤버를 2개 생성하고, 이름을 같게 세팅한다.
    // when - 생성한 멤버 2개를 회원가입 시킨다.
    // then - 예외가 발생해야 테스트가 성공이다.
    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception{
        // given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생 해야 한다.

        // then
        fail("예외가 발생해야 한다.");

    }
}