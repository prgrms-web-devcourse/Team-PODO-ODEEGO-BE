package podo.odeego.domain.account.custom.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import podo.odeego.domain.account.custom.entity.CustomAccount;
import podo.odeego.domain.account.custom.exception.WrongUsernameOrPasswordException;
import podo.odeego.domain.account.custom.repository.CustomAccountRepository;

@Service
@Transactional
public class CustomAccountService {

	private final CustomAccountRepository customAccountRepository;

	public CustomAccountService(CustomAccountRepository customAccountRepository) {
		this.customAccountRepository = customAccountRepository;
	}

	public CustomAccount findByUsernameAndPassword(String username, String password) {
		return customAccountRepository.findByUsernameAndPassword(username, password)
			.orElseThrow(() -> new WrongUsernameOrPasswordException(
				"Wrong username or password. username: %s, password: %s".formatted(username, password)));
	}

	public void save(String username, String password, Long memberId) {
		customAccountRepository.save(new CustomAccount(username, password, memberId));
	}
}
