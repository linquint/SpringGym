package com.ku.gymcrud.gymcrud.controllers;

import com.ku.gymcrud.gymcrud.entities.Client;
import com.ku.gymcrud.gymcrud.entities.Registration;
import com.ku.gymcrud.gymcrud.repositories.GymRepository;
import com.ku.gymcrud.gymcrud.repositories.RegistrationRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Validated
public class ClientController {
    @Autowired
    public GymRepository gymRepository;

    @Autowired
    public RegistrationRepository registrationRepository;

    @GetMapping("/")
    public String clients(Model model) {
        List<Client> clientList = gymRepository.findAll();
        model.addAttribute("clients", clientList);
        return "client_list";
    }

    @GetMapping("/new")
    public String addClientGet(Model model) {
        model.addAttribute("client", new Client());
        return "client_new";
    }

    @PostMapping("/new")
    public String addClient(
            @Validated @ModelAttribute("client") Client client,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "client_new";
        }
        Client new_client = new Client(client.getName(), client.getSurname(), client.getEmail(), client.getPhone());
        gymRepository.save(new_client);
        return "redirect:/";
    }

    @GetMapping("/update/{id}")
    public String updateClientGet(
            Model model,
            @PathVariable("id") Integer id
    ) {
        Client client = gymRepository.getReferenceById(id);
        model.addAttribute("client", client);
        return "client_update";
    }

    @PostMapping("/update/{id}")
    public String updateClientPost(
            Model model,
            @PathVariable("id") Integer id,
            @Validated @ModelAttribute("client") Client client,
            BindingResult result
    ) {
        if (result.hasErrors()) {
            return "client_update";
        } else {
            Client client_update = gymRepository.getReferenceById(id);
            client_update.setName(client.getName());
            client_update.setSurname(client.getSurname());
            client_update.setEmail(client.getEmail());
            client_update.setPhone(client.getPhone());
            gymRepository.save(client_update);
            return "redirect:/";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteClient(
            Model model,
            @PathVariable("id") Integer id
    ) {
        Client client = gymRepository.getReferenceById(id);
        for (Registration r:client.getRegistrations()) {
            registrationRepository.deleteById(r.getId());
        }
        gymRepository.deleteById(id);
        return "redirect:/";
    }
}
