package podo.odeego.domain.member.entity;

import static javax.persistence.EnumType.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
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

	@Column(length = 20, unique = true)
	private String nickname;

	private String defaultStationName;

	@Enumerated(value = STRING)
	private MemberType memberType;

	private String profileImageUrl;

	@Column(nullable = false, length = 20)
	private String provider;

	@Column(nullable = false, unique = true, length = 80)
	private String providerId;

	@OneToMany(mappedBy = "member")
	private List<GroupMember> groupMembers = new ArrayList<>();

	protected Member() {
	}

	private Member(String provider, String providerId) {
		this.provider = provider;
		this.providerId = providerId;
	}

	public Member(String profileImageUrl, MemberType memberType, String provider, String providerId) {
		this(provider, providerId);
		this.profileImageUrl = profileImageUrl;
		this.memberType = memberType;
	}

	public static Member ofStationName(String nickname, String stationName, String provider, String providerId) {
		Member member = new Member(provider, providerId);
		member.nickname = nickname;
		member.defaultStationName = stationName;
		return member;
	}

	public static Member ofNickname(String nickname, String provider, String providerId) {
		Member member = new Member(provider, providerId);
		member.nickname = nickname;
		return member;
	}

	public static Member ofProfileImageUrl(String profileImageUrl, String provider, String providerId) {
		Member member = new Member(provider, providerId);
		member.profileImageUrl = profileImageUrl;
		return member;
	}

	public void addGroupMember(GroupMember groupMember) {
		if (this.groupMembers.contains(groupMember)) {
			throw new AlreadyParticipatingGroupException(
				"Member '%d' is already participating group '%s'.".formatted(this.id, groupMember.getGroupId())
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

	public List<GroupMember> getGroupMembers() {
		return this.groupMembers;
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

	public String defaultStationName() {
		return defaultStationName;
	}

	public MemberType memberType() {
		return memberType;
	}

	public String profileImageUrl() {
		return profileImageUrl;
	}

	public String provider() {
		return provider;
	}

	public String providerId() {
		return providerId;
	}
}
