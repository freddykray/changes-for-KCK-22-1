package com.example.changesForKCK;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@EnableScheduling
public class FileService {

    private final String PDF_PATH = "./changesFile.pdf";

    @Autowired
    private DateService dateService;

    public void downloadPdfFileFromUrl() throws IOException {
        String url = getUrlChanges();
        try (InputStream in = new URL(url).openStream()) {
            Files.copy(in, Paths.get(PDF_PATH), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void downloadPdfFileFromUrl(String filePath, String fileName) throws IOException {
        try (InputStream in = new URL(filePath).openStream()) {
            Files.copy(in, Paths.get(fileName), StandardCopyOption.REPLACE_EXISTING);
        }

    }

    public String parsePdf() throws IOException {
        File file = new File(PDF_PATH);

        if (!file.exists()) {
            throw new FileNotFoundException("PDF нет по пути: " + file.getAbsolutePath());
        }

        PDDocument document = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        document.close();

        return text;
    }

    public String getUrlChanges() throws IOException {
        Document document;
        {
            try {
                disableSSL();
                document = Jsoup.connect("https://lmk-lipetsk.ru/main_razdel/shedule/index.php")
                                .userAgent("Mozilla/5.0")

                                .timeout(15000)
                                .get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        Element links = document.select(
                String.format("a:contains(Изменение учебных занятий на %s)", findNeedDateChanges())
        ).first();
        return findUrl(links);
    }

    private String findNeedDateChanges() {

        LocalDate date = LocalDate.now();

        if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
            date = date.plusDays(3);
        } else {
            date = date.plusDays(1);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        return date.format(formatter);
    }

    private String findUrl(Element link) throws IOException {
        HashMap<LocalDate, String> dateToUrl = new HashMap<>();

            String url = link.absUrl("href");

            downloadPdfFileFromUrl(url, PDF_PATH);

            String stringPdf = parsePdf();

            LocalDate date = dateService.extractDateFromPdf(stringPdf);

            dateToUrl.put(date, url);


        LocalDate newDate = dateToUrl.keySet()
                                     .stream()
                                     .max(LocalDate::compareTo)
                                     .orElseThrow(() -> new RuntimeException(
                                             "Не нашли ни одной даты"));
        return dateToUrl.get(newDate);
    }

    private void disableSSL() throws Exception {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public X509Certificate[] getAcceptedIssuers() {return new X509Certificate[0];}

                    public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                    public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                }
        };

        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, trustAllCerts, new SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        HttpsURLConnection.setDefaultHostnameVerifier((hostname, session) -> true);
    }

}
