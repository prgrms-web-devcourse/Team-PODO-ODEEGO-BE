package podo.odeego.domain.member.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.domain.member.exception.MemberNicknameDuplicatedException;
import podo.odeego.domain.member.exception.MemberNotFoundException;
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

	public Long signUp(Long memberId, MemberSignUpRequest signUpRequest) {
		checkNicknameDuplicated(signUpRequest.nickname());

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId)));
		member.signUp(signUpRequest.nickname(), signUpRequest.defaultStationName());
		return member.id();
	}

	private void checkNicknameDuplicated(String nickname) {
		Optional<Member> member = memberRepository.findByNickname(nickname);
		if (member.isPresent()) {
			throw new MemberNicknameDuplicatedException(
				"Cannot sig up with duplicated nickname: %s".formatted(nickname));
		}
	}

	public Long join(String nickname) {
		try {
			Member newMember = Member.ofNickname(nickname, "provider", "providerId");
			Member savedMember = memberRepository.saveAndFlush(newMember);
			return savedMember.id();
		} catch (DataIntegrityViolationException e) {
			throw new MemberNicknameDuplicatedException(
				"Cannot execute member join. Nickname '%s' is duplicated.".formatted(nickname)
			);
		}
	}
}
