package util;

import java.util.Random;
import java.util.UUID;

public class RandomPrimarykey {
    public static String getPrimarykey() {
        Long simple=System.currentTimeMillis();
        //三位随机数
        int random=new Random().nextInt(900)+100;//为变量赋随机值100-999;
        return simple.toString()+random;
    }

    public static void main(String[] args) {
        String orderingID= getPrimarykey();
        System.out.println(orderingID);
    }
}
