package org.example.gymbackend.telegramBot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import org.example.gymbackend.dto.AdminDto;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.Status;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.UserRepo;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
@Component
public class GYMTelegramBot extends TelegramLongPollingBot {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;

    @Autowired
    public GYMTelegramBot(UserRepo userRepo,RoleRepo roleRepo) {
        this.userRepo = userRepo;
        this.roleRepo=roleRepo;
    }


    @Override
    public String getBotUsername() {
        return "gym_bek_bot";
    }

    @Override
    public String getBotToken() {
        return "6833970681:AAHfG31E4e-KjOujk4lcoHowAVVbYV96Ggg";
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
                    sendMessage.setChatId(user.getChatId());
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


                } else if (user.getStatus().equals(Status.SET_NAME)) {
                    user.setStatus(Status.SET_IMAGE);
                    System.out.println(message.getText());
                    user.setFullName(message.getText());
                    sendMessage.setText("Iltimos rasmingizni yuboring yuboring!");
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);
                }

            } else if (message.hasContact()) {;
                if (user.getStatus().equals(Status.SHARE_CONTACT)){

                    Contact contact = message.getContact();
                    Optional<User> findUserOptional = userRepo.findByPhoneNumber(contact.getPhoneNumber());
                    if (findUserOptional.isPresent()) {
                        User findUser = findUserOptional.get();
                        List<Role> roles = findUser.getRoles();
                        Role admin = roleRepo.findByName("ROLE_ADMIN");

                        for (Role role : roles) {
                            if (role.equals(admin)){
                                user.setStatus(Status.SET_PASSWORD);
                                sendMessage.setText("Iltimos parolingizni kiriting yuboring!");
                                sendMessage.setChatId(findUser.getChatId());
                                System.out.println("salom");
                                execute(sendMessage);
                            }
                        }



                        userRepo.save(findUser);
                    }
                    else {
                        System.out.println(contact.getPhoneNumber());
                        user.setStatus(Status.SET_NAME);
                        user.setPhoneNumber(contact.getPhoneNumber());
                        System.out.println(message.getChat());
                        sendMessage.setText("Assalom aleykum botimizga xush kelibsiz iltimos  ism sharifingizni kiriting!");
                        sendMessage.setChatId(user.getChatId());

                        execute(sendMessage);
                        userRepo.save(user);
                    }

                    }

            }
            else if (message.hasPhoto()) {

                if (user.getStatus().equals(Status.SET_IMAGE)){

                    List<PhotoSize> photo = message.getPhoto();
                    GetFile getFile = new GetFile(photo.get(0).getFileId());
                    File file = execute(getFile);
                    String fileUrl = file.getFileUrl(getBotToken());
                    URL url = new URL(fileUrl);
                    InputStream inputStream = url.openStream();
                    FileUtils.copyInputStreamToFile(inputStream,new java.io.File(message.getPhoto().get(0).getFilePath()));
                    System.out.println(fileUrl);
                    System.out.println(message.getPhoto().get(0).getFilePath());

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
