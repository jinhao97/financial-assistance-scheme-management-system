package sg.gov.financial.assistance.scheme.assignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sg.gov.financial.assistance.scheme.assignment.entity.SchemeEntity;

import java.util.List;

@Repository
public interface SchemeRepository extends JpaRepository<SchemeEntity, Long> {
    @Query("SELECT s FROM SchemeEntity s WHERE CURRENT_DATE BETWEEN s.startDate AND s.endDate")
    List<SchemeEntity> findAllSchemesWithinCurrentDate();
}
