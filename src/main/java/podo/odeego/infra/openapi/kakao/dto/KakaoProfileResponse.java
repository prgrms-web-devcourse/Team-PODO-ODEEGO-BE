package podo.odeego.infra.openapi.kakao.dto;

public class KakaoProfileResponse {
	private final Long providerId;
	private final String profileImageUrl;

	private KakaoProfileResponse(Long providerId, String profileImageUrl) {
		this.providerId = providerId;
		this.profileImageUrl = profileImageUrl;
	}

	public static KakaoProfileResponse from(GetUserInfoResponse response) {
		return new KakaoProfileResponse(
			response.id(),
			response.kakao_account().profile().profile_image_url()
		);
	}

	public static KakaoProfileResponse forTest(Long providerId, String profileImageUrl) {
		return new KakaoProfileResponse(providerId, profileImageUrl);
	}

	public Long providerId() {
		return providerId;
	}

	public String profileImageUrl() {
		return profileImageUrl;
	}
}
