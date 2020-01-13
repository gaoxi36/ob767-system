package cn.ob767.systemservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

@Slf4j
public class ObjectRedisSerializer implements RedisSerializer<Object> {

    /**
     * 定义序列化和反序列化转化类
     */
    private Converter<Object, byte[]> serializer = new SerializingConverter();
    private Converter<byte[], Object> deserializer = new DeserializingConverter();

    /**
     * 定义转换空字节数组
     */
    private static final byte[] EMPTY_ARRAY = new byte[0];

    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        byte[] byteArray;
        if (null == obj) {
            log.error("---------------------------------->Redis待序列化的对象为空.");
            byteArray = EMPTY_ARRAY;
        } else {
            try {
                byteArray = serializer.convert(obj);
            } catch (Exception e) {
                log.error("---------------------------------->Redis序列化对象失败,异常:" + e.getMessage());
                byteArray = EMPTY_ARRAY;
            }
        }
        return byteArray;
    }

    @Override
    public Object deserialize(byte[] data) throws SerializationException {
        Object obj = null;
        if ((null == data) || (data.length == 0)) {
            log.info("---------------------------------->Redis待反序列化的对象为空.");
        } else {
            try {
                obj = deserializer.convert(data);
            } catch (Exception e) {
                log.error("---------------------------------->Redis反序列化对象失败,异常:" + e.getMessage());
            }
        }
        return obj;
    }
}