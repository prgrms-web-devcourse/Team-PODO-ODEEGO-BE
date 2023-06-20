package podo.odeego.domain.account.social.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SocialAccount {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, length = 20)
	private String provider;

	@Column(nullable = false, unique = true)
	private Long providerId;

	private Long memberId;

	protected SocialAccount() {
	}

	public SocialAccount(String provider, Long providerId, Long memberId) {
		this.provider = provider;
		this.providerId = providerId;
		this.memberId = memberId;
	}

	public Long memberId() {
		return memberId;
	}

	public String provider() {
		return provider;
	}
}
