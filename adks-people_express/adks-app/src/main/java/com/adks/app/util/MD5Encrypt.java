package com.adks.app.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @Description:MD5工具类
 * @author: lxh
 * @date: 2014-8-14 下午05:28:24
 */
public class MD5Encrypt {
        private static String encryptedString;

        private MD5Encrypt() {
        }


        /**
         * @param string
         * @return String
         */
        public static String encrypt(String string) {
                encryptedString = encryptString(string);
                return encryptedString;
        }

        /**
         * Compares the supplied plain text string to the encrypted string.
         *
         * @param string - The plain text string
         * @return boolean
         */
        public static boolean compareEncrypted(String string) {
                if (encryptedString == null || !encryptedString.equals(encryptString(string)))
                        return false;
                else
                        return true;
        }

        /**
         * Compares the supplied plain text string to supplied the encrypted string.
         *
         * @param crypt  - The encrypted string.
         * @param string - The plain text string.
         * @return boolean
         */
        public static boolean compareEncrypted(String crypt, String string) {
                if (crypt == null || !crypt.equals(encryptString(string)))
                        return false;
                else
                        return true;

        }


        /**
         * bytes to hexs.
         */
        public static String byte2hex(byte[] b) {
                String hs = "";
                String stmp = "";
                for (int n = 0; n < b.length; n++) {
                        stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
                        if (stmp.length() == 1)
                                hs = hs + "0" + stmp;
                        else
                                hs = hs + stmp;
                        //if (n<b.length-1)  hs=hs+":";
                }

                return hs.toUpperCase();
        }

        /**
         * hexs to bytes.
         */
        public static byte[] hex2byte(String b) {

                byte[] bb = new byte[b.length() / 2];
                for (int n = 0, i = 0; n < (b.length()); i++, n += 2) {
                        bb[i] = (byte) (Integer.parseInt(b.substring(n, n + 2), 16) & 0XFF);

                }
                return bb;
        }

        /**
         * encrypt the string
         *
         * @param string
         * @return String
         */
        private static String encryptString(String str) {
                if ((str == null) || (str.length() == 0)) return null;
                byte[] val = str.getBytes();
                MessageDigest algorithm = null;

                try {
                        algorithm = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                        return null;
                }

                algorithm.reset();
                algorithm.update(val);
                byte[] digest = algorithm.digest();
                String crypt = byte2hex(digest);

                return crypt;

        }
}
