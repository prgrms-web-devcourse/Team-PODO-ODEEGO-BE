package podo.odeego.domain.group.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

	List<GroupMember> findAllByGroup(Group group);
}
