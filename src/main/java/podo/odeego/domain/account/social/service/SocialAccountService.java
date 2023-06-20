package podo.odeego.domain.account.social.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.account.social.entity.SocialAccount;
import podo.odeego.domain.account.social.repository.SocialAccountRepository;

@Service
@Transactional
public class SocialAccountService {

	private final SocialAccountRepository socialAccountRepository;

	public SocialAccountService(SocialAccountRepository socialAccountRepository) {
		this.socialAccountRepository = socialAccountRepository;
	}

	@Transactional(readOnly = true)
	public Optional<SocialAccount> findByProviderAndProviderId(String provider, Long providerId) {
		return socialAccountRepository.findByProviderAndProviderId(provider, providerId);
	}

	public SocialAccount save(String provider, Long providerId, Long memberId) {
		return socialAccountRepository.save(new SocialAccount(provider, providerId, memberId));
	}
}
