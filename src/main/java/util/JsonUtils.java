package util;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.type.JavaType;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;

public class JsonUtils {
    //public static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {}

    private static ObjectMapper objectMapper;

    static {
        objectMapper = new JsonObjectMapper();
        objectMapper.enable(SerializationConfig.Feature.INDENT_OUTPUT);
        //objectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyyMMddHHmmss"));
    }

    public static <T> String toJson(T obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            //LOGGER.error("class: {} serialize to JSON error: {}", ToStringBuilder.reflectionToString(obj), e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T fromJson(String json, Class<T> cls) {
        try {
            if (org.apache.commons.lang.StringUtils.isBlank(json)) {
                return null;
            }
            return objectMapper.readValue(json, cls);
        } catch (IOException e) {
            e.printStackTrace();
            //LOGGER.error("deserialize failed! JSON string: {}, class: {}, reason: {}", json, cls, e);
        }
        return null;
    }

    public static <E, T extends Collection<E>> T fromJson(String json, Class<E> cls, Class<T> collectionCls) {
        if (org.apache.commons.lang.StringUtils.isBlank(json)) {
            return null;
        }
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionCls, cls);
        try {
            return objectMapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
            /*LOGGER.error("deserialize failed! JSON string: {}, Object class:{}, Collection class: {}, reason: {}",
                    json, cls, collectionCls, e);*/
        }
        return null;
    }
}
