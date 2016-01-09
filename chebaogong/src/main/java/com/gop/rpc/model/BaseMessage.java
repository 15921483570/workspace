
package com.gop.rpc.model;

public class BaseMessage
{

    private String id;

    private String result;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getResult()
    {
        return result;
    }

    public void setResult(String result)
    {
        this.result = result;
    }

    @Override
    public String toString()
    {
        return "BaseMessage [id=" + id + ", result=" + result + "]";
    }

}
