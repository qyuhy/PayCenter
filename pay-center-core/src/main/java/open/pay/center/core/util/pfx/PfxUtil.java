package open.pay.center.core.util.pfx;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Enumeration;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 16:05
 * Email: qyuhy@qq.com
 * 生成pfx格式的数字证书
 */
public class PfxUtil {
    public static void main(String[] args) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException, SignatureException, InvalidKeyException, UnrecoverableEntryException {
        String certPath = "d:/hyman.pfx";
        KeyStore store = KeyStore.getInstance("PKCS12");
        store.load(null, null);
        /* RSA算法产生公钥和私钥 */
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        KeyPair keyPair = kpg.generateKeyPair();
        System.out.println("公钥:"+byte2Hex(keyPair.getPublic().getEncoded()));
        System.out.println("私钥:"+byte2Hex(keyPair.getPrivate().getEncoded()));

        // 组装证书
        String issuer = "C=CN,ST=BJ,L=BJ,O=SICCA,OU=SC,CN=SICCA";
        String subject = issuer;
        Security.addProvider(new BouncyCastleProvider());
        X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(new X500Principal(issuer));
        certGen.setNotBefore(new Date(System.currentTimeMillis() - 50000));
        certGen.setNotAfter(new Date(System.currentTimeMillis() + 50000));
        certGen.setSubjectDN(new X500Principal(subject));
        certGen.setPublicKey(keyPair.getPublic());
        certGen.setSignatureAlgorithm("SHA1WithRSAEncryption");

        X509Certificate cert = certGen.generateX509Certificate(keyPair.getPrivate());
        System.out.println(cert.toString());

        store.setKeyEntry("hyman-yu", keyPair.getPrivate(),
                "123456".toCharArray(), new Certificate[]{cert});

        FileOutputStream fout =new FileOutputStream(certPath);
        store.store(fout, "123456".toCharArray());
        fout.close();

        System.out.println("=================读取pfx文件================");
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(new FileInputStream(certPath), "123456".toCharArray());
        Enumeration<String> aliases = ks.aliases();
        while (aliases.hasMoreElements()){
            String s = aliases.nextElement();
            //读取私钥
            PrivateKey privateKey = (PrivateKey)ks.getKey(s, "123456".toCharArray());
            System.out.println(ks.getKey(s, "123456".toCharArray()));
            KeyStore.Entry entry = ks.getEntry(s, new KeyStore.PasswordProtection("123456".toCharArray()));
            PublicKey publicKey = ((KeyStore.PrivateKeyEntry)entry).getCertificate().getPublicKey();
            System.out.println("解析后的公钥为:"+byte2Hex(publicKey.getEncoded()));
            System.out.println("解析后的私钥为:"+byte2Hex(privateKey.getEncoded()));
        }

    }

    public static String byte2Hex(byte[] srcBytes) {
        StringBuilder hexRetSB = new StringBuilder();
        for (byte b : srcBytes) {
            String hexString = Integer.toHexString(0x00ff & b);
            hexRetSB.append(hexString.length() == 1 ? 0 : "").append(hexString);
        }
        return hexRetSB.toString();
    }
}
