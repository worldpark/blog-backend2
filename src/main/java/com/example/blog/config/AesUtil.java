package com.example.blog.config;

import com.example.blog.exception.CustomErrorCode;
import com.example.blog.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.codec.Hex;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AesUtil {

    private static Logger logger = LoggerFactory.getLogger(AesUtil.class);
    private static String privateKey = "MY_MY_PRIVATE_KEY_AES256_ahdsafa";
    private static String AESIv = "afgdeghjtjkfcsdh";
    private static String algorithm = "AES/CBC/PKCS5Padding";

    public String aesEncode(String rawStr){

        try{
            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(AESIv.getBytes());

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] encrpytionByte = cipher.doFinal(rawStr.getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(encrpytionByte);

        }catch (NullPointerException nullPointerException){
            logger.error("error: ", nullPointerException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.NOT_EXISTS_DATA, "에러가 발생하였습니다.");
        }catch (Exception exception){
            logger.error("error: ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "에러가 발생하였습니다.");
        }
    }

    public String aesDecode(String encodedStr){

        try {

            SecretKeySpec secretKeySpec = new SecretKeySpec(privateKey.getBytes(), "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(AESIv.getBytes());

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

            byte[] decodeBytes = Base64.getDecoder().decode(encodedStr);
            byte[] decrypted = cipher.doFinal(decodeBytes);

            return new String(decrypted);

        }catch (NullPointerException nullPointerException){
            logger.error("error: ", nullPointerException);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.NOT_EXISTS_DATA, "에러가 발생하였습니다.");
        }catch (Exception exception){
            logger.error("error: ", exception);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR, CustomErrorCode.UNKNOWN_ERROR, "에러가 발생하였습니다.");
        }
    }
}
