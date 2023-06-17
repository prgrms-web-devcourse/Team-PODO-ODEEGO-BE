package podo.odeego.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	boolean existsByNickname(String nickname);
}
