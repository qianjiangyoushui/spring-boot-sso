package com.test.web1.utils;




import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * Created by Administrator on 2017/10/16.
 */
public class DecryptDataUtil {
    private String appid;
    private String session_key;
    final Base64.Decoder decoder = Base64.getDecoder();
    final Base64.Encoder encoder = Base64.getEncoder();

    public DecryptDataUtil(String appid, String session_key) {
        this.appid = appid;
        this.session_key = session_key;
    }

    public String  DecryptData(String encryptedData, String iv){
        byte[] aesBytes = decoder.decode(session_key);
        byte[] ivBytes = decoder.decode(iv);
        try{
            IvParameterSpec paramSpec = new IvParameterSpec(ivBytes);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// 创建密码器
            SecretKeySpec key = new SecretKeySpec(aesBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, key,paramSpec);// 初始化
            byte[] resultaaa = decoder.decode(encryptedData);
            return new String(cipher.doFinal(resultaaa)); // 解密
        }catch (Exception e){
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args){
        DecryptDataUtil decryptData = new DecryptDataUtil("wx4f4bc4dec97d474b","tiihtNczf5v6AKRyjwEUhQ==");
        String data = "CiyLU1Aw2KjvrjMdj8YKliAjtP4gsMZM" +
                "QmRzooG2xrDcvSnxIMXFufNstNGTyaGS" +
                "9uT5geRa0W4oTOb1WT7fJlAC+oNPdbB+" +
                "3hVbJSRgv+4lGOETKUQz6OYStslQ142d" +
                "NCuabNPGBzlooOmB231qMM85d2/fV6Ch" +
                "evvXvQP8Hkue1poOFtnEtpyxVLW1zAo6" +
                "/1Xx1COxFvrc2d7UL/lmHInNlxuacJXw" +
                "u0fjpXfz/YqYzBIBzD6WUfTIF9GRHpOn" +
                "/Hz7saL8xz+W//FRAUid1OksQaQx4CMs" +
                "8LOddcQhULW4ucetDf96JcR3g0gfRK4P" +
                "C7E/r7Z6xNrXd2UIeorGj5Ef7b1pJAYB" +
                "6Y5anaHqZ9J6nKEBvB4DnNLIVWSgARns" +
                "/8wR2SiRS7MNACwTyrGvt9ts8p12PKFd" +
                "lqYTopNHR1Vf7XjfhQlVsAJdNiKdYmYV" +
                "oKlaRv85IfVunYzO0IKXsyl7JCUjCpoG" +
                "20f0a04COwfneQAGGwd5oa+T8yO5hzuy" +
                "Db/XcxxmK01EpqOyuxINew==";
        try {
            String s = decryptData.DecryptData(data,"r7BXXKkLb8qrSNn05n0qiA==");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
