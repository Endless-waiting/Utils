package cn.ac.iie;

import java.util.Random;

public class StringUtils {
    /**
     * 从String[] 数组中随机取出其中一个String字符串
     *
     * @param s
     * @return
     * @author mingzijian
     */
    public static String randomOne(String s[]) {
        return s[new Random().nextInt(s.length - 1)];
    }

    // 生成指定长度的中文字符串
    public static String randomString(int number) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (i < number) {
            builder.append(getRandomChar());
            i++;
        }
        return builder.toString();
    }

    // 随机生成一个中文字符
    public static char getRandomChar() {
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
}
