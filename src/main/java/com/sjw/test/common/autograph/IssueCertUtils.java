//package com.sjw.test.common.autograph;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.security.Key;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//
///**
// * @author Sunny
// * @version 1.0
// * @date 2020/4/26 15:48
// */
//public class IssueCertUtils {
//    private IssueCertUtils() {
//    }
//
//    /**
//     * 证书私钥的存储设施
//     *
//     * @param alias        别名（会与对应的privateKey关联）
//     * @param privateKey   密钥-私钥
//     * @param password     密码(用来保护私钥的密码)
//     * @param certificates 证书链
//     * @param pfxFile      pfx文件
//     */
//    public static void createKeyStore(String alias, Key privateKey, char[] password, Certificate[] certificates, String pfxFile) {
//        try {
//            //获取指定类型的KeyStore对象
//            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            //加载KeyStore
//            keyStore.load(null, password);
//
//            //将给定的密钥分配给指定的别名，并用给定的密码来保护它
//            keyStore.setKeyEntry(alias, privateKey, password, certificates);
//
//            //以下几步就是想私钥证书导出
//            OutputStream outputStream = new FileOutputStream(pfxFile);
//            keyStore.store(outputStream, password);
//            outputStream.close();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        } catch (CertificateException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
