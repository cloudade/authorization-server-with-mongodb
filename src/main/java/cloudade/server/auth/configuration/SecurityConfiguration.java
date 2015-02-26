package cloudade.server.auth.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import cloudade.server.auth.mongo.user.MongoUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired private MongoUserDetailsManager mongoUserDetailsManager;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/js/**").antMatchers("/css/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		// @formatter:off
		http
		.authorizeRequests()
			.antMatchers("/login", "/logout.do", "/api/**").permitAll()
			.antMatchers("/**").authenticated()
		.and()
			.formLogin()
			.loginProcessingUrl("/login.do")
			.usernameParameter("name")
			.loginPage("/login")
		.and()
			.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout.do")) // AntPathRequestMatcher for GET request
		.and()
		.userDetailsService(mongoUserDetailsManager);
		// @formatter:on
	}

}
