package org.example.gymbackend;

import lombok.RequiredArgsConstructor;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class GymBackendApplication {
    private final UserRepo userRepo;

    public static void main(String[] args) {
        SpringApplication.run(GymBackendApplication.class, args);
//        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
//        GYMTelegramBot gymTelegramBot = new GYMTelegramBot();
//        telegramBotsApi.registerBot(gymTelegramBot);
    }
}
