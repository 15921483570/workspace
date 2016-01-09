
package com.gop.rpc;


import org.springframework.beans.factory.FactoryBean;


public class RpcMethodFactory implements FactoryBean<RpcMethod>
{
    private MethodProxy<RpcMethod> methodProxy;

    public MethodProxy<RpcMethod> getMethodProxy()
    {
        return methodProxy;
    }

    public void setMethodProxy(MethodProxy<RpcMethod> methodProxy)
    {
        this.methodProxy = methodProxy;
    }

    public RpcMethod getObject()
        throws Exception
    {
        return methodProxy.getWallet(RpcMethod.class);
    }

    public Class<?> getObjectType()
    {

        return RpcMethod.class;
    }

    public boolean isSingleton()
    {
        return true;
    }

}
