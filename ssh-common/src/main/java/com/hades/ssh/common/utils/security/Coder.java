package com.hades.ssh.common.utils.security;

import org.apache.commons.codec.binary.Base64;
import java.security.MessageDigest;

public abstract class Coder {

	public static final String KEY_SHA = "SHA";
	
	/**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptBASE64ToStr(String key) throws Exception {
    	return new String(new Base64().decode(key), "UTF-8");
    }
    
    public static byte[] decryptBASE64(String key) throws Exception {
        return new Base64().decode(key);
    }
    
    /**
     * BASE64加密
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] bytes) throws Exception {
        return new Base64().encodeToString(bytes);
    }
    
    public static String encryptStrToBASE64(String str) throws Exception {
    	return new Base64().encodeToString(str.getBytes("UTF-8"));
    }
    
    public static String encryptMD5(String str) {
        return Md5Utils.hash(str);
    }
    
    /**
     * SHA加密
     *
     * @param data
     * @return
     * @throws Exception
     */
    public static byte[] encryptSHA(byte[] data) throws Exception {

        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);

        return sha.digest();

    }

    /**
     * Turns array of bytes into string
     *
     * @param buf Array of bytes to convert to hex string
     * @return Generated hex string
     */
    public static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10)
                strbuf.append("0");

            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }
    
}
