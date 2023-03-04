package podo.odeego.domain.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByProviderAndProviderId(String provider, String providerId);

	boolean existsByNickname(String nickname);
}
