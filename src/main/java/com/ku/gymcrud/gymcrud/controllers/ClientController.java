package com.ku.gymcrud.gymcrud.controllers;

import com.ku.gymcrud.gymcrud.entities.Client;
import com.ku.gymcrud.gymcrud.entities.Registration;
import com.ku.gymcrud.gymcrud.repositories.ClientRepository;
import com.ku.gymcrud.gymcrud.repositories.RegistrationRepository;
import com.ku.gymcrud.gymcrud.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@Validated
public class ClientController {
  @Autowired
  public ClientRepository clientRepository;

  @Autowired
  public RegistrationRepository registrationRepository;

  @Autowired
  public FileStorageService fileStorageService;

  @GetMapping("/")
  public String clients(Model model) {
    List<Client> clientList = clientRepository.findAll();
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
          BindingResult result,
          @RequestParam("agreementFile") MultipartFile agreement
  ) {
    if (result.hasErrors()) {
      return "client_new";
    }
    Client new_client = new Client(client.getName(), client.getSurname(), client.getEmail(), client.getPhone());
    if (!agreement.isEmpty()) {
      new_client.setAgreement(agreement.getOriginalFilename());
    }
    clientRepository.save(new_client);
    if (!agreement.isEmpty()) {
      fileStorageService.store(agreement, new_client.getId().toString());
    }
    return "redirect:/";
  }

  @GetMapping("/client/{id}/agreement")
  @ResponseBody
  public ResponseEntity<Resource> getAgreementFile(@PathVariable Integer id) {
    Client client = clientRepository.getReferenceById(id);
    Resource resource = fileStorageService.loadFile(client.getId().toString());
    return ResponseEntity
            .ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + client.getAgreement() + "\"")
            .body(resource);
  }

  @GetMapping("/update/{id}")
  public String updateClientGet(
          Model model,
          @PathVariable("id") Integer id
  ) {
    Client client = clientRepository.getReferenceById(id);
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
      Client client_update = clientRepository.getReferenceById(id);
      client_update.setName(client.getName());
      client_update.setSurname(client.getSurname());
      client_update.setEmail(client.getEmail());
      client_update.setPhone(client.getPhone());
      clientRepository.save(client_update);
      return "redirect:/";
    }
  }

  @GetMapping("/delete/{id}")
  public String deleteClient(
          Model model,
          @PathVariable("id") Integer id
  ) {
    Client client = clientRepository.getReferenceById(id);
    for (Registration r : client.getRegistrations()) {
      registrationRepository.deleteById(r.getId());
    }
    clientRepository.deleteById(id);
    return "redirect:/";
  }
}
