package open.pay.center.core.util.cer;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V1CertificateGenerator;

import javax.security.auth.x500.X500Principal;
import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;

/**
 * User: hyman
 * Date: 2017/5/4 0004
 * Time: 15:42
 * Email: qyuhy@qq.com
 */
public class CertHandlers {
    /**
     *
     * @param pair 密钥对
     * @param bdate 有效期
     * @param edate 有效期
     * @param info 证书信息
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchProviderException
     * @throws SignatureException
     */
    @SuppressWarnings("deprecation")
    public X509Certificate generateV1Certificate(KeyPair pair, Date bdate, Date edate, X500Principal info)
            throws InvalidKeyException, NoSuchProviderException, SignatureException
    {
        // generate the certificate
        Security.addProvider(new BouncyCastleProvider());
        X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
        certGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
        certGen.setIssuerDN(info);
        certGen.setNotBefore(bdate);
        certGen.setNotAfter(edate);
        certGen.setSubjectDN(new X500Principal("CN=Test Certificate"));
        certGen.setPublicKey(pair.getPublic());
        // i get error here
        certGen.setSignatureAlgorithm("SHA256WithRSAEncryption");
        return certGen.generateX509Certificate(pair.getPrivate(), "BC");
    }
    /**
     * * 生成证书文件
     * @param address 文件保存的路径
     * @param bdate 有效期
     * @param edate 有效期
     * @param info 证书信息
     * @param algorithm 算法名称
     * @param keysize 密钥长度
     * @param random 随机源
     * @throws Exception
     */

    public void ProCert(String address,Date bdate,Date edate,X500Principal info,String algorithm,int keysize, SecureRandom random)throws Exception{
        // create the keys
        KeyGen pc = new KeyGen();
        KeyPair kp = pc.getKeyPair(algorithm, keysize, random);
        // generate the certificate
        X509Certificate cert = generateV1Certificate(kp,bdate,edate,info);
        // show some basic validation
        cert.checkValidity(new Date());
        cert.verify(cert.getPublicKey());
        FileOutputStream o = new FileOutputStream(address);
        Writer wr = new OutputStreamWriter(o, Charset.forName("UTF-8"));
        wr.write("-----BEGIN CERTIFICATE-----\r\n");
        wr.write(new sun.misc.BASE64Encoder().encode(cert.getEncoded()));
        wr.write("\r\n-----END CERTIFICATE-----\r\n");
        wr.flush();
        wr.close();
    }
    /**
     * 获取证书对象
     * @param address 证书文件路径
     * @return
     * @throws Exception
     */
    public  X509Certificate GetCert(String address) throws Exception {
        X509Certificate cert = null;
        FileInputStream fis = new FileInputStream(address);
        BufferedInputStream bis = new BufferedInputStream(fis);
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        while (bis.available() > 0) {
            cert=(X509Certificate) cf.generateCertificate(fis);
            //System.out.println(cert.getPublicKey());
        }
        return cert;
    }

    public static void main(String[] args) {
        CertHandlers ch = new CertHandlers();
        try {
            ch.ProCert("D:\\test1.cer", new java.sql.Date(System.currentTimeMillis() - 50000), new java.sql.Date(System.currentTimeMillis() + 50000), new X500Principal("CN=Test Certificate"), "RSA", 2048, new SecureRandom());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        try {
            X509Certificate cert = ch.GetCert("D:\\hyman.pfx");
            System.out.println(cert.getPublicKey());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
