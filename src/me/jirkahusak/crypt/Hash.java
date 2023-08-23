package me.jirkahusak.crypt;

import org.bouncycastle.crypto.digests.Blake2bDigest;
import org.bouncycastle.util.encoders.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {

    public static String hashSHA512(String hashing){
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] hashBytes = md.digest(hashing.getBytes());

            StringBuilder hashStringBuilder = new StringBuilder();
            for(byte b: hashBytes){
                hashStringBuilder.append(String.format("%02x", b));
            }

            return hashStringBuilder.toString();
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }
    public static String hashBlake2B(String password){
        byte[] data = password.getBytes();
        Blake2bDigest blake2bDigest = new Blake2bDigest(null, 128 / 8, null, null);

        blake2bDigest.update(data, 0, data.length);

        byte[] hashBytes = new byte[blake2bDigest.getDigestSize()];
        blake2bDigest.doFinal(hashBytes, 0);

        String hashHex = Hex.toHexString(hashBytes);
        return hashHex;
    }
}
