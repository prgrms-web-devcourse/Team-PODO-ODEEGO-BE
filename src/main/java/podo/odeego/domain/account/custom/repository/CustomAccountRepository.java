package podo.odeego.domain.account.custom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import podo.odeego.domain.account.custom.entity.CustomAccount;

public interface CustomAccountRepository extends CrudRepository<CustomAccount, Long> {

	@Query
	Optional<CustomAccount> findByUsernameAndPassword(String username, String password);
}
