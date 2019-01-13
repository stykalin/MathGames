package ru.alstmedia.render;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

import java.io.File;
import java.io.IOException;

public class HtmlRender {

    public static Configuration createConfig() {

        Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
//        String pathToTemplates = "D:\\A_Development\\Java\\MathMaze\\src\\main\\java\\ru\\alstmedia\\render\\templates";
        String pathToTemplates = System.getProperty("user.dir") + "/src/main/java/ru/alstmedia/render/templates";
        try {
            cfg.setDirectoryForTemplateLoading(new File(pathToTemplates));
        } catch (IOException e) {
            e.printStackTrace();
        }

        cfg.setDefaultEncoding("UTF-8");
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);

        return cfg;
    }

}
