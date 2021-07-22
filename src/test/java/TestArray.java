import com.alibaba.fastjson.JSON;
import org.apache.flink.api.java.tuple.Tuple2;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shenss
 * @create 2021-05-24 16:40
 **/
public class TestArray {
    public static void main(String[] args) {
        String line = "{\"alt\":\"24\",\"angle\":\"87\",\"deviceId\":\"00001417653\",\"lastStNum\":1,\"lat\":\"24.615316\",\"lineId\":\"ae38ec9620bbf4801ea44f1d75961f60\",\"lineName\":\"492路\",\"lng\":\"118.044391\",\"mileToLast\":0,\"speed\":10,\"stName\":\"珩圣西路口站\",\"time\":\"2021-05-21 15:04:45\",\"totalMile\":111549,\"upDown\":1}\n";
        Object realTimeData = JSONObject.parse(line);
        System.out.println(realTimeData);

        //字符串转为JsonObject
        JSONObject shop_user = JSON.parseObject(line);
        System.out.println(shop_user.get("alt"));

        // tuple2的用法
        Tuple2 tuple= new Tuple2<>(1,2);
        System.out.println(tuple.f0);
        System.out.println(tuple.f1);

        List<String> list = new ArrayList<String>();
        list.add("a,c,c");
        list.add("b");
        list.add("c");
        System.out.println(list.toString());

    }
}
