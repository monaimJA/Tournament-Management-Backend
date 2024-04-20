package com.monaim.tournoi.services;

import com.monaim.tournoi.dtos.GoalDto;
import com.monaim.tournoi.entity.Goal;
import com.monaim.tournoi.mappers.GoalMapper;
import com.monaim.tournoi.repos.GoalRepository;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GoalService {
    private final GoalRepository goalRepository;
    private final GoalMapper goalMapper;
    @Autowired
    public GoalService(GoalRepository goalRepository, GoalMapper goalMapper) {
        this.goalRepository = goalRepository;
        this.goalMapper = goalMapper;
    }

    public GoalDto createGoal(GoalDto goalDto) throws Exception {
        try{
            Goal goal = goalMapper.convertToEntity(goalDto);
            Goal goalResp = goalRepository.save(goal);
            return goalMapper.convertToDto(goalResp);
        }
        catch (Exception ex ){
            throw new Exception("Error occurred in the createGoal : " + goalDto.getId());
        }
    }

    public boolean deleteGoalById(long id) throws Exception{
        try{
            Optional<Goal> goalOptional = goalRepository.findById(id);
            if(goalOptional.isPresent()){
                goalRepository.deleteById(id);
                return true;
            }
            else{
                return false;
            }
        }
        catch (Exception ex){
            throw new Exception("Error Occurred in the deleteById : "+ id);
        }
    }

    public GoalDto updateGoalById(GoalDto goalDto) throws Exception {
        try {
            Goal goal = goalMapper.convertToEntity(goalDto);
            Goal goalResp = goalRepository.save(goal);
            return goalMapper.convertToDto(goalResp);
        }
        catch (Exception ex) {
            throw new Exception("Error occurred in the updateGoalById : " + goalDto.getId());
        }
    }

    public GoalDto findGoalById(long id) throws Exception{
        try {
            Optional<Goal> goal = goalRepository.findById(id);
            if(goal.isPresent()){
                return goalMapper.convertToDto(goal.get());
            }
            else {
                throw new EntityNotFoundException("Goal with id : "+id+" Not Found");
            }
        }
        catch (Exception ex) {
            throw new Exception("Error occurred in the findGoalById : "+id);
        }
    }
}
