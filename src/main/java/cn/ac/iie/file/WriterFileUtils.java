package cn.ac.iie.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class WriterFileUtils {
    /** 输出数据到CSV文件 */
    public static void setDataToCSVFile(String path,String charsetName, boolean isOverWrite, List<String> list) {
        try {
            BufferedWriter bufferedWriter = transfer(path,charsetName);
            if (isOverWrite) {
                for (String s : list) {
                    bufferedWriter.write(s);
                    bufferedWriter.write("\r\n");
                }
            } else {
                for (String s : list) {
                    bufferedWriter.append(s);
                    bufferedWriter.append("\r\n");
                }
            }

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 输出数据到文件 CSV  */
    public static void setDataToCSVFile(String path,String charsetName, boolean isOverWrite, List<String[]> list, String split) {
        ArrayList<String> arrayList = new ArrayList<>();
        for (String[] strings : list) {
            StringBuilder sb = new StringBuilder();
            for (String string : strings) {
                sb.append(string).append(split);
            }
            sb.deleteCharAt(sb.lastIndexOf(split));
            arrayList.add(sb.toString());
        }
        setDataToCSVFile(path,charsetName, isOverWrite, arrayList);
    }

    private static BufferedWriter transfer(String path,String charsetName) {
        BufferedWriter bufferedWriter = null;
        try {
            File file = new File(path);
            if (!file.exists() && !file.isDirectory()) {
                file.createNewFile();
            }
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), charsetName));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bufferedWriter;
    }
}
