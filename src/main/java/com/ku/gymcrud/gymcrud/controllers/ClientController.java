package com.ku.gymcrud.gymcrud.controllers;

import com.ku.gymcrud.gymcrud.entities.Client;
import com.ku.gymcrud.gymcrud.entities.Registration;
import com.ku.gymcrud.gymcrud.repositories.GymRepository;
import com.ku.gymcrud.gymcrud.repositories.RegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
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
        return "client_new";
    }

    @PostMapping("/new")
    public String addClient(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ) {
        Client client = new Client(name, surname, email, phone);
        gymRepository.save(client);
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
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("email") String email,
            @RequestParam("phone") String phone
    ) {
        Client client = gymRepository.getReferenceById(id);
        client.setName(name);
        client.setSurname(surname);
        client.setEmail(email);
        client.setPhone(phone);
        gymRepository.save(client);
        return "redirect:/";
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
