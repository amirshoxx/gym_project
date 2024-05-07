package org.example.gymbackend.telegramBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class GYMTelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        return "Gym_new_bot";
    }

    @Override
    public String getBotToken() {
        return "6997658732:AAFNqaIqOOMNaFqQUrq5W25tA47ssI0_6T4";
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() ) {

                Long chatId = message.getChatId();
                SendMessage sendMessage = new SendMessage();
            }
        }

    }


}
