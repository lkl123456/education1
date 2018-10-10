package com.adks.commons.cryptlib;


import org.apache.commons.codec.binary.Base64;

public class CryptLib {
    private static int RC4_SKIP_LENGTH = 1020;
    private static char DEFAULT_RC4_KEY[] = {0x52, 0xf1, 0x12, 0xc0, 0x15, 0x9e, 0x88, 0xa0, 0x0c, 0xfb, 0x35, 0x76, 0xe2, 0x4c, 0xd1, 0xcd};
    public static String encrypt(String input)
    {
        RC4 rc4 = new RC4(DEFAULT_RC4_KEY);
        byte[] result = rc4.encrypt(input.getBytes(), RC4_SKIP_LENGTH);
        return Base64.encodeBase64String(result);
        //return Base64.getEncoder().encodeToString(result);
    }

    public static String decrypt(String input)
    {
        //byte[] result = Base64.getDecoder().decode(input.getBytes());
        byte[] result  =Base64.decodeBase64(input.getBytes());
        RC4 rc4 = new RC4(DEFAULT_RC4_KEY);
        return new String(rc4.decrypt(result, RC4_SKIP_LENGTH));
    }

    public static byte[] encrypt(byte[] input)
    {
        RC4 rc4 = new RC4(DEFAULT_RC4_KEY);
        byte[] result = rc4.encrypt(input, RC4_SKIP_LENGTH);
        //return Base64.getEncoder().encode(result);
        return Base64.encodeBase64(result);
    }

    public static byte[] decrypt(byte[] input)
    {
        //byte[] result = Base64.getDecoder().decode(input);
        byte[] result = Base64.decodeBase64(input);
        RC4 rc4 = new RC4(DEFAULT_RC4_KEY);
        return rc4.decrypt(result, RC4_SKIP_LENGTH);
    }

    public static String encrypt(String input,String key)
    {
        byte[] keyByte = key.getBytes();
        char [] arr=new char[keyByte.length];
        for(int i=0; i<keyByte.length; i++) {
            arr[i] = (char) keyByte[i];
        }

        RC4 rc4 = new RC4(arr);
        byte[] result = rc4.encrypt(input.getBytes(), RC4_SKIP_LENGTH);
        return Base64.encodeBase64String(result);
        //return Base64.getEncoder().encodeToString(result);
    }

    public static String decrypt(String input,String key)
    {
        byte[] keyByte = key.getBytes();
        char [] arr=new char[keyByte.length];
        for(int i=0; i<keyByte.length; i++) {
            arr[i] = (char) keyByte[i];
        }
        byte[] result  =Base64.decodeBase64(input.getBytes());
        RC4 rc4 = new RC4(arr);
        return new String(rc4.decrypt(result, RC4_SKIP_LENGTH));
    }

}
