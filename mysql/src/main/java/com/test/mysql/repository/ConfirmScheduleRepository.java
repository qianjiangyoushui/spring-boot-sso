package com.test.mysql.repository;

import com.test.mysql.entity.ConfirmSchedule;
import com.test.mysql.entity.Schedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfirmScheduleRepository extends JpaRepository<ConfirmSchedule, Long> {
  /*  @Query("select t from Schedule t where t.userid =?1 and t.unitid =?2")
    List<Schedule> findByUserAndDepartment(Long  userid, Long unitid);
*/
//    @Query("select t from Schedule t where t.user = :userid")
//    Page<Schedule> findByUser(@Param("userid") String userid, Pageable pageRequest);

}
