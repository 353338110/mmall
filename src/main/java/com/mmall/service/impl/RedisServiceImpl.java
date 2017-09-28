package com.mmall.service.impl;

import com.google.common.base.Supplier;
import com.mmall.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import util.JsonUtils;

import java.util.Collection;
import java.util.concurrent.TimeUnit;
@Component("iRedisService")
public class RedisServiceImpl implements IRedisService{


    private StringRedisTemplate redisTemplate;
    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> void put(String key, T obj) {
        redisTemplate.opsForValue().set(key, JsonUtils.toJson(obj));
    }

    public <T> void put(String key, T obj, int timeout) {
        put(key, obj, timeout, TimeUnit.MINUTES);
    }

    public <T> void put(String key, T obj, int timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, JsonUtils.toJson(obj), timeout, unit);
    }

    public <T> T get(String key, Class<T> cls) {
        return JsonUtils.fromJson(redisTemplate.opsForValue().get(key), cls);
    }

    public <E, T extends Collection<E>> T get(String key, Class<E> cls, Class<T> collectionClass) {
        return JsonUtils.fromJson(redisTemplate.opsForValue().get(key), cls, collectionClass);
    }

    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t);
            }
        }
        return t;
    }

    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t, timeout);
            }
        }
        return t;
    }

    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout, TimeUnit unit) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t, timeout, unit);
            }
        }
        return t;
    }

    public <T> T putIfAbsent(String key, Class<T> cls, Supplier<T> supplier, int timeout, TimeUnit unit, boolean refresh) {
        T t = get(key, cls);
        if (t == null) {
            t = supplier.get();
            if (t != null) {
                put(key, t, timeout, unit);
            }
        } else {
            if (refresh) {
                expire(key, timeout, unit);
            }
        }
        return t;
    }

    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier) {
        T t = get(key, cls, collectionCls);
        if (t == null || t.isEmpty()) {
            t = supplier.get();
            if (t != null && t.size() > 0) {
                put(key, t);
            }
        }
        return t;
    }

    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier, int timeout) {
        return putIfAbsent(key, cls, collectionCls, supplier, timeout, TimeUnit.SECONDS);
    }

    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier, int timeout, TimeUnit unit) {
        return putIfAbsent(key, cls, collectionCls, supplier, timeout, unit, false);
    }

    public <E, T extends Collection<E>> T putIfAbsent(String key, Class<E> cls, Class<T> collectionCls, Supplier<T> supplier, int timeout, TimeUnit unit, boolean refresh) {
        T t = get(key, cls, collectionCls);
        if (t == null || t.isEmpty()) {
            t = supplier.get();
            if (t != null && t.size() > 0) {
                put(key, t, timeout, unit);
            }
        } else {
            if (refresh) {
                expire(key, timeout, unit);
            }
        }
        return t;
    }


    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    public boolean expire(String key, long timeout) {
        return expire(key, timeout, TimeUnit.MINUTES);
    }

    public void put(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void put(String key, String value, int timeout) {
        put(key, value, timeout, TimeUnit.MINUTES);
    }

    public void put(String key, String value, int timeout, TimeUnit unit) {
        redisTemplate.opsForValue().set(key, value, timeout, unit);
    }


    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
