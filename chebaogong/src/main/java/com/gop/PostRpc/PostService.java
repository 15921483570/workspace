package com.gop.PostRpc;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.service.IoService;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.alibaba.fastjson.JSONObject;
import com.gop.rpc.RpcConnectionFactory;
import com.gop.rpc.model.JsonRpcModel;

public class PostService extends IoHandlerAdapter {
	

	public PostService() {
		System.out.println("开始创建");
	}

	private static Boolean isOpen;

	private String ip = "localhost";
	private int port = 8089;

	private String userName = "root";
	private String passWord = "root";

	private CountDownLatch countDownLatchLog = new CountDownLatch(1);
	private CountDownLatch countDownLatcMethodExe = new CountDownLatch(1);

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public static boolean isOpen() {
		return isOpen;
	}

	public static void setOpen(boolean isOpen) {
		PostService.isOpen = isOpen;
	}

	private String result;

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String exeMethod(JsonRpcModel jsonRpcModel) throws InterruptedException {
		NioSocketConnector connector = new NioSocketConnector();
		connector.getFilterChain().addLast("CDECODE",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"))));
		connector.setHandler(this);
		ConnectFuture connectFuture = connector.connect(new InetSocketAddress(ip, port));
		connectFuture.await();
		IoSession ioSession = connectFuture.getSession();
		connectFuture.addListener(new IoFutureListener<IoFuture>() {

			@Override
			public void operationComplete(IoFuture future) {
				IoSession ioSession = future.getSession();
				ioSession.write(new JsonRpcModel("login", userName, passWord));
				if (isOpen == null) {
					System.out.println("开始解锁");
				}
				countDownLatchLog.countDown();
			}
		});
		countDownLatchLog.await(10000, TimeUnit.MILLISECONDS);
		ioSession.write(jsonRpcModel);
		countDownLatcMethodExe.await(10000, TimeUnit.MILLISECONDS);
		connectFuture.cancel();
		connector.dispose();
		return result;
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println(message);
		JSONObject jn = JSONObject.parseObject(message.toString());
		if (jn.getString("id").equals("login:-2")) {
			return;
		}
		result = message.toString();
		countDownLatcMethodExe.countDown();
	}

}
