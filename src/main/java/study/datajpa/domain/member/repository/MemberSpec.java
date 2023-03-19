package study.datajpa.domain.member.repository;


import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import study.datajpa.domain.member.entity.Member;
import study.datajpa.domain.team.entity.Team;


public class MemberSpec {

    public static Specification<Member> teamName(final String teamName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(teamName)) {
                return null;
            }
            Join<Member, Team> t = root.join("team", JoinType.INNER);
            return criteriaBuilder.equal(t.get("teamName"), teamName);
        };
    }

    public static Specification<Member> userName(final String userName) {
        return (root, query, criteriaBuilder) -> {
            if (StringUtils.isEmpty(userName)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("userName"), userName);
        };
    }
}