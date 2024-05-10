package org.example.gymbackend;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.SubscriptionTypeRepo;
import org.springframework.context.ApplicationContext;
import lombok.RequiredArgsConstructor;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.telegramBot.GYMTelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.telegramBot.GYMTelegramBot;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@RequiredArgsConstructor
@SpringBootApplication
public class GymBackendApplication {

    public static void main(String[] args) throws TelegramApiException {
        ApplicationContext context = SpringApplication.run(GymBackendApplication.class, args);
        UserRepo userRepo = context.getBean(UserRepo.class);
        RoleRepo roleRepo = context.getBean(RoleRepo.class);
        SubscriptionTypeRepo subscriptionTypeRepo = context.getBean(SubscriptionTypeRepo.class);
        PasswordEncoder passwordEncoder = context.getBean(PasswordEncoder.class);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        GYMTelegramBot gymTelegramBot = new GYMTelegramBot(userRepo,roleRepo,passwordEncoder,subscriptionTypeRepo);
        telegramBotsApi.registerBot(gymTelegramBot);

    }

}