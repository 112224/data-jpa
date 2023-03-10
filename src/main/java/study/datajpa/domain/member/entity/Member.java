package study.datajpa.domain.member.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import study.datajpa.domain.common.entity.BaseEntity;
import study.datajpa.domain.common.entity.JpaBaseEntity;
import study.datajpa.domain.team.entity.Team;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id", "userName", "age"})
@NamedQuery(
        name = "Member.findByUserName",
        query = "select m from Member m where m.userName =: userName"
)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String userName;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String userName) {
        this.userName = userName;
    }

    public Member(String userName, int age) {
        this.userName = userName;
        this.age = age;
    }

    public Member(String userName, int age, Team team) {
        this.userName = userName;
        this.age = age;
        if (team != null) {
            changeTeam(team);
        }
    }

    public void changeUserName(String userName) {
        this.userName = userName;
    }
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
