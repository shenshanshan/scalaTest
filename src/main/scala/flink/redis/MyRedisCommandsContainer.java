package flink.redis;

import java.io.Serializable;
import java.util.Map;

/**
 * @author shenss
 * @create 2021-05-24 19:14
 **/
public interface MyRedisCommandsContainer extends Serializable {
    Map<String,String> hget(String key);
    String hgetByKey(String key,String field);
    void close();
}
