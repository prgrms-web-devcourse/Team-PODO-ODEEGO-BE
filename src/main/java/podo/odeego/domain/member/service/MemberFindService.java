package podo.odeego.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.dto.MemberDefaultStationGetResponse;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.exception.MemberNotFoundException;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.domain.station.service.StationFindService;

@Service
@Transactional(readOnly = true)
public class MemberFindService {

	private final MemberRepository memberRepository;
	private final StationFindService stationFindService;

	public MemberFindService(MemberRepository memberRepository, StationFindService stationFindService) {
		this.memberRepository = memberRepository;
		this.stationFindService = stationFindService;
	}

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId))
			);
	}

	public MemberDefaultStationGetResponse findDefaultStation(Long memberId) {
		String defaultStationName = findById(memberId).defaultStationName();
		return new MemberDefaultStationGetResponse(
			stationFindService.findByName(defaultStationName)
		);
	}
}
