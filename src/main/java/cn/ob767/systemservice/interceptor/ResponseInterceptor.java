package cn.ob767.systemservice.interceptor;

import cn.ob767.systemservice.utils.enumerations.ResponseStatus;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

@Data
@JSONType(serialzeFeatures = {SerializerFeature.NotWriteDefaultValue, SerializerFeature.WriteMapNullValue})
public class ResponseInterceptor<T> {

    @JSONField(ordinal = 1)
    private int code;
    @JSONField(ordinal = 2)
    private String ret;
    @JSONField(ordinal = 3)
    private String message;
    @JSONField(ordinal = 4)
    private T data;

    public ResponseInterceptor(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.ret = responseStatus.getRet();
        this.message = responseStatus.getMessage();
    }

    @Override
    public String toString(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",this.code);
        jsonObject.put("ret",this.ret);
        jsonObject.put("message",this.message);
        jsonObject.put("data",this.data);
        return jsonObject.toJSONString();
    }
}
