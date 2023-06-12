package podo.odeego.domain.group.service;

import static org.assertj.core.api.Assertions.*;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import podo.odeego.config.TestConfig;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.entity.GroupCapacity;
import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.entity.ParticipantType;
import podo.odeego.domain.group.exception.GroupHostNotMatchException;
import podo.odeego.domain.group.repository.GroupMemberRepository;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;

@ExtendWith(SpringExtension.class)
@Import(TestConfig.class)
@DataJpaTest
class GroupRemoveServiceTest {

	@Autowired
	private GroupRemoveService groupRemoveService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupMemberRepository groupMemberRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private EntityManager em;

	@Test
	@DisplayName("그룹의 호스트만 그룹을 삭제할 수 있다.")
	void only_group_host_can_delete_group() {
		// given
		Member groupHost = memberRepository.save(Member.ofNickname("host"));
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(GroupMember.newInstance(groupHost, ParticipantType.HOST));

		Group savedGroup = groupRepository.save(group);

		// when
		groupRemoveService.remove(groupHost.id(), savedGroup.id());

		// then
		Optional<Group> findGroup = groupRepository.findById(savedGroup.id());
		assertThat(findGroup).isNotPresent();
	}

	@Test
	@DisplayName("그룹의 호스트가 아닌 회원이 그룹을 삭제할 때 예외가 발생한다.")
	void throws_exception_when_delete_group_except_host() {
		// given
		Member groupHost = memberRepository.save(Member.ofNickname("groupHost"));
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(GroupMember.newInstance(groupHost, ParticipantType.HOST));
		Group savedGroup = groupRepository.save(group);

		Member nonGroupHost = memberRepository.save(Member.ofNickname("nonGroupHost"));

		// when & then
		assertThatThrownBy(() -> groupRemoveService.remove(nonGroupHost.id(), savedGroup.id()))
			.isInstanceOf(GroupHostNotMatchException.class);
	}

	@Test
	@DisplayName("호스트로 참여한 회원이 그룹 정보를 삭제할 때, 그룹과 그룹에 속해있는 그룹멤버가 모두 삭제된다.")
	void group_should_be_deleted_if_member_participates_as_host() {
		// given
		Member hostMember =
			memberRepository.save(Member.ofNickname("host"));

		GroupMember groupMemberAsHost = GroupMember.newInstance(hostMember, ParticipantType.HOST);
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(groupMemberAsHost);
		groupRepository.save(group);

		// when
		groupRemoveService.deleteGroupInfoByParticipantType(hostMember.id());

		// then
		int actualAllGroupSize = groupRepository.findAll()
			.size();
		assertThat(actualAllGroupSize).isZero();

		int actualAllGroupMemberSize = groupMemberRepository.findAll()
			.size();
		assertThat(actualAllGroupMemberSize).isZero();
	}

	@Test
	@DisplayName("게스트로 참여한 회원이 그룹 정보를 삭제할 때, 그룹은 삭제되지 않고 그룹 멤버만 삭제된다.")
	void only_groupMember_is_deleted_if_member_participates_as_guest() {
		// given
		Member guestMember =
			memberRepository.save(Member.ofNickname("guest"));

		GroupMember groupMemberAsGuest = GroupMember.newInstance(guestMember, ParticipantType.GUEST);
		Group group = new Group(new GroupCapacity(GroupCapacity.MAX_CAPACITY), Group.GROUP_VALID_TIME);
		group.addGroupMember(groupMemberAsGuest);
		groupRepository.save(group);

		// when
		groupRemoveService.deleteGroupInfoByParticipantType(guestMember.id());

		// then
		int actualAllGroupSize = groupRepository.findAll()
			.size();
		assertThat(actualAllGroupSize).isNotZero();

		int actualAllGroupMemberSize = groupMemberRepository.findAll()
			.size();
		assertThat(actualAllGroupMemberSize).isZero();
	}
}