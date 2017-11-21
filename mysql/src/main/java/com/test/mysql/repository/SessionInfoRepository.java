package com.test.mysql.repository;

import com.test.mysql.entity.AppInfo;
import com.test.mysql.entity.SessionInfo;
import com.test.mysql.entity.User;
import com.test.mysql.model.SessionInfoQo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface SessionInfoRepository extends JpaRepository<SessionInfo, Long> {

    public SessionInfo search(SessionInfoQo qo);

    public  SessionInfo findByUuidAndSkey(String uuid, String skey);
    public SessionInfo findByOpenId(String openId);
    @Modifying
    @Query("update SessionInfo u set u.userInfo=?1, u.lastVisitTime = ?2 where u.uuid = ?3")
    public int setFixedUserInfoAndlastVisitTimeFor(String userInfo, Date lastVisitTime, String uuid);

    @Modifying
    @Query(value="update SessionInfo o set o.lastVisitTime=:lastVisitTime where o.uuid =:uuid")
    public int findByUuid(@Param("uuid") String uuid, @Param("lastVisitTime") Date lastVisitTime);

    public SessionInfo findBySkey(String skey);
}
