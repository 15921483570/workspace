
package com.gop.rpc;

public interface RpcMethod
{

    public void wallet_transfer_to_address_rpc(String id, String amount_to_transfer,
                                               String assetSymbo, String fromAccountName,
                                               String toAddress, String guid);

    public void blockchain_get_transaction_rpc(String id, String transaction_id);
    
    public void blockchain_get_gop_account_balance_record(String id,String block);

}
