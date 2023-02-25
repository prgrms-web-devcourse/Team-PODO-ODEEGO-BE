package podo.odeego.domain.user.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.user.entity.Member;
import podo.odeego.domain.user.repository.MemberRepository;

@Service
@Transactional
public class MemberService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	@Transactional(readOnly = true)
	public Member findByUsername(String username) {
		return memberRepository.findByUsername(username)
			.orElseThrow(() -> new RuntimeException("User not found: username - %s".formatted(username)));
	}

	@Transactional(readOnly = true)
	public Member findByProviderAndProviderId(String provider, String providerId) {
		return memberRepository.findByProviderAndProviderId(provider, providerId)
			.orElseThrow(() -> new RuntimeException(
				"User not found: provider - %s / providerId - %s".formatted(provider, providerId)));
	}

	public Member join(OAuth2User oauth2User, String provider) {
		return memberRepository.findByProviderAndProviderId(provider, oauth2User.getName())
			.map(member -> {
				log.info("User already joined: {} for provider: {}, providerId: {}.", member, provider,
					oauth2User.getName());
				return member;
			})
			.orElseGet(() -> {
				Map<String, Object> attributes = oauth2User.getAttributes();
				@SuppressWarnings("unchecked")
				Map<String, Object> properties = (Map<String, Object>)attributes.get("properties");

				String nickname = (String)properties.get("profile_nickname");
				return memberRepository.save(
					new Member("nickname", provider, oauth2User.getName())
				);
			});
	}
}
