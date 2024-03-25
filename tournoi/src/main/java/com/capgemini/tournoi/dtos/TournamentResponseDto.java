package com.capgemini.tournoi.dtos;

import com.capgemini.tournoi.entity.Team;
import com.capgemini.tournoi.enums.StatusTournoi;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentResponseDto {
    private Long id ;
    private String label;
    private Date startDate;
    private Date endDate;
    private StatusTournoi statusTournoi;
}
