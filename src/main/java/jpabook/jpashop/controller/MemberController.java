package jpabook.jpashop.controller;


import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import javax.validation.Valid;
import java.util.List;

// 회원을 등록하는 컨트롤러
@Controller
@RequiredArgsConstructor // 생성자 생략 롬복 어노테이션
public class MemberController {

    private final MemberService memberService;

    // 매핑 종류 - Get 가져온다
    // 매핑 주소 - members/new
    // 이름 - 폼 만들기
    // 매개변수 - 모델 클래스의 모델
    // 동작 - 매개변수로 받은 모델값을 싣고
    // 반환 - return 주소 값으로 이동한다.
    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // 매핑 종류 - Post 날린다.
    // 매핑 주소 - members/new
    // 이름 - 만들기
    // 매개변수 - 멤버폼과, 검증 오류가 발생할 경우 오류 내용을 보관하는 스프링 프레임워크에서 제공하는 객체를 매개변수로 받는다.
    // 동작 - 만약 매개변수로 받은 결과에 에러가 존재한다면, 리턴값 주소로 돌아가고, 아니라면 회원을 가입시킨다.
    // 반환 - 홈으로 다시 돌아간다.
    @PostMapping(value = "/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {
        // @Valid - 빈 검증기(Bean Validator)를 이용해 객체의 제약 조건을 검증하도록 지시하는 어노테이션

        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());
        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);
        memberService.join(member);

        return "redirect:/";
    }


    // 매핑 종류 - Get 가져온다.
    // 매핑 주소 - members
    // 이름 - 리스트
    // 매개변수 - 모델
    // 동작 - 멤버 서비스에서 멤버들을 찾아, 멤버 리스트로 만들고, 멤버 리스트를 members/memberList 에 members 를 넘긴다.
    // 반환 - 반환 사이트로 넘긴다.
    //추가하기
    @GetMapping(value = "/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
