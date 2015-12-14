package app.controllers;

import app.models.DailyEntry;
import app.models.User;
import app.src.entry.*;
import app.src.forms.EntryForm;
import app.src.helpers.DateTimeHelper;
import app.src.repositories.EntryRepository;
import app.src.repositories.user.UserRepository;
import app.src.user.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    /**
     * Facebook instance
     */
    private Facebook facebook;

    @Autowired
    public EntryRepository entryRepository;

    @Autowired
    private UserRepository userRepository;

    @Inject
    public ProfileController(Facebook facebook) {
        this.facebook = facebook;
    }

    @RequestMapping( method = RequestMethod.GET)
    public String profile(EntryForm entryForm, Model model, @RequestParam(value = "dailyAbsolute", required=false) Double dailyAbsolute) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (dailyAbsolute != null) {
            model.addAttribute("dailyFeedbackMessage", (new DailyFeedback().toString(dailyAbsolute)));
        }
        User user = userRepository.findByEmail(principal.toString());

        Profile profile = new Profile(user, entryRepository);
        Iterator it = profile.getProfileData().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            model.addAttribute(pair.getKey().toString(), pair.getValue());
            it.remove();
        }

        return "profile/profile";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String profileSubmit(@Valid EntryForm entryForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = userRepository.findByEmail(principal.toString());

            Profile profile = new Profile(user, entryRepository);
            Iterator it = profile.getProfileData().entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry)it.next();
                model.addAttribute(pair.getKey().toString(), pair.getValue());
                it.remove();
            }
            return "profile/profile";
        }

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User user = userRepository.findByEmail(principal.toString());
        try {
            DailyEntry entry = (new EntrySaver()).setEntryRepository(this.entryRepository).save(entryForm, user.getId());
            return "redirect:/profile?dailyAbsolute=" + entry.getAbsoluteAmount();
        } catch (DataIntegrityViolationException e) {
            e.getStackTrace();
            return "redirect:/profile?error=Invalid Entry";
        }
    }

    @RequestMapping ("/noAlcoholEntry")
    /**
     * Kui kasutaja ei tarbinud üldse eile alkoholi?? Kas see saab üldse juhtuda?
     */
    public String noAlcoholYesterday()
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(principal.toString());

        EntryForm entryForm = new EntryForm();
        entryForm.setRelativePercentage(0.);
        entryForm.setAmount(0);
        entryForm.setDate(DateTimeHelper.yesterday(LocalDateTime.now()));
        (new EntrySaver()).setEntryRepository(entryRepository).save(entryForm, user.getId());
        return "redirect:/profile?dailyAbsolute=0";
    }

    @RequestMapping ("/deleteEntry")
    public String deleteEntry(@RequestParam(value = "id") int id)
    {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(principal.toString());

        entryRepository.deleteByUserIdAndId(user.getId(), id);
        return "redirect:/profile?entryDeleted=true";
    }
}