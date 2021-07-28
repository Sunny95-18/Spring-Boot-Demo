package com.sjw.test.common.autograph;

import sun.security.tools.keytool.CertAndKeyGen;
import sun.security.x509.*;

import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Random;

/**
 * 颁发证书
 * @author Sunny
 * @version 1.0
 * @date 2020/4/26 15:42
 */
public class IssueCert {
    private static SecureRandom secureRandom;

              static {
                 try {
                         secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
                     } catch (NoSuchAlgorithmException e) {
                         e.printStackTrace();
                     } catch (NoSuchProviderException e) {
                         e.printStackTrace();
                     }
             }
     /**
        * 私钥证书-用于签名
        */
             public static final String ISSUE_PFX_FILE = "D:\\signverify\\mycert\\ISSUE.pfx";
      /**
        * 公钥证书-用于验签
        */
      public static final String ISSUE_CRT_FILE = "D:\\signverify\\mycert\\ISSUE.cer";

              /**
        * 定义pfx根证书文件
        */
              public static final String ROOT_ISSUE_PFX_FILE = "D:\\signverify\\rootcert\\ROOTCA.pfx";

              /**
        * 定义私钥证书的密码
        */
              public static final String ROOT_ISSUE_PFX_PASSWORD = "123456";
      /**
        * 定义crt根证书文件
        */
              public static final String ROOT_ISSUE_CRT_FILE = "D:\\signverify\\rootcert\\ROOTCA.cer";

              /**
        * 定义根证书的别名
        */
              public static final String ROOT_ISSUE_ALIAS = "rootca";

              /**
        * 证书别名
        */
              public static final String ISSUE_ALIAS = "subject";

              /**
        * 私钥证书密码
        */
              public static final String ISSUE_PASSWORD = "123456";


              /**
        * 签名算法
        */
              public static final String SIG_ALG = "MD5WithRSA";

              public static void main(String[] args) {
                 try {

                         X500Name issue = new X500Name("CN=RootCA,OU=ISI,O=BenZeph,L=CD,ST=SC,C=CN");
                         X500Name subject = new X500Name(
                                         "CN=subject,OU=ISI,O=BenZeph,L=CD,ST=SC,C=CN");
                         createIssueCert(issue, subject);
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
             }

              /**
        *
        */
              public static void createIssueCert(X500Name rootX500name, X500Name subjectX500Name) {
                 try {
                         CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", SIG_ALG, null);

                         //生成密钥时候使用的随机数的来源
                         certAndKeyGen.setRandom(secureRandom);

                         //设置密钥的大小
                         certAndKeyGen.generate(1024);


                         //设置时间，设置证书有效期的时候需要使用到
//                         long validity = 60L * 60L * 24L * 365;
                      long validity=100L;
                         Date startDate = new Date();
                         Date endDate = new Date();
                         endDate.setTime(startDate.getTime() + validity * 1000);
                         //设置证书有效期
                         CertificateValidity interval = new CertificateValidity(startDate, endDate);

                         //获取X509CertInfo对象，并为其添加所有的强制属性
                         X509CertInfo info = new X509CertInfo();

                         info.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
                         info.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(new Random().nextInt() & 0x7fffffff));

                         AlgorithmId algID = AlgorithmId.get(SIG_ALG);
                         info.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(algID));

                         info.set(X509CertInfo.SUBJECT, subjectX500Name);

                         info.set(X509CertInfo.KEY, new CertificateX509Key(certAndKeyGen.getPublicKey()));

                         info.set(X509CertInfo.VALIDITY, interval);

                         info.set(X509CertInfo.ISSUER, rootX500name);

                         PrivateKey privateKey = getPrivateKey();

                         X509CertImpl cert = new X509CertImpl(info);
                         cert.sign(privateKey, SIG_ALG);

                         //X509Certificate certificate = (X509Certificate) cert;

                         X509Certificate x509Certificate = readX509Certificate();

                         X509Certificate[] x509Certificates = new X509Certificate[]{cert, x509Certificate};

                         IssueCertUtils.createKeyStore(ISSUE_ALIAS, certAndKeyGen.getPrivateKey(), ISSUE_PASSWORD.toCharArray(), x509Certificates, ISSUE_PFX_FILE);

                         OutputStream outputStream = new FileOutputStream(new File(ISSUE_CRT_FILE));
                         outputStream.write(cert.getEncoded());
                         outputStream.close();

                     } catch (NoSuchAlgorithmException e) {
                         e.printStackTrace();
                     } catch (NoSuchProviderException e) {
                         e.printStackTrace();
                     } catch (InvalidKeyException e) {
                         e.printStackTrace();
                     } catch (CertificateException e) {
                         e.printStackTrace();
                     } catch (IOException e) {
                         e.printStackTrace();
                     } catch (SignatureException e) {
                         e.printStackTrace();
                     }
             }


             /**
       * 获取私钥
       *
       * @return
       */
             private static PrivateKey getPrivateKey() {
                 try {
                         //后去指定类型的KeyStore对象
                         KeyStore keyStore = KeyStore.getInstance("PKCS12");
                         InputStream in = null;
                         in = new FileInputStream(ROOT_ISSUE_PFX_FILE);
                         keyStore.load(in, ROOT_ISSUE_PFX_PASSWORD.toCharArray());
                         in.close();
                         //使用指定的密码来获取指定的别名对应的私钥
                         Key key = keyStore.getKey(ROOT_ISSUE_ALIAS, ROOT_ISSUE_PFX_PASSWORD.toCharArray());
                         return (PrivateKey) key;
                     } catch (KeyStoreException e) {
                         e.printStackTrace();
                     } catch (FileNotFoundException e) {
                         e.printStackTrace();
                     } catch (CertificateException e) {
                         e.printStackTrace();
                     } catch (NoSuchAlgorithmException e) {
                         e.printStackTrace();
                     } catch (IOException e) {
                         e.printStackTrace();
                     } catch (UnrecoverableKeyException e) {
                         e.printStackTrace();
                     }
                 return null;
             }

             /**
       * 读取crt根证书信息
       *
       * @return
       */
             private static X509Certificate readX509Certificate() {
                 InputStream inputStream = null;
                 try {
                         inputStream = new FileInputStream(ROOT_ISSUE_CRT_FILE);
                         CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                         X509Certificate certificate = (X509Certificate) certificateFactory.generateCertificate(inputStream);

                         inputStream.close();
                         return certificate;
                     } catch (FileNotFoundException e) {
                         e.printStackTrace();
                     } catch (CertificateException e) {
                         e.printStackTrace();
                     } catch (IOException e) {
                         e.printStackTrace();
                     }
                 return null;
             }
}
