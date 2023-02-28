package podo.odeego.domain.group.entity;

public enum ParticipantType {

	HOST,
	GUEST;

	public boolean isHost() {
		return this == HOST;
	}
}
