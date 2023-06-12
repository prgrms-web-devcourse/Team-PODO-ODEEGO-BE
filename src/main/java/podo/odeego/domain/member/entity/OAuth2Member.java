package podo.odeego.domain.member.entity;

import javax.persistence.Entity;

@Entity
public class OAuth2Member extends Member {

	private String provider;

	private String providerId;

	protected OAuth2Member() {
	}

	public OAuth2Member(String provider, String providerId) {
		this.provider = provider;
		this.providerId = providerId;
	}
}
