package com.example.changesForKCK.telegrambot;

import com.example.changesForKCK.ChangesService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@AllArgsConstructor
@Service
@Data
@Slf4j
public class TelegramBotService extends TelegramLongPollingBot {

    private final String BOT_USERNAME = "ChangesKSKBot";

    private final String BOT_TOKEN = "7945520148:AAE6V8Bd3deCMM6C9seUM9lJSUUPNqvitCk";

    private final ChangesService changesService;

    private final SubscriptionService subscriptionService;

    @Override
    public void onUpdateReceived(Update update) {

        if (!update.hasMessage()) {
            return;
        }

        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();

        if ("/start".equals(text)) {

            subscriptionService.addSubscriber(chatId);

            log.info(
                    "🔔 Новый подписчик: chatId={} username={} firstName={} lastName={}",
                    chatId,
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getFrom().getFirstName(),
                    update.getMessage().getFrom().getLastName()
            );

            sendText(chatId, "Вы подписались на уведомления о заменах 🔔");
            return;
        } else if ("/changes".equals(text)) {
            handleChanges(chatId);
        } else if ("/subscribers".equals(text)) {
            try {
                handlerSubscribers(chatId);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleChanges(Long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(ChangesService.actualChanges);

        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlerSubscribers(Long chatId) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText("Количество подписчиков: " + subscriptionService.getSubscribers().size());
        execute(message);
    }

    public void sendToAll(String msg) {
        for (Long chatId : subscriptionService.getAll()) {
            sendText(chatId, msg);
        }
    }

    private void sendText(long chatId, String message) {
        try {
            execute(new SendMessage(String.valueOf(chatId), message));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}
