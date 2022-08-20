package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


// 테스트
@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    // 멤버리퍼지토리에 의존성 주입
    @Autowired
    MemberRepository memberRepository;

    @Test // 테스트
    @Transactional // DB와 관련된 클래스를 실행할때 저장할떄 사용한다.
    @Rollback(false) //  ( 테스트는 기본이 롤백이 true 라서 )
    public void testMember() {
        Member member = new Member();
        member.setName("memberA");
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.findOne(savedId);
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
        Assertions.assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장
    }
}