package com.fitness.controller;

import com.fitness.entity.Exercise;
import com.fitness.repository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@Controller
public class ExerciseController {
    
    @Autowired
    private ExerciseRepository exerciseRepository;
    
    @GetMapping("/elements/{slug}")
    public String showExercise(@PathVariable String slug, Model model) {
        Optional<Exercise> exerciseOpt = exerciseRepository.findBySlug(slug);
        if (exerciseOpt.isEmpty()) {
            return "redirect:/";
        }
        model.addAttribute("exercise", exerciseOpt.get());
        model.addAttribute("allExercises", exerciseRepository.findAll());
        return "exercise-detail";
    }
}
