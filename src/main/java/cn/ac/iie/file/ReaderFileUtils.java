package cn.ac.iie.file;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.read.builder.ExcelReaderBuilder;
import com.sun.org.apache.xerces.internal.xs.datatypes.ObjectList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReaderFileUtils {
    /** 从文件读取数据 */
    public static List getDataFromFile(String path, String charsetName, String split) throws Exception {
        String filePath = System.getProperty("user.dir") + path;
        FileInputStream inputStream = new FileInputStream(new File(filePath));
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
    public static List getDataFromFile(String path, String split) throws Exception {
        return getDataFromFile(path, "UTF-8", split);
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

    public static List<Map<String,Object>> getDataFromExcelFile(String path,Object sheet){
        NoModelDataLister lister = new NoModelDataLister();
        ExcelReaderBuilder excelReaderBuilder = EasyExcel.read(path, lister);
        if (sheet instanceof Integer){
            excelReaderBuilder.sheet((Integer) sheet).doRead();
        } else if (sheet instanceof String){
            excelReaderBuilder.sheet((String) sheet).doRead();
        } else {
            excelReaderBuilder.sheet().doRead();
        }
        return lister.list;
    }
}
class NoModelDataLister extends AnalysisEventListener<Map<String,Object>> {
    List<Map<String, Object>> list = new ArrayList<>();

    @Override
    public void invoke(Map<String, Object> stringObjectMap, AnalysisContext analysisContext){
        list.add(stringObjectMap);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        System.out.println("共添加"+list.size()+"条数据");
    }
}
