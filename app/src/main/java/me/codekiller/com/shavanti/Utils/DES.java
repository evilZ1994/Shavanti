package me.codekiller.com.shavanti.Utils;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class DES {
    private Cipher enCipher = null;
    private Cipher deCipher = null;

    public DES(String strKey){
        Security.addProvider(new BouncyCastleProvider());
        Key key = getKey(strKey.getBytes());
        try {
            enCipher = Cipher.getInstance("DES");
            enCipher.init(Cipher.ENCRYPT_MODE, key);
            deCipher = Cipher.getInstance("DES");
            deCipher.init(Cipher.DECRYPT_MODE, key);
        } catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }

    }

    public String encrypt(String str) {
        return byteArr2HexStr(encrypt(str.getBytes()));
    }

    public byte[] encrypt(byte[] bytes) {
        try {
            return enCipher.doFinal(bytes);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String decrypt(String str) {
        return new String(decrypt(hexStr2ByteArr(str)));
    }

    public byte[] decrypt(byte[] arrB) {
        try {
            return deCipher.doFinal(arrB);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String byteArr2HexStr(byte[] arrB) {
        int len = arrB.length;
        StringBuffer sBuffer = new StringBuffer(len*2);
        for(int i=0; i<len; i++){
            int tmp = arrB[i];
            while(tmp < 0){
                tmp = tmp + 256;
            }
            if (tmp < 16) {
                sBuffer.append("0");
            }
            sBuffer.append(Integer.toString(tmp, 16));
        }
        return sBuffer.toString();
    }

    private byte[] hexStr2ByteArr(String str) {
        byte[] arrB = str.getBytes();
        int len = arrB.length;
        byte[] arrOut = new byte[len/2];
        for(int i=0; i<len; i=i+2){
            String strTmp = new String(arrB, i, 2);
            arrOut[i/2] = (byte)Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }

    private Key getKey(byte[] bytes) {
        byte[] arrB = new byte[8];
        for (int i = 0; i < bytes.length && i < arrB.length; i++) {
            arrB[i] = bytes[i];
        }
        return new SecretKeySpec(arrB, "DES");
    }

}
