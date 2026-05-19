package cn.itcast.background_travel.util;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class JedisUtil {
    private static JedisPool jedisPool;

    static {
        InputStream is = JedisPool.class.getClassLoader().getResourceAsStream("jedis.properties");
        Properties pro = new Properties();
        try {
            pro.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(Integer.parseInt(pro.getProperty("maxTotal")));
        config.setMaxIdle(Integer.parseInt(pro.getProperty("maxIdle")));

        String host = pro.getProperty("host");
        int port = Integer.parseInt(pro.getProperty("port"));
        String password = pro.getProperty("password");

        if (password != null && !password.isEmpty()) {
            jedisPool = new JedisPool(config, host, port, 2000, password);
        } else {
            jedisPool = new JedisPool(config, host, port);
        }
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}
