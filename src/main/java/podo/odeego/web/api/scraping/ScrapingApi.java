package podo.odeego.web.api.scraping;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import podo.odeego.infra.scaping.place.PlaceImageScraper;
import podo.odeego.infra.scaping.place.PlaceScraper;
import podo.odeego.web.api.scraping.exception.NotAuthorizedException;

@RestController
@RequestMapping("/scraping")
public class ScrapingApi {

	private final String adminId;
	private final PlaceScraper placeScraper;
	private final PlaceImageScraper placeImageScraper;

	public ScrapingApi(
		@Value("${scraping.authentication.admin.id}") String adminId,
		PlaceScraper placeScraper,
		PlaceImageScraper placeImageScraper
	) {
		this.adminId = adminId;
		this.placeScraper = placeScraper;
		this.placeImageScraper = placeImageScraper;
	}

	@PostMapping("/scrap/station/recommend-place")
	public ResponseEntity<Void> scrapRestaurantsAndCafes(
		@RequestParam(name = "admin-id") String adminId
	) {
		verifyAdminAuthorization(adminId);
		placeScraper.scrapRestaurantsAndCafesNearByStation();
		return ResponseEntity.ok()
			.build();
	}

	@PostMapping("/scrap/station/recommend-place/images")
	public ResponseEntity<Void> scrapImages(
		@RequestParam(name = "admin-id") String adminId
	) {
		verifyAdminAuthorization(adminId);
		placeImageScraper.scrapImagesByPlace();
		return ResponseEntity.ok()
			.build();
	}

	private void verifyAdminAuthorization(String adminId) {
		if (!matchesAdmin(adminId)) {
			throw new NotAuthorizedException("No Authorization for adminId='%s'".formatted(adminId));
		}
	}

	private boolean matchesAdmin(String adminId) {
		return this.adminId.equals(adminId);
	}
}
