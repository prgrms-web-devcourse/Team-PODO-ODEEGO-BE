package podo.odeego.web.api.member;

import static org.apache.http.HttpHeaders.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import podo.odeego.domain.member.dto.MemberSignUpRequest;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.domain.station.dto.StationInfo;
import podo.odeego.domain.station.entity.Station;
import podo.odeego.domain.station.service.StationFindService;
import podo.odeego.web.auth.JwtProvider;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
class MemberApiTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private MemberService memberService;

	@Autowired
	private JwtProvider jwtProvider;

	@MockBean
	private StationFindService stationFindService;

	@Test
	@DisplayName("기본 지하철역을 가져올 수 있습니다")
	public void getDefaultStation() throws Exception {
		//given
		Member joinedMember = memberService.join("profileImageUrl");
		memberService.signUp(joinedMember.id(), new MemberSignUpRequest("사용자123", "강남역"));
		String accessToken = jwtProvider.generateAccessToken(joinedMember.id());

		given(stationFindService.findByName("강남역"))
			.willReturn(new StationInfo(new Station("강남역", 37.123, 127.123, "2")));

		//when
		ResultActions result = mockMvc.perform(
			get("/api/v1/members/default-station")
				.header(AUTHORIZATION, "Bearer %s".formatted(accessToken))
		);

		//then
		result.andExpect(status().isOk())
			.andDo(document("get-default-station",
				requestHeaders(
					headerWithName(AUTHORIZATION).description("Access Token")
				),
				responseFields(
					fieldWithPath("stationName").description("지하철역 이름"),
					fieldWithPath("lat").description("위도"),
					fieldWithPath("lng").description("경도")
				)
			));
	}
}