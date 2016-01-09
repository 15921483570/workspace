
package com.gop.rpc;


import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.mina.core.future.ConnectFuture;


public class GopWalletDefalutObjectFactory extends BasePooledObjectFactory<ConnectFuture>
{
  
    private RpcConnection rpcConnection;

    public RpcConnection getRpcConnection()
    {

        return rpcConnection;
    }

    public void setRpcConnection(RpcConnection rpcConnection)
    {
        this.rpcConnection = rpcConnection;
    }

    @Override
    public ConnectFuture create()
        throws Exception
    {
        ConnectFuture connectFuture = rpcConnection.getConnectFuture();
        return connectFuture;
    }

    @Override
    public boolean validateObject(PooledObject<ConnectFuture> p)
    {

        ConnectFuture connectFuture = p.getObject();
        connectFuture.awaitUninterruptibly();
        if (connectFuture.getSession().isClosing())
        {

            return false;
        }
        else
        {

            return true;
        }
    }

    @Override
    public PooledObject<ConnectFuture> wrap(ConnectFuture connectFuture)
    {

        return new DefaultPooledObject<ConnectFuture>(connectFuture);
    }

    @Override
    public void destroyObject(PooledObject<ConnectFuture> p)
        throws Exception
    {
        p.getObject().cancel();
    }

}
