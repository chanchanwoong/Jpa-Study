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
            biDirection(em);

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
}
