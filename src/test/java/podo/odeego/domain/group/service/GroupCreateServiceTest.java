package podo.odeego.domain.group.service;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.group.dto.request.GroupCreateRequest;
import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.repository.GroupRepository;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.repository.MemberRepository;

@SpringBootTest
@Transactional
class GroupCreateServiceTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private GroupCreateService groupCreateService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private MemberRepository memberRepository;

	@Test
	@DisplayName("회원은 모임을 생성할 수 있다.")
	void create_group() {
		// given
		Member member = new Member("username", "provider", "provider_id");
		Member savedMember = memberRepository.save(member);

		GroupCreateRequest createRequest = new GroupCreateRequest(2L);

		// when
		UUID savedGroupId = groupCreateService.create(savedMember.id(), createRequest);

		// then
		Group findGroup = groupRepository.findById(savedGroupId).get();

		Member actualOwner = findGroup.owner();
		Member expectedOwner = savedMember;

		assertThat(actualOwner).isEqualTo(expectedOwner);
	}
}