package com.test.mysql.repository;

import com.test.mysql.entity.FertilizationCalculate;
import com.test.mysql.entity.WeatherData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WeatherRepository extends JpaRepository<WeatherData, Long> {
  /*  @Query("select t from Schedule t where t.userid =?1 and t.unitid =?2")
    List<Schedule> findByUserAndDepartment(Long  userid, Long unitid);
*/
    @Query(value="select t from WeatherData t where t.state = :state")
    Page<WeatherData> findByState(@Param("state") int state, Pageable pageRequest);

    @Query(value="select t from WeatherData t where t.country = :country")
    Page<WeatherData> findBycountry(@Param("country") String  country, Pageable pageRequest);

  @Modifying
  @Transactional
  @Query("delete from WeatherData es where es.state = ?1")
  int deleteByState(int state);
}
