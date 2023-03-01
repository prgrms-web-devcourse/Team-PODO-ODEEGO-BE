package podo.odeego.domain.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.dto.MemberJoinResponse;
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

	public MemberJoinResponse join(String provider, String providerId, String profileImageUrl) {
		return memberRepository.findByProviderAndProviderId(provider, providerId)
			.map(member -> {
				log.info("Member already exist: {} for provider: {}, providerId: {}.", member, provider, providerId);
				return MemberJoinResponse.existMember(member.id());
			})
			.orElseGet(() -> {
				log.info("New Member for provider: {}, providerId: {}.", provider, providerId);
				Member savedMember = memberRepository.save(
					Member.ofProfileImageUrl(profileImageUrl, provider, providerId));
				return MemberJoinResponse.newMember(savedMember.id());
			});
	}

	public Long join(String nickname) {
		Member savedMember = memberRepository.save(Member.ofNickname(nickname, "provider", "providerId"));
		return savedMember.id();
	}
}
