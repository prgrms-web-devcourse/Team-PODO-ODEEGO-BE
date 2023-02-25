package podo.odeego.domain.user.entity;

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

	@Column(nullable = false, length = 20)
	private String username;

	@Column(nullable = false, length = 20)
	private String provider;

	@Column(nullable = false, length = 80)
	private String providerId;

	protected Member() {
	}

	public Member(String username, String provider, String providerId) {
		this.username = username;
		this.provider = provider;
		this.providerId = providerId;
	}

	public Long id() {
		return id;
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
