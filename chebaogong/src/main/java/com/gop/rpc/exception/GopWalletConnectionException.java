
package com.gop.rpc.exception;

public class GopWalletConnectionException extends GopWalletException
{
    
    private static final long serialVersionUID = 4816808941593258975L;

    public GopWalletConnectionException(String message)
    {
        super(message);
    }

    public GopWalletConnectionException(Throwable cause)
    {
        super(cause);
    }

    public GopWalletConnectionException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
