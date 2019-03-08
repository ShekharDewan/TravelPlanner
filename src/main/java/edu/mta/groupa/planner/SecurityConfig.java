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

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private NewUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
	  throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder());
	}

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

	 @Bean
	 public AuthenticationManager customAuthenticationManager() throws Exception {
	     return authenticationManager();
	 }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	     auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
     
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}