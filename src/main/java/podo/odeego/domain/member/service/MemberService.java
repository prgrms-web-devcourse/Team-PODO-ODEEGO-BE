package podo.odeego.domain.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.dto.MemberJoinRes;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;

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

	public MemberJoinRes join(OAuth2User oauth2User, String provider) {
		return memberRepository.findByProviderAndProviderId(provider, oauth2User.getName())
			.map(member -> {
				log.info("Member already exist: {} for provider: {}, providerId: {}.", member, provider,
					oauth2User.getName());
				return MemberJoinRes.existMember(member.id());
			})
			.orElseGet(() -> {
				log.info("New Member for provider: {}, providerId: {}.", provider,
					oauth2User.getName());
				Member newMember = memberRepository.save(
					new Member(provider, oauth2User.getName())
				);
				return MemberJoinRes.newMember(newMember.id());
			});
	}
}
