package ru.itmo.zhmeh.ui.dialogs;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import ru.itmo.zhmeh.domain.Maintenance;
import ru.itmo.zhmeh.domain.MaintenanceType;

import java.time.Instant;
import java.util.Optional;

public class AddMaintenanceDialog extends Dialog<Maintenance> {

    public AddMaintenanceDialog() {
        setTitle("Добавить обслуживание");
        setHeaderText("Заполните данные обслуживания");

        // Кнопки ОК/Отмена
        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        // Форма
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        // Поля
        TextField instrumentIdField = new TextField();
        instrumentIdField.setPromptText("ID прибора (число)");

        ComboBox<MaintenanceType> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(MaintenanceType.values());
        typeCombo.setPromptText("Выберите тип");

        TextArea detailsArea = new TextArea();
        detailsArea.setPromptText("Что сделали (например: electrode cleaning)");
        detailsArea.setPrefRowCount(3);

        TextField doneAtField = new TextField();
        doneAtField.setPromptText("Дата (формат: YYYY-MM-DD или оставить пустым)");

        TextField ownerField = new TextField();
        ownerField.setText("SYSTEM"); // по умолчанию
        ownerField.setPromptText("Владелец");

        // Добавляем в сетку
        grid.add(new Label("ID прибора*:"), 0, 0);
        grid.add(instrumentIdField, 1, 0);
        grid.add(new Label("Тип*:"), 0, 1);
        grid.add(typeCombo, 1, 1);
        grid.add(new Label("Детали*:"), 0, 2);
        grid.add(detailsArea, 1, 2);
        grid.add(new Label("Дата (опционально):"), 0, 3);
        grid.add(doneAtField, 1, 3);
        grid.add(new Label("Владелец:"), 0, 4);
        grid.add(ownerField, 1, 4);

        // Валидация: кнопка OK неактивна, пока не заполнены обязательные поля
        Node okButtonNode = getDialogPane().lookupButton(okButton);
        okButtonNode.setDisable(true);

        Callback<Boolean, Boolean> validator = valid -> {
            boolean instrumentIdValid = !instrumentIdField.getText().isBlank()
                    && instrumentIdField.getText().matches("\\d+");
            boolean typeValid = typeCombo.getValue() != null;
            boolean detailsValid = !detailsArea.getText().isBlank();

            okButtonNode.setDisable(!(instrumentIdValid && typeValid && detailsValid));
            return valid;
        };

        instrumentIdField.textProperty().addListener((obs, old, val) -> validator.call(true));
        typeCombo.valueProperty().addListener((obs, old, val) -> validator.call(true));
        detailsArea.textProperty().addListener((obs, old, val) -> validator.call(true));

        getDialogPane().setContent(grid);

        // Конвертация результата
        setResultConverter(dialogButton -> {
            if (dialogButton == okButton) {
                return new Maintenance(
                        0, // id назначит сервис
                        Long.parseLong(instrumentIdField.getText().trim()),
                        typeCombo.getValue().toString(),
                        detailsArea.getText().trim(),
                        doneAtField.getText().trim(),
                        ownerField.getText().trim()
                );
            }
            return null;
        });
    }
}
