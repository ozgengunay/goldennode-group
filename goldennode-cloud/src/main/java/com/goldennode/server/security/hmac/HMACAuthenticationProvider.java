package com.goldennode.server.security.hmac;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Base64;
import com.goldennode.server.security.GoldenNodeUser;

public class HMACAuthenticationProvider extends AbstractProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(HMACAuthenticationProvider.class);
    @Autowired
    private HMACUserDetailsService hmacUerDetailsService;
    private String algorithm = "HmacSHA256";

    /**
     * This is the method which actually performs the check to see whether the user is indeed the correct user
     * 
     * @param userDetails
     * @param authentication
     * @throws AuthenticationException
     */
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, HMACAuthenticationToken authentication) throws AuthenticationException {
        if (authentication != null) {
            if (authentication.getCredentials() == null) {
                LOGGER.debug("Authentication failed: no credentials provided");
                throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
            HMACCredentials restCredentials = (HMACCredentials) authentication.getCredentials();
            if (!restCredentials.getSignature().equals(generateSignature(restCredentials.getRequestData(), ((GoldenNodeUser) userDetails).getPassword()))) {
                LOGGER.debug("Authentication failed: password does not match stored value");
                throw new BadCredentialsException(messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        } else {
            throw new AuthenticationCredentialsNotFoundException(MessageFormat.format("Expected Authentication Token object of type {0}, but instead received {1}",
                    UsernamePasswordAuthenticationToken.class.getSimpleName(), authentication.getClass().getSimpleName()));
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, HMACAuthenticationToken authentication) throws AuthenticationException {
        LOGGER.debug("Loading user by apikey>" + username);
        UserDetails loadedUser = hmacUerDetailsService.loadUserByUsername(username);
        LOGGER.debug("Loaded user>" + loadedUser);
        return loadedUser;
    }

    public String generateSignature(String data, String secretKey) throws IllegalArgumentException {
        byte[] hmacData = null;
        if (secretKey != null) {
            try {
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes("UTF-8"), this.algorithm);
                Mac mac = Mac.getInstance(algorithm);
                mac.init(secretKeySpec);
                hmacData = mac.doFinal(data.getBytes("UTF-8"));
                return new String(Base64.encode(hmacData), "UTF-8");
            } catch (InvalidKeyException ike) {
                throw new RuntimeException("Invalid Key while encrypting.", ike);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException("Unsupported Encoding while encrypting.", e);
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
            }
        }
        return "";
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (HMACAuthenticationToken.class.isAssignableFrom(authentication));
    }
}