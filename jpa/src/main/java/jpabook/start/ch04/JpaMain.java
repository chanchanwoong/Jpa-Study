package jpabook.start.ch04;

import jpabook.start.ch04.eneity.Member;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        //엔티티 매니저 팩토리 생성
        // persistenceUnitName은 persistence.xml의 persistence-unit과 일치해야 한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager(); //엔티티 매니저 생성

        EntityTransaction tx = em.getTransaction(); //트랜잭션 기능 획득

        try {
            tx.begin(); //트랜잭션 시작
            logic(em);  //비즈니스 로직
            tx.commit();//트랜잭션 커밋

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback(); //트랜잭션 롤백
        } finally {
            em.close(); //엔티티 매니저 종료
        }

        emf.close(); //엔티티 매니저 팩토리 종료
    }

    private static void logic(EntityManager em) {
        Member member = new Member();
        member.setName("찬웅");
        member.setCity("부산");
        member.setStreet("15");
        member.setZipcode("zip");

        // 등록
        em.persist(member);

        // 한 건 조회
        Member findMember = em.createQuery("select m from Member m", Member.class).getSingleResult();
        System.out.println("findMemeber = " + findMember.toString());

        // 삭제
        em.remove(member);
    }
}
