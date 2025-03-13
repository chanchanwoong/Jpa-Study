package jpabook.start.ch05;

import jpabook.start.ch05.entity.Member;
import jpabook.start.ch05.entity.Team;

import javax.persistence.*;
import java.util.*;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
//            logic(em);

            // 객체 그래프 탐색을 통한 객체 접근
//            objectGraphSearch(em, "member1");

            // team 갱신
//            updateRelation(em);

            // 양방향 객체 그래프 탐색
//            biDirection(em);

            // 주인을 지정한 양방향 연관관계 저장
            testSave(em);

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void logic(EntityManager em) {
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);

        System.out.println("member1 = " + member1.toString());
        System.out.println("member2 = " + member2.toString());
    }

    private static void objectGraphSearch(EntityManager em, String memberId) {
        Member member = em.find(Member.class, memberId);
        System.out.println("member1의 팀 이름 = " + member.getTeam().getName());
    }

    private static void updateRelation(EntityManager em) {
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member = em.find(Member.class, "member1");
        System.out.println("갱신 전 member1 = " + member.getTeam().getName());

        // member1의 팀을 team2로 갱신
        member.setTeam(team2);

        System.out.println("갱신 후 member2 = " + em.find(Member.class, "member1").getTeam().getName());
    }

    private static void biDirection(EntityManager em) {
        Team team1 = new Team("team1", "팀1");

        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");
        member1.setTeam(team1);
        member2.setTeam(team1);

        List<Member> members = Arrays.asList(member1, member2);
        team1.setMembers(members);

        em.persist(team1);

        Team team = em.find(Team.class, "team1");
        List<Member> membersByBiDirection = team.getMembers();

        System.out.println("양방향 연관관계를 통해 TEAM에서 MEMBER 접근");
        System.out.println(membersByBiDirection.toString());

        for (Member member : membersByBiDirection) {
            System.out.println("member.username = " + member.getUsername());
        }
    }


    /*
        연관관계의 주인을 설정하면, 따로 Team 객체의 Member 필드를 추가해주지 않아도 자동으로 명시된다.
    */
    private static void testSave(EntityManager em) {
        // 연관관계의 주인을 사용하지 못해 외래키 값이 NULL로 들어가는 코드
        Member member1 = new Member("member1", "회원1");
        em.persist(member1);

        Member member2 = new Member("member2", "회원2");
        em.persist(member2);

        Team team1 = new Team("team1", "팀1");
        team1.getMembers().add(member1);
        team1.getMembers().add(member2);
        em.persist(team1);



        // 연관관계의 주인을 통한 자동 외래키 지정 코드
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        Member member3 = new Member("member3", "회원3");
        member3.setTeam(team2);
        em.persist(member3);

        Member member4 = new Member("member4", "회원4");
        member4.setTeam(team2);
        em.persist(member4);


    }
}
