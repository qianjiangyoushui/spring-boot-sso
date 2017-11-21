package com.test.web1.service;

import com.alibaba.fastjson.JSONObject;
import com.test.mysql.entity.SessionInfo;
import com.test.mysql.entity.User;
import com.test.mysql.repository.SessionInfoRepository;
import com.test.mysql.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Administrator on 2017/10/13.
 */
@Service
public class SessionInfoService {
    @Autowired
    private SessionInfoRepository sessionInfoRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional
    public String checkSessionAuth(JSONObject params) {
        SessionInfo sessionInfo=  sessionInfoRepository.findByUuidAndSkey(params.getString("uuid"),params.getString("skey"));
        if(null==sessionInfo){
            return "false";
        }else{
            Calendar c_now = Calendar.getInstance();
            long now = c_now.getTimeInMillis();
            Calendar c_createTime =  Calendar.getInstance();
            c_createTime.setTime(sessionInfo.getCreateTime());
            long createTimeMillis = c_createTime.getTimeInMillis();
            Calendar c_lastvisit = Calendar.getInstance();
            c_lastvisit.setTime(sessionInfo.getLastVisitTime());
            long lastVisitMillis = c_lastvisit.getTimeInMillis();
            if((now-createTimeMillis)/86400>params.getLong("login_duration")){
                return "false";
            }else if((now-lastVisitMillis)>params.getLong("session_duration")){
                return "false";
            }else{
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //params.put("last_visit_time",format.format(Calendar.getInstance().getTime()));
                sessionInfo.setLastVisitTime(Calendar.getInstance().getTime());
                sessionInfoRepository.findByUuid(sessionInfo.getUuid(),sessionInfo.getLastVisitTime());
                sessionInfoRepository.flush();
                return sessionInfo.getUserInfo();
            }

        }
    }

    /**
     *
     * @param params
     * @return
     * 返回值说明。异常或者失败返回0，更新返回1，新增返回2.
     */
    @Transactional
    public int  changeSessionInfo(JSONObject params){

        if(changeSessionForLogin(params)){

            //将用户信息注册到用户表。
            registerUser(params);
            String uuid = get_id_csessioninfo(params.get("openid").toString());
            if (!"".equals(uuid)) {
                params.put("uuid",uuid);
                String userInfo = params.getString("user_info");
                Date lastVisitTime = params.getDate("last_visit_time");
                int result = sessionInfoRepository.setFixedUserInfoAndlastVisitTimeFor(userInfo,lastVisitTime,uuid);
                if(result==1){
                    return 1;
                }else {
                    return 0;
                }
            } else {
                SessionInfo sessionInfo = new SessionInfo();
                UUID uuid_u = UUID.randomUUID();
                String uuid_Str=  uuid_u.toString().replace("-", "").toUpperCase();
                sessionInfo.setUuid(uuid_Str);
                sessionInfo.setLastVisitTime(params.getDate("last_visit_time"));
                sessionInfo.setCreateTime(params.getDate("create_time"));
                sessionInfo.setOpenId(params.getString("openid"));
                sessionInfo.setUserInfo(params.getString("user_info"));
                sessionInfo.setSessionKey(params.getString("session_key"));
                sessionInfo.setSkey(params.getString("skey"));
                sessionInfoRepository.save(sessionInfo);
                return 2;
            }
        }else {
            return 0;
        }
    }
    @Transactional
    private void registerUser(JSONObject params) {
        String openid = params.getString("openid");
        User user = userRepository.findByOpenid(openid);
        //JSONObject json = params.getJSONObject("user_info");
        String s = params.getString("user_info");
        JSONObject json = JSONObject.parseObject(s);
        if(null==user){
            user = new User();
            user.setOpenid(openid);
            user.setNicename(json.getString("nickName"));
            user.setSex(json.getInteger("gender"));
            user.setCreatedate(Calendar.getInstance().getTime());
            user.setSkey(params.getString("skey"));
            userRepository.save(user);
        }
    }

    public String get_id_csessioninfo(String openid){
       SessionInfo sessionInfo = sessionInfoRepository.findByOpenId(openid);
        if(sessionInfo!=null){
            return sessionInfo.getUuid();
        }else{
            return "";
        }
    }
    /**
     * 这个方法是翻译php的，可能有bug.
     * @param params
     * @return
     */
    public boolean changeSessionForLogin(JSONObject params){
        String openid = params.get("openid").toString();
        SessionInfo sessionInfo = sessionInfoRepository.findByOpenId(openid);
        if (sessionInfo!=null){
            if(sessionInfo.getCreateTime()!=null){
                Calendar c_now = Calendar.getInstance();
                long now = c_now.getTimeInMillis();
                Calendar c_createTime =  Calendar.getInstance();
                c_createTime.setTime(sessionInfo.getCreateTime());
                long createTimeMillis = c_createTime.getTimeInMillis();
                if((now-createTimeMillis)/86400>params.getLong("session_duration")){
                    return true;
                }else{
                    return true;
                }
            }else{
                return false;
            }
        }else{
            return true;
        }
    }

    public SessionInfo getSessionInfoByOpenid(String openid) {
        return sessionInfoRepository.findByOpenId(openid);
    }

    public SessionInfo getSessionInfoBySessionkey(String sessionKey) {
        return sessionInfoRepository.findBySkey(sessionKey);
    }
}
