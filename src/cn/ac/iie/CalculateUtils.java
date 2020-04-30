package cn.ac.iie;

public class CalculateUtils {
    /**
     * 随机返回a和b其中一个数
     *
     * @param a min
     * @param b max
     */
    public static int randomAB(int a, int b) {
        return (int) ((Math.random() * Math.abs(a - b)) + Math.min(a, b));
    }
}
