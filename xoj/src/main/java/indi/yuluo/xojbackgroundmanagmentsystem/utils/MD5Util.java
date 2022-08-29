package indi.yuluo.xojbackgroundmanagmentsystem.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    /**
     * 1.MD5（message-digest algorithm 5）信息摘要算法，
     * 它的长度一般是32位的16进制数字符串（如81dc9bdb52d04dc20036dbd8313ed055）
     * 2.由于系统密码明文存储容易被黑客盗取
     * 3.应用：注册时，将密码进行md5加密，存到数据库中，防止可以看到数据库数据的人恶意篡改。
     * 登录时,将密码进行md5加密,与存储在数据库中加密过的密码进行比对
     * 4.md5不可逆，即没有对应的算法，从产生的md5值逆向得到原始数据。
     * 但是可以使用暴力破解，这里的破解并非把摘要还原成原始数据，如暴力枚举法。
     */
    public final static String getMD5(String str) {
        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        //16是表示转换为16进制数
        String md5Str = new BigInteger(1, digest).toString(16);
        return md5Str;
    }
}
