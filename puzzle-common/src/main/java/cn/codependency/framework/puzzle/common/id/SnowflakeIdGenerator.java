package cn.codependency.framework.puzzle.common.id;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

@Slf4j
public class SnowflakeIdGenerator implements IdGenerator<Long> {

    private static volatile String HOST_IDENTIFY_CACHE;

    @Override
    public Long generatorId() {
        return nextId();
    }

    // ==============================Fields===========================================
    /**
     * 工作机器ID
     */
    private long workerId;

    /**
     * 数据中心ID
     */
    private long datacenterId;

    /**
     * 开始纪元 (2024-11-25)
     */
    private final long epoch = 1732464000000L;

    /**
     * 毫秒内序列
     */
    private long sequence = 0L;
    /**
     * 机器id所占的位数
     */
    private final long workerIdBits = 10L;
    /**
     * 数据标识id所占的位数
     */
    private final long datacenterIdBits = 0L;
    /**
     * 支持的最大机器id
     */
    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);
    /**
     * 支持的最大数据标识id
     */
    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
    /**
     * 序列在id中占的位数
     */
    private final long sequenceBits = 12L;

    /**
     * 机器ID向左移位数
     */
    private final long workerIdShift = sequenceBits;
    /**
     * 数据标识id向左移位数
     */
    private final long datacenterIdShift = sequenceBits + workerIdBits;
    /**
     * 时间截向左移位数
     */
    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
    /**
     * 生成序列的掩码
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    // ==============================Constructors=====================================

    private static InetAddress getInnerNetworkIp() throws SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                if (inetAddress instanceof Inet4Address) {
                    if (inetAddress.isLoopbackAddress()) {
                        continue;
                    }
                    if (inetAddress.getHostAddress().indexOf(":") != -1) {
                        continue;
                    }
                    return inetAddress;
                }
            }
        }
        return null;
    }

    /**
     * 构造函数
     *
     * @param workerId     工作ID
     * @param datacenterId 数据中心ID
     */
    public SnowflakeIdGenerator(long workerId, long datacenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
        }
        if (datacenterId > maxDatacenterId || datacenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    /**
     * 构造函数
     *
     * @param workerId 工作ID
     */
    public SnowflakeIdGenerator(long workerId) {
        this(workerId, 0L);
    }

    /**
     * 基于hostname和ip地址初始化雪花算法
     */
    public SnowflakeIdGenerator() {
        try {
            this.workerId = getHostWorkId(maxWorkerId);
            this.datacenterId = 0;
        } catch (Exception e) {
            throw new IllegalArgumentException("init workId error", e);
        }
    }

    /**
     * 通过hostname以及ip地址计算workId
     *
     * @param maxWorkerId
     * @return
     * @throws Exception
     */
    private synchronized static long getHostWorkId(long maxWorkerId) throws Exception {
        if (StringUtils.isEmpty(HOST_IDENTIFY_CACHE)) {
            StringBuilder appId = new StringBuilder();
            InetAddress innerNetworkIp = getInnerNetworkIp();
            InetAddress localHost = InetAddress.getLocalHost();
            appId.append(localHost.getHostName()).append("#").append(innerNetworkIp.getAddress()[2] & 255).append(".")
                    .append(innerNetworkIp.getAddress()[3] & 255);
            HOST_IDENTIFY_CACHE = appId.toString();
            log.info(String.format("init snowflake host identify: %s, workId: %s", HOST_IDENTIFY_CACHE,
                    calcWorkIdByHostIdentify(HOST_IDENTIFY_CACHE, maxWorkerId)));
        }
        return calcWorkIdByHostIdentify(HOST_IDENTIFY_CACHE, maxWorkerId);
    }

    /**
     * 通过host标识计算workId
     *
     * @param hostIdentify
     * @param maxWorkerId
     * @return
     */
    private static long calcWorkIdByHostIdentify(String hostIdentify, long maxWorkerId) {
        return (hostIdentify.hashCode() & '\uffff') % (maxWorkerId + 1);
    }

    // ==============================Methods==========================================

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        // 如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(String.format(
                    "Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        // 如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            // 毫秒内序列溢出
            if (sequence == 0) {
                // 阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        // 时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        // 上次生成ID的时间截
        lastTimestamp = timestamp;

        // 移位并通过或运算拼到一起组成ID
        return ((timestamp - epoch) << timestampLeftShift) | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift) | sequence;
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return System.currentTimeMillis();
    }

}