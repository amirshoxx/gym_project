package org.example.gymbackend.telegramBot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.example.gymbackend.entity.Status;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Component
public class GYMTelegramBot extends TelegramLongPollingBot {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public GYMTelegramBot(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo=roleRepo;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public String getBotUsername() {
        return "khamroyevHB_bot";
    }

    @Override
    public String getBotToken() {
        return "7113041141:AAE5Ru8NDzvrom_XAFWfFMTvYsPvIKfBUIM";
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            SendMessage sendMessage = new SendMessage();
            User user = selectUser(chatId);
            if (message.hasText() ) {



                if (message.getText().equalsIgnoreCase("/start") ) {
                    user.setStatus(Status.SHARE_CONTACT);

                    sendMessage.setText("Iltimos contactingizni yuboring!");
                    sendMessage.setReplyMarkup(genContactButtons());
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);
                } else if (user.getStatus().equals(Status.SET_PASSWORD)) {

                    String text = message.getText();
                    Optional<User> optionalUser = userRepo.findByPassword(text.toString());
                    if (optionalUser.isPresent()){
                        user.setStatus(Status.SET_SETTING);
                        sendMessage.setReplyMarkup(genSettingButtons());
                        sendMessage.setChatId(chatId);
                        execute(sendMessage);
                        userRepo.save(user);
                    }else{
                        user.setStatus(Status.SET_PASSWORD);
                        sendMessage.setText("Iltimos passwordni to'gri yuboring yuboring!");
                        sendMessage.setChatId(chatId);
                        execute(sendMessage);
                        userRepo.save(user);
                    }


                }

            } else if (message.hasContact()) {

                if (user.getStatus().equals(Status.SHARE_CONTACT)){

                    Contact contact = message.getContact();
                    Optional<User> findUserOptional = userRepo.findByPhoneNumber(contact.getPhoneNumber());
                    if (findUserOptional.isPresent()) {
                        User findUser = findUserOptional.get();
                        if (findUser.getAuthorities().equals("ROLE_ADMIN")) {
                            user.setStatus(Status.SET_PASSWORD);
                            sendMessage.setText("Iltimos parolingizni kiriting yuboring!");
                            sendMessage.setChatId(chatId);
                            execute(sendMessage);

                        }
                        userRepo.save(user);
                    }else {
                        user.setPhoneNumber(contact.getPhoneNumber());
                        user.setStatus(Status.SET_IMAGE);
                        sendMessage.setText("Iltimos rasmingizni kiriting yuboring!");
                        sendMessage.setChatId(chatId);
                        execute(sendMessage);
                        userRepo.save(user);
                    }
                    }

            }
        }

    }





//    private User selectUser(Long chatId) {
//        Optional<User> userOptional = userRepo.findAllByChatId(chatId);
//        if (userOptional.isPresent()) {
//            return userOptional.get(); // Return existing user
//        } else {
//            // Create and save a new user
//            User newUser = new User(chatId);
//            return userRepo.save(newUser);
//        }
//    }

    private User selectUser(Long chatId) {
        Optional<User> userOptional = userRepo.findAllByChatId(chatId);
        return userOptional.orElseGet(() -> userRepo.save(new User(chatId)));
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
    private ReplyKeyboardMarkup genSettingButtons(){
        List<KeyboardRow> rows=new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        rows.add(row);

        KeyboardButton button = new KeyboardButton();
        button.setText("Sozlamalar");
        row.add(button);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;

    }
}
