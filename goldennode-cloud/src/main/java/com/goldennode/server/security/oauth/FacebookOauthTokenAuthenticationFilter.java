package com.goldennode.server.security.oauth;

import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.security.SocialAuthenticationException;
import org.springframework.social.security.SocialAuthenticationFailureHandler;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.provider.SocialAuthenticationService;
import org.springframework.social.support.URIBuilder;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.goldennode.commons.entity.Users;
import com.goldennode.commons.entity.Users.SocialMediaService;
import com.goldennode.server.security.hmac.filter.NoRedirectStrategy;
import com.goldennode.server.security.social.RegistrationForm;
import com.goldennode.server.security.social.service.DuplicateEmailException;
import com.goldennode.server.security.social.service.UserService;

public class FacebookOauthTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private static final Logger LOGGER = LoggerFactory.getLogger(FacebookOauthTokenAuthenticationFilter.class);
	private SocialAuthenticationServiceLocator authServiceLocator;

	private boolean updateConnections = true;

	private UserIdSource userIdSource;

	private UsersConnectionRepository usersConnectionRepository;

	private SimpleUrlAuthenticationFailureHandler delegateAuthenticationFailureHandler;

	private static final String providerId = "facebook";
	@Autowired
	private UserService service;

	public FacebookOauthTokenAuthenticationFilter(AuthenticationManager authManager, UserIdSource userIdSource,
			UsersConnectionRepository usersConnectionRepository,
			SocialAuthenticationServiceLocator authServiceLocator) {
		super("/");
		setAuthenticationManager(authManager);
		this.userIdSource = userIdSource;
		this.usersConnectionRepository = usersConnectionRepository;
		this.authServiceLocator = authServiceLocator;
		this.delegateAuthenticationFailureHandler = new SimpleUrlAuthenticationFailureHandler();
		super.setAuthenticationFailureHandler(
				new SocialAuthenticationFailureHandler(delegateAuthenticationFailureHandler));
		SimpleUrlAuthenticationSuccessHandler sas = new SimpleUrlAuthenticationSuccessHandler();
		sas.setRedirectStrategy(new NoRedirectStrategy());
		super.setAuthenticationSuccessHandler(sas);
	}

	public void setUpdateConnections(boolean updateConnections) {
		this.updateConnections = updateConnections;
	}

	public UsersConnectionRepository getUsersConnectionRepository() {
		return usersConnectionRepository;
	}

	public SocialAuthenticationServiceLocator getAuthServiceLocator() {
		return authServiceLocator;
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		Authentication auth = null;
		Set<String> authProviders = authServiceLocator.registeredAuthenticationProviderIds();
		if (!authProviders.isEmpty() && authProviders.contains(providerId)) {
			SocialAuthenticationService<?> authService = authServiceLocator.getAuthenticationService(providerId);
			auth = attemptAuthService(authService, request, response);
			if (auth == null) {
				throw new AuthenticationServiceException("authentication failed");
			}
		}
		return auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

	@Deprecated
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		Authentication aut = SecurityContextHolder.getContext().getAuthentication();
		return (aut == null || !aut.isAuthenticated()) && request.getParameter("input_token") != null;
	}

	protected Connection<?> addConnection(SocialAuthenticationService<?> authService, String userId,
			ConnectionData data) {
		HashSet<String> userIdSet = new HashSet<String>();
		userIdSet.add(data.getProviderUserId());
		Set<String> connectedUserIds = usersConnectionRepository.findUserIdsConnectedTo(data.getProviderId(),
				userIdSet);
		if (connectedUserIds.contains(userId)) {
			// already connected
			return null;
		} else if (!authService.getConnectionCardinality().isMultiUserId() && !connectedUserIds.isEmpty()) {
			return null;
		}

		ConnectionRepository repo = usersConnectionRepository.createConnectionRepository(userId);

		if (!authService.getConnectionCardinality().isMultiProviderUserId()) {
			List<Connection<?>> connections = repo.findConnections(data.getProviderId());
			if (!connections.isEmpty()) {
				// TODO maybe throw an exception to allow UI feedback?
				return null;
			}
		}

		// add new connection
		Connection<?> connection = authService.getConnectionFactory().createConnection(data);
		connection.sync();
		repo.addConnection(connection);
		return connection;
	}

	private Authentication attemptAuthService(final SocialAuthenticationService<?> authService,
			final HttpServletRequest request, HttpServletResponse response)
			throws SocialAuthenticationRedirectException, AuthenticationException {
		String token = request.getParameter("input_token");
		if (token == null)
			throw new SocialAuthenticationException("Token is not valid");

		URIBuilder builder = URIBuilder.fromUri(String.format("%s/debug_token", "https://graph.facebook.com"));

		builder.queryParam("access_token", "454667354883206|31ed3c54e9ad0f717f7fc2691a8be442");
		builder.queryParam("input_token", token);

		URI uri = builder.build();
		RestTemplate restTemplate = new RestTemplate();
		JsonNode resp = restTemplate.getForObject(uri, JsonNode.class);

		Boolean isValid = resp.path("data").findValue("is_valid").asBoolean();

		if (!isValid)
			throw new SocialAuthenticationException("Token is not valid");

		AccessGrant accessGrant = new AccessGrant(token, null, null,
				resp.path("data").findValue("expires_at").longValue());

		Connection<?> connection = ((OAuth2ConnectionFactory<?>) authService.getConnectionFactory())
				.createConnection(accessGrant);
		SocialAuthenticationToken tok = new SocialAuthenticationToken(connection, null);
		Assert.notNull(tok.getConnection());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) {
			return doAuthentication(authService, request, tok);
		} else {
			addConnection(authService, request, tok);
			return null;
		}
	}

	private void addConnection(final SocialAuthenticationService<?> authService, HttpServletRequest request,
			SocialAuthenticationToken token) {
		// already authenticated - add connection instead
		String userId = userIdSource.getUserId();
		Object principal = token.getPrincipal();
		if (userId == null || !(principal instanceof ConnectionData))
			return;

		addConnection(authService, userId, (ConnectionData) principal);

	}

	private Authentication doAuthentication(SocialAuthenticationService<?> authService, HttpServletRequest request,
			SocialAuthenticationToken token) {
		try {
			if (!authService.getConnectionCardinality().isAuthenticatePossible())
				return null;
			token.setDetails(authenticationDetailsSource.buildDetails(request));
			Authentication success = getAuthenticationManager().authenticate(token);
			Assert.isInstanceOf(SocialUserDetails.class, success.getPrincipal(), "unexpected principle type");
			updateConnections(authService, token, success);
			return success;
		} catch (BadCredentialsException e) {

			RegistrationForm registration = createRegistrationDTO(token.getConnection());
			Users registered;
			try {
				registered = service.registerNewUserAccount(registration);
			} catch (DuplicateEmailException e1) {
				throw new SocialAuthenticationException("An email address was found from the database.");
			}
			ConnectionRepository repo = usersConnectionRepository.createConnectionRepository(registered.getEmail());
			repo.addConnection(token.getConnection());
			Authentication success = getAuthenticationManager().authenticate(token);
			return success;

		}
	}

	private void updateConnections(SocialAuthenticationService<?> authService, SocialAuthenticationToken token,
			Authentication success) {
		if (updateConnections) {
			String userId = ((SocialUserDetails) success.getPrincipal()).getUserId();
			Connection<?> connection = token.getConnection();
			ConnectionRepository repo = getUsersConnectionRepository().createConnectionRepository(userId);
			repo.updateConnection(connection);
		}
	}

	private RegistrationForm createRegistrationDTO(Connection<?> connection) {
		RegistrationForm dto = new RegistrationForm();

		if (connection != null) {
			// UserProfile socialMediaProfile = connection.fetchUserProfile();
			Facebook facebook = (Facebook) connection.getApi();
			String[] fields = { "id", "email", "first_name", "last_name" };
			User socialMediaProfile = facebook.fetchObject("me", User.class, fields);

			dto.setEmail(socialMediaProfile.getEmail());
			dto.setFirstName(socialMediaProfile.getFirstName());
			dto.setLastName(socialMediaProfile.getLastName());
			ConnectionKey providerKey = connection.getKey();
			dto.setSignInProvider(SocialMediaService.valueOf(providerKey.getProviderId().toUpperCase()));
		}

		return dto;
	}

}
