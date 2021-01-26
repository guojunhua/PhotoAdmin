package com.tools.common.util;

import org.springframework.stereotype.Component;

/**
 * 1)将长网址用md5算法生成32位签名串，分为4段,，每段8个字符；
 *
 * 2)对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作。超过30位的忽略处理。
 *
 * 3)将每段得到的这30位又分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串；
 *
 * 4)这样一个md5字符串能够获得4个6位串。取里面的随意一个就可作为这个长url的短url地址。
 */
@Component
public class TinyUrlUtil {

    public String[] ShortText(String string){
        String key = "qwe20190909";//自己定义生成MD5加密字符串前的混合KEY
        String[] chars = new String[]{//要使用生成URL的字符
                "a","b","c","d","e","f","g","h",
                "i","j","k","l","m","n","o","p",
                "q","r","s","t","u","v","w","x",
                "y","z","0","1","2","3","4","5",
                "6","7","8","9","A","B","C","D",
                "E","F","G","H","I","J","K","L",
                "M","N","O","P","Q","R","S","T",
                "U","V","W","X","Y","Z"
        };

        String hex = EncriptUtil.md5(key + string);
        int hexLen = hex.length();
        int subHexLen = hexLen / 8;
        String[] ShortStr = new String[4];
        for (int i = 0; i < subHexLen; i++) {
            String outChars = "";
            int j = i + 1;
            String subHex = hex.substring(i * 8, j * 8);
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

            for (int k = 0; k < 6; k++) {
                int index = (int) (Long.valueOf("0000003D", 16) & idx);
                outChars += chars[index];
                idx = idx >> 5;
            }
            ShortStr[i] = outChars;
        }

        return ShortStr;
    }
}
