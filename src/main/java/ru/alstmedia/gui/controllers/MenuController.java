package ru.alstmedia.gui.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import ru.alstmedia.CalcPrintApplication;
import ru.alstmedia.MazeApplication;
import ru.alstmedia.calculations.MathSign;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static ru.alstmedia.utils.ProjectUtil.writeTextToClipboard;

public class MenuController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mathLabPane;

    @FXML
    private TextField resultNumber;

    @FXML
    private ChoiceBox<MathSign> operation_dropdown;

    @FXML
    private Button buttonGenerateLabirinth;

    @FXML
    private AnchorPane mathLabPane1;

    @FXML
    private TextField colResult1;

    @FXML
    private Button buttonPrintCalcExamples;

    @FXML
    private TextField colResult2;

    @FXML
    private TextField colResult3;

    @FXML
    private TextField colResult4;


    @FXML
    void initialize() {
        initMathMaze();
        initCalcExample();

        buttonGenerateLabirinth.setOnAction(mathMazeAction());
        buttonPrintCalcExamples.setOnAction(calcExampleAction());

    }

    /**
     * Инициализация значений по умолчанию для "Математического лабиринта"
     */
    private void initMathMaze() {
        operation_dropdown.getItems().addAll(MathSign.values());
        operation_dropdown.setValue(MathSign.PLUS);
        resultNumber.setText("20");
        System.out.println("operation_dropdown.getItems: " + operation_dropdown.getItems());
    }

    /**
     * Обработка нажатия кнопки генерации "Математического лабиринта"
     *
     * @return действие.
     */
    private EventHandler<ActionEvent> mathMazeAction() {
        return event -> {
            System.out.println("значение операции: " + operation_dropdown.getValue());
            System.out.println("значение результата: " + resultNumber.getText());

            if (resultNumber.getText().isEmpty()) {
                String message = "Внимание! Поле 'Результат' пустое. \nВведите в поле значение результата выбранной операции. \nНапример: 10";
                showAlert(Alert.AlertType.WARNING, message);
            } else if (!isNumber(resultNumber.getText())) {
                String message = "Внимание! \nВ поле 'Результат' вы ввели '" + resultNumber.getText() + "' - это не число. \nВведите в поле числовое значение. Например: 10";
                showAlert(Alert.AlertType.WARNING, message);
            } else {
                try {
                    MazeApplication.generateAndOpenMaze(operation_dropdown.getValue(), Integer.parseInt(resultNumber.getText()));
                } catch (Throwable ex) {
                    executeCatchAction(ex);
                }
            }

        };
    }

    /**
     * Инициализация значений по умолчанию для "Математического лабиринта"
     */
    private void initCalcExample() {
        colResult1.setText("10");
        colResult2.setText("20");
        colResult3.setText("30");
        colResult4.setText("40");
    }

    /**
     * Обработка нажатия кнопки генерации "Реши примеры"
     *
     * @return действие.
     */
    private EventHandler<ActionEvent> calcExampleAction() {
        return event -> {
            boolean isColsCorrect = true;
            //check isNumber in all cols
            List<TextField> colsResultNums = Arrays.asList(colResult1, colResult2, colResult3, colResult4);
            for (int i = 0; i < colsResultNums.size(); i++) {
                if (!isNumber(colsResultNums.get(i).getText())) {
                    String message = String.format("Внимание! В поле 'Столбец %s' вы ввели '%s' - это не число. \nВведите в поле числовое значение. Например: 10",
                            i + 1, colsResultNums.get(i).getText());
                    showAlert(Alert.AlertType.WARNING, message);
                    isColsCorrect = false;
                }
            }

            if (isColsCorrect) {
                try {
                    CalcPrintApplication.generateCalcExamples(colResult1.getText(), colResult2.getText(), colResult3.getText(), colResult4.getText());
                } catch (Throwable ex) {
                    executeCatchAction(ex);
                }
            }


        };
    }


    private static void executeCatchAction(Throwable ex) {
        ex.printStackTrace();
        writeTextToClipboard(ex.toString() + "\n\n" + Arrays.toString(ex.getStackTrace()));
        String message = "К сожалению произошла ошибка :( Необходимо обратиться к разработчику. \n Описание ошибки: " + ex.toString() + "\n Сообщение скопировано в буфер.";
        showAlert(Alert.AlertType.ERROR, message, Arrays.toString(ex.getStackTrace()));
        System.exit(1);
    }

    private static boolean isNumber(String stringNumber) {
        try {
            Integer.parseInt(stringNumber);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private static void showAlert(Alert.AlertType alertType, String headerText, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(headerText);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

