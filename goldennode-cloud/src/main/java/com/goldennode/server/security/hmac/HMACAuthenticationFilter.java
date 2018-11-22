package com.goldennode.server.security.hmac;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

public class HMACAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	protected HMACAuthenticationFilter(String defaultFilterProcessesUrl) {
		super(defaultFilterProcessesUrl);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException, IOException, ServletException {
		RequestWrapper requestWrapper = new RequestWrapper(request);

		String authorizationHeader = getHeaderValue(request, "Authorization");
		String signatureHeader = getHeaderValue(request, "Signature");
		String publicKey;
		if (authorizationHeader.equals("")) {
			throw new InsufficientAuthenticationException("Invalid Authorization Header");
		}
		if (signatureHeader.equals("")) {
			throw new BadCredentialsException("Invalid Signature Header");
		}
		String[] values = authorizationHeader.split(",");
		if (values.length < 2)
			throw new InsufficientAuthenticationException("Invalid Authorization Header");
		publicKey = values[0].split("=")[1];
		AbstractAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(publicKey, new HMACCredentials(
				authorizationHeader + request.getRequestURI() + requestWrapper.getPayload(), signatureHeader));

		// Allow subclasses to set the "details" property
		setDetails(request, authRequest);
		request.setAttribute("requestWrapper", requestWrapper);
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter((RequestWrapper)request.getAttribute("requestWrapper"), response);
	}

	private String getHeaderValue(HttpServletRequest request, String headerParameterName) {
		return (request.getHeader(headerParameterName) != null) ? request.getHeader(headerParameterName) : "";
	}

	/**
	 * Provided so that subclasses may configure what is put into the
	 * authentication request's details property.
	 *
	 * @param request
	 *            that an authentication request is being created for
	 * @param authRequest
	 *            the authentication request object that should have its details
	 *            set
	 */
	protected void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	@Override
	/**
	 * Because we require the API client to send credentials with every request,
	 * we must authenticate on every request
	 */
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}
}
