package ru.itis.demo.helpers;

import ru.itis.demo.models.User;

public interface PdfGenerator {
    void generatePdf(User user, String key);
}
