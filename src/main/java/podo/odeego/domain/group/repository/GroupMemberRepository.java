package podo.odeego.domain.group.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

	List<GroupMember> findGroupMembersByGroup(Group group);

	@Query("""
		select gm
		from GroupMember gm
		inner join gm.member m
		where m.id = :memberId""")
	Optional<GroupMember> findByMemberId(@Param("memberId") Long memberId);
}
