/*
 * 文件名：Pool.java 版权：Copyright by www.guoren.com 描述： 修改人：汪洋 修改时间：2015年12月15日 跟踪单号： 修改单号： 修改内容：
 */

package com.gop.rpc.util;


import java.io.Closeable;
import java.io.IOException;

import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import com.gop.rpc.exception.GopWalletConnectionException;
import com.gop.rpc.exception.GopWalletException;


public class Pool<T> implements Closeable
{
    protected GenericObjectPool<T> internalPool;

    /**
     * Using this constructor means you have to set and initialize the internalPool yourself.
     */
    public Pool()
    {}

    public boolean isClosed()
    {
        return this.internalPool.isClosed();
    }

    public Pool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
    {
        initPool(poolConfig, factory);
    }

    public void initPool(final GenericObjectPoolConfig poolConfig, PooledObjectFactory<T> factory)
    {

        if (this.internalPool != null)
        {
            try
            {
                closeInternalPool();
            }
            catch (Exception e)
            {}
        }

        this.internalPool = new GenericObjectPool<T>(factory, poolConfig);
    }

    public void returnObject(T obj)
    {
        try
        {
            internalPool.returnObject(obj);
        }
        catch (Exception e)
        {
            throw new GopWalletException("Could not return a resource from the pool", e);
        }
    }

    public void destory(T obj)
    {
        try
        {
            internalPool.invalidateObject(obj);
        }
        catch (Exception e)
        {
            throw new GopWalletException("Invalidated gopconnect not currently part of this pool",
                e);
        }
    }

    public T getResource()
    {
        try
        {
            return internalPool.borrowObject();
        }
        catch (Exception e)
        {
            throw new GopWalletConnectionException("Could not get a resource from the pool", e);
        }
    }

    public void closeInternalPool()
    {
        try
        {
            internalPool.close();

        }
        catch (Exception e)
        {
            throw new GopWalletException("Could not destroy the pool");
        }
    }

    @SuppressWarnings("unused")
    private boolean poolInactive()
    {
        return this.internalPool == null || this.internalPool.isClosed();
    }

    public void close()
        throws IOException
    {
        
        internalPool.close();
    }

}
