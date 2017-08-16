/**
 *
 */
package test.mybatis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.cache.Cache;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @author xiaoqingli 自定义mybatis缓存，访问Redis
 */
public class MyBatisRedisCache implements Cache {

    public static final int EXPIRE = 3600 * 12;

    private static Log log = LogFactory.getLog(MyBatisRedisCache.class);

    private static int DATABASE = 1;

    private JedisPool masterPool;
    private JedisPool slavePool;

    private static String password;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private String id;

    private static Map<String, MyBatisRedisCache> instances = new HashMap<String, MyBatisRedisCache>();

    public static MyBatisRedisCache getInstance(String id) {
        return instances.get(id);
    }

    private Jedis redisClient = null;
    private static String masterIp = "";
    private static int masterPort = 0;

    public MyBatisRedisCache(String id) {
        log.info("initialize redis cache..." + id);
        this.id = id;

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        InputStream infile = cl.getResourceAsStream("redis.properties");
        Properties props = new Properties();
         masterIp = "";
         masterPort = 0;
        String slaveIp = "";
        int slavePort = 0;
        try {
            props.load(infile);
            masterIp = props.getProperty("redis-server-master-ip");
            masterPort = Integer.parseInt(props
                    .getProperty("redis-server-master-port"));
            slaveIp = props.getProperty("redis-server-slave-ip");
            slavePort = Integer.parseInt(props
                    .getProperty("redis-server-slave-port"));
            password = props.getProperty("password");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JedisPoolConfig masterConfig = new JedisPoolConfig();
        masterPool = new JedisPool(masterConfig, masterIp, masterPort);

        JedisPoolConfig slaveConfig = new JedisPoolConfig();
        slavePool = new JedisPool(slaveConfig, slaveIp, slavePort);

        instances.put(id, this);
        redisClient = createReids();
    }

    protected static Jedis createReids() {
       Jedis jedis = new Jedis(masterIp,masterPort);
       jedis.auth(password);
        jedis.select(DATABASE);
        jedis.flushDB();
        return jedis;
    }

    //@Override
    public void clear() {
        redisClient.flushDB();
    }


    //	@Override
    public String getId() {
        return this.id;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.cache.Cache#getObject(java.lang.Object)
     */
//	@Override
    public Object getObject(Object key) {
        Object value = SerializeUtil.unserialize(redisClient.get(SerializeUtil.serialize(key.toString())));
        return value;

    }


    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.cache.Cache#getReadWriteLock()
     */
//	@Override
    public ReadWriteLock getReadWriteLock() {
        return this.readWriteLock;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.cache.Cache#getSize()
     */
//	@Override
    public int getSize() {
        return Integer.valueOf(redisClient.dbSize().toString());
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.cache.Cache#putObject(java.lang.Object,
     * java.lang.Object)
     */
//	@Override
    public void putObject(Object key, Object value) {
        redisClient.set(SerializeUtil.serialize(key.toString()), SerializeUtil.serialize(value));
    }

    /*
     * (non-Javadoc)
     *
     * @see org.apache.ibatis.cache.Cache#removeObject(java.lang.Object)
     */
//	@Override
    public Object removeObject(Object key) {
        return redisClient.expire(SerializeUtil.serialize(key.toString()), 0);
    }

}
