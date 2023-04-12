package com.ku.gymcrud.gymcrud.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name="clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @NotBlank(message = "Laukelis negali būti tuščias.")
    @Size(min = 3, max = 20, message = "Vardas turi būti sudarytas iš bent 3 simbolių, bet nedaugiau, kaip 20.")
    private String name;

    @Column
    @NotBlank(message = "Laukelis negali būti tuščia.")
    @Size(min = 3, max = 25, message = "Pavardė turi būti sudaryta iš bent 3 simbolių, bet nedaugiau, kaip 25.")
    private String surname;

    @Column
    @NotBlank(message = "Laukelis negali būti tuščias.")
    @Email(message = "Įvestas netinkamas el. pašto adresas")
    private String email;

    @Column
    @Size(max = 15, message = "Telefono numeris gali būti sudarytas iš ne daugiau, kaip 15 simbolių.")
    private String phone;

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

    public Client(String name, String surname, String email, String phone) {
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
}
