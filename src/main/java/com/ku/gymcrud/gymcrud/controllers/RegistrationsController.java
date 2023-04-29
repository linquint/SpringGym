package com.ku.gymcrud.gymcrud.controllers;

import com.ku.gymcrud.gymcrud.entities.Client;
import com.ku.gymcrud.gymcrud.entities.Registration;
import com.ku.gymcrud.gymcrud.entities.Workouts;
import com.ku.gymcrud.gymcrud.repositories.ClientRepository;
import com.ku.gymcrud.gymcrud.repositories.RegistrationRepository;
import com.ku.gymcrud.gymcrud.repositories.WorkoutsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class RegistrationsController {
    @Autowired
    public RegistrationRepository registrationRepository;

    @Autowired
    public ClientRepository clientRepository;

    @Autowired
    public WorkoutsRepository workoutsRepository;

    @GetMapping("/registrations/{id}")
    public String clientRegistrations(
            Model model,
            @PathVariable("id") Integer id
    ) {
        Workouts workout = workoutsRepository.getReferenceById(id);
        List<Client> clients = clientRepository.findAll();
        List<Integer> registeredIds = new ArrayList<>();
        List<Client> availableClients = new ArrayList<>();

        for (Registration r:workout.getRegistrations()) {
            registeredIds.add(r.getClient().getId());
        }
        for (Client c:clients) {
            if (!registeredIds.contains(c.getId())) {
                availableClients.add(c);
            }
        }

        model.addAttribute("workout", workout);
        model.addAttribute("clients", availableClients);
        return "registrations";
    }

    @PostMapping("/registrations/{id}/add")
    public String addRegistration(
            Model model,
            @PathVariable("id") Integer id,
            @RequestParam("client_id") Integer client_id
    ) {
        Client client = clientRepository.getReferenceById(client_id);
        Workouts workout = workoutsRepository.getReferenceById(id);
        Registration registration = new Registration(client, workout);
        registrationRepository.save(registration);
        return "redirect:/registrations/{id}";
    }

    @GetMapping("/registrations/{workout_id}/delete/{registration_id}")
    public String deleteRegistration(
            Model model,
            @PathVariable("workout_id") Integer id,
            @PathVariable("registration_id") Integer reg_id
    ) {
        registrationRepository.deleteById(reg_id);
        return "redirect:/registrations/{workout_id}";
    }
}
