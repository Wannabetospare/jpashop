package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;



// 서비스, 스프링 트랜잭션을 true로 열어놔서 읽기만 함, 생성자 주입( 생성자 자동생성, 생성자가 하나라서 @Autowired 생략 - 의존성 주입 )
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    // 이름 - 회원가입
    // 매개변수 - 멤버를 매개변수로 받는다.
    // 동작 - 중복회원인증 메서드를 통해 중복회원을 검증하고, 통과하면 멤버에 저장한다.
    // 반환값 - 매개변수로 받은 멤버의 id 값을 반환한다.
    @Transactional // 변경
    public Long join (Member member){

        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member);
        return member.getId();

    }

    // 이름 - 회원가입을 할때 중복가입 검증하기
    // 매개변수 - 멤버를 매개변수로 받는다.
    // 동작 - 멤버 리퍼지토리에서 멤버의 이름을 매개변수로 받아, 멤버리스트형을 받아서 findMembers 리스트에 저장, 만약 비어있지 않다면 throw 값 실행
    // 반환값 - x
    private void validateDuplicateMember(Member member){
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    // 이름 - 전체 회원 조회
    // 매개변수 - x
    // 동작 - 멤버 리퍼지토리에서 멤버를 리스트형으로 전부 찾는다.
    // 반환값 - 동작값으로 멤버 리스트형을 반환한다.
    public List<Member> findMembers(){

        return memberRepository.findAll();

    }

    // 이름 - 멤버 하나 찾기
    // 매개변수 - 멤버의 id 값
    // 동작 - 멤버 리퍼지토리에서 findOne 으로 매개변수 값을 통해 멤버를 하나 찾는다.
    // 반환값 - 동작값으로 멤버를 반환한다.
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);

    }

}
