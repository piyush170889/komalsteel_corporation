package com.replete.komalapp.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.Random;

/**
 * Created by patil on 12/4/2015.
 */
public class CommonUtilitiesCalculateHash {
    private String action1 ="";
//    private String base_url="https://secure.payu.in";

    /**
     *
     * @return
     */
    public String getTransactionId(){
        String txnid ="";
        Random rand = new Random();
        String rndm = Integer.toString(rand.nextInt())+(System.currentTimeMillis() / 1000L);
        txnid=hashCal("SHA-256",rndm).substring(0,20);
        return txnid;
    }

    /**
     *
     * @param params
     * @return
     */
    public String calculateHash(Map<String,String> params){
        String hash="";
        String hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
        String hashString="";
        String salt="eCwWELxi";

        try{
            if( empty(params.get("key"))
                    || empty(params.get("txnid"))
                    || empty(params.get("amount"))
                    || empty(params.get("firstname"))
                    || empty(params.get("email"))
                    || empty(params.get("phone"))
                    || empty(params.get("productinfo"))
                    || empty(params.get("surl"))
                    || empty(params.get("furl"))
//                    || empty(params.get("service_provider"))
                    )
            {
                throw new Exception("Required Values are empty");
            }else{
                String[] hashVarSeq=hashSequence.split("\\|");

                for(String part : hashVarSeq)
                {
                    hashString= (empty(params.get(part)))?hashString.concat(""):hashString.concat(params.get(part));
                    hashString=hashString.concat("|");
                }
                hashString=hashString.concat(salt);

                hash=hashCal("SHA-512",hashString);
//                this.action1=base_url.concat("/_payment");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return hash;
    }

    /**
     *
     * @param type
     * @param str
     * @return
     */
    private String hashCal(String type,String str){
        byte[] hashseq=str.getBytes();
        StringBuffer hexString = new StringBuffer();
        try{
            MessageDigest algorithm = MessageDigest.getInstance(type);
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (int i=0;i<messageDigest.length;i++) {
                String hex=Integer.toHexString(0xFF & messageDigest[i]);
                if(hex.length()==1) hexString.append("0");
                hexString.append(hex);
            }

        }catch(NoSuchAlgorithmException nsae){ }

        return hexString.toString();


    }

    /**
     *
     * @param s
     * @return
     */
    private boolean empty(String s)
    {
        if(s== null || s.trim().equals(""))
            return true;
        else
            return false;
    }

}
