
package com.gop.rpc;

public interface walletProxy<T>
{
    @SuppressWarnings("hiding")
    public <T> T getWallet(Class<T> interfaces);
}
