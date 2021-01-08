package ru.itis;

import com.google.auto.service.AutoService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@AutoService(Processor.class)
@SupportedAnnotationTypes(value = {"ru.itis.HtmlInput", "ru.itis.HtmlForm"})
public class HtmlProcessor extends AbstractProcessor {
    private static final String templateFileName = "form.ftl";

    private final Configuration freemarkerConfiguration;

    public HtmlProcessor() {
        freemarkerConfiguration = new Configuration(Configuration.VERSION_2_3_27);
        try {
            freemarkerConfiguration.setDirectoryForTemplateLoading(new File("C:\\Users\\garie\\Desktop\\JavaLab3\\Annotations\\src\\main\\resources"));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        // получить типы с аннотацией HtmlForm
        Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(HtmlForm.class);
        for (Element element : annotatedElements) {
            // получаем путь с class-файлам
            String path = HtmlProcessor.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            // создадим путь к html-файлу
            // User.class -> User.html
            path = path.substring(1) + element.getSimpleName().toString() + ".html";
            // формируем путь для записи данных
            Path out = Paths.get(path);
            HtmlForm annotation = element.getAnnotation(HtmlForm.class);
            Map<String, Object> root = new HashMap<>();
            root.put("action", annotation.action());
            root.put("method", annotation.method());
            root.put("inputs", getElementInputs(element));
            createHtml(root, out);
        }
        return true;
    }

    private List<HtmlInput> getElementInputs(Element element) {
        List<? extends Element> inputs = element.getEnclosedElements();
        return inputs.stream()
                .filter(elem -> elem.getKind().isField())
                .map(elem -> elem.getAnnotation(HtmlInput.class))
                .collect(Collectors.toList());
    }

    private void createHtml(Map<String, Object> elements, Path out) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(out.toFile()));
//            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
//            cfg.setClassForTemplateLoading(HtmlCreator.class, "/templates");
            Template template = this.freemarkerConfiguration.getTemplate(templateFileName);
//            Template temp = cfg.getTemplate(templateFileName);
//            temp.process(elements, writer);
            template.process(elements, writer);
        } catch (IOException | TemplateException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
