package com.ku.gymcrud.gymcrud.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "registration")
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "workout_id")
    private Workouts workout;

    @Temporal(TemporalType.DATE)
    private LocalDate registration_date;

    @PrePersist
    protected void onCreate() {
        registration_date = LocalDate.now();
    }

    public Registration() {
    }

    public Registration(Client client, Workouts workout) {
        this.client = client;
        this.workout = workout;
    }

    public Registration(Client client, Workouts workout, LocalDate registration_date) {
        this.client = client;
        this.workout = workout;
        this.registration_date = registration_date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Workouts getWorkout() {
        return workout;
    }

    public void setWorkout(Workouts workout) {
        this.workout = workout;
    }

    public LocalDate getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(LocalDate registration_date) {
        this.registration_date = registration_date;
    }
}
