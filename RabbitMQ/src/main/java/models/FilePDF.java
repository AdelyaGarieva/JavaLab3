package models;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.UUID;

@AllArgsConstructor
@Builder
@Data
public class FilePDF {

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public void createPDF(String title, UserInfo userInfo) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(createPath(userInfo.getName(), title)));
            document.open();
            addTitlePage(document, title, userInfo);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }

    private String createPath(String name, String title) {
        return "C:/temp/" + name + "_" + title + "_" + UUID.randomUUID().toString() + ".pdf";
    }

    private static void addTitlePage(Document document, String title, UserInfo userInfo) throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);

        preface.add(new Paragraph(title, catFont));
        addEmptyLine(preface, 5);

        preface.add(new Paragraph("Name: " + userInfo.getName(), smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Surname: " + userInfo.getSurname(), smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Password number: " + userInfo.getPassportNumber(), smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Age: " + userInfo.getAge(), smallBold));
        addEmptyLine(preface, 1);

        preface.add(new Paragraph("Date of issue: " + userInfo.getIssueDate(), smallBold));
        addEmptyLine(preface, 1);

        document.add(preface);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
