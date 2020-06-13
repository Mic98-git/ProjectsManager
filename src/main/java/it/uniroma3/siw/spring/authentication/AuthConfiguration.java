package it.uniroma3.siw.spring.authentication;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.WebSecurityEnablerConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import it.uniroma3.siw.spring.model.Credentials;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	DataSource dataSource;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			
			.antMatchers(HttpMethod.GET, "/","/index","/login","/users/register").permitAll()
			
			.antMatchers(HttpMethod.POST, "/login","/users/register").permitAll()
			
			.antMatchers(HttpMethod.GET, "/admin").hasAnyAuthority(Credentials.ADMIN_ROLE)
			
			.anyRequest().authenticated()
			
			.and().formLogin()
			
			.defaultSuccessUrl("/home")
			
			.and().logout()
			
			.logoutUrl("/logout")
			
			.logoutSuccessUrl("/index");
	}
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication()
			.dataSource(this.dataSource)
			.authoritiesByUsernameQuery("SELECT user_name,role FROM credentials WHERE user_name=?")
			.usersByUsernameQuery("SELECT user_name, password, 1 as enabled FROM credentials WHERE user_name=?");
	}
	
	@Bean
	PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }
}
