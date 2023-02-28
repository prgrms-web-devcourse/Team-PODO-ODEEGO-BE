package podo.odeego.web.api.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetMemberInfoResponse {

	private Long id;
	private String connected_at;
	private Properties properties;
	@JsonProperty("kakao_account")
	private KakaoAccount kakao_account;

	public GetMemberInfoResponse() {
	}

	public Long id() {
		return id;
	}

	public KakaoAccount kakao_account() {
		return kakao_account;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setKakaoAccount(KakaoAccount kakao_account) {
		this.kakao_account = kakao_account;
	}

	public static class Properties {
		private String nickname;
		private String profile_image;
		private String thumbnail_image;

		public Properties() {
		}
	}

	public static class KakaoAccount {

		private Boolean profile_nickname_needs_agreement;
		private Boolean profile_image_needs_agreement;
		private Profile profile;
		private Boolean has_email;
		private Boolean email_needs_agreement;
		private Boolean is_email_valid;
		private Boolean is_email_verified;
		private String email;
		private Boolean has_age_range;
		private Boolean age_range_needs_agreement;
		private Boolean age_range;
		private Boolean has_gender;
		private Boolean gender_needs_agreement;
		private String gender;

		public KakaoAccount() {
		}

		public Profile profile() {
			return profile;
		}

		public void setProfile(Profile profile) {
			this.profile = profile;
		}

		public static class Profile {

			private String nickname;
			private String thumbnail_image_url;
			@JsonProperty("profile_image_url")
			private String profile_image_url;
			private Boolean is_default_image;

			public String profile_image_url() {
				return profile_image_url;
			}

			public void setProfile_image_url(String profile_image_url) {
				this.profile_image_url = profile_image_url;
			}

			public Profile() {
			}
		}
	}
}
