package podo.odeego.infra.openapi.naver.imagesearch;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.infra.openapi.naver.imagesearch.dto.ImageSearchRequest;
import podo.odeego.infra.openapi.naver.imagesearch.dto.ImageSearchResponse;

@SpringBootTest
class ImageSearchClientTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ImageSearchClient imageSearchClient;

	@Test
	@DisplayName("질의어를 이용하여 이미지를 클릭할 수 있다.")
	void search_image() {
		// given
		String query = "어글리스토브 강남";
		ImageSearchRequest request = new ImageSearchRequest(query);

		// when
		ImageSearchResponse response = imageSearchClient.callImageSearchApi(request);

		// then
		assertThat(response).isNotNull();
		log.info(response.toString());
	}
}