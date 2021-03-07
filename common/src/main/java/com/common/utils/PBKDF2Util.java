package com.common.utils;


import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

public class PBKDF2Util {

    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";


    public static final int SALT_BYTE_SIZE = 32 / 2;         //盐的长度
    public static final int HASH_BIT_SIZE = 128 * 4;         //生成密文的长度
    public static final int PBKDF2_ITERATIONS = 1000;        //迭代次数


    /**
     * 判断密码是否正确
     * @param attemptedPassword 输入的密码
     * @param encryptedPassword 正确的密码
     * @param salt 密码盐
     * @return 密码正确返回true
     */
    public static boolean authenticate(String attemptedPassword, String encryptedPassword, String salt)
            {
        // 用相同的盐值对用户输入的密码进行加密
        String encryptedAttemptedPassword = getEncryptedPassword(attemptedPassword, salt);
        // 把加密后的密文和原密文进行比较，相同则验证成功，否则失败
        return encryptedAttemptedPassword.equals(encryptedPassword);
    }



    /**
     * @auther: Ragty
     * @describe: 生成密文
     * @param: [password(明文密码), salt(盐值)]
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    public static String getEncryptedPassword(String password, String salt) {

        KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), PBKDF2_ITERATIONS, HASH_BIT_SIZE);
        SecretKeyFactory f = null;
        try {
            f = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return toHex(f.generateSecret(spec).getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




    /**
     * @auther: Ragty
     * @describe: 通过加密的强随机数生成盐(最后转换为16进制)
     * @param: []
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    public static String generateSalt() throws NoSuchAlgorithmException {
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        return toHex(salt);
    }




    /**
     * @auther: Ragty
     * @describe: 十六进制字符串转二进制字符串
     * @param: [hex]
     * @return: byte[]
     * @date: 2018/11/2
     */
    private static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }





    /**
     * @auther: Ragty
     * @describe: 二进制字符串转十六进制字符串
     * @param: [array]
     * @return: java.lang.String
     * @date: 2018/11/2
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0)
            return String.format("%0" + paddingLength + "d", 0) + hex;
        else
            return hex;
    }


    public static void main(String args[]) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String salt = generateSalt();
        String pas = getEncryptedPassword("123456", salt);
        System.out.println("salt:"+salt);
        System.out.println("password:"+pas);
        System.out.println(authenticate("123456",pas, salt));
    }
}
