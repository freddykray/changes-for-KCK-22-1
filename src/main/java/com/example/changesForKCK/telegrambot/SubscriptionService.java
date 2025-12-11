package com.example.changesForKCK.telegrambot;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Service
@Data
public class SubscriptionService {

    private final String FILE_PATH = "subscribers.json";

    private final ObjectMapper mapper = new ObjectMapper();
    private Set<Long> subscribers = new HashSet<>();

    @PostConstruct
    public void loadFromFile() {
        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                mapper.writeValue(file, subscribers);
                return;
            }

            Long[] arr = mapper.readValue(file, Long[].class);
            subscribers = new HashSet<>(Set.of(arr));

        } catch (Exception e) {
            throw new RuntimeException("Ошибка чтения subscribers.json", e);
        }
    }

    public void addSubscriber(Long chatId) {
        subscribers.add(chatId);
        saveToFile();
    }

    public Set<Long> getAll() {
        return subscribers;
    }

    private void saveToFile() {
        try {
            mapper.writeValue(new File(FILE_PATH), subscribers);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка сохранения subscribers.json", e);
        }
    }
}
