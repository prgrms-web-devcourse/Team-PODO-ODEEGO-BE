package podo.odeego.domain.member.entity;

import static javax.persistence.FetchType.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import podo.odeego.domain.group.entity.Group;
import podo.odeego.domain.group.exception.AlreadyParticipatingGroupException;
import podo.odeego.domain.type.BaseTime;

@Entity
public class Member extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;

	@Column(nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 20)
	private String provider;

	@Column(nullable = false, length = 80)
	private String providerId;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "group_id")
	private Group group;

	protected Member() {
	}

	public Member(String username, String provider, String providerId) {
		this.username = username;
		this.provider = provider;
		this.providerId = providerId;
	}

	public void participateGroup(Group group) {
		this.group = group;
	}

	public void verifyNonOfGroupParticipating() {
		if (isParticipatingGroup()) {
			throw new AlreadyParticipatingGroupException(
				"Member '%d' is already participating group '%s'.".formatted(id(), this.group().id().toString())
			);
		}
	}

	private boolean isParticipatingGroup() {
		return this.group != null;
	}

	public Long id() {
		return id;
	}

	public Group group() {
		return group;
	}

	public String username() {
		return username;
	}

	public String provider() {
		return provider;
	}

	public String providerId() {
		return providerId;
	}
}
