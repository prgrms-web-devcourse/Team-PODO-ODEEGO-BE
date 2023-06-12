package podo.odeego.domain.member.entity;

import static javax.persistence.EnumType.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;

import podo.odeego.domain.group.entity.GroupMember;
import podo.odeego.domain.group.exception.AlreadyParticipatingGroupException;
import podo.odeego.domain.member.exception.MemberNicknameOutOfBoundsException;
import podo.odeego.domain.member.exception.MemberNicknameUnformattedException;
import podo.odeego.domain.type.BaseTime;

@Entity
@DiscriminatorColumn
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Member extends BaseTime {

	private static final int MIN_LENGTH = 3;
	private static final int MAX_LENGTH = 20;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "member_id")
	private Long id;

	@Column(length = 20, unique = true)
	private String nickname;

	private String defaultStationName;

	@Enumerated(value = STRING)
	@Column(nullable = false)
	private MemberType type;

	private String profileImageUrl;

	// @Column(nullable = false, length = 20)
	// private String provider;
	//
	// @Column(nullable = false, unique = true, length = 80)
	// private String providerId;

	@OneToMany(mappedBy = "member")
	private List<GroupMember> groupMembers = new ArrayList<>();

	protected Member() {
	}

	// private Member(String provider, String providerId) {
	// 	this.provider = provider;
	// 	this.providerId = providerId;
	// }

	// public Member(String profileImageUrl, MemberType type, String provider, String providerId) {
	// 	this(provider, providerId);
	// 	this.profileImageUrl = profileImageUrl;
	// 	this.type = type;
	// }

	public Member(String profileImageUrl, MemberType type) {
		this.profileImageUrl = profileImageUrl;
		this.type = type;
	}

	//for test
	// public static Member ofStationName(String nickname, String stationName, String provider, String providerId) {
	// 	Member member = new Member(provider, providerId);
	// 	member.nickname = nickname;
	// 	member.defaultStationName = stationName;
	// 	member.type = MemberType.REGULAR;
	// 	return member;
	// }

	//for test
	public static Member ofStationName(String nickname, String stationName) {
		Member member = new Member();
		member.nickname = nickname;
		member.defaultStationName = stationName;
		member.type = MemberType.REGULAR;
		return member;
	}

	// //for test
	// public static Member ofNickname(String nickname, String provider, String providerId) {
	// 	Member member = new Member(provider, providerId);
	// 	member.nickname = nickname;
	// 	member.type = MemberType.REGULAR;
	// 	return member;
	// }

	//for test
	public static Member ofNickname(String nickname) {
		Member member = new Member();
		member.nickname = nickname;
		member.type = MemberType.REGULAR;
		return member;
	}

	public void signUp(String nickname, String defaultStationName) {
		verifyMemberType();
		verifyNickname(nickname);
		this.nickname = nickname;
		this.defaultStationName = defaultStationName;
		this.type = MemberType.REGULAR;
	}

	private void verifyMemberType() {
		if (MemberType.REGULAR.equals(type)) {
			throw new MemberAlreadyRegularException(
				"Can't signUp this member because member's type is %s".formatted(type.toString()));
		}
	}

	private void verifyNickname(String nickname) {
		if (!isValidNicknameLength(nickname)) {
			throw new MemberNicknameOutOfBoundsException(
				"Nickname Length of %s is not valid. It should be between '%d' and '%d'. But was '%d'.".formatted(
					nickname, MIN_LENGTH, MAX_LENGTH, nickname.length()));
		}
		if (!isValidNicknamePattern(nickname)) {
			throw new MemberNicknameUnformattedException(
				"Nickname is invalid format. Input was %s".formatted(nickname));
		}
	}

	private boolean isValidNicknameLength(String nickname) {
		return MIN_LENGTH <= nickname.length() && nickname.length() <= MAX_LENGTH;
	}

	private boolean isValidNicknamePattern(String nickname) {
		return Pattern.matches("^[0-9가-힣]+$", nickname);
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

	public MemberType type() {
		return type;
	}

	public String profileImageUrl() {
		return profileImageUrl;
	}
}
