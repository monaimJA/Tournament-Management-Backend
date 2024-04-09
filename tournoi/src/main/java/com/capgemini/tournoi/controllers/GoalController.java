package com.capgemini.tournoi.controllers;

import com.capgemini.tournoi.dtos.GoalDto;
import com.capgemini.tournoi.services.GoalService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/goals")
public class GoalController {
    private final GoalService goalService;
    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }
    @PostMapping
    public ResponseEntity<GoalDto> createGoal(@Valid @RequestBody GoalDto goalDto) throws Exception{
        GoalDto goalDtoResp = goalService.createGoal(goalDto);
        return new ResponseEntity<>(goalDtoResp, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGoalById(@PathVariable long id) throws Exception{
        if(goalService.deleteGoalById(id)){
            return ResponseEntity.ok("Goal with ID " + id + " deleted successfully");
        }
        else {
            return ResponseEntity.badRequest().body("Failed to delete goal with ID " + id + ". Please verify the data.");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<GoalDto> updateGoal(@PathVariable long id, @Valid @RequestBody GoalDto goalDto) throws Exception{
        goalDto.setId(id);
        return ResponseEntity.ok(goalService.updateGoalById(goalDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GoalDto> getGoalById(@PathVariable long id) throws Exception {
        GoalDto goal = goalService.findGoalById(id);
        return ResponseEntity.ok(goal);
    }

}
