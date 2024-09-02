package sg.gov.financial.assistance.scheme.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sg.gov.financial.assistance.scheme.assignment.entity.ApplicantEntity;
import sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData;

import java.util.List;

@Repository
public interface ApplicantRepository extends JpaRepository<ApplicantEntity, Long> {

    @Query("SELECT head FROM ApplicantEntity head LEFT JOIN FETCH head.householdMembers WHERE head.household IS NULL")
    List<ApplicantEntity> findAllHouseholdsWithMembers();


    @Query(
            """
                    SELECT NEW sg.gov.financial.assistance.scheme.assignment.entity.HouseholdData(member)
                    FROM ApplicantEntity member
                    INNER JOIN ApplicantEntity applicant
                    ON member.household.id = applicant.id
                    WHERE applicant.uin = :uin
            """
    )
    List<HouseholdData> findHouseholdWithMembersByUin(@Param("uin") String uin);

    ApplicantEntity findApplicantEntityByUin(String uin);
}
