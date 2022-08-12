package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.junit4.statements.SpringRepeat;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    // @Test는 JUnit4를 사용하면 org.junit.Test를 사용하셔야 합니다. 만약 JUnit5 버전을 사용하면 그 것에 맞게 사용하시면 됩니다.
    @Test
    // 트랜잭션 어노테이션은 스프링과 자바가 있는데 스프링부트이기 때문에 스프링 트랜잭션 어노테이션 사용
    @Transactional
    // 테스트 버전은 실행이 끝나면 자동으로 롤백을 하기 때문에 false 값을 넣어 롤백을 없앤다(=커밋,저장한다.)
    @Rollback(false)
    public void testMember() throws Exception {
        // given
        //  새로운 멤버 객체 생성, id는 자동생성, 멤버이름 세팅
        Member member = new Member();
        member.setUsername("memberA");

        // when
        // 생성한 멤버를 리퍼지토리에 저장
        Long saveId = memberRepository.save(member);
        // 위에서 생성한 id 값으로 리퍼지토리에서 멤버를 찾음
        Member findMember = memberRepository.find(saveId);

        // then
        // 저장한 멤버값과 찾는 멤버값이 같은지 확인, jpa 가 동일성을 보장해주기 때문에 같다.(주소x, 내용o)
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장

        System.out.println("findMember == member -> " + (findMember == member));

        
    }


    

}