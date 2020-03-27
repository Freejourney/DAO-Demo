package cn.jedisdemo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by ua28 on 3/27/20.
 */
public class TestJedis {

    /**
     *  直接连接
     */
    public static void directConnect() {
        // 设置IP地址和端口
        Jedis jedis = new Jedis("47.100.89.5", 6379);

        // 保存数据
        jedis.set("duixiang", "对象");
        // 获取数据
        System.out.println(jedis.get("duixiang"));
        // 释放资源
        jedis.close();
    }

    /**
     * 连接池方式连接
     */
    public static void poolConnect() {
        // 获得连接池配置对象
        JedisPoolConfig config = new JedisPoolConfig();
        // 设置最大连接数
        config.setMaxTotal(30);
        // 设置最大闲置连接数
        config.setMaxIdle(10);

        // 获得连接池
        JedisPool jedisPool = new JedisPool(config, "47.100.89.5", 6379);
        // 获得连接对象
        Jedis jedis = null;
        try {
            // 通过连接池获得连接
            jedis = jedisPool.getResource();
            // 设置数据
            jedis.set("中文key", "中English");
            // 获取数据
            String value1 = jedis.get("duixiang");
            String value2 = jedis.get("中文key");
            System.out.println(value1 + "\n" + value2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 释放资源
            if (jedis != null)
                jedis.close();
            if (jedisPool != null)
                jedisPool.close();
        }
    }

    public static void main(String[] args) {
        directConnect();
        poolConnect();
    }

}
