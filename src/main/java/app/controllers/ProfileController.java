package app.controllers;

import app.models.User;
import app.src.forms.EntryForm;
import app.src.repositories.EntryRepository;
import app.src.entry.EntrySaver;
import app.src.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import javax.validation.Valid;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    /**
     * Facebook instance
     */
    private Facebook facebook;

    @Autowired
    private EntryRepository entryRepository;

    @Autowired
    private UserRepository userRepository;

    @Inject
    public ProfileController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping( method = RequestMethod.GET)
    public String profile(EntryForm entryForm) {

        if (!facebook.isAuthorized()) {
            return "index";
        }
        return "profile/profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String profileSubmit(@Valid EntryForm entryForm, BindingResult bindingResult) {
        if (!facebook.isAuthorized()) {
            return "index";
        }

        if (bindingResult.hasErrors()) {
            return "profile/profile";
        }
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByEmail(principal.toString());

        (new EntrySaver()).setEntryRepository(this.entryRepository).save(entryForm, user.getId());

        return "redirect:/";
    }
}