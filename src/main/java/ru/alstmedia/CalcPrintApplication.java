package ru.alstmedia;

import freemarker.template.Configuration;
import freemarker.template.Template;
import ru.alstmedia.render.HtmlRender;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class CalcPrintApplication {

    private static final String FILE_NAME = "calcExamples.html";

    public static void generateCalcExamples(String col1MaxNum, String col2MaxNum, String col3MaxNum, String col4MaxNum) throws Exception {

        Map<String, Object> root = new HashMap<>();
        root.put("col1MaxNum", col1MaxNum);
        root.put("col2MaxNum", col2MaxNum);
        root.put("col3MaxNum", col3MaxNum);
        root.put("col4MaxNum", col4MaxNum);

        Configuration cfg = new HtmlRender().createConfig();
        Template template = cfg.getTemplate("calc-template.ftl");

        Writer out = new OutputStreamWriter(new FileOutputStream(FILE_NAME));
        template.process(root, out);

        out.flush();
        out.close();

        System.out.println("html successfully created");

        File htmlFile = new File(FILE_NAME);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
}
