package podo.odeego.domain.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.dto.MemberJoinResponse;
import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.entity.MemberType;
import podo.odeego.domain.member.exception.DefaultStationNotExistsException;
import podo.odeego.domain.member.exception.MemberNicknameDuplicatedException;
import podo.odeego.domain.member.exception.MemberNotFoundException;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.station.exception.StationNotFoundException;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional
public class MemberService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final StationFindService stationFindService;
	private final MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository, StationFindService stationFindService) {
		this.memberRepository = memberRepository;
		this.stationFindService = stationFindService;
	}

	public MemberJoinResponse join(String profileImageUrl, String provider, String providerId) {
		return memberRepository.findByProviderAndProviderId(provider, providerId)
			.map(member -> {
				log.info("Member already exist: {} for provider: {}, providerId: {}, memberType: {}.", member, provider,
					providerId, member.type());
				return new MemberJoinResponse(member.id(), member.profileImageUrl(), member.type());
			})
			.orElseGet(() -> {
				log.info("New Member for provider: {}, providerId: {}.", provider, providerId);
				Member savedMember = memberRepository.save(
					new Member(profileImageUrl, MemberType.PRE, provider, providerId)
				);
				return new MemberJoinResponse(savedMember.id(), savedMember.profileImageUrl(), savedMember.type());
			});
	}

	public Long join(String nickname) {
		verifyUniqueNickname(nickname);
		Member savedMember = memberRepository.save(Member.ofNickname(nickname, "provider", "providerId"));
		return savedMember.id();
	}

	public void signUp(Long memberId, MemberSignUpRequest signUpRequest) {
		verifyUniqueNickname(signUpRequest.nickname());

		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId)));
		member.signUp(signUpRequest.nickname(), signUpRequest.defaultStationName());
	}

	private void verifyUniqueNickname(String nickname) {
		if (memberRepository.existsByNickname(nickname)) {
			throw new MemberNicknameDuplicatedException(
				"Cannot sign up with duplicated nickname: %s".formatted(nickname));
		}
	}

	private void verifyStationExists(String stationName) {
		try {
			stationFindService.verifyStationExists(stationName);
		} catch (StationNotFoundException e) {
			throw new DefaultStationNotExistsException(e.getMessage());
		}
	}

	public void leave(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId)));
		memberRepository.delete(member);
	}
}
