package podo.odeego.domain.member.service;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.response.GroupResponses;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.group.service.GroupQueryService;
import podo.odeego.domain.group.service.GroupRemoveService;
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
	private final GroupRemoveService groupRemoveService;
	private final GroupQueryService groupQueryService;
	private final GroupMemberRepository groupMemberRepository;
	private final GroupRepository groupRepository;

	public MemberService(
		MemberRepository memberRepository,
		GroupRemoveService groupRemoveService,
		GroupQueryService groupQueryService,
		GroupMemberRepository groupMemberRepository, GroupRepository groupRepository) {
		this.memberRepository = memberRepository;
		this.groupRemoveService = groupRemoveService;
		this.groupQueryService = groupQueryService;
		this.groupMemberRepository = groupMemberRepository;
		this.groupRepository = groupRepository;
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

	public void leave(Long memberId) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId)));

		GroupResponses groupResponses = groupQueryService.getAll(memberId);

		if (isGroupExists(groupResponses)) {
			UUID groupId = groupResponses.getGroups().get(0).getGroupId();
			Group group = groupRepository.findById(groupId).get();

			if (group.isGroupHost(member)) {
				groupRemoveService.remove(member.id(), groupId);
			} else { // participating as guest
				GroupMember groupMember = member.getGroupMembers().get(0);
				group.removeGroupMember(groupMember);
				// Long groupMemberId = groupMember.id();
				// groupMemberRepository.deleteById(groupMemberId);
			}
		}

		memberRepository.delete(member);
	}

	private boolean isGroupExists(GroupResponses groupResponses) {
		return groupResponses.getGroups().size() != 0;
	}
}
