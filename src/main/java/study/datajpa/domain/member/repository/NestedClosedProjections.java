package study.datajpa.domain.member.repository;

public interface NestedClosedProjections {

    String getUserName();
    TeamInfo getTeam();

    interface TeamInfo {
        String getTeamName();
    }
}
