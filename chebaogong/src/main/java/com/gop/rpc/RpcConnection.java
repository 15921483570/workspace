
package com.gop.rpc;


import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.apache.mina.core.filterchain.IoFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.common.base.Optional;
import com.gop.rpc.model.JsonRpcModel;

import static com.google.common.base.Preconditions.checkState;


public class RpcConnection implements InitializingBean, DisposableBean
{
    private static final Logger log = Logger.getLogger(RpcConnection.class);

    private static final String LOGIN_METHOD = "login";

    private static final String WALLET_OPEN = "wallet_open";

    private static final String WALLET_UNLOCK = "wallet_unlock";

    private static final String CDECODE = "cdecode";

    private static final String SGINLE_THREAD = "sginel_thread";

    private NioSocketConnector nioSocketConnector;

    private IoHandler handler;

    private Map<String, IoFilter> filters;

    private String ip;

    private Integer port;

    private String user;

    private String password;

    private String walletName = "ocean1";

    private String walletPassword = "wy6591731";

    public String getWalletName()
    {
        return walletName;
    }

    public void setWalletName(String walletName)
    {
        this.walletName = walletName;
    }

    public String getWalletPassword()
    {
        return walletPassword;
    }

    public void setWalletPassword(String walletPassword)
    {
        this.walletPassword = walletPassword;
    }

    public String getIp()
    {
        return ip;
    }

    public void setIp(String ip)
    {
        this.ip = ip;
    }

    public Integer getPort()
    {
        return port;
    }

    public void setPort(Integer port)
    {
        this.port = port;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Map<String, IoFilter> getFilters()
    {
        return filters;
    }

    public void setFilters(Map<String, IoFilter> filters)
    {
        this.filters = filters;
    }

    public RpcConnection()
    {

    }

    public RpcConnection(String ip, int port, String user, String password)
    {
        super();
        this.ip = ip;
        this.port = port;
        this.user = user;
        this.password = password;

    }

    public IoHandler getHandler()
    {
        return handler;
    }

    public void setHandler(IoHandler handler)
    {
        this.handler = handler;

    }

    public void init()
        throws InterruptedException
    {

        if (nioSocketConnector.isDisposed())
        {
            nioSocketConnector = new NioSocketConnector();
        }

        nioSocketConnector.getFilterChain().addLast(CDECODE,
            new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
        nioSocketConnector.getFilterChain().addLast(SGINLE_THREAD,
            new ExecutorFilter(Executors.newSingleThreadExecutor()));
        Optional<IoHandler> handlerOpt = Optional.fromNullable(handler);
        checkState(handlerOpt.isPresent(), "没有设置Iohandler");
        nioSocketConnector.setHandler(handler);
        ConnectFuture connectFuture = nioSocketConnector.connect(new InetSocketAddress(ip, port));
        connectFuture.awaitUninterruptibly(1000);
        IoSession ioSession = connectFuture.getSession();
        ioSession.write(new JsonRpcModel(LOGIN_METHOD, user, password));
        ioSession.write(new JsonRpcModel(WALLET_OPEN, walletName));
        ioSession.write(new JsonRpcModel(WALLET_UNLOCK, 99999999, walletPassword));
        connectFuture.await();
        connectFuture.cancel();
        // nioSocketConnector.dispose(true);
    }

    public ConnectFuture getConnectFuture()
        throws InterruptedException
    {
        if (nioSocketConnector.isDisposed())
        {
            init();
        }
        ConnectFuture connectFuture = nioSocketConnector.connect(new InetSocketAddress(ip, port));
        connectFuture.awaitUninterruptibly(1000);
        log.info("开始登陆");
        IoSession ioSession = connectFuture.getSession();
        ioSession.write(new JsonRpcModel(LOGIN_METHOD, user, password));
        return connectFuture;
    }

    public NioSocketConnector getNioSocketConnector()
    {
        return nioSocketConnector;
    }

    public void setNioSocketConnector(NioSocketConnector nioSocketConnector)
    {
        this.nioSocketConnector = nioSocketConnector;
    }

    public void destroy()
        throws Exception
    {
      
        if (!nioSocketConnector.isDisposed())
        {
            nioSocketConnector.dispose();
        }

    }

    public void afterPropertiesSet()
        throws Exception
    {
        ip = Optional.fromNullable(ip).or(Protocol.DEFAULT_HOST);
        port = Optional.fromNullable(port).or(Protocol.DEFAULT_PORT);
        user = Optional.fromNullable(user).or(Protocol.DEFAULT_USER);
        password = Optional.fromNullable(password).or(Protocol.DEFAULT_PASSWOR);
        nioSocketConnector = Optional.fromNullable(nioSocketConnector).or(
            new NioSocketConnector());
        init();
    }

}
