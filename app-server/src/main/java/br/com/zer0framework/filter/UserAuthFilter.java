package br.com.zer0framework.filter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.Statement;

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

//@WebFilter(filterName = "userAuthFilter", urlPatterns = "/api/*")
public class UserAuthFilter implements Filter {
	static Statement consulta = null;
	static ResultSet tabela = null;
	protected FilterConfig filterConfig;

	public void init(FilterConfig filterConfig) {
		this.filterConfig = filterConfig;
	} 

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException{
    		final HttpServletResponse res = (HttpServletResponse)response;
		final HttpServletRequest req = (HttpServletRequest)request;

		String token = req.getHeader(HttpHeaders.AUTHORIZATION);
		if (token == null) {
			token = request.getParameter("token");
		}

		// TODO Validate Token
		if (token == null || token.trim().isEmpty()){
	        res.setStatus(401);
	        return;
	    } else { 
			// user:milliseconds
			String decrypted = SecurityUtil.decryptor(token);
			String[] splited = decrypted.split(":");
			String username = splited[0];

			request.getServletContext().setAttribute("username", username);
	    }
		filterChain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}
}