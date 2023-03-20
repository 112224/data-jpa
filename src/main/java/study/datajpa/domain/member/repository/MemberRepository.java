package study.datajpa.domain.member.repository;


import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import study.datajpa.domain.member.dto.MemberDto;
import study.datajpa.domain.member.entity.Member;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberRepositoryCustom, JpaSpecificationExecutor<Member> {

    List<Member> findByUserNameAndAgeIsGreaterThan(String userName, int age);

    List<Member> findByUserName(@Param("userName") String userName);

    @Query("select m from Member m where m.userName = :userName and m.age >= :age")
    List<Member> findUser(@Param("userName") String userName, @Param("age") int age);

    @Query("select m.userName from Member m")
    List<String> findUserNameList();

    @Query("select new study.datajpa.domain.member.dto.MemberDto(m.id, m.userName, t.teamName) " +
            "from Member m join m.team t")
    List<MemberDto> findMemberDto();

    @Query("select m from Member m where m.userName in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    List<Member> findListByUserName(String userName);
    Optional<Member> findOptionalByUserName(String userName);
    Member findMemberByUserName(String userName);

    @Query(value = "select m from Member m left join m.team t", countQuery = "select count(m) from Member m")
    Page<Member> findByAge(int age, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where m.age >= :age")
    int bulkAgePlus(@Param("age") int age);

    @EntityGraph(attributePaths = {"team"})
    List<Member> findFetchByUserName(String userName);

    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByUserName(String userName);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByUserName(String userName);

    <T> List<T> findProjectionsByUserName(@Param("userName") String userName, Class<T> type);

    @Query(value = "select * from Member where user_name = ?", nativeQuery = true)
    Member findByNativeQuery(String userName);

    @Query(value = "select m.member_id as id, m.user_name as userName, t.team_name as teamName " +
            "from member m left join team t on m.team_id = t.team_id " +
            "where m.team_id is not null ",
            countQuery = "select count (*) from Member",
            nativeQuery = true)
    Page<MemberProjection> findByNativeProjection(Pageable pageable);
}
