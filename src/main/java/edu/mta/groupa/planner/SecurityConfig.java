package edu.mta.groupa.planner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import edu.mta.groupa.planner.service.NewUserDetailsService;
/**
 * This class configures Spring's security settings.
 * Login details are set, including which pages are viewable without login,
 * which password encoder to use, and how to process Users.
 * 
 * @author Jennifer & Maryse
 *
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	/**
	 * Our overridden version of Spring's UserDetailsService.
	 */
	@Autowired
	private NewUserDetailsService userDetailsService;
	/**
	  * Registers password encoder with our overridden UserDetailsService
	  * and uses it when building the authentication manager.
	  * 
	  * @param auth	the AuthenticationManagerBuilder.
	  * @throws Exception
	  */
	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	/**
	 * Configures application security options.  Prevents users
	 * without authentication from accessing pages other than 
	 * the login and registration pages.
	 * Tells Spring which page to use for login and login success.
	 * Tells Spring which field to use as username.
	 * 
	 * @param http HttpSecurity.
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
				.antMatchers("/signup/**", "/h2/**",
						"/js/**",
						"/jsp/**",
						"/css/**",
						"/img/**",
						"/webjars/**").permitAll()
				.anyRequest().authenticated()
				.anyRequest().hasRole("USER")
				.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
				.usernameParameter("email")
				.defaultSuccessUrl("/", true)
				.and()
			.logout()
				.permitAll();

	        http.csrf().disable();
	        http.headers().frameOptions().disable();
	}
	/**
	 * Registers the AuthenticationManager as a Bean.
	 * 
	 * @return the AuthenticationManager.
	 * @throws Exception
	 */
	 @Bean
	 public AuthenticationManager customAuthenticationManager() throws Exception {
	     return authenticationManager();
	 }
	 /**
	  * Globally registers password encoder with our overridden UserDetailsService
	  * and uses it when building the authentication manager.
	  * 
	  * @param auth	the AuthenticationManagerBuilder.
	  * @throws Exception
	  */
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
     /**
      * Registers Spring's BCryptPasswordEncoder as a Bean
      * for password encoding.
      * 
      * @return the password encoder.
      */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}