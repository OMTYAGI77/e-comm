package com.one.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.one.aim.constants.ErrorCodes;
import com.one.constants.StringConstants;
import com.one.service.impl.UserDetailsServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			
			String path = request.getRequestURI();
			if (path.contains("/api/auth/signin")) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = getTokenFromRequest(request);
			if (StringUtils.hasText(token) && jwtUtils.validateToken(token)) {
				String username = jwtUtils.getUsernameFromToken(token);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (ExpiredJwtException ex) {
			String isRefreshToken = request.getHeader("isRefreshToken");
			String requestURL = request.getRequestURL().toString();
			if (isRefreshToken != null && isRefreshToken.equals("true") && requestURL.contains("refreshtoken")) {
				allowForRefreshToken(ex, request);
			} else
				request.setAttribute("exception", ex);
			request.setAttribute(StringConstants.JWT_INVALID_TOKEN, ErrorCodes.EC_INVALID_TOKEN);
		} catch (BadCredentialsException ex) {
			request.setAttribute(StringConstants.JWT_BAD_CREDENTIALS, ErrorCodes.EC_INVALID_TOKEN);
		} catch (Exception e) {
			log.error("Cannot set user authentication: {}", e);
			request.setAttribute(StringConstants.JWT_INVALID_TOKEN, ErrorCodes.EC_INVALID_TOKEN);
		}
		filterChain.doFilter(request, response);
	}

	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		request.setAttribute("claims", ex.getClaims());
	}

	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}