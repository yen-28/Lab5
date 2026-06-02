package ru.itmo.zhmeh.ui.dialogs;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import ru.itmo.zhmeh.domain.Calibration;
import ru.itmo.zhmeh.domain.CalibrationResult;
import ru.itmo.zhmeh.domain.CalibrationType;

import java.util.Optional;

public class AddCalibrationDialog extends Dialog<Calibration> {

    public AddCalibrationDialog() {
        setTitle("Добавить калибровку");
        setHeaderText("Заполните данные калибровки");

        // Кнопки ОК/Отмена
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Форма
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Поля ввода
        TextField instrumentIdField = new TextField();
        instrumentIdField.setPromptText("ID прибора (число)");

        ComboBox<CalibrationType> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(CalibrationType.values());
        typeCombo.setPromptText("Выберите тип");

        ComboBox<CalibrationResult> resultCombo = new ComboBox<>();
        resultCombo.getItems().addAll(CalibrationResult.values());
        resultCombo.setPromptText("Выберите результат");

        TextArea commentArea = new TextArea();
        commentArea.setPromptText("Комментарий (опционально, до 128 символов)");
        commentArea.setPrefRowCount(2);

        TextField calibratedAtField = new TextField();
        calibratedAtField.setPromptText("Дата (формат: YYYY-MM-DD или оставить пустым)");

        TextField ownerField = new TextField();
        ownerField.setText("SYSTEM");
        ownerField.setPromptText("Владелец");

        // Добавляем в сетку
        grid.add(new Label("ID прибора*:"), 0, 0);
        grid.add(instrumentIdField, 1, 0);
        grid.add(new Label("Тип*:"), 0, 1);
        grid.add(typeCombo, 1, 1);
        grid.add(new Label("Результат*:"), 0, 2);
        grid.add(resultCombo, 1, 2);
        grid.add(new Label("Комментарий:"), 0, 3);
        grid.add(commentArea, 1, 3);
        grid.add(new Label("Дата калибровки:"), 0, 4);
        grid.add(calibratedAtField, 1, 4);
        grid.add(new Label("Владелец:"), 0, 5);
        grid.add(ownerField, 1, 5);

        // Валидация: кнопка OK неактивна, пока не заполнены обязательные поля
        Node okButtonNode = getDialogPane().lookupButton(okButton);
        okButtonNode.setDisable(true);

        Callback<Boolean, Boolean> validator = valid -> {
            boolean instrumentIdValid = !instrumentIdField.getText().isBlank()
                    && instrumentIdField.getText().matches("\\d+");
            boolean typeValid = typeCombo.getValue() != null;
            boolean resultValid = resultCombo.getValue() != null;

            okButtonNode.setDisable(!(instrumentIdValid && typeValid && resultValid));
            return valid;
        };

        instrumentIdField.textProperty().addListener((obs, old, val) -> validator.call(true));
        typeCombo.valueProperty().addListener((obs, old, val) -> validator.call(true));
        resultCombo.valueProperty().addListener((obs, old, val) -> validator.call(true));

        getDialogPane().setContent(grid);

        // Конвертация результата
        setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return new Calibration(
                        0, // id назначит сервис
                        typeCombo.getValue().toString(),
                        Long.parseLong(instrumentIdField.getText().trim()),
                        resultCombo.getValue().toString(),
                        commentArea.getText().trim(),
                        calibratedAtField.getText().trim(),
                        ownerField.getText().trim()
                );
            }
            return null;
        });
    }
}