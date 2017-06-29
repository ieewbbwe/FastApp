package com.webber.mcorelibspace.demo.net;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

/**
 * Created by mxh on 2017/6/29.
 * Describe：
 */

public class SslContextFactory {
    final String CLIENT_AGREEMENT = "TLS";//使用协议
    final String CLIENT_TRUST_MANAGER = "X.509";
    @Deprecated
    final String CLIENT_TRUST_PASSWORD = "151102";//信任证书密码，该证书默认密码是123456
    @Deprecated
    final String CLIENT_TRUST_KEYSTORE = "BKS";
    SSLContext sslContext = null;

    public SSLSocketFactory getSSLSocketFactory(InputStream... certificates) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance(CLIENT_TRUST_MANAGER);
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));

                try {
                    if (certificate != null)
                        certificate.close();
                } finally {
                    certificate.close();
                }
            }

            SSLContext sslContext = SSLContext.getInstance(CLIENT_AGREEMENT);

            TrustManagerFactory trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());

            trustManagerFactory.init(keyStore);
            sslContext.init(
                    null,
                    trustManagerFactory.getTrustManagers(),
                    new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("SSL Load Failed!!");
        }
    }

    /**
     * forTest
     */
    private void initSslContext() {
        String keyStoreType = KeyStore.getDefaultType();
        try {
            KeyStore keyStore = KeyStore.getInstance(keyStoreType);
            keyStore.load(null, null);

            CertificateFactory cf = CertificateFactory.getInstance("X.509");

            // set root ca
            int idx = 1;
            InputStream rootCaFile = SslContextFactory.class.getClassLoader().getResourceAsStream("res/raw/deals_server.crt");
            while (rootCaFile.available() > 0) {
                Certificate cert = cf.generateCertificate(rootCaFile);
                keyStore.setCertificateEntry("root_ca_" + idx, cert);
                idx++;
            }
            // set auction yql ca
            InputStream auctionYQLCaFile = SslContextFactory.class.getClassLoader().getResourceAsStream("res/raw/auction_yql.crt");
            Certificate auctionYQLCa = cf.generateCertificate(auctionYQLCaFile);
            keyStore.setCertificateEntry("auction_yql", auctionYQLCa);
            String tmfAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(tmfAlgorithm);
            tmf.init(keyStore);

            sslContext = SSLContext.getInstance("TLSv1");
            sslContext.init(
                    null,
                    tmf.getTrustManagers(),
                    null
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
