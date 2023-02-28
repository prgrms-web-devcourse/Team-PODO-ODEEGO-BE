package podo.odeego.web.api.auth.dto;

public class GetMemberInfoResponse {

	private final Long id;
	private final String connected_at;
	private final Properties properties;
	private final KakaoAccount kakao_acount;

	public GetMemberInfoResponse(Long id, String connected_at, Properties properties, KakaoAccount kakao_acount) {
		this.id = id;
		this.connected_at = connected_at;
		this.properties = properties;
		this.kakao_acount = kakao_acount;
	}

	public Long id() {
		return id;
	}

	public String connected_at() {
		return connected_at;
	}

	public Properties properties() {
		return properties;
	}

	public KakaoAccount kakao_acount() {
		return kakao_acount;
	}

	public static class Properties {
		private final String nickname;
		private final String profile_image;
		private final String thumbnail_image;

		public Properties(String nickname, String profile_image, String thumbnail_image) {
			this.nickname = nickname;
			this.profile_image = profile_image;
			this.thumbnail_image = thumbnail_image;
		}

		public String nickname() {
			return nickname;
		}

		public String profile_image() {
			return profile_image;
		}

		public String thumbnail_image() {
			return thumbnail_image;
		}

	}

	public static class KakaoAccount {
	}
}
