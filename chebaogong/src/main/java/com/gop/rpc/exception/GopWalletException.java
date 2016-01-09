
package com.gop.rpc.exception;

public class GopWalletException extends RuntimeException
{

  
    private static final long serialVersionUID = -9084819946780425716L;

    public GopWalletException(String message)
    {
        super(message);
    }

    public GopWalletException(Throwable cause)
    {
        super(cause);
    }

    public GopWalletException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
