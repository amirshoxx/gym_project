package org.example.gymbackend;

import lombok.SneakyThrows;
import org.example.gymbackend.telegramBot.GYMTelegramBot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@SpringBootApplication
public class GymBackendApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(GymBackendApplication.class, args);
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        GYMTelegramBot gymTelegramBot = new GYMTelegramBot();
        telegramBotsApi.registerBot(gymTelegramBot);

    }

}
