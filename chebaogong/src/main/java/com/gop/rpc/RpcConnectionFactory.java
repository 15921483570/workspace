
package com.gop.rpc;


import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.mina.core.future.ConnectFuture;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Optional;
import com.gop.rpc.connection.GopWalletConnectionFactory;
import com.gop.rpc.util.Pool;


public class RpcConnectionFactory implements GopWalletConnectionFactory, InitializingBean, DisposableBean
{

    private Pool<ConnectFuture> pool;

    private PooledObjectFactory<ConnectFuture> pooledFactory;

    private GenericObjectPoolConfig poolConfig;

    public Pool<ConnectFuture> getPool()
    {
        return pool;
    }

    public void setPool(Pool<ConnectFuture> pool)
    {
        this.pool = pool;
    }

    public PooledObjectFactory<ConnectFuture> getPooledFactory()
    {
        return pooledFactory;
    }

    public void setPooledFactory(PooledObjectFactory<ConnectFuture> pooledFactory)
    {
        this.pooledFactory = pooledFactory;
    }

    public GenericObjectPoolConfig getPoolConfig()
    {
        return poolConfig;
    }

    public void setPoolConfig(GenericObjectPoolConfig poolConfig)
    {
        this.poolConfig = poolConfig;
    }

    public RpcConnectionFactory()
    {

    };

    private Pool<ConnectFuture> createPool()
    {

        return new Pool<ConnectFuture>(poolConfig, pooledFactory);
    }

    public ConnectFuture getConnection()
    {
        if (pool == null)
        {
            pool = createPool();
        }
        return pool.getResource();
    }

    public void returnResource(ConnectFuture connectFuture)
    {
        pool.returnObject(connectFuture);
    }

    public void destroy()
        throws Exception
    {
       
        this.pool.closeInternalPool();
    }

    public void afterPropertiesSet()
        throws Exception
    {
        poolConfig = Optional.fromNullable(poolConfig).or(new GopWalletDefalutPoolConfig());
        pooledFactory = Optional.fromNullable(pooledFactory).or(
            new GopWalletDefalutObjectFactory());

    }

    public void returnConnetion(ConnectFuture connectFuture)
    {
        returnResource(connectFuture);
    }

}
