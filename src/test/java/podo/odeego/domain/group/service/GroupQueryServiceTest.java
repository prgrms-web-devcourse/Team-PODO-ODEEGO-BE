package podo.odeego.domain.group.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestConfig;
import podo.odeego.domain.group.dto.GroupMemberExistResponse;
import podo.odeego.domain.group.dto.response.GroupResponse;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.group.exception.GroupHostNotMatchException;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class GroupQueryServiceTest {

	@Autowired
	private GroupQueryService groupQueryService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("그룹의 호스트만 그룹의 상세정보를 조회할 수 있다.")
	void only_group_host_can_query_group_details() {
		// given
		Member groupHost = memberRepository.save(Member.ofNickname("host"));
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(GroupMember.newInstance(groupHost, ParticipantType.HOST));

		Group savedGroup = groupRepository.save(group);

		// when
		GroupResponse groupResponse = groupQueryService.getOne(groupHost.id(), savedGroup.id());

		// then
		Long actualGroupHostId = groupResponse.getHostId();
		Long expectedGroupHostId = groupHost.id();

		assertThat(actualGroupHostId).isEqualTo(expectedGroupHostId);
	}

	@Test
	@DisplayName("그룹 호스트가 아닌 회원이 그룹의 상세정보를 조회하면 예외가 발생한다.")
	void throws_exception_when_query_group_details_except_group_host() {
		// given
		Member groupHost = memberRepository.save(Member.ofNickname("groupHost"));
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(GroupMember.newInstance(groupHost, ParticipantType.HOST));
		Group savedGroup = groupRepository.save(group);

		Member nonGroupHost = memberRepository.save(Member.ofNickname("nonGroupHost"));

		// when & then
		assertThatThrownBy(() -> groupQueryService.getOne(nonGroupHost.id(), savedGroup.id()))
			.isInstanceOf(GroupHostNotMatchException.class);
	}

	@DisplayName("멤버는 그룹에 속해있는지 확인할 수 있다.")
	@Test
	void isSubmitted() {
		// given
		Member host = memberRepository.save(Member.ofNickname("groupHost"));
		Member member = memberRepository.save(Member.ofNickname("groupMember"));
		Group group = groupRepository.save(
			new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME));
		group.addGroupMember(GroupMember.newInstance(host, ParticipantType.HOST));
		group.addGroupMember(GroupMember.newInstance(member, ParticipantType.GUEST));

		GroupMemberExistResponse expect = new GroupMemberExistResponse(true);
		// when
		GroupMemberExistResponse actual = groupQueryService.isSubmitted(group.id(), member.id());

		// then
		assertThat(actual)
			.isEqualTo(expect);
	}

	@DisplayName("멤버는 그룹에 속해있지 않은지 확인할 수 있다.")
	@Test
	void isNotSubmitted() {
		// given
		Member host = memberRepository.save(Member.ofNickname("groupHost"));
		Member member = memberRepository.save(Member.ofNickname("groupMember"));
		Group group = groupRepository.save(
			new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME));
		group.addGroupMember(GroupMember.newInstance(host, ParticipantType.HOST));

		GroupMemberExistResponse expect = new GroupMemberExistResponse(false);
		// when
		GroupMemberExistResponse actual = groupQueryService.isSubmitted(group.id(), member.id());

		// then
		assertThat(actual)
			.isEqualTo(expect);
	}
}