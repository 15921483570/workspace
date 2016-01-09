
package com.gop.rpc.model;


import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;


public class JsonRpcModel
{

    private String jsonrpc;

    private String id;

    private String method;

    private Object[] params;

    public JsonRpcModel()
    {}

    public JsonRpcModel(String method, Object... params)
    {
        this.jsonrpc = "2.0";
        this.id = "-2";
        this.method = method;
        this.params = params;
    }

    public JsonRpcModel(String jsonrpc, String id, String method, String[] params)
    {
        super();
        this.jsonrpc = jsonrpc;
        this.id = id;
        this.method = method;
        this.params = params;
    }

    public JsonRpcModel(String id, String method, Object[] params)
    {
        super();
        this.id = id;
        this.jsonrpc = "2.0";
        this.method = method;
        this.params = params;
    }

    public String getJsonrpc()
    {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc)
    {
        this.jsonrpc = jsonrpc;
    }

    public String getId()
    {
        return Joiner.on(":").join(method,id);
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMethod()
    {
        return method;
    }

    public void setMethod(String method)
    {
        this.method = method;
    }

    public Object[] getParams()
    {
        return params;
    }

    public void setParams(String[] params)
    {
        this.params = params;
    }

    @Override
    public String toString()
    {
        return JSONObject.toJSONString(this);
    }
}
