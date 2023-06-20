package podo.odeego.web.auth.service;

import org.springframework.stereotype.Service;

import podo.odeego.domain.account.custom.entity.CustomAccount;
import podo.odeego.domain.account.custom.service.CustomAccountService;
import podo.odeego.domain.member.entity.Member;
import podo.odeego.domain.member.service.MemberService;
import podo.odeego.web.auth.dto.JoinCustomAccountRequest;
import podo.odeego.web.auth.dto.LoginMemberInfoResponse;

@Service
public class CustomAuthService {

	private final CustomAccountService customAccountService;
	private final MemberService memberService;

	public CustomAuthService(CustomAccountService customAccountService, MemberService memberService) {
		this.customAccountService = customAccountService;
		this.memberService = memberService;
	}

	public void join(JoinCustomAccountRequest joinRequest) {
		Member joinedMember = memberService.join(joinRequest.getProfileImageUrl());
		customAccountService.save(joinRequest.getUsername(), joinRequest.getPassword(), joinedMember.id());
	}

	public LoginMemberInfoResponse login(String username, String password) {
		CustomAccount customAccount = customAccountService.findByUsernameAndPassword(username, password);
		Member foundMember = memberService.findById(customAccount.memberId());
		return new LoginMemberInfoResponse(foundMember.id(), foundMember.profileImageUrl(), foundMember.type());
	}
}
