package cn.ac.iie;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InOutUtils {
    /** 从文件读取数据 */
    public static List getDataFromFile(Class clazz, String path, String charsetName, String split) throws Exception {
        InputStream inputStream = clazz.getResourceAsStream(path);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charsetName));

        //文件读取
        String line = null;
        //结果集合
        List result = new ArrayList();
        while ((line = bufferedReader.readLine()) != null) {
            line = line.trim();
            //使用空白字符分割
            if (split == null) {
                result.add(line);
            } else {
                String[] strings = line.split(split);
                result.add(strings);
            }
        }
        return result;
    }


    /** 从文件读取数据  */
    public static List getDataFromFile(Class clazz, String path, String split) throws Exception {
        return getDataFromFile(clazz, path, "utf-8", split);
    }

    /** 从CSV文件中读取数据  */
    public static List<String[]> getDataFromCSVFile(String path, String charsetName, String split) {
        ArrayList<String[]> list = new ArrayList<>();
        try {
            File file = new File(path);
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream, charsetName));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                line = line.trim();
                String item[] = line.split(split);//CSV格式文件为逗号分隔符文件，这里根据逗号切分
                list.add(item);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    /** 从CSV文件中读取数据 */
    public static List<String[]> getDataFromCSVFile(String path, String split) {
        return getDataFromCSVFile(path,"utf-8",split);
    }

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
