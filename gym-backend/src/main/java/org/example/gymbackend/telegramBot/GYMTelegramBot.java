package org.example.gymbackend.telegramBot;

import lombok.SneakyThrows;
import org.example.gymbackend.entity.Status;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class GYMTelegramBot extends TelegramLongPollingBot {

    @Autowired
    UserRepo userRepo;

    @Override
    public String getBotUsername() {
        return "Gym_new_bot";
    }

    @Override
    public String getBotToken() {
        return "6997658732:AAFNqaIqOOMNaFqQUrq5W25tA47ssI0_6T4";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText() ) {

                Long chatId = message.getChatId();
                SendMessage sendMessage = new SendMessage();
                User user = selectUser(chatId);

                if (user.getStatus().equals(Status.START) ) {
                    user.setStatus(Status.SHARE_CONTACT);
                    sendMessage.setText("Iltimos contactingizni yuboring!");
                    sendMessage.setReplyMarkup(genContactButtons());
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                }

            }
        }

    }





    private User selectUser(Long chatId) {
        Optional<User> userOptional = userRepo.findAllByChatId(chatId);
        if (userOptional.isPresent()) {
            return userOptional.get(); // Return existing user
        } else {
            // Create and save a new user
            User newUser = new User(chatId);
            return userRepo.save(newUser);
        }
    }

    private ReplyKeyboardMarkup genContactButtons(){
        List<KeyboardRow> rows=new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        rows.add(row);

        KeyboardButton button = new KeyboardButton();
        button.setText("share contact!");
        button.setRequestContact(true);
        row.add(button);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;

    }
}
