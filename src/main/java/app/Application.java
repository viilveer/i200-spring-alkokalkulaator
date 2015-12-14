package app;

/**
 * Created by Mihkel on 05.10.2015.
 */

import app.src.social.SocialSignInAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
public class Application {
    public static void main(String[] args) throws Exception{
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public SignInAdapter signInAdapter() {
        return new SocialSignInAdapter(new HttpSessionRequestCache());
    }
}
