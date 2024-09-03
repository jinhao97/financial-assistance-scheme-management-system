package sg.gov.financial.assistance.scheme.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sg.gov.financial.assistance.scheme.assignment.entity.AdministratorEntity;

import java.util.Optional;

@Repository
public interface AdministratorRepository extends JpaRepository<AdministratorEntity, Long> {
    Optional<AdministratorEntity> findByUin(String uin);

    Boolean existsByUin(String uin);
}
