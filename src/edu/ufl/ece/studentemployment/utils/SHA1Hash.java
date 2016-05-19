/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



package edu.ufl.ece.studentemployment.utils;

//~--- JDK imports ------------------------------------------------------------

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Vaibhav Garg
 * This class computes the SHA1 hash for given data such as passwords to store in database.String sha1_ad1 = AeSimpleSHA1.SHA1("Buy new mobile phone!
 */
public class SHA1Hash {
    
    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < data.length; i++) {
            int halfbyte  = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;

            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }

                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }

        return buf.toString();
    }

    /*
     * @param text the text to be converted to SHA1 hash
     * @return the SHA1 hash
     */
    public static String SHA1(String text) {
        Log logger=LogFactory.getLog(SHA1Hash.class);
        MessageDigest md;
        String        hash = null;

        try {
            md = MessageDigest.getInstance("SHA-1");

            byte[] sha1hash = new byte[40];

            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
            hash     = convertToHex(sha1hash);
        } catch (NoSuchAlgorithmException e) {
            logger.error("Exception in SHA1",e);
            //e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            //e.printStackTrace();
             logger.error("Exception in SHA1",e);
        } catch (Exception e) {
            //e.printStackTrace();
            logger.error("Exception in SHA1",e);
        } finally {
            return hash;
        }
    }

    /*
     * @param hash1 The hash from database
     * @param passwd The passwd which user entered and needs to be checked
     * @return Returns a boolean value if passwords match
     */
     
    public static boolean checkHash(String hash1, String passwd) {
        boolean match      = false;
        String  passwdHash = SHA1(passwd);
        //System.out.println(passwdHash + "*******");
        // check if our hashes match
        if (hash1.equals(passwdHash)) {
            match = true;
        }

        return match;
    }
}
