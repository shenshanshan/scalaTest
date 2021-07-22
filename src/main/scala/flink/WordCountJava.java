package flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.util.Collector;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;

/**
 * @author shenss
 * @create 2021-05-18 15:15
 **/
public class WordCountJava {
    public static void main(String[] args) throws Exception {
//        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        // (ab,2)
        //(ef,1)
//        String path = "E:\\a.txt";
//        //获取数据源
//        DataSet<String> source = env.readTextFile(path);
//        //对数据进行处理
//        DataSet<Tuple2<String,Integer>> result = source.flatMap(new MyFlatMapper())
//                .groupBy(0)
//                .sum(1);
//        //打印输出
//        result.print();

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<String> text = env.fromElements("java java scala", "scala java python");
        DataSet<Tuple2<String, Integer>> counts = text.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String s, Collector<Tuple2<String, Integer>> out) throws Exception {
                String[] value = s.toLowerCase().split(" ");
                for (String word : value) {
                    out.collect(new Tuple2<String, Integer>(word, 1));
                }
            }
        })
                .groupBy(0)
                .sum(1);
        counts.print();
        //(scala,2)
        //(java,3)
        //(python,1)

    }

    //实现FlatMapFunction接口，重写flatMap方法
    public static class MyFlatMapper implements FlatMapFunction<String, Tuple2<String,Integer>> {
        @Override
        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            String[] words = s.split(" ");
            for (String word : words) {
                collector.collect(new Tuple2<>(word,1));
            }
        }
    }
}
