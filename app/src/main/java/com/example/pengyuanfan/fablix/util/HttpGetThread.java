package com.example.pengyuanfan.fablix.util;

import android.os.Bundle;
import android.os.Message;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


/**
 * Created by pengyuanfan on 5/13/2016.
 */
public class HttpGetThread extends Thread {
    URL url;
    Handler output;

    public HttpGetThread(URL url, Handler output){
        super();
        this.url=url;
        this.output=output;
    }

    // always verify the host - dont check for certificate
    final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    };

    /**
     * Trust every server - dont check for any certificate
     */
    private static void trustAllHosts() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[] {};
            }

            public void checkClientTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }

            public void checkServerTrusted(X509Certificate[] chain,
                                           String authType) throws CertificateException {
            }
        } };

        // Install the all-trusting trust manager
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection
                    .setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        Message msg = Message.obtain();
        msg.what=fail;
        Log.d("d","start");
        try {
            //Get Http Connection and trust all CA
            HttpURLConnection conn = null;
            if (url.getProtocol().toLowerCase().equals("https")) {
                trustAllHosts();
                HttpsURLConnection https = (HttpsURLConnection) url.openConnection();
                https.setHostnameVerifier(DO_NOT_VERIFY);
                conn = https;
            } else {
                conn = (HttpURLConnection) url.openConnection();
            }
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            Log.d("d","connected");
            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK) {
                //Read Data
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str;
                StringBuffer strbuffer = new StringBuffer();
                while ((str = reader.readLine()) != null) {
                    strbuffer.append(str);
                }
                Log.d("d", "dataing");
                //Send Message to Caller Handler
                Bundle res = new Bundle();
                res.putString("result", strbuffer.toString());
                msg.setData(res);
                msg.what = success;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d("d","over");
        output.sendMessage(msg);
    }

    public static final int
                            fail=0,
                            success= 1 ;
}
