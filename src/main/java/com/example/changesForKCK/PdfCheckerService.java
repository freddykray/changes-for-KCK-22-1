package com.example.changesForKCK;

import com.example.changesForKCK.telegrambot.TelegramBotService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PdfCheckerService {

    private static String lastDate;

    private final FileService fileService;

    private final DateService dateService;

    private final ChangesService changesService;

    private final TelegramBotService bot;

    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void checkUpdatesAndChanges() {
        try {
            System.out.println("🔍 Проверяю замену...");

            fileService.downloadPdfFileFromUrl();

            String pdfText = fileService.parsePdf();
            String dateFromPdf = dateService.extractDateFromPdf(pdfText).toString();

            if (lastDate == null) {
                lastDate = dateFromPdf;
                System.out.println("Последняя дата замен " + dateFromPdf);
                changesService.saveChangesForKCK();
                return;
            }

            if (dateFromPdf.equals(lastDate)) {
                return;
            }

            System.out.println("🆕 Обнаружено обновление! Новая дата последних замен: " + dateFromPdf);
            lastDate = dateFromPdf;

            bot.sendToAll(changesService.saveChangesForKCK());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
