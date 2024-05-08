package org.example.gymbackend;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.repository.UserRepo;
import org.example.gymbackend.telegramBot.GYMTelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
@RequiredArgsConstructor
public class GymBackendApplication {
    private final UserRepo userRepo;

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(GymBackendApplication.class, args);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        GYMTelegramBot gymTelegramBot = new GYMTelegramBot();
        telegramBotsApi.registerBot(gymTelegramBot);
    }
}
