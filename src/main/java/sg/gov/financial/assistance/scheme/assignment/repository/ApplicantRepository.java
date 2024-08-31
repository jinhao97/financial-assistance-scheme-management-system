package sg.gov.financial.assistance.scheme.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long> {

    @Query("SELECT head FROM ApplicantEntity head LEFT JOIN FETCH head.householdMembers WHERE head.household IS NULL")
    List<ApplicantEntity> findAllHouseholdsWithMembers();
}
