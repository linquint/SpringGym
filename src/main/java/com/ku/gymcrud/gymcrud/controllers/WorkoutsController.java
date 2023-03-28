package com.ku.gymcrud.gymcrud.controllers;

import com.ku.gymcrud.gymcrud.entities.Registration;
import com.ku.gymcrud.gymcrud.entities.Workouts;
import com.ku.gymcrud.gymcrud.repositories.RegistrationRepository;
import com.ku.gymcrud.gymcrud.repositories.WorkoutsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.List;

@Controller
public class WorkoutsController {
    @Autowired
    public WorkoutsRepository workoutsRepository;

    @Autowired
    public RegistrationRepository registrationRepository;

    @GetMapping("/workouts")
    public String displayWorkouts(Model model) {
        List<Workouts> workoutList = workoutsRepository.findAll();
        model.addAttribute("workouts", workoutList);
        return "workouts_list";
    }

    @GetMapping("/workouts/new")
    public String addWorkoutGet(Model model) {
        return "workout_new";
    }

    @PostMapping("/workouts/new")
    public String addWorkout(
            Model model,
            @RequestParam("name") String name,
            @RequestParam("date") Date date,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location
    ) {
        Workouts workout = new Workouts(name, date, places, location);
        workoutsRepository.save(workout);
        return "redirect:/workouts";
    }

    @GetMapping("/workouts/update/{id}")
    public String updateWorkoutGet(
            Model model,
            @PathVariable("id") Integer id
    ) {
        Workouts workout = workoutsRepository.getReferenceById(id);
        model.addAttribute("workout", workout);
        return "workout_update";
    }

    @PostMapping("/workouts/update/{id}")
    public String updateWorkout(
            Model model,
            @PathVariable("id") Integer id,
            @RequestParam("name") String name,
            @RequestParam("date") Date date,
            @RequestParam("places") Integer places,
            @RequestParam("location") String location
    ) {
        Workouts workout = workoutsRepository.getReferenceById(id);
        workout.setName(name);
        workout.setDate(date);
        workout.setPlaces(places);
        workout.setLocation(location);
        workoutsRepository.save(workout);
        return "redirect:/workouts";
    }

    @GetMapping("/workouts/delete/{id}")
    public String deleteWorkout(
            Model model,
            @PathVariable("id") Integer id
    ) {
        Workouts workout = workoutsRepository.getReferenceById(id);
        for (Registration r:workout.getRegistrations()) {
            registrationRepository.deleteById(r.getId());
        }
        workoutsRepository.deleteById(id);
        return "redirect:/workouts";
    }
}
