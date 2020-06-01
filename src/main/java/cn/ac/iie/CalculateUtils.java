package cn.ac.iie;

public class CalculateUtils {
    /**
     * 随机返回指定范围内的数字
     *
     * @param min 起始值
     * @param max 结束值
     */
    public static int getRandomInt(int min, int max) {
        return (int) ((Math.random() * Math.abs(min - max)) + Math.min(min, max));
    }
    /**
     * 随机返回指定范围内的数字
     *
     * @param min 起始值
     * @param max 结束值
     */
    public static long getRandomLong(long min,long max){
        long rtn = min + (long)(Math.random() * (max - min));
        if(rtn == min || rtn == max){
            return getRandomLong(min,max);
        }
        return rtn;
    }
}
