package com.vector.springbootshiro.Utils;

import java.util.Random;

/**
 * @author WangJiaHui
 * @description: 随机盐工具类
 * @ClassName SaltUtils
 * @date 2022/4/20 15:57
 */
public class SaltUtils {
    public static String getSalt(int n){
        char[] chars =
                "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0!@#$%^&*()_+1234567890-=".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            // Random.nextInt() 返回伪随机的，均匀分布int值介于0（含）和指定值（不包括），
            char aChar = chars[new Random().nextInt(chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String salt = getSalt(4);
        System.out.println(salt);
    }
}
