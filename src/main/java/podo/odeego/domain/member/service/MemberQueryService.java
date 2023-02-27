package podo.odeego.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.exception.MemberNotFoundException;
import podo.odeego.domain.member.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberQueryService {

	private final MemberRepository memberRepository;

	public MemberQueryService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId))
			);
	}
}
