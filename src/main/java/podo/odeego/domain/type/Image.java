package podo.odeego.domain.type;

import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Lob;

@Embeddable
public class Image {

	@Lob
	private String url;

	protected Image() {
	}

	public Image(String url) {
		this.url = url;
	}

	public String url() {
		return url;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Image image = (Image)o;
		return Objects.equals(url, image.url);
	}

	@Override
	public int hashCode() {
		return Objects.hash(url);
	}
}
