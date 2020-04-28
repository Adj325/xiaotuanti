package com.adj.xiaotuanti.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import sun.misc.BASE64Decoder;

/*
PKCS1
-----BEGIN PUBLIC KEY-----
MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYYcUBGl+bCbqJ3SdV2Fo72Edv
PXKvLUG1adunaemTM3LValHNJhQkagUnCnn37z0XCTNzK2eW+Xn7WUr7J1a7kSsG
FqC9p6nbdkwLPvtVqVIspGYlV1CsVQeb/j92/f1+kfNk3aCK9qaL6T27KVcp9Wke
MFUCUxE0bwQRTHaUQwIDAQAB
-----END PUBLIC KEY-----


PKCS1
-----BEGIN RSA PRIVATE KEY-----
MIICXQIBAAKBgQDYYcUBGl+bCbqJ3SdV2Fo72EdvPXKvLUG1adunaemTM3LValHN
JhQkagUnCnn37z0XCTNzK2eW+Xn7WUr7J1a7kSsGFqC9p6nbdkwLPvtVqVIspGYl
V1CsVQeb/j92/f1+kfNk3aCK9qaL6T27KVcp9WkeMFUCUxE0bwQRTHaUQwIDAQAB
AoGAYIBaB//nG5HfK5UB8fsnYwE1+pqBBzc70th/Kc7GaOvNDvj3unJ9ETBnU94M
P1Lm/9sOoxJ8hIyGpl5QXmpQ90vyRCu4DWDuAczOSyei54Q43P9v+lcl28BxMVoB
Tr3FOIEfAXCxr+1HA/SfAgyq9mus8bOXG3wsIwm6z2t274ECQQDm5jlIJzeuLI4i
bfpWEf4+jWcPEf0HkYm5msNpLkn+otd1b/3N9gEqEcSCmJGwbSRMnqAjCW3vOU2T
Iw0KMdtRAkEA7+eJPesztDsY5AkE6TVx102tcQ8fhoEiD7uWuUUXSK63c8EljG7e
T/inESJRfCndGdGykIdkbvze2sbV40SpUwJBAIwFq1EdLz/5lww8A2a0rKjEWW4j
K32ufYXH257qSkcX+28lRhXjjFs1wSStXxRNa4jnXRdCPZ+n8Wo8BEldaLECQGka
Xx4pdTV4zqbY7LXfHEjfVyU5Iu6ilHGgFaCFjHXjVav46qJj0DHpFgiDqH9lN+Cf
5kM7jdyyHFBs3Df9VNECQQCHZnFHvnc7h4JsLKpktI8AaD7ddpwO2SHtQsWYphM5
xhkFi+YlI9+vS1VbJeYrOg4wIVug14OKxvrU2HAToA6G
-----END RSA PRIVATE KEY-----


PKCS8
-----BEGIN PRIVATE KEY-----
MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANhhxQEaX5sJuond
J1XYWjvYR289cq8tQbVp26dp6ZMzctVqUc0mFCRqBScKeffvPRcJM3MrZ5b5eftZ
SvsnVruRKwYWoL2nqdt2TAs++1WpUiykZiVXUKxVB5v+P3b9/X6R82TdoIr2povp
PbspVyn1aR4wVQJTETRvBBFMdpRDAgMBAAECgYBggFoH/+cbkd8rlQHx+ydjATX6
moEHNzvS2H8pzsZo680O+Pe6cn0RMGdT3gw/Uub/2w6jEnyEjIamXlBealD3S/JE
K7gNYO4BzM5LJ6LnhDjc/2/6VyXbwHExWgFOvcU4gR8BcLGv7UcD9J8CDKr2a6zx
s5cbfCwjCbrPa3bvgQJBAObmOUgnN64sjiJt+lYR/j6NZw8R/QeRibmaw2kuSf6i
13Vv/c32ASoRxIKYkbBtJEyeoCMJbe85TZMjDQox21ECQQDv54k96zO0OxjkCQTp
NXHXTa1xDx+GgSIPu5a5RRdIrrdzwSWMbt5P+KcRIlF8Kd0Z0bKQh2Ru/N7axtXj
RKlTAkEAjAWrUR0vP/mXDDwDZrSsqMRZbiMrfa59hcfbnupKRxf7byVGFeOMWzXB
JK1fFE1riOddF0I9n6fxajwESV1osQJAaRpfHil1NXjOptjstd8cSN9XJTki7qKU
caAVoIWMdeNVq/jqomPQMekWCIOof2U34J/mQzuN3LIcUGzcN/1U0QJBAIdmcUe+
dzuHgmwsqmS0jwBoPt12nA7ZIe1CxZimEznGGQWL5iUj369LVVsl5is6DjAhW6DX
g4rG+tTYcBOgDoY=
-----END PRIVATE KEY-----



*/

/**
 * RSA算法
 * 使用过程：
 *        1. 到 http://web.chacuo.net/netrsakeypair ，获取PKCS1格式的公钥及私钥
 *           在这里不要使用密码加密
 *        2. 到 http://tool.chacuo.net/cryptrsapkcs1pkcs8 ，将对应的PKCS1私钥转为PKCS8格式的私钥
 *        3. 前端(js)使用“PKCS1公钥”加密数据，后端(java)使用“PKCS1私钥”解密
 *        PS: PKCS1 PKCS8 只是版本上的不同，不同版本包含的信息不同，但内容是一致的所以可以互相转换
 *            相当于一首歌有ape格式，也有flac。格式不同，但都是代表了那首歌曲。
 * 来源：https://blog.csdn.net/qq_34024275/article/details/82424100
 */
