package podo.odeego.infra.openapi.naver.imagesearch;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import podo.odeego.infra.openapi.naver.imagesearch.client.ImageSearchClient;
import podo.odeego.infra.openapi.naver.imagesearch.dto.ImageQueryDto;

@SpringBootTest
class ImageSearchClientTest {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ImageSearchClient imageSearchClient;

	@Test
	@DisplayName("질의어를 이용하여 이미지를 클릭할 수 있다.")
	void search_image() {
		// given
		String query = "어글리스토브 강남점 강남역";

		// when
		List<ImageQueryDto> response = imageSearchClient.queryImages(query);

		// then
		assertThat(response).isNotNull();
		for (ImageQueryDto imageQueryDto : response) {
			log.info(imageQueryDto.toString());
		}
	}
}