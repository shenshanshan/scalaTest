package flink.redis;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisConfigBase;
import org.apache.flink.util.Preconditions;

/**
 * @author shenss
 * @create 2021-05-24 19:13
 **/
public class RedisSource extends RichSourceFunction<MyRedisRecord> {

        private static final long serialVersionUID = 1L;
        private String additionalKey;
        private String additionField;
        private MyRedisCommand redisCommand;
        private FlinkJedisConfigBase flinkJedisConfigBase;
        private MyRedisCommandsContainer redisCommandsContainer;
        private volatile boolean isRunning = true;

        public RedisSource(FlinkJedisConfigBase flinkJedisConfigBase, MyRedisCommandDescription redisCommandDescription) {
                Preconditions.checkNotNull(flinkJedisConfigBase, "Redis connection pool config should not be null");
                Preconditions.checkNotNull(redisCommandDescription, "MyRedisCommandDescription  can not be null");
                this.flinkJedisConfigBase = flinkJedisConfigBase;
                this.redisCommand = redisCommandDescription.getCommand();
                this.additionalKey = redisCommandDescription.getAdditionalKey();
                this.additionField = redisCommandDescription.getAdditionalKey();
        }


        @Override
        public void open(Configuration parameters) throws Exception {
                this.redisCommandsContainer =
                        MyRedisCommandsContainerBuilder.build(this.flinkJedisConfigBase);
        }

        @Override
        public void run(SourceContext sourceContext) throws Exception {
                while (isRunning){
                        switch(this.redisCommand) {
                        case HGET:
                                sourceContext.collect(new MyRedisRecord(
                                this.redisCommandsContainer.hget(this.additionalKey), this.redisCommand.getRedisDataType()));
                        break;
                        case HGETVAL:
                                sourceContext.collect(new MyRedisRecord(
                                this.redisCommandsContainer.hgetByKey(this.additionalKey, this.additionField),this.redisCommand.getRedisDataType()));
                                break;
                        default:
                                throw new IllegalArgumentException("Cannot process such data type: " + this.redisCommand);
                        }
                }

        }

        @Override
        public void cancel()  {
                isRunning = false;
                if (this.redisCommandsContainer != null) {
                        this.redisCommandsContainer.close();
                }
        }
}
