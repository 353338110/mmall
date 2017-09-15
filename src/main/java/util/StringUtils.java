package util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static final String imageRegex = "<img>";
    public static String getPrimarykey() {
        Long simple=System.currentTimeMillis();
        //三位随机数
        int random=new Random().nextInt(900)+100;//为变量赋随机值100-999;
        return simple.toString()+random;
    }

    public static String mixedReplace(String str,List<String> fileUrls){
        int count = 0;
        int matcherLength = 0;
        Pattern pattern = Pattern.compile(imageRegex);
        Matcher matcher = pattern.matcher(str);
        while (matcher.find()){
            matcherLength++;
        }
        if (matcherLength!=fileUrls.size()){
            return null;
        }
        String s = matcher.replaceAll("<img>"+fileUrls.get(count++)+"</img>"); //替换后的字符串
        return s;
    }
    public static void main(String[] args) {
        List<String> fileUrls = new ArrayList<>();
        fileUrls.add("http://localhost:8080/image/aaaaaaaaaaaaaaaaaaa.jpg");
        fileUrls.add("http://localhost:8080/image/bbbbbbbbbbbbbbbbbbb.jpg");
        String orderingID= mixedReplace("sjflajsl<img>sdfs<img>" +
                "sjfksjkf",fileUrls);
        System.out.println(orderingID);
    }
}
