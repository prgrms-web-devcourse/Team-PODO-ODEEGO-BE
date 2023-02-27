package podo.odeego.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.group.entity.GroupMember;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {
}
