package ru.itis.demo.helpers;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Component;
import ru.itis.demo.models.User;

import java.io.*;
import java.util.UUID;

@Component
public class PdfGeneratorImpl implements PdfGenerator {
    private User user;
    private String key;

    @Override
    public void generatePdf(User user, String key) {
        this.user = user;
        this.key = key;

        try {
            tryToWrite();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private void tryToWrite() throws IOException {
        String templateName = getTemplateName(key);
        String readyTemplateString = setInfo(user, getPageTemplate(templateName));
        String path = "generated/" + key + "/" + UUID.randomUUID() + ".pdf";
        File file = new File(path);
        boolean isCreated = file.createNewFile();
        if (isCreated) {
            byte[] bytes = readyTemplateString.getBytes();
            HtmlConverter.convertToPdf(new ByteArrayInputStream(bytes), new FileOutputStream(file), new ConverterProperties());
        }
    }

    private String setInfo(User user, String pageTemplate) {
        return pageTemplate
                .replace("${firstName}", user.getFirstName())
                .replace("${lastName}", user.getLastName())
                .replace("${middleName}", user.getMiddleName())
                .replace("${birthday}", user.getBirthday());
    }

    private String getPageTemplate(String templateName) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("templates/" + templateName))) {
            String line;
            StringBuilder pageContent = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                pageContent.append(line);
            }
            return pageContent.toString();
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private String getTemplateName(String key) {
        String[] parts = key.split("\\.");
        return parts[parts.length - 1] + ".html";
    }
}
