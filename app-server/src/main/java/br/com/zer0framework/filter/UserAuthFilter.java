package br.com.zer0framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.zer0framework.utils.security.SecurityUtil;
import br.com.zer0framework.utils.HttpHeaders;

// TODO RegisterUserSevlet

@WebFilter(filterName = "userAuthFilter", urlPatterns = "/api/*")
public class UserAuthFilter implements Filter {
	protected FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	} 

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		final HttpServletResponse res = (HttpServletResponse) response;
		final HttpServletRequest req = (HttpServletRequest) request;

		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (token == null) {
			token = request.getParameter("token");
		}

		if (token == null || token.trim().isEmpty()) {
			res.setStatus(401);
			return;
		}

		try {
            if (SecurityUtil.validateToken(token)) {
                res.setStatus(200);
                filterChain.doFilter(request, response);
            } else {
                res.setStatus(401);
            }
        }catch (NumberFormatException e){
		    res.setStatus(401);
        }

	}
	@Override
	public void destroy() {

	}
}