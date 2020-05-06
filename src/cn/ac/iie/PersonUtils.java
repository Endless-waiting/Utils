package cn.ac.iie;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PersonUtils {
    static List<String[]> listOfXian;
    static List<String[]> listOfDi;
    static List<String[]> listOfSheng;
    static Map<String,String> mapOfXian;
    static Map<String,String> mapOfDi;
    static Map<String,String> mapOfSheng;
    static List<String> listOfCode;
    static List<String> firstName;
    static List<String> secondNameFemale;
    static List<String> secondNameMale;

    /** 产生一个人的信息，以数组的形式返回
     * @return String[] 分别是：姓名，性别，年龄，生日，婚姻状况，手机号，省份证号，所在的省，所在的地级市，所在的县
     * */
    public static String[] getPersonInfo(){
        String[] name_sex_age = getName_Sex_Age();
        String name = name_sex_age[0];
        String sex = name_sex_age[1];
        String age = name_sex_age[2];

        if (listOfXian == null || listOfDi == null || listOfSheng == null){
            try {
                listOfXian = InOutUtils.getDataFromFile(PersonUtils.class, "/Data/Area/县级市", "\\s+");
                listOfDi = InOutUtils.getDataFromFile(PersonUtils.class, "/Data/Area/地级市", "\\s+");
                listOfSheng = InOutUtils.getDataFromFile(PersonUtils.class, "/Data/Area/省", "\\s+");
                for (String[] strings : listOfXian) {
                    mapOfXian.put(strings[0],strings[1]);
                }
                for (String[] strings : listOfDi) {
                    mapOfDi.put(strings[0],strings[1]);
                }
                for (String[] strings : listOfSheng) {
                    mapOfSheng.put(strings[0],strings[1]);
                }
                listOfCode = new ArrayList<String>(mapOfXian.keySet());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // ID 省份证号码
        StringBuilder id = new StringBuilder();
        // 随机生成县级市的代码和名称
        String codeOfXian = listOfCode.get(CalculateUtils.randomAB(0, listOfCode.size()));
        String nameOfXian = mapOfXian.get(codeOfXian);
        // 获得省分的编码和名称
        String codeOfSheng = codeOfXian.substring(0, 2);
        String nameOfSheng = mapOfSheng.get(codeOfSheng);
        // 获得地级市的编码和名称
        String codeOfDi = codeOfXian.substring(0, 4);
        String nameOfDi = mapOfDi.get(codeOfDi);
        // 生日
        String birthday = DateUtils.randomDate(Integer.valueOf(age)-1,Integer.valueOf(age));
        // 序号位
        String no = String.valueOf(CalculateUtils.randomAB(10, 99));

        id.append(codeOfXian).append(birthday).append(no).append(sex);

        // 校验身份证号，生成最后一位
        String checkId = checkId(id.toString());
        id.append(checkId);

        // 婚姻状况
        String married;
        if (Integer.valueOf(age)<=18)
            married = "0";
        else {
            double random = Math.random();
            if (random>0.2)
                married = "1";
            else
                married = "0";
        }

        // 手机号
        String tel = getTel();

        String[] info = new String[]{name,sex,age,birthday,married,tel,id.toString(),nameOfSheng,nameOfDi,nameOfXian};
        return info;
    }

    /** 随即生成指定人数的信息
     * @param number 指定的人数
     * @return List<String[]>  String[]中分别是：姓名，性别，年龄，生日，婚姻状况，手机号，省份证号，所在的省，所在的地级市，所在的县
     * */
    public static List<String[]> listPersonInfo(int number){
        List<String[]> list = new ArrayList<>();
        int i = 0;
        while (i<number){
            list.add(getPersonInfo());
            i++;
        }
        return list;
    }

    /** 随机产生一个人的信息，包括姓名，性别，年龄
     * */
    public static String[] getName_Sex_Age() {
        String name = null;
        boolean flag = false;
        String[] strings = new String[]{null,"0",String.valueOf((int)(Math.random()*100))};

        double random = Math.random();
        if (random<=0.5){
            flag = true;
            strings[1]="1";
        }

        try {
            name = firstName() + secondName(flag);
            strings[0] = name;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strings;
    }

    /** 随即生成指定人数的信息，包括姓名，性别，年龄
     * @param number 指定的人数
     * @return List<String[]>  String[]中分别是：姓名，性别，年龄
     * */
    public static List<String[]> listName_Sex_Age(int number){
        List<String[]> list = new ArrayList<>();
        int i = 0;
        while (i<number){
            list.add(getName_Sex_Age());
            i++;
        }
        return list;
    }

    /** 随即生成手机号
     * */
    public static String getTel() {
        String[] telFirst="134,135,136,137,138,139,150,151,152,157,158,159,130,131,132,155,156,133,153".split(",");
        int index=getNum(0,telFirst.length-1);
        String first=telFirst[index];
        String second=String.valueOf(getNum(1,888)+10000).substring(1);
        String third=String.valueOf(getNum(1,9100)+10000).substring(1);
        return first+second+third;
    }
    private static int getNum(int start,int end) {
        return (int)(Math.random()*(end-start+1)+start);
    }

    /** 随机生成姓氏
     * */
    private static String firstName(){
        if (firstName == null){
            try {
                firstName = InOutUtils.getDataFromFile(PersonUtils.class, "/Data/Name/百家姓","\\s+");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return firstName.get(CalculateUtils.randomAB(0,firstName.size()));
    }

    /** 随机生成名字
     * @param sex 性别，true为男性，false为女性 */
    private static String secondName(boolean sex){
        if(sex){
            if (secondNameMale == null){
                try {
                    secondNameMale = InOutUtils.getDataFromFile(PersonUtils.class, "/Data/Name/男性名","\\s+");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return secondNameMale.get(CalculateUtils.randomAB(0,secondNameMale.size()));
        }else{
            if (secondNameFemale == null){
                try {
                    secondNameFemale = InOutUtils.getDataFromFile(PersonUtils.class, "/Data/Name/女性名","\\s+");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return secondNameFemale.get(CalculateUtils.randomAB(0,secondNameFemale.size()));
        }
    }

    /** 校验身份证号
     * @param id 省份证号的前十七位数。
     * @return check 通过对前十七位数的加权运算生成最后的校验位。X代表10
     * */
    private static String checkId(String id){
        char[] chars = id.toCharArray();
        int sum = Integer.valueOf(chars[0])*7+
                Integer.valueOf(chars[1])*9+
                Integer.valueOf(chars[2])*10+
                Integer.valueOf(chars[3])*5+
                Integer.valueOf(chars[4])*8+
                Integer.valueOf(chars[5])*4+
                Integer.valueOf(chars[6])*2+
                Integer.valueOf(chars[7])*1+
                Integer.valueOf(chars[8])*6+
                Integer.valueOf(chars[9])*3+
                Integer.valueOf(chars[10])*7+
                Integer.valueOf(chars[11])*9+
                Integer.valueOf(chars[12])*10+
                Integer.valueOf(chars[13])*5+
                Integer.valueOf(chars[14])*8+
                Integer.valueOf(chars[15])*4+
                Integer.valueOf(chars[16])*2;
        int check = sum % 11;
        if (check == 10)
            return "X";
        return String.valueOf(check);
    }
}
