package open.pay.center.core.util;

import open.pay.center.core.exception.PayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Enumeration;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 17:17
 * Email: qyuhy@qq.com
 */
public class KeyTool {
    private static Logger LOGGER = LoggerFactory.getLogger(KeyTool.class);
    public static final String CER_TYPE_X509 = "X509";
    public static final String KEY_STORE_TYPE = "PKCS12";

    /**
     * 从cer文本中读取公钥
     * @param cerType
     * @param cerText
     * @return
     */
    public static PublicKey parseCerTextOfBase64Encoded(String cerType,String cerText){
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(cerType == null ? CER_TYPE_X509 : cerType);
            InputStream inputStream = new ByteArrayInputStream(cerText.getBytes());
            return certificateFactory.generateCertificate(inputStream).getPublicKey();
        } catch (CertificateException e) {
            LOGGER.error("数字证书格式异常::Text={}",cerText);
            throw new PayException(e);
        }
    }

    /**
     * 从cer文本中读取公钥
     * @param cerText
     * @return
     */
    public static PublicKey parseCerTextOfBase64Encoded(String cerText){
        return parseCerTextOfBase64Encoded(null,cerText);
    }


    /**
     * 从cer文件中读取公钥
     * @param path
     * @param cerType
     * @return
     */
    public static PublicKey loadByCerPath(String path,String cerType){
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(cerType == null ? CER_TYPE_X509 : cerType);
            fis = new FileInputStream(path);
            bis = new BufferedInputStream(fis);
            return certificateFactory.generateCertificate(bis).getPublicKey();
        } catch (FileNotFoundException e) {
            LOGGER.error("公钥文件不存在::{},tip={}",path,e.getMessage());
            throw new PayException();
        } catch (CertificateException e) {
            LOGGER.error("数字证书格式异常::PATH={},tip={}",path,e.getMessage());
            throw new PayException(e);
        } finally {
            closeInputStream(bis,fis);
        }
    }

    /**
     * 从cer文件中读取公钥
     * @param path
     * @return
     */
    public static PublicKey loadByCerPath(String path){
        return loadByCerPath(path,null);
    }


    /**
     * 从pfx结尾的文件中读取秘钥对
     * @param path              pfx文件路径
     * @param passwordOfStore   私钥密码
     * @return
     */
    public static Pair loadKeyPairByPfxPath(String path, String passwordOfStore){
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(path);
            KeyStore ks = KeyStore.getInstance(KEY_STORE_TYPE);
            ks.load(fis, passwordOfStore.toCharArray());
            Enumeration<String> aliases = ks.aliases();
            String aliase = null;
            if (aliases.hasMoreElements()){
                aliase = aliases.nextElement();
            }
            if(aliase == null){
                throw new CertificateException();
            }
            //根据别名得到私钥
            PrivateKey privateKey = (PrivateKey)ks.getKey(aliase,passwordOfStore.toCharArray());
            //根据别名得到公钥
            KeyStore.Entry entry = ks.getEntry(aliase, new KeyStore.PasswordProtection(passwordOfStore.toCharArray()));
            PublicKey publicKey = ((KeyStore.PrivateKeyEntry)entry).getCertificate().getPublicKey();
            return Pair.of(privateKey,publicKey);
        } catch (KeyStoreException e) {
            LOGGER.error("数字证书存储异常::{}",e.getMessage());
            throw new PayException(e);
        } catch (CertificateException e) {
            LOGGER.error("数字证书格式异常::{}",e.getMessage());
            throw new PayException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new PayException(e);
        } catch (FileNotFoundException e) {
            LOGGER.error("找不到数字证书::path={}",path);
            throw new PayException(e);
        } catch (IOException e) {
            LOGGER.error("读取数字证书异常::path={}",path);
            throw new PayException(e);
        } catch (UnrecoverableKeyException e) {
            throw new PayException(e);
        } catch (UnrecoverableEntryException e) {
            throw new PayException(e);
        } finally {
            closeInputStream(fis);
        }
    }

    /**
     * 根据pfx文件获取私钥
     * @param path
     * @param passwordOfStore
     * @return
     */
    public static PrivateKey loadPrivateKeyByPfxPath(String path, String passwordOfStore){
        Pair<PrivateKey,PublicKey> pair = loadKeyPairByPfxPath(path, passwordOfStore);
        return pair.first();
    }


    private static void closeInputStream(InputStream ...inputStreams){
        if(inputStreams == null || inputStreams.length == 0){
            return;
        }
        for (InputStream inputStream : inputStreams) {
            if(inputStream != null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new PayException(e);
                }
            }
        }
    }


    public static void main(String[] args) {
        String cerText = "-----BEGIN CERTIFICATE-----\n" +
                "MIICrzCCAZcCBgFb0nEC9zANBgkqhkiG9w0BAQsFADAbMRkwFwYDVQQDExBUZXN0IENlcnRpZmlj\n" +
                "YXRlMB4XDTE3MDUwNDA3NTAyMVoXDTE3MDUwNDA3NTIwMVowGzEZMBcGA1UEAxMQVGVzdCBDZXJ0\n" +
                "aWZpY2F0ZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJcoG4EdyC8z88uHzrw+c/3g\n" +
                "nJD8H3roy18Ma+jjD29hM9PiILrrN2rJgP86fxnAtBcI3x2mnkQwRHRwfEaeqRIg4nAJpDiscPSe\n" +
                "JPcwHyxRwjo13KrNndZSkaDBPy2ViAAffJs1LFjjqQkQqUB+X3LCOmylKFBjm84I8txgeReVq6U+\n" +
                "SOq1V7TKAzKNrhtR1iMN+FbCmJq1Ju5Ewd1o0s3umsISd2HZ8vPg4Usw7DHaPj8tWJifakUk5fKx\n" +
                "ke2O70tAwQ2X8XHvrJ00tNEhpTVZ8hwNSObD0aklsviCsa5GxTo+KBzEzM5aJ47B7AfcqXNPmLVB\n" +
                "gAD7NWS2HVyM8XkCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAUCs7E6KwzsuLEST/VGjJQoT7mw94\n" +
                "vLGV0H/NKl7umLG9ep1P9S6dVc1jHB038J1uEvOHOsH7C1O05E/rDwSJ9RtCnjQ0oHsddy5ZxSqH\n" +
                "3svMeNkubJibzuVanrtUl1PwrsMzkZJCd5jyeLPcqL0ca/M5phq0sH7uOYpfjfoMiYMxW4soeyqg\n" +
                "Lz2OL7CpTrBaWYMm/KAOaji/uEiSeIK4U3QeAzort/GI4N7fU5P36h8F0z0jlmcYeAx8UVqBh+h7\n" +
                "TJ0shVfpCCZHUlI+iJLcwG+rxsBTkeAvod3a4/RsLk/uaofYEIFSeumBsbmddoPNxOkqWVmfGswl\n" +
                "sJmQAB3d3A==\n" +
                "-----END CERTIFICATE-----\n";
        System.out.println("从文本中读取公钥:"+HexUtil.byte2Hex(parseCerTextOfBase64Encoded(null,cerText).getEncoded()));
        System.out.println("从文件中读取公钥:"+HexUtil.byte2Hex(loadByCerPath("d:/test1.cer",null).getEncoded()));
        System.out.println();
        Pair<PrivateKey,PublicKey> keyPair = loadKeyPairByPfxPath("d:/hymanyu.pfx", "123456");
        System.out.println("从pxf文件中读取私钥:"+HexUtil.byte2Hex(keyPair.first().getEncoded()));
        System.out.println("从pxf文件中读取公钥:"+HexUtil.byte2Hex(keyPair.second().getEncoded()));
        System.out.println("从cer文件中读取公钥:"+HexUtil.byte2Hex(loadByCerPath("d:/hymanyu.cer",null).getEncoded()));
    }


}
