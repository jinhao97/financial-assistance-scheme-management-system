package sg.gov.financial.assistance.scheme.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.gov.financial.assistance.scheme.assignment.entity.ApplicationEntity;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {

    ApplicationEntity findApplicationEntitiesBySchemeEntityIdAndApplicantEntityId(Long schemeEntityId, Long applicantEntityId);

    List<ApplicationEntity> findApplicationEntitiesByApplicantEntityId(Long applicantEntityId);
}
