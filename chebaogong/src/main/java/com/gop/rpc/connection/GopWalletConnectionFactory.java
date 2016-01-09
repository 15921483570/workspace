
package com.gop.rpc.connection;


import org.apache.mina.core.future.ConnectFuture;


public interface GopWalletConnectionFactory
{
    ConnectFuture getConnection();

    public void returnConnetion(ConnectFuture connectFuture);
}
