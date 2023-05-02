package com.ku.gymcrud.gymcrud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  @NotBlank(message = "Laukelis negali būti tuščias.")
  @Size(min = 3, max = 20, message = "Naudotojo vardas turi būti sudarytas iš bent 3 simbolių, bet nedaugiau, kaip 20.")
  private String username;

  @Column(nullable = false)
  @NotBlank(message = "Laukelis negali būti tuščias.")
  @Size(min = 6, max = 128, message = "Slaptažodis turi būti sudarytas iš bent 6 simbolių, bet nedaugiau, kaip 128.")
  private String password;

  @Column(nullable = false)
  @NotBlank(message = "Laukelis negali būti tuščias.")
  private String type = "client";

  @Column(nullable = false)
  @NotBlank(message = "Laukelis negali būti tuščias.")
  @Size(min = 3, max = 20, message = "Vardas turi būti sudarytas iš bent 3 simbolių, bet nedaugiau, kaip 20.")
  private String name;

  @Column(nullable = false)
  @NotBlank(message = "Laukelis negali būti tuščia.")
  @Size(min = 3, max = 25, message = "Pavardė turi būti sudaryta iš bent 3 simbolių, bet nedaugiau, kaip 25.")
  private String surname;

  @Column(nullable = false)
  @NotBlank(message = "Laukelis negali būti tuščias.")
  @Email(message = "Įvestas netinkamas el. pašto adresas")
  private String email;

  @Column
  @Size(max = 15, message = "Telefono numeris gali būti sudarytas iš ne daugiau, kaip 15 simbolių.")
  private String phone;

  @Column(length = 128)
  @Value("${agreement:null}")
  private String agreement = null;

  @OneToMany(mappedBy = "client")
  private List<Registration> registrations;

  public List<Registration> getRegistrations() {
    return registrations;
  }

  public void setRegistrations(List<Registration> registrations) {
    this.registrations = registrations;
  }

  public Client() {
  }

  public Client(String username, String password, String type) {
    this.username = username;
    this.password = password;
    this.type = type;
  }

  public Client(String name, String surname, String email, String phone) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.phone = phone;
  }

  public Client(String username, String password, String type, String name, String surname, String email, String phone) {
    this.username = username;
    this.password = password;
    this.type = type;
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.phone = phone;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getAgreement() {
    return agreement;
  }

  public void setAgreement(String agreement) {
    this.agreement = agreement;
  }

  public String getType() {
    return type;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    HashSet<GrantedAuthority> authorities = new HashSet<>();
    authorities.add(new SimpleGrantedAuthority(this.type));
    return authorities;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