public class RSAUtils {


    /**
     * 公钥加密
     *
     * @param content
     * @param public_key
     * @return
     * @throws Exception
     */
    public static String signWithPublicKey(String content, String public_key) throws Exception {
        byte[] buffer = Base64.decodeBase64(public_key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
        RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
        Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
        cipher.init(1, publicKey);
        byte[] data = content.getBytes("utf-8");
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        int key_len = publicKey.getModulus().bitLength() / 8 - 11;
        for (int i = 0; inputLen - offSet > 0; offSet = i * key_len) {
            byte[] cache;
            if (inputLen - offSet > key_len) {
                cache = cipher.doFinal(data, offSet, key_len);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
        return new String(Base64.encodeBase64(encryptedData));
    }

    /*
    * 获取PKCS8私钥
    * 来源：https://www.cnblogs.com/stream1/archive/2012/05/29/2523597.html
    * */
    private static PrivateKey getPrivateKey() {
        PrivateKey privateKey = null;
        String privatekeyPKCS8 =
                "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANhhxQEaX5sJuondJ1XYWjvYR289cq8tQbVp26dp6ZMzctVqUc0mFCRqBScKeffvPRcJM3MrZ5b5eftZSvsnVruRKwYWoL2nqdt2TAs++1WpUiykZiVXUKxVB5v+P3b9/X6R82TdoIr2povpPbspVyn1aR4wVQJTETRvBBFMdpRDAgMBAAECgYBggFoH/+cbkd8rlQHx+ydjATX6moEHNzvS2H8pzsZo680O+Pe6cn0RMGdT3gw/Uub/2w6jEnyEjIamXlBealD3S/JEK7gNYO4BzM5LJ6LnhDjc/2/6VyXbwHExWgFOvcU4gR8BcLGv7UcD9J8CDKr2a6zxs5cbfCwjCbrPa3bvgQJBAObmOUgnN64sjiJt+lYR/j6NZw8R/QeRibmaw2kuSf6i13Vv/c32ASoRxIKYkbBtJEyeoCMJbe85TZMjDQox21ECQQDv54k96zO0OxjkCQTpNXHXTa1xDx+GgSIPu5a5RRdIrrdzwSWMbt5P+KcRIlF8Kd0Z0bKQh2Ru/N7axtXjRKlTAkEAjAWrUR0vP/mXDDwDZrSsqMRZbiMrfa59hcfbnupKRxf7byVGFeOMWzXBJK1fFE1riOddF0I9n6fxajwESV1osQJAaRpfHil1NXjOptjstd8cSN9XJTki7qKUcaAVoIWMdeNVq/jqomPQMekWCIOof2U34J/mQzuN3LIcUGzcN/1U0QJBAIdmcUe+dzuHgmwsqmS0jwBoPt12nA7ZIe1CxZimEznGGQWL5iUj369LVVsl5is6DjAhW6DXg4rG+tTYcBOgDoY=";
        PKCS8EncodedKeySpec priPKCS8;
        try {
            priPKCS8 = new PKCS8EncodedKeySpec(
                    new BASE64Decoder().decodeBuffer(privatekeyPKCS8));
            KeyFactory keyf = KeyFactory.getInstance("RSA");
            privateKey = keyf.generatePrivate(priPKCS8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    /**
     * 私钥解密
     *
     * @param content       密文
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String content) throws Exception {

        String input_charset = "UTF-8";
        PrivateKey prikey = getPrivateKey();

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, prikey);

        InputStream ins = new ByteArrayInputStream(Base64.decodeBase64(content));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
        byte[] buf = new byte[128];
        int bufl;

        while ((bufl = ins.read(buf)) != -1) {
            byte[] block = null;

            if (buf.length == bufl) {
                block = buf;
            } else {
                block = new byte[bufl];
                for (int i = 0; i < bufl; i++) {
                    block[i] = buf[i];
                }
            }

            writer.write(cipher.doFinal(block));
        }

        return new String(writer.toByteArray(), input_charset);
    }

    public static void main(String[] args) {
        try {
            String encryptedData = "uMpleRH347bF1MmqlSDWburMC566zKUHXFHq89VcpJooYDNuuAV4rUg2clwWji/6LgHQxpm9iizEqtsK9I7MxCSoiAXm4KFxtFFpPgtIb1iJdbDl8P+G3NuxNkwAmtyUIkuNxEIBdEi7UHU1njdxUVBsWy7anY7LbNwKDMzDtkc=";
            encryptedData = "iHACz5gJztOOEmm11sayKNmeYDkBHY15iFDxhYYN4coz8Zuv16DwuoQwEJY5VWLfL46rVVpIqd0JyB3guYQOoA2k/G8D9wdr2uvL/12bwMeLKc6CvUKGijcw3b+YCxmLVvkVRFX6aor5M0J/WKpGBGtEcRnasZ2GnkSBY84t20Q=";
            System.out.println(decryptByPrivateKey(encryptedData));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}