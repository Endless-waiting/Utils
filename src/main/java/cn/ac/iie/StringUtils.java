package cn.ac.iie;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    /**
     * 从String[] 数组中随机取出其中一个String字符串
     *
     * @param s
     * @return
     * @author mingzijian
     */
    public static String getStringArrayByRandom(String s[]) {
        return s[new Random().nextInt(s.length - 1)];
    }

    // 生成指定长度的中文字符串
    public static String getStringsByRandomm(int number) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < number) {
            builder.append(getCharByRandom());
            i++;
        }
        return builder.toString();
    }

    // 随机生成一个中文字符
    public static char getCharByRandom() {
        String str = "";
        int hightPos;
        int lowPos;

        Random random = new Random();

        hightPos = (176 + Math.abs(random.nextInt(39)));
        lowPos = (161 + Math.abs(random.nextInt(93)));

        byte[] b = new byte[2];
        b[0] = (Integer.valueOf(hightPos)).byteValue();
        b[1] = (Integer.valueOf(lowPos)).byteValue();

        try {
            str = new String(b, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("错误");
        }
        return str.charAt(0);
    }

    /**
     * 是否是英文
     * @param c
     * @return
     */
    public static boolean isEnglish(String charaString){
        return charaString.matches("^[a-zA-Z]*");
    }

    /** 判断是否是中文 */
    public static boolean isChinese(String str){
        String regEx = "[\\u4e00-\\u9fa5]+";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        if(m.find())
            return true;
        else
            return false;
    }
}
