package app.controllers;


import javax.inject.Inject;
import javax.validation.Valid;

import app.src.forms.SignupForm;
import app.src.repositories.user.UserRepositoryInterface;
import app.src.social.SocialSignInUtils;
import app.src.user.exceptions.UserNameAlreadyInUseException;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInAttempt;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;

@Controller
public class SignupController {

    private final UserRepositoryInterface userRepository;
    private final ProviderSignInUtils providerSignInUtils;

    @Inject
    public SignupController(UserRepositoryInterface userRepository,
                            ConnectionFactoryLocator connectionFactoryLocator,
                            UsersConnectionRepository connectionRepository) {
        this.userRepository = userRepository;

        this.providerSignInUtils = new ProviderSignInUtils(connectionFactoryLocator, connectionRepository);
        System.out.println(connectionRepository.getClass());
    }

    @RequestMapping(value="/signup", method=RequestMethod.GET)
    public String signupForm(SignupForm signupForm, WebRequest request, Model model) {
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (connection != null) {
            signupForm = SignupForm.fromProviderUser(connection.fetchUserProfile());
            model.addAttribute("signupForm", signupForm);
            return "profile/signUp";
        }
        return "redirect:/";
    }

    @RequestMapping(value="/signup", method=RequestMethod.POST)
    public String signup(@Valid SignupForm signupForm, BindingResult formBinding, WebRequest request, Model model) {
        System.out.println(request.getAttribute(ProviderSignInAttempt.SESSION_ATTRIBUTE, RequestAttributes.SCOPE_SESSION));
        Connection<?> connection = providerSignInUtils.getConnectionFromSession(request);
        if (formBinding.hasErrors()) {
            return "profile/signUp";
        }
        if (connection != null) {
            try {
                signupForm.setEmail(connection.fetchUserProfile().getEmail());
                UserFactory.createFromSignupForm(userRepository, signupForm);
                SocialSignInUtils.signin(signupForm.getEmail());

                providerSignInUtils.doPostSignUp(signupForm.getEmail(), request);
                return "redirect:/";
            } catch (UserNameAlreadyInUseException e) {
                formBinding.rejectValue("username", "user.duplicateUsername", "already in use");
            }
        }
        model.addAttribute("signupForm", signupForm);

        return "profile/signUp";
    }



}
