
package com.gop.rpc;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;


import com.gop.rpc.connection.GopWalletConnectionFactory;
import com.gop.rpc.model.JsonRpcModel;


public class MethodProxy<T> implements InvocationHandler, walletProxy<T>
{
    private GopWalletConnectionFactory connectionFactory;

    public GopWalletConnectionFactory getConnectionFactory()
    {
        return connectionFactory;
    }

    public void setConnectionFactory(GopWalletConnectionFactory connectionFactory)
    {
        this.connectionFactory = connectionFactory;
    }

    @SuppressWarnings({"unchecked", "hiding"})
    public <T> T getWallet(Class<T> interfaces)
    {
        ClassLoader classloader = interfaces.getClassLoader();
        Class<?>[] classs = new Class[] {interfaces};

        return (T)Proxy.newProxyInstance(classloader, classs, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args)
        throws Throwable
    {
        ConnectFuture connectFuture = null;
        try
        {
            Object[] obs = new Object[args.length - 1];
            for (int i = 0; i < obs.length; i++ )
            {
                obs[i] = args[i + 1];
            }
            JsonRpcModel jsonrpc = new JsonRpcModel((String)args[0], method.getName(), obs);
            connectFuture = connectionFactory.getConnection();
            connectFuture.awaitUninterruptibly();
            IoSession ioSession = connectFuture.getSession();
            ioSession.write(jsonrpc.toString());
        }
        catch (Exception e)
        {
            throw new IllegalStateException(e.getMessage());
        }
        finally
        {
            connectionFactory.returnConnetion(connectFuture);
        }
        return null;
    }

}