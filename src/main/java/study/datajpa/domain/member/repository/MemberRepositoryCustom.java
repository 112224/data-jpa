package study.datajpa.domain.member.repository;

import study.datajpa.domain.member.entity.Member;

import java.util.List;

public interface MemberRepositoryCustom {
    List<Member> findMemberCustom();
}
