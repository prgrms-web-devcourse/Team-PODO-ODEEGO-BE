package podo.odeego.domain.member.entity;

import javax.persistence.Entity;

@Entity
public class CustomMember extends Member {

	private String username;

	private String password;

	protected CustomMember() {
	}

	public CustomMember(String username, String password) {
		this.username = username;
		this.password = password;
	}
}
