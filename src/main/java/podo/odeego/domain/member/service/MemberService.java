package podo.odeego.domain.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.entity.MemberType;
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
				log.info("Member already exist: {} for provider: {}, providerId: {}, memberType: {}.", member, provider,
					providerId, member.type());
				return new MemberJoinResponse(member.id(),
					member.type());
			})
			.orElseGet(() -> {
				log.info("New Member for provider: {}, providerId: {}.", provider, providerId);
				Member savedMember = memberRepository.save(
					new Member(profileImageUrl, MemberType.PRE, provider, providerId)
				);
				return new MemberJoinResponse(savedMember.id(), savedMember.type());
			});
	}

	public Long join(String nickname) {
		Member savedMember = memberRepository.save(Member.ofNickname(nickname, "provider", "providerId"));
		return savedMember.id();
	}
}
