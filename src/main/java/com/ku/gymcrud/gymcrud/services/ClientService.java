package com.ku.gymcrud.gymcrud.services;

import com.ku.gymcrud.gymcrud.entities.Client;
import com.ku.gymcrud.gymcrud.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ClientService implements UserDetailsService {
  @Autowired
  ClientRepository clientRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Client client = clientRepository.findByUsername(username);
    if (client == null) {
      throw new UsernameNotFoundException("Naudotojas nerastas");
    }
    return client;
  }
}
