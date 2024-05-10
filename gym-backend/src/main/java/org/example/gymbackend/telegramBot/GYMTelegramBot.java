package org.example.gymbackend.telegramBot;

import lombok.SneakyThrows;
import org.example.gymbackend.entity.Role;
import org.example.gymbackend.entity.Status;
import org.example.gymbackend.entity.SubscriptionType;
import org.example.gymbackend.entity.User;
import org.example.gymbackend.repository.RoleRepo;
import org.example.gymbackend.repository.SubscriptionTypeRepo;
import org.example.gymbackend.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
@Component
public class GYMTelegramBot extends TelegramLongPollingBot {
    private final UserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final SubscriptionTypeRepo subscriptionTypeRepo;

    @Autowired
    public GYMTelegramBot(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder, SubscriptionTypeRepo subscriptionTypeRepo) {
        this.userRepo = userRepo;
        this.roleRepo=roleRepo;
        this.passwordEncoder=passwordEncoder;
        this.subscriptionTypeRepo=subscriptionTypeRepo;
    }


    @Override
    public String getBotUsername() {
        return "gym_bek_bot";
    }

    @Override
    public String getBotToken() {
        return "6833970681:AAHfG31E4e-KjOujk4lcoHowAVVbYV96Ggg";
    }
    String[] subscriptionInfo = new String[4];

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage()) {
            Message message = update.getMessage();
            Long chatId = message.getChatId();
            SendMessage sendMessage = new SendMessage();
            User user = selectUser(chatId);
            if (message.hasText() ) {
                String text2 = message.getText();

                if (message.getText().equalsIgnoreCase("/start") ) {

                    user.setStatus(Status.SHARE_CONTACT);
                    sendMessage.setText("Iltimos contactingizni yuboring!");
                    sendMessage.setReplyMarkup(genContactButtons());
                    sendMessage.setChatId(user.getChatId());
                    execute(sendMessage);
                    userRepo.save(user);
                } else if (user.getStatus().equals(Status.SET_PASSWORD)) {
                    String text = message.getText();
                    User foundUser = userRepo.findByFullName(user.getFullName()).get();
                    if (passwordEncoder.matches(text,foundUser.getPassword())){
                        foundUser.setStatus(Status.SET_SETTING);
                        sendMessage.setReplyMarkup(genInlineSettingsButtons());
                        sendMessage.setText("iltimos sozlamalar buttonini bosing!");
                        sendMessage.setChatId(foundUser.getChatId());
                        execute(sendMessage);
                        userRepo.save(foundUser);
                    } else {
                        sendMessage.setText("Iltimos passwordni to'gri yuboring!");
                        sendMessage.setChatId(chatId);
                        execute(sendMessage);
                    }
                }
                else if (user.getStatus().equals(Status.SET_NAME)) {
                    user.setStatus(Status.SET_IMAGE);
                    user.setFullName(message.getText());
                    sendMessage.setText("Iltimos rasmingizni yuboring yuboring!");
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);
                } else if (user.getStatus().equals(Status.TARIF_ADD)) {
                    subscriptionInfo = new String[4]; // Tozalash
                    user.setStatus(Status.ADD_SUBSCRIPTION_NAME);
                    sendMessage.setText("Nomini kiriting");
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);
                } else if (user.getStatus().equals(Status.ADD_SUBSCRIPTION_NAME)) {
                    subscriptionInfo[0] = text2;
                    user.setStatus(Status.ADD_SUBSCRIPTION_REQUEST_PRICE);
                    sendMessage.setText("Narxini kiriting");
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);
                } else if (user.getStatus().equals(Status.ADD_SUBSCRIPTION_REQUEST_PRICE)) {
                    subscriptionInfo[1] = text2;
                    user.setStatus(Status.ADD_SUBSCRIPTION_REQUEST_TITLE);
                    sendMessage.setText("Sarlavhani kiriting");
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);
                } else if (user.getStatus().equals(Status.ADD_SUBSCRIPTION_REQUEST_TITLE)) {
                    subscriptionInfo[2] = text2;
                    user.setStatus(Status.ADD_SUBSCRIPTION_REQUEST_DAY_COUNT);
                    sendMessage.setText("Kunni kiriting");
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);
                } else if (user.getStatus().equals(Status.ADD_SUBSCRIPTION_REQUEST_DAY_COUNT)) {
                    subscriptionInfo[3] = text2;
                    user.setStatus(Status.START);
                    sendMessage.setText(" Muvaffaqiyatli qo'shildi.");
                    sendMessage.setChatId(chatId);
                    execute(sendMessage);
                    userRepo.save(user);

                    SubscriptionType subscriptionType = new SubscriptionType();
                    subscriptionType.setName(subscriptionInfo[0]);
                    subscriptionType.setPrice(Double.parseDouble(subscriptionInfo[1]));
                    subscriptionType.setTitle(subscriptionInfo[2]);
                    subscriptionType.setDayCount(Integer.parseInt(subscriptionInfo[3]));
                    saveSubscriptionType(subscriptionType);
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
                                findUser.setStatus(Status.SET_PASSWORD);
                                sendMessage.setText("Iltimos parolingizni kiriting yuboring!");
                                sendMessage.setChatId(findUser.getChatId());

                                execute(sendMessage);
                                userRepo.save(findUser);
                            }
                        }




                    }
                    else {
                        user.setStatus(Status.SET_NAME);
                        user.setPhoneNumber(contact.getPhoneNumber());
                        sendMessage.setText("Assalom aleykum botimizga xush kelibsiz iltimos  ism sharifingizni kiriting!");
                        sendMessage.setChatId(user.getChatId());
                        execute(sendMessage);
                        userRepo.save(user);
                    }

                }

            }
            else if (message.hasPhoto()) {

                if (user.getStatus().equals(Status.SET_IMAGE)) {
                    List<PhotoSize> photos = message.getPhoto();
                    String fileId = photos.get(0).getFileId();

                    // Download the photo file
                    GetFile getFile = new GetFile(fileId);
                    File file = execute(getFile);

                    if (file != null) {
                        String fileUrl = file.getFileUrl(getBotToken());
                        URL url = new URL(fileUrl);

                        try (InputStream inputStream = url.openStream()) {
                            // Convert InputStream to byte array
                            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                            int nRead;
                            byte[] data = new byte[1024];
                            while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                                buffer.write(data, 0, nRead);
                            }
                            buffer.flush();
                            byte[] photoBytes = buffer.toByteArray();

                            // Save the photoBytes to the user's image field
//                            user.setImage(Arrays.toString(photoBytes));
                            System.out.println(Arrays.toString(photoBytes));
//                            userRepo.save(user);

                            System.out.println("Photo saved successfully to the user's image field in the database!");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    } else {
                        System.out.println("Failed to retrieve file information.");
                    }
                }

            }else if (update.hasCallbackQuery()) {

            CallbackQuery callbackQuery = update.getCallbackQuery();
            String data = callbackQuery.getData();
            Long chatId = callbackQuery.getFrom().getId();
            User user = selectUser(chatId);
            SendMessage sendMessage = new SendMessage();

            if (user.getStatus().equals(Status.SET_SETTING)) {
                user.setStatus(Status.SET_RATE);
                sendMessage.setText("birini tanlang?");
                sendMessage.setReplyMarkup(genInlineRatesButtons());
                sendMessage.setChatId(user.getChatId());
                execute(sendMessage);
                userRepo.save(user);

            } else if (user.getStatus().equals(Status.SET_RATE)) {
                if (data.equals("tarif")) {
                    System.out.println(data);
                    user.setStatus(Status.TARIF_ADD);
                    sendMessage.setText("Tarifni qo'shish uchun tugmani bosing:");
                    sendMessage.setReplyMarkup(genTarifAddButtons());
                    sendMessage.setChatId(user.getChatId());
                    execute(sendMessage);
                    userRepo.save(user);

                }

            }

        }

    }


    private void saveSubscriptionType(SubscriptionType subscription) {
        subscriptionTypeRepo.save(subscription);
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

    private ReplyKeyboardMarkup genTarifAddButtons(){
        List<KeyboardRow> rows=new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        rows.add(row);

        KeyboardButton button = new KeyboardButton();
        button.setText("tarif add!");
        row.add(button);

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(rows);
        replyKeyboardMarkup.setResizeKeyboard(true);

        return replyKeyboardMarkup;

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
    private InlineKeyboardMarkup genInlineSettingsButtons(){
        List<List<InlineKeyboardButton>> rows=new ArrayList<>();
        List<InlineKeyboardButton> row=new ArrayList<>();
        rows.add(row);

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("sozlamalar ⚙\uFE0F");
        button.setCallbackData("sozlamalar");
        row.add(button);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rows);
        return inlineKeyboardMarkup;

    }
    private InlineKeyboardMarkup genInlineRatesButtons(){
        List<List<InlineKeyboardButton>> rows=new ArrayList<>();
        List<InlineKeyboardButton> row=new ArrayList<>();
        rows.add(row);

        InlineKeyboardButton button = new InlineKeyboardButton();
        button.setText("ta'riflar");
        button.setCallbackData("tarif");
        row.add(button);

        InlineKeyboardButton button1 = new InlineKeyboardButton();
        button1.setText("foydalanuvchilar \uD83E\uDDCD\u200D♂\uFE0F");
        button1.setCallbackData("foydalanuvchilar");
        row.add(button1);

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup(rows);
        return inlineKeyboardMarkup;

    }
}
