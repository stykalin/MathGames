package ru.alstmedia;

import freemarker.template.Configuration;
import freemarker.template.Template;
import ru.alstmedia.calculations.MathSign;
import ru.alstmedia.generator.MazeGenerator;
import ru.alstmedia.render.HtmlRender;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.*;

/**
 * Created by Family on 10.04.2018.
 */
public class MazeApplication {

    private static final String FILE_NAME = "maze.html";

    public static void generateAndOpenMaze(MathSign sign, int resultNumber) throws Exception {
        MazeGenerator mg = new MazeGenerator();
//        MathSign sign = MathSign.PLUS;
//        int resultNumber = 10;

        String[][] table = mg.fillTable(sign, resultNumber);

        Map<String, Object> root = new HashMap<>();
        root.put("sign", sign.getName());
        root.put("result", resultNumber);
        root.put("table", mg.getTableForTemplate(table));

        Configuration cfg = new HtmlRender().createConfig();
        Template template = cfg.getTemplate("maze-temp.ftl");

        Writer out = new OutputStreamWriter(new FileOutputStream(FILE_NAME));
        template.process(root, out);

        out.flush();
        out.close();

        System.out.println("html successfully created");

        File htmlFile = new File(FILE_NAME);
        Desktop.getDesktop().browse(htmlFile.toURI());
    }
}
