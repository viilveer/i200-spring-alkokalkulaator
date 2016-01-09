package app.components;

/**
 * Created by Mihkel on 20.10.2015.
 */

import javax.inject.Inject;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.security.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableWebSecurity
public class SecurityContext extends WebSecurityConfigurerAdapter {

    @Inject
    private DataSource dataSource;

    @Autowired
    public void registerAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select email from user where email = ?")
                .authoritiesByUsernameQuery("select email, 'ROLE_USER' from user where email = ?");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/**/*.css","/**/*.js", "/**/*.map", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/javax.faces.resource/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .apply(getSpringSocialConfigurer())
                .and()
                .formLogin()
                .loginPage("/")
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .and()
                .authorizeRequests()
                .antMatchers("/admin/**", "/favicon.ico", "/resources/**", "/auth/**", "/signin/**", "/signup/**", "/disconnect/facebook", "/").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .rememberMe()
                .and()
                .csrf().disable();
    }

    private SpringSocialConfigurer getSpringSocialConfigurer() {
        SpringSocialConfigurer config = new SpringSocialConfigurer();
        config.alwaysUsePostLoginUrl(true);
        config.postLoginUrl("/profile?login=true");

        return config;
    }

    @Bean
    public SocialUserDetailsService socialUserDetailsService() {
        return new SimpleSocialUserDetailsService(userDetailsService());
    }
}
