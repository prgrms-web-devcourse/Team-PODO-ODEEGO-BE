package podo.odeego.infra.openapi.naver;

import org.springframework.util.MultiValueMap;

public abstract class ClientRequest {

	private String query;

	public ClientRequest(String query) {
		this.query = query;
	}

	public String query() {
		return query;
	}

	public abstract MultiValueMap<String, String> toMultiValueMap();
}
