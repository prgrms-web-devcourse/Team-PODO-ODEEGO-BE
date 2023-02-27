package podo.odeego.domain.member.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.exception.AlreadyParticipatingGroupException;
import podo.odeego.domain.type.BaseTime;

@Entity
public class Member extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;

	@Column(length = 20)
	private String nickname;

	@Column(nullable = false, length = 20)
	private String provider;

	@Column(nullable = false, length = 80)
	private String providerId;

	@OneToMany(mappedBy = "member")
	private List<GroupMember> groupMembers = new ArrayList<>();

	protected Member() {
	}

	public Member(String provider, String providerId) {
		this.provider = provider;
		this.providerId = providerId;
	}

	public Member(String nickname, String provider, String providerId) {
		this(provider, providerId);
		this.nickname = nickname;
	}

	public void addGroupMember(GroupMember groupMember) {
		if (this.groupMembers.contains(groupMember)) {
			throw new AlreadyParticipatingGroupException(
				"Member '%d' is already participating group '%s'.".formatted(this.id, groupMember.id())
			);
		}
		this.groupMembers.add(groupMember);
	}

	public void verifyNonOfGroupParticipating() {
		if (isParticipatingGroup()) {
			throw new AlreadyParticipatingGroupException(
				"Member '%d' is already participating group '%s'.".formatted(id(), this.groupMembers.get(0).id())
			);
		}
	}

	private boolean isParticipatingGroup() {
		return this.groupMembers.size() != 0;
	}

	public Long id() {
		return id;
	}

	public String nickname() {
		return nickname;
	}

	public String provider() {
		return provider;
	}

	public String providerId() {
		return providerId;
	}
}
