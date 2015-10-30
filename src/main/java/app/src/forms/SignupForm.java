package app.src.forms;

/**
 * Created by Mihkel on 27.10.2015.
 */

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.social.connect.UserProfile;

public class SignupForm {

    @NotEmpty
    private String name;

    private String email;

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static SignupForm fromProviderUser(UserProfile providerUser) {
        SignupForm form = new SignupForm();
        form.setEmail(providerUser.getEmail());
        form.setName(providerUser.getName());
        return form;
    }
}