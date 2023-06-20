package podo.odeego.domain.account.custom.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class CustomAccount {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true, length = 20)
	private String username;

	@Column(nullable = false, length = 16)
	private String password;

	@Column
	private Long memberId;

	protected CustomAccount() {
	}

	public CustomAccount(String username, String password, Long memberId) {
		this.username = username;
		this.password = password;
		this.memberId = memberId;
	}

	public Long memberId() {
		return memberId;
	}
}
