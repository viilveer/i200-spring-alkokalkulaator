package app.components;

/**
 * Created by Mihkel on 20.10.2015.
 */

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @Inject
    private DataSource dataSource;

    @Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println(auth.toString());
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .usersByUsernameQuery("select email from user where email = ?")
            .authoritiesByUsernameQuery("select email, 'ROLE_USER' from user where email = ?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
            .ignoring()
            .antMatchers("/**/*.css", "/**/*.png", "/**/*.gif", "/**/*.jpg");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .formLogin()
            .loginPage("/")
            .loginProcessingUrl("/signin/authenticate")
            .and()
            .logout()
            .logoutUrl("/signout")
            .deleteCookies("JSESSIONID")
            .and()
            .authorizeRequests()
            .antMatchers("/admin/**", "/favicon.ico", "/resources/**", "/auth/**", "/signin/**", "/signup/**", "/disconnect/facebook", "/").permitAll()
            .antMatchers("/**").authenticated()
            .and()
            .rememberMe();
    }
}
