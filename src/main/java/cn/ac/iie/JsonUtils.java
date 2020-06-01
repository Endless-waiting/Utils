package cn.ac.iie;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {
    /** 解析原始数据data，返回数组
     * @param key 解析的jsonarray的key值
     * @param data 原始数据，json格式的字符串
     * @return 将数据封装到List<String[]>中 */
    public static List<String[]> analyJSON(String key, String data){
        // 将json数据解析成jsonArray
        JSONObject dataObject = JSONObject.parseObject(data);
        JSONArray jsonArray = dataObject.getJSONArray(key);
        return analyJSON(key,jsonArray);
    }

    /** 解析jsonArray，返回数组
     * @param key 解析的jsonarray的key值
     * @param jsonArray fastjson解析原始数据后通过key获取的json格式的数组josnArray
     * @return 将数据封装到List<String[]>中 */
    public static List<String[]> analyJSON(String key,JSONArray jsonArray){
        if (jsonArray==null || "null".equals(jsonArray)){
            return null;
        }

        // 计数器
        int i = 0;

        // 获得字段值，并将字段值存储在第一行
        List<String[]> list = new ArrayList<>();
        JSONObject firstArray = jsonArray.getJSONObject(0);
        String[] header = new String[firstArray.size()];
        Iterator<String> headerIterator = firstArray.keySet().iterator();
        i = 0;
        while (headerIterator.hasNext()){
            header[i] = headerIterator.next();
            i++;
        }
        list.add(header);

        // 根据获得的表头，解析数据
        try {
            Iterator<Object> arrayIterator = jsonArray.iterator();
            while (arrayIterator.hasNext()){
                JSONObject object = JSONObject.parseObject(arrayIterator.next().toString());
                String[] str = new String[object.size()];
                i = 0;
                for (String colValue : header) {
                    str[i] = object.getString(colValue);
                    i++;
                }
                list.add(str);
            }
        } catch (Exception e){
            e.getStackTrace();
        }

        return list;
    }
}
