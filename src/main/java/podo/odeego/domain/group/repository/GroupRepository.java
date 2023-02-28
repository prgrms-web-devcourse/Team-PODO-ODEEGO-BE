package podo.odeego.domain.group.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import podo.odeego.domain.group.entity.Group;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {
}
