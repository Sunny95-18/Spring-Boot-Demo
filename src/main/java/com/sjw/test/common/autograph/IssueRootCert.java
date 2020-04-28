//package com.sjw.test.common.autograph;
//
//import lombok.extern.slf4j.Slf4j;
//import sun.security.tools.keytool.CertAndKeyGen;
//import sun.security.x509.X500Name;
//
//import java.io.*;
//import java.security.*;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//
///**
// *  颁发根证书
// * @author Sunny
// * @version 1.0
// * @date 2020/4/26 15:37
// */
//@Slf4j
//public class IssueRootCert {
//    private static SecureRandom secureRandom;
//
//              static {
//                 //定义随机数来源
//                 try {
//                         secureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN");
//                     } catch (NoSuchAlgorithmException e) {
//                         log.error("算法不存在");
//                     } catch (NoSuchProviderException e) {
//                         log.error("该随机数提供者不存在");
//                     }
//             }
//
//              /**
//        * 定义pfx根证书文件
//        */
//         public static final String ROOT_ISSUE_PFX_FILE = "D:\\signverify\\rootcert\\ROOTCA.pfx";
//
//              /**
//        * 定义私钥证书的密码
//        */
//              public static final String ROOT_ISSUE_PFX_PASSWORD = "123456";
//      /**
//        * 定义crt根证书文件
//        */
//              public static final String ROOT_ISSUE_CRT_FILE = "D:\\signverify\\rootcert\\ROOTCA.cer";
//
//              /**
//        * 定义根证书的别名
//        */
//              public static final String ROOT_ISSUE_ALIAS = "rootca";
//
//              public static void main(String[] args) {
//                 try {
//                         X500Name issue = new X500Name("CN=RootCA,OU=ISI,O=BenZeph,L=CD,ST=SC,C=CN");
//                         issueRootCert(issue);
//                     } catch (IOException e) {
//                         e.printStackTrace();
//                     }
//
//             }
//
//              /**
//        * 签名算法
//        */
//              public static final String ALGORITHM = "MD5WithRSA";
//
//              public static void issueRootCert(X500Name x500Name) {
//                  try {
//                      CertAndKeyGen certAndKeyGen = new CertAndKeyGen("RSA", ALGORITHM, null);
//                      //设置生成密钥时使用的随机数的来源
//                      certAndKeyGen.setRandom(secureRandom);
//
//                      //设置密钥长度，太短容易被攻击破解
//                      certAndKeyGen.generate(1024);
//
//                      //时间间隔设置为1年（设置证书有效期的时候需要使用到）
////                      long interval = 60L * 60L * 24L * 365;
//                      //
//                      long interval=100L;
//                      X509Certificate x509Certificate = certAndKeyGen.getSelfCertificate(x500Name, interval);
//
//                      X509Certificate[] x509Certificates = new X509Certificate[]{x509Certificate};
//
//                      IssueCertUtils.createKeyStore(ROOT_ISSUE_ALIAS, certAndKeyGen.getPrivateKey(), ROOT_ISSUE_PFX_PASSWORD.toCharArray(), x509Certificates, ROOT_ISSUE_PFX_FILE);
//                      //根据私钥导出公钥
//                      OutputStream outputStream = new FileOutputStream(new File(ROOT_ISSUE_CRT_FILE));
//                      outputStream.write(x509Certificate.getEncoded());
//                      outputStream.close();
//                  } catch (NoSuchAlgorithmException e) {
//                      e.printStackTrace();
//                  } catch (NoSuchProviderException e) {
//                      e.printStackTrace();
//                  } catch (InvalidKeyException e) {
//                      e.printStackTrace();
//                  } catch (CertificateException e) {
//                      e.printStackTrace();
//                  } catch (SignatureException e) {
//                      e.printStackTrace();
//                  } catch (FileNotFoundException e) {
//                      e.printStackTrace();
//                  } catch (IOException e) {
//                      e.printStackTrace();
//                  }
//              }
//
//}
