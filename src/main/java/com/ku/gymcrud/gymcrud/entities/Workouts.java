package com.ku.gymcrud.gymcrud.entities;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name="workouts")
public class Workouts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String name;

    @Temporal(TemporalType.DATE)
    private Date date;

    @Column
    private Integer places;

    @Column(columnDefinition = "TEXT")
    private String location;

    @OneToMany(mappedBy = "workout")
    private List<Registration> registrations;

    public List<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(List<Registration> registrations) {
        this.registrations = registrations;
    }

    public Workouts() {
    }

    public Workouts(String name, Date date, Integer places, String location) {
        this.name = name;
        this.date = date;
        this.places = places;
        this.location = location;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getPlaces() {
        return places;
    }

    public void setPlaces(Integer places) {
        this.places = places;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
