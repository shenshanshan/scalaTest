package com.cnc.cache;

import java.util.HashMap;
import java.util.List;

/**
 * @author shenss
 * @create 2021-06-11 14:51
 **/
public class MapTest {

    private static HashMap<String,List<String>> mapSave = new HashMap<>();
    private static HashMap<String,Integer> map = new HashMap<>();

    public static void main(String args[]) {
//        mapSave.clear();
//
//        List<String> list = new ArrayList<>(); //不要将这句写在外面，不然会重复写入数据。
//        list.add("aaa");
//        list.add("bbb");
//        list.add("ccc");
//        list.add(3+"");
//        list.add(4+"");
//        list.add(5+"");
//
//        String key = Integer.toString(mapSave.size()); //当需要存入多组数据，且组数不知道的时候，可以直接用mapSave.size()替代。
//        mapSave.putIfAbsent(key,list); //将数据缓存进去，putIfAbsent是会对key进行排序。无排序可以使用mapSave.put(key,list);
//        if(mapSave!=null && mapSave.size()>0) {
//            Iterator<Map.Entry<String, List<String>>> it = mapSave.entrySet().iterator(); //利用迭代器循环输出
//            List<String> field = new ArrayList<>();
//            List<String> value = new ArrayList<>();
//            while (it.hasNext()) {
//                Map.Entry<String,List<String>> entry=it.next();
//                System.out.println("key="+entry.getKey()+","+"value="+entry.getValue());
//                field.add(entry.getKey().toString());
//                value.add(entry.getValue().toString());
//            }
//            System.out.println("keyList="+field+","+"valueList="+value); //输出为List类型
//        }
//
//        int a;
//        map.clear();   //清空数据
//        if(map.isEmpty()){ //判断map是否有数据
//            a = 888;
//            map.put("first",a); //写入数据
//            map.get("first");   //得到数据
//        }
//        System.out.println(map.get("first"));

        int i=0;
        for(int j= 0; j<2 ; j++ ){
            i += j;
        }
        System.out.println(i);
    }
}
