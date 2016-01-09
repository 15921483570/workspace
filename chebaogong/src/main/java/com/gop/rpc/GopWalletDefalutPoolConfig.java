
package com.gop.rpc;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


public class GopWalletDefalutPoolConfig extends GenericObjectPoolConfig
{
    public GopWalletDefalutPoolConfig()
    {
        // defaults to make your life with connection pool easier :)
        setTestWhileIdle(true);
        setTestOnBorrow(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(2);
        // /setMaxIdle(3);
        setMaxTotal(3);
        setMaxWaitMillis(10000);
        setMinIdle(1);

    }

}
