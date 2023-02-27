package podo.odeego.domain.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {

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