package com.capgemini.tournoi.utils;

import com.capgemini.tournoi.dtos.PlayerDto;
import com.capgemini.tournoi.entity.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender emailSender;

    public void sendCustomizedEmail(PlayerDto playerDto, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(playerDto.getEmail());
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
    }
}
