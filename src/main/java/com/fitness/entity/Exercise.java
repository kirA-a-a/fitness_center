package com.fitness.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "exercises")
public class Exercise {

    @Id
    @Column(columnDefinition = "varchar(36) NOT NULL DEFAULT gen_random_uuid()::text")
    private String id;

    @PrePersist
    protected void onCreate() {
        if (id == null) {
            id = java.util.UUID.randomUUID().toString();
        }
    }

    @Column(nullable = false, unique = true)
    private String slug;
    
    @Column(nullable = false)
    private String name;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "execution_technique", columnDefinition = "TEXT")
    private String executionTechnique;
    
    @Column(name = "progression", columnDefinition = "TEXT")
    private String progression;
    
    @Column(name = "muscle_groups", columnDefinition = "TEXT")
    private String muscleGroups;
    
    @Column(name = "common_mistakes", columnDefinition = "TEXT")
    private String commonMistakes;
    
    @Column(name = "training_tips", columnDefinition = "TEXT")
    private String trainingTips;
    
    @Column(name = "image_path")
    private String imagePath;
    
    // Конструкторы
    public Exercise() {
    }
    
    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getSlug() {
        return slug;
    }
    
    public void setSlug(String slug) {
        this.slug = slug;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getExecutionTechnique() {
        return executionTechnique;
    }
    
    public void setExecutionTechnique(String executionTechnique) {
        this.executionTechnique = executionTechnique;
    }
    
    public String getProgression() {
        return progression;
    }
    
    public void setProgression(String progression) {
        this.progression = progression;
    }
    
    public String getMuscleGroups() {
        return muscleGroups;
    }
    
    public void setMuscleGroups(String muscleGroups) {
        this.muscleGroups = muscleGroups;
    }
    
    public String getCommonMistakes() {
        return commonMistakes;
    }
    
    public void setCommonMistakes(String commonMistakes) {
        this.commonMistakes = commonMistakes;
    }
    
    public String getTrainingTips() {
        return trainingTips;
    }
    
    public void setTrainingTips(String trainingTips) {
        this.trainingTips = trainingTips;
    }
    
    public String getImagePath() {
        return imagePath;
    }
    
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
