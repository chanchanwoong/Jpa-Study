package jpabook.start.ch05;

import jpabook.start.ch05.entity.Member;
import jpabook.start.ch05.entity.Team;

public class JpaMain {

    public static void main(String[] args) {
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");

        Team team1 = new Team("team1", "팀1");

        member1.setTeam(team1);
        member2.setTeam(team1);

        Team findTeam = member1.getTeam();

        System.out.println("findTeam = " + findTeam.toString());
    }
}
