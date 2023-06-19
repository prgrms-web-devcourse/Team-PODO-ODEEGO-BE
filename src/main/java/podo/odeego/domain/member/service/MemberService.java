package podo.odeego.domain.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.service.GroupRemoveService;
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
	private final MemberFindService memberFindService;
	private final GroupRemoveService groupRemoveService;

	public MemberService(
		MemberRepository memberRepository,
		MemberFindService memberFindService,
		GroupRemoveService groupRemoveService
	) {
		this.memberRepository = memberRepository;
		this.memberFindService = memberFindService;
		this.groupRemoveService = groupRemoveService;
	}

	public Member join(String profileImageUrl) {
		return memberRepository.save(new Member(profileImageUrl, MemberType.PRE));
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

	public void leave(Long memberId) {
		Member findMember = memberFindService.findById(memberId);

		groupRemoveService.deleteGroupInfoByParticipantType(memberId);

		memberRepository.delete(findMember);
	}

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId)));
	}
}
