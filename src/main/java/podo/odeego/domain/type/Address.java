package podo.odeego.domain.type;

import java.util.Objects;

import javax.persistence.Embeddable;

@Embeddable
public class Address {

	private String address;

	public Address() {
	}

	public Address(String address) {
		this.address = address;
	}

	public String address() {
		return address;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Address that = (Address)o;
		return Objects.equals(address, that.address);
	}

	@Override
	public int hashCode() {
		return Objects.hash(address);
	}
}
