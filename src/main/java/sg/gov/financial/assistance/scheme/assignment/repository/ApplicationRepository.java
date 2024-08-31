package sg.gov.financial.assistance.scheme.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sg.gov.financial.assistance.scheme.assignment.entity.ApplicationEntity;

@Repository
public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long> {
}
