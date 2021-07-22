package com.cnc.cache.vo;

import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shenss
 * @create 2021-06-15 16:07
 **/
public class TestList {
    public static void main(String[] args) {
        User user = new User("s", "1",5);
        User user1 = new User("s", "2",10);
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user1);
        List<User> tmp = userList.stream().filter(e -> e.getName().equals("s")).collect(Collectors.toList());
        Integer maxAge = tmp.stream().mapToInt(t -> t.getAge()).max().getAsInt();
        System.out.println(maxAge);

//        for (int i = 0 ; i < userList.size() ; i++){
//            User tmp = userList.get(i);
//            tmp.setGender("s");
//        }
//
//        for (int i = 0 ; i < userList.size() ; i++){
//            System.out.println(userList.get(i).getGender());
//        }

        System.out.println();

//        String objectStr = "{\"id\":1,\"msg\":1,\"sendTime\":\"2021-06-17\"}";
//        JSONObject jsonObject = (JSONObject) JSONObject.parse(objectStr);
//        Message message1 = JSONObject.toJavaObject(jsonObject, Message.class);
//        System.out.println(message1);

//        int s =  Double.valueOf("2.0").intValue();
//        System.out.println(s);
//
//        System.out.println(Integer.valueOf(2.3d));
    }
}
