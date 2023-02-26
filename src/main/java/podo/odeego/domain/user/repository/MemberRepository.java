package podo.odeego.domain.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import podo.odeego.domain.user.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByUsername(String username);

	Optional<Member> findByProviderAndProviderId(String provider, String providerId);
}