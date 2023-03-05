package podo.odeego.web.api.scraping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.infra.scaping.place.PlaceScraper;

@RestController
@RequestMapping("/scraping")
public class ScrapingApi {

	private final String adminId;
	private final PlaceScraper placeScraper;

	public ScrapingApi(
		@Value("{scraping.authentication.admin.id}") String adminId,
		PlaceScraper placeScraper
	) {
		this.adminId = adminId;
		this.placeScraper = placeScraper;
	}

	// TODO: Open Query API - DB의 지하철역을 조회해서 지하철역을 query로. response 를 DB에 다시 저장
	@PostMapping("/recommend-place/near-by-station")
	public ResponseEntity<Void> scrapRestaurantsAndCafes(
		@RequestParam(name = "admin-id") String adminId
	) {
		placeScraper.scrapRestaurantsAndCafesNearByStation();
		return ResponseEntity.ok()
			.build();
	}

	// TODO: Image API - DB의 place를 조회해서, name으로 open api query. response를 DB에 다시 저장

	private void verifyAdminAuthorization(String adminId) {
		if (!matchesAdmin(adminId)) {
			throw new NotAuthorizedException("No Authorization for adminId='%s'".formatted(adminId));
		}
	}

	private boolean matchesAdmin(String adminId) {
		return this.adminId.equals(adminId);
	}
}
