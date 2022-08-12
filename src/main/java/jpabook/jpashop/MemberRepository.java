package jpabook.jpashop;


import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em; // 생성자를 따로 만들지 않아도 선언만해도 엔티티매니저가 생성된다.

    // 멤버를 받아서 엔티티매니저에 저장하고, 아이디 값을 반환
    public Long save(Member member){
        em.persist(member);
        return member.getId();
    }

    // 아이디 값을 받아서 멤버를 찾고, 멤버를 반환
    public Member find(Long id){
        return em.find(Member.class, id);
    }



}
