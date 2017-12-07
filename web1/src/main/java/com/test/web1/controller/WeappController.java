package com.test.web1.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.util.Base64;
import com.test.mysql.entity.*;
import com.test.mysql.model.FertilizationQo;
import com.test.mysql.model.WeatherQo;
import com.test.mysql.repository.FertilizationRepository;
import com.test.mysql.repository.WeatherRepository;
import com.test.web1.constant.Constants;
import com.test.web1.constant.ReturnCodeType;
import com.test.web1.service.AppInfoService;
import com.test.web1.service.SessionInfoService;

import com.test.web1.utils.DecryptDataUtil;
import com.test.web1.utils.HttpsRequestUtil;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/weapp")
public class WeappController {
    @Autowired
    private AppInfoService appInfoService;
    @Autowired
    private SessionInfoService sessionInfoService;
    @Autowired
    private FertilizationRepository fertilizationRepository;
    @Autowired
    private WeatherRepository weatherRepository;
    @RequestMapping("/userinfo")
    public @ResponseBody String getUserinfo(HttpServletRequest request){
        String skey=request.getHeader(Constants.WX_HEADER_SKEY);
        String name1 = request.getParameter("name");
        SessionInfo sessionInfo = sessionInfoService.getSessionInfoBySessionkey(skey);
        return JSONObject.toJSONString(sessionInfo);
    }
    @RequestMapping("/fertilization")
    public @ResponseBody String fertalizatinlist(HttpServletRequest request){
        String skey=request.getHeader(Constants.WX_HEADER_SKEY);
        String name = request.getParameter("size");
        BigDecimal size = BigDecimal.ZERO;
        try{
             size = new BigDecimal(name);
        }catch(Exception e){
            JSONObject result = new JSONObject();
            result.put("state","0");
            return result.toString();
        }
        FertilizationQo fertilizationQo = new FertilizationQo();
        fertilizationQo.setSize(100);
        Pageable pageable = new PageRequest(fertilizationQo.getPage(), fertilizationQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
        JSONObject result = new JSONObject();
        Page<FertilizationCalculate> pageList = fertilizationRepository.findByState(0,pageable);
        List<FertilizationCalculate> resultList = new ArrayList<FertilizationCalculate>();
        for (FertilizationCalculate object:pageList.getContent()){
            object.setSum(size.multiply(object.getVolume()).setScale(2));
            resultList.add(object);
        }
        result.put("content",resultList);
        result.put("total",pageList.getTotalElements());
        result.put("page",pageList.getTotalPages());
        result.put("size",pageList.getSize());
        result.put("state","1");
        /*String re = result.toString();
        String re1 = result.toJSONString();*/
        return result.toString();
    }
    @RequestMapping("/weather")
    public @ResponseBody String weather(HttpServletRequest request){
        String skey=request.getHeader(Constants.WX_HEADER_SKEY);
        String country = request.getParameter("country");
        WeatherQo weatherQo = new WeatherQo();
        weatherQo.setSize(100);
        Pageable pageable = new PageRequest(weatherQo.getPage(), weatherQo.getSize(), new Sort(Sort.Direction.ASC, "id"));
        JSONObject result = new JSONObject();
        Page<WeatherData> pageList = weatherRepository.findBycountry(country,pageable);
        result.put("content",pageList.getContent());
        result.put("total",pageList.getTotalElements());
        result.put("page",pageList.getTotalPages());
        result.put("size",pageList.getSize());
        result.put("state","1");
        /*String re = result.toString();
        String re1 = result.toJSONString();*/
        return result.toString();
    }

    @RequestMapping("/login")
    public @ResponseBody String login(HttpServletRequest request) {

        String code = request.getHeader(Constants.WX_HEADER_CODE);
        String encryptData = request.getHeader(Constants.WX_HEADER_ENCRYPTED_DATA);
        String iv = request.getHeader(Constants.WX_HEADER_IV);

        AppInfo appInfo = appInfoService.getAppInfo();
        JSONObject jsonObject = new JSONObject();
        if (appInfo == null) {
            //未配置小程序appid
            jsonObject.put("code", ReturnCodeType.MA_NO_APPID);
            jsonObject.put("returnMessage", "NO_APPID");
            jsonObject.put("data", "");
            return jsonObject.toString();
        } else {
            //已配置小程序appid
            String appid = appInfo.getAppid();
            String secret = appInfo.getSecret();
            String ip = appInfo.getIp();
            String qcloud_appid = appInfo.getQcloudAppid();
            int loginDuration = appInfo.getLoginDuration();
            int sessionDuration = appInfo.getSessionDuration();
            StringBuffer url = new StringBuffer("https://api.weixin.qq.com/sns/jscode2session?");
            url.append("appid=").append(appid);
            url.append("&secret=").append(secret);
            url.append("&js_code=").append(code);
            url.append("&grant_type=").append("authorization_code");
            String result = HttpsRequestUtil.httpGet(url.toString(), HttpMethod.GET, null, null);
            JSONObject json = JSONObject.parseObject(result);
            //通过code换取了session_key openid expires_in等信息
            if ("".equals(result)) {
                //换取失败
                jsonObject.put("returnCode", ReturnCodeType.MA_WEIXIN_NET_ERR);
                jsonObject.put("returnMessage", "WEIXIN_NET_ERR");
                jsonObject.put("returnData", "");
                return jsonObject.toString();
            } else {
                if (!StringUtils.isEmpty(json.get("openid"))
                        && !StringUtils.isEmpty(json.get("session_key"))
                        && !StringUtils.isEmpty(json.get("expires_in"))) {
                    //openid session_key换取成功
                    UUID uuid_u = UUID.randomUUID();
                    String uuid = uuid_u.toString().replace("-", "").toUpperCase();
                    String skey = uuid_u.toString().replace("-", "").toUpperCase(); //生成skey
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String create_time = format.format(Calendar.getInstance().getTime());
                    String last_visit_time = format.format(Calendar.getInstance().getTime());
                    String openid = json.get("openid").toString();
                    String session_key = json.get("session_key").toString();
                    int errCode = 0;
                    String user_info = "false";
                    //解密用户数据
                    DecryptDataUtil decryptDataUtil = new DecryptDataUtil(appid, session_key);
                    user_info = decryptDataUtil.DecryptData(encryptData, iv);
                    if ("".equals(user_info)) {
                        errCode = 1;//意味着解密失败
                    }
                    if (user_info.equals("flase") || errCode != 0) {
                        //解密失败
                        jsonObject.put("returnCode", ReturnCodeType.MA_DECRYPT_ERR);
                        jsonObject.put("returnMessage", "DECRYPT_FAIL");
                        jsonObject.put("returnData", "");
                        return jsonObject.toString();
                    } else {
                        //解密成功
                        JSONObject params = new JSONObject();
                        params.put("uuid", uuid);
                        params.put("skey", skey);
                        params.put("create_time", create_time);
                        params.put("last_visit_time", last_visit_time);
                        params.put("openid", openid);
                        params.put("session_key", session_key);
                        params.put("user_info", user_info);
                        params.put("login_duration", loginDuration);
                        params.put("session_duration", sessionDuration);
                        //更改用户登录状态
                        int  r = sessionInfoService.changeSessionInfo(params);
                        if (r== 1) {
                            //update
                            SessionInfo sessionInfo = sessionInfoService.getSessionInfoByOpenid(openid);
                            JSONObject arrJson = new JSONObject();
                            arrJson.put("id", sessionInfo.getUuid());
                            arrJson.put("skey", sessionInfo.getSkey());
                            arrJson.put("userinfo", sessionInfo.getUserInfo());
                            arrJson.put("duration", json.get("expires_in"));
                            JSONObject resultJson = new JSONObject();
                            resultJson.put("code", ReturnCodeType.MA_OK);
                            resultJson.put("returnMessage", "UPDATE_SESSION_SUCCESS");
                            resultJson.put("data", arrJson);
                            return resultJson.toString();
                        } else if (r == 2) {
                            SessionInfo sessionInfo = sessionInfoService.getSessionInfoByOpenid(openid);
                            JSONObject arrJson = new JSONObject();
                            arrJson.put("id", sessionInfo.getUuid());
                            arrJson.put("skey", sessionInfo.getSkey());
                            arrJson.put("userinfo", sessionInfo.getUserInfo());
                            arrJson.put("duration", json.get("expires_in"));
                            JSONObject resultJson = new JSONObject();
                            resultJson.put("code", ReturnCodeType.MA_OK);
                            resultJson.put("returnMessage", "NEW_SESSION_SUCCESS");
                            resultJson.put("data", arrJson);
                            return resultJson.toString();
                        } else {
                            //fail
                            JSONObject resultJson = new JSONObject();
                            resultJson.put("code", ReturnCodeType.MA_CHANGE_SESSION_ERR);
                            resultJson.put("returnMessage", "CHANGE_SESSION_ERR");
                            resultJson.put("data", "");
                            return resultJson.toString();
                        }

                    }

                } else if (StringUtils.isEmpty(json.get("errcode")) && StringUtils.isEmpty(json.get("errmsg"))) {
                    //换取session-key openid等信息有误
                    jsonObject.put("code", ReturnCodeType.MA_WEIXIN_NET_ERR);
                    jsonObject.put("returnMessage", "WEIXIN_CODE_ERR");
                    jsonObject.put("data", "");
                    return jsonObject.toString();
                } else {
                    jsonObject.put("code", ReturnCodeType.MA_WEIXIN_NET_ERR);
                    jsonObject.put("returnMessage", "WEIXIN_CODE_ERR");
                    jsonObject.put("data", "");
                    return jsonObject.toString();
                }
            }
        }


    }

}
