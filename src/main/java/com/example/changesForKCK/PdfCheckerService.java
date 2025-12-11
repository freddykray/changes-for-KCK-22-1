package com.example.changesForKCK;

import com.example.changesForKCK.telegrambot.TelegramBotService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PdfCheckerService {

    private final FileService fileService;

    private final DateService dateService;

    private final ChangesService changesService;

    private final TelegramBotService bot;

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void checkUpdatesAndChanges() {
        try {
            System.out.println("🔍 Проверяю замену...");

            if (fileService.getUrlChanges() == null) {
                return;
            }

            changesService.saveChangesForKCK();
            bot.sendToAll(changesService.saveChangesForKCK());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
