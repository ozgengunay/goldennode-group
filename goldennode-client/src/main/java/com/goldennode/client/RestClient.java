package com.goldennode.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.goldennode.client.service.Credentials;
import com.goldennode.client.service.RegistrationServiceImpl;

public class RestClient {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    private static String apiKey;
    private static String secretKey;
    private static String host;
    private static String SERVER_URL = "https://api.goldennode.io";
    static {
        apiKey = System.getProperty("com.goldennode.client.apiKey");
        secretKey = System.getProperty("com.goldennode.client.secretKey");
        host = System.getProperty("com.goldennode.client.host");
        if (apiKey == null) {
            apiKey = System.getenv("GN_APIKEY");
        }
        if (secretKey == null) {
            secretKey = System.getenv("GN_SECRETKEY");
        }
        if (host == null) {
            host = System.getenv("GN_HOST");
        }
        if (host == null) {
            host = SERVER_URL;
        }
        if (apiKey == null || secretKey == null) {
            try {
                Credentials cred = new RegistrationServiceImpl().registerTempAccount();
                apiKey = cred.getApiKey();
                secretKey = cred.getSecretKey();
            } catch (GoldenNodeException e) {
                LOGGER.error("Error occured", e);
                throw new RuntimeException("can't load apiKey");
            }
        }
    }

    public static Response call(String uri, String method) throws GoldenNodeException {
        return call(uri, method, null);
    }

    public static Response call(String uri, String method, boolean isSecure) throws GoldenNodeException {
        return call(uri, method, null, isSecure);
    }

    public static Response call(String uri, String method, String body, boolean isSecure) throws GoldenNodeException {
        try {
            URL url = new URL(host + uri);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            (con).setSSLSocketFactory(socketFactory);
            if (isSecure)
                signRequest(con, body);
            if (body != null) {
                con.setRequestProperty("Content-Type", "application/json");
                con.setDoOutput(true);
                OutputStream os = con.getOutputStream();
                os.write(body.getBytes());
                os.flush();
                os.close();
            }
            int responseCode = con.getResponseCode();
            InputStream is = con.getErrorStream() == null ? con.getInputStream() : con.getErrorStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(is));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return new Response(response.toString(), responseCode);
        } catch (IOException e) {
            throw new GoldenNodeException(e);
        }
    }

    public static Response call(String uri, String method, String body) throws GoldenNodeException {
        return call(uri, method, body, true);
    }

    public static void main(String arg[]) {
    }

    public static void signRequest(HttpURLConnection con, String body) {
        String auth = "key=" + apiKey + ",timestamp=" + System.currentTimeMillis() / 1000L + ",nonce=" + UUID.randomUUID().toString();
        String sigBase = auth + con.getURL().getPath() + (body == null ? "" : body) + con.getRequestMethod();
        con.addRequestProperty("Signature", generateHmacSHA256Signature(sigBase, secretKey));
        LOGGER.debug("Signature to be created from : " + sigBase);
        con.addRequestProperty("Authorization", auth);
    }

    private static String generateHmacSHA256Signature(String data, String secretKey) {
        byte[] hmacData = null;
        try {
            SecretKeySpec sKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(sKey);
            hmacData = mac.doFinal(data.getBytes("UTF-8"));
            return new String(Base64.getEncoder().encode(hmacData), "UTF-8");
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException | InvalidKeyException e) {
            LOGGER.error("Error occured", e);
            return null;
        }
    }
}
