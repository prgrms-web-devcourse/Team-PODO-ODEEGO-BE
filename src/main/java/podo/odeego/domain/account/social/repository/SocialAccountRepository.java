package podo.odeego.domain.account.social.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import podo.odeego.domain.account.social.entity.SocialAccount;

public interface SocialAccountRepository extends CrudRepository<SocialAccount, Long> {

	Optional<SocialAccount> findByProviderAndProviderId(String provider, Long providerId);
}
