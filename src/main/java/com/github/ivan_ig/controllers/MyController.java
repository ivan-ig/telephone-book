package com.github.ivan_ig.controllers;

import com.github.ivan_ig.dao.PersonDAO;
import com.github.ivan_ig.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Controller
@RequestMapping("/contacts")
public class MyController {

    private final PersonDAO personDAO;

    @Autowired
    public MyController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("contacts", personDAO.index());
        return "contacts/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        if(personDAO.show(id).getPhoneNumber() == null && personDAO.show(id).getEmail() == null) {
            return "contacts/empty";
        }
        model.addAttribute("person", personDAO.show(id));
        return "contacts/person";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("newPerson") Person person) {
        return "contacts/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("newPerson") @Valid Person person,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "contacts/new";
        }
        personDAO.save(person);
        return "redirect:/contacts";
    }

    @PatchMapping("/{id}")
    public String patch(@PathVariable("id") int id,
                        @ModelAttribute("editPerson") @Valid Person person,
                        BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "contacts/edit";
        }
        personDAO.edit(person, id);
        return "redirect:/contacts";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("editPerson", personDAO.show(id));
        return "contacts/edit";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect:/contacts";
    }

}
