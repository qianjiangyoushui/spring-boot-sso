package com.test.mysql.repository;

import com.test.mysql.entity.Schedule;
import com.test.mysql.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
  /*  @Query("select t from Schedule t where t.userid =?1 and t.unitid =?2")
    List<Schedule> findByUserAndDepartment(Long  userid, Long unitid);
*/
    @Query(value="select t from Schedule t where t.user = :userid")
    Page<Schedule> findByUserid(@Param("userid") String  userid, Pageable pageRequest);
    @Query(value="select t from Schedule t where t.user = :user")
    Page<Schedule> findByUser(@Param("user") User  user, Pageable pageRequest);

}
