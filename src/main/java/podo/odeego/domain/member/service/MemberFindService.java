package podo.odeego.domain.member.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.exception.MemberNotFoundException;
import podo.odeego.domain.member.repository.MemberRepository;
import podo.odeego.web.error.exception.EntityNotFoundException;

@Service
@Transactional(readOnly = true)
public class MemberFindService {

	private final MemberRepository memberRepository;

	public MemberFindService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}
	
	public Member findById(Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotFoundException(
				"Cannot find Member for memberId=%d.".formatted(memberId))
			);
	}

	public Member findByUsername(String username) {
		return memberRepository.findByNickname(username)
			.orElseThrow(() -> new EntityNotFoundException("User not found: username - %s".formatted(username)));
	}

	public Member findByProviderAndProviderId(String provider, String providerId) {
		return memberRepository.findByProviderAndProviderId(provider, providerId)
			.orElseThrow(() -> new EntityNotFoundException(
				"User not found: provider - %s / providerId - %s".formatted(provider, providerId)));
	}
}
