package com.lonelySnow.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SendMailUtils {
    // 随机验证码
    public String achieveCode() {
        String[] beforeShuffle = new String[] {"0","1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D",
                "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
                "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z" };
        // 把数组转换为集合
        List list = Arrays.asList(beforeShuffle); // 编译时为List接口当中的 运行时为Arrays内部类当中的
        // static void shuffle(List<?> list) 使用默认随机源对列表进行置换，所有置换发生的可能性都是大致相等的。
        Collections.shuffle(list);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            sb.append(list.get(i));
        }
        String afterShuffle = sb.toString();
        // 随机获取五个值
        String result = afterShuffle.substring(3, 9);
        return result;
    }
}
