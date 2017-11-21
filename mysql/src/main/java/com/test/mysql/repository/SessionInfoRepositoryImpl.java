package com.test.mysql.repository;

import com.test.mysql.entity.SessionInfo;
import com.test.mysql.entity.User;
import com.test.mysql.model.SessionInfoQo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SessionInfoRepositoryImpl  {
    @PersistenceContext
    private EntityManager em;
    @SuppressWarnings("unchecked")
    public SessionInfo search(SessionInfoQo qo) {
        String dataSql = "select t from CSessionInfo t where 1 = 1";

        if(null != qo && !StringUtils.isEmpty(qo.getUuid())) {
            dataSql += " and t.uuid = ?1";
        }
        if(null != qo && !StringUtils.isEmpty(qo.getSky())) {
            dataSql += " and t.skey = ?2";
        }

        Query dataQuery = em.createQuery(dataSql);

        if(null != qo && !StringUtils.isEmpty(qo.getUuid())) {
            dataQuery.setParameter(1, qo.getUuid());
            dataQuery.setParameter(2, qo.getSky());
        }
        List<SessionInfo> data = dataQuery.getResultList();
        if(data!=null&&data.size()>0){
            return data.get(0);
        }else{
            return null;
        }
    }

}
