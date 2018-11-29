package com.goldennode.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RestClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestClient.class);
    private static String publicKey;
    private static String secretKey;
    private static String SERVER_URL = "http://localhost:8080";
    static {
        publicKey = System.getenv("GN_PK");
        secretKey = System.getenv("GN_SK");
        if (publicKey == null) {
            publicKey = System.getProperty("com.goldennode.client.publicKey");
        }
        if (secretKey == null) {
            secretKey = System.getProperty("com.goldennode.client.secretKey");
        }
        if (publicKey == null) {
            throw new RuntimeException("can't load publicKey");
        }
        if (secretKey == null) {
            throw new RuntimeException("can't load secretKey");
        }
    }

    public static Response call(String uri, String method) throws GoldenNodeException {
        return call(uri, method, null);
    }

    public static Response call(String uri, String method, String body) throws GoldenNodeException {
        try {
            URL url = new URL(SERVER_URL + uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod(method);
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Accept-Charset", "UTF-8");
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

    public static void main(String arg[]) {
    }

    public static void signRequest(HttpURLConnection con, String body) {
        String auth = "key=" + publicKey + ",timestamp=" + System.currentTimeMillis() / 1000L + ",nonce=" + UUID.randomUUID().toString();
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
