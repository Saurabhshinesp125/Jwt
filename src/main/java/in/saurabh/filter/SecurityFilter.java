package in.saurabh.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import in.saurabh.utils.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{

	private JwtUtils jwtUtil;
	private UserDetailsService detailsService;
	
	
	public SecurityFilter(JwtUtils jwtUtil, UserDetailsService detailsService) {
		super();
		this.jwtUtil = jwtUtil;
		this.detailsService = detailsService;
	}

     @Override
	protected void doFilterInternal(
			HttpServletRequest request,
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {
    	 
		// read Token from request header
		String token= request.getHeader("Authorization");
		if(token!=null) {
			//validate read the subject from token
			String username = jwtUtil.getUsername(token);
			
			//check User details
			if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
			{
           // load user from DB
				UserDetails user = detailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = 
						new UsernamePasswordAuthenticationToken(username,user.getPassword(),user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		        SecurityContextHolder.getContext().setAuthentication(authentication);
				
		    }
	  }

		filterChain.doFilter(request,response);
     }
     
}
