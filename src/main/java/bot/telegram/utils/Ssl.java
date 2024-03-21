package bot.telegram.utils;

import javax.net.ssl.*;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class Ssl {
    public static void disableVerification () {

        TrustManager[] trustAllCerts = new TrustManager [] {new X509ExtendedTrustManager() {
            @Override
            public void checkClientTrusted (X509Certificate[] chain, String authType, Socket socket) {

            }

            @Override
            public void checkServerTrusted (X509Certificate [] chain, String authType, Socket socket) {

            }

            @Override
            public void checkClientTrusted (X509Certificate [] chain, String authType, SSLEngine engine) {

            }

            @Override
            public void checkServerTrusted (X509Certificate [] chain, String authType, SSLEngine engine) {

            }

            @Override
            public X509Certificate [] getAcceptedIssuers () {
                return null;
            }

            @Override
            public void checkClientTrusted (X509Certificate [] certs, String authType) {
            }

            @Override
            public void checkServerTrusted (X509Certificate [] certs, String authType) {
            }

        }};

        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init (null, trustAllCerts, new java.security.SecureRandom());
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace ();
        }
        if (sc != null) HttpsURLConnection.setDefaultSSLSocketFactory (sc.getSocketFactory());
    }
}
