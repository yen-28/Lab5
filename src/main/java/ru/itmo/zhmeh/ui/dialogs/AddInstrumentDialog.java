package ru.itmo.zhmeh.ui.dialogs;

import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.itmo.zhmeh.domain.Instrument;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import ru.itmo.zhmeh.domain.*;

import java.util.Optional;

import java.awt.*;

public class AddInstrumentDialog extends Dialog<Instrument> {

    public AddInstrumentDialog() {
        setTitle("Добавить прибор");
        setHeaderText("Заполните данные нового прибора");

        ButtonType okBtn = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        getDialogPane().getButtonTypes().addAll(okBtn, ButtonType.CANCEL);

        //форма с полями
        GridPane grid = new GridPane();
        grid.setHgap(15);
        grid.setVgap(15);

        // Поля ввода
        TextField ownerUsernameField = new TextField();
        ownerUsernameField.setPromptText("по умолчанию - SYSTEM"); //TODO проверить это умолчание

        TextField nameField = new TextField();
        nameField.setPromptText("Например: pH meter Mettler A1");

        ComboBox<InstrumentType> typeCombo = new ComboBox<>();
        typeCombo.getItems().addAll(InstrumentType.values());
        typeCombo.setPromptText("Выберите тип из списка");

        TextField inventoryField = new TextField();
        inventoryField.setPromptText("(можно пусто)");

        TextField locationField = new TextField();
        locationField.setPromptText("");

        ComboBox<InstrumentStatus> statusCombo = new ComboBox<>();
        statusCombo.getItems().addAll(InstrumentStatus.values());

        //добавляем всё в сетку
        grid.add(new Label("Ваше имя: "), 0, 0);
        grid.add(ownerUsernameField, 1, 0);
        grid.add(new Label("Название:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Тип:"), 0, 2);
        grid.add(typeCombo, 1, 2);
        grid.add(new Label("Инвентарный номер:"), 0, 3);
        grid.add(inventoryField, 1, 3);
        grid.add(new Label("Место:"), 0, 4);
        grid.add(locationField, 1, 4);
        grid.add(new Label("Статус:"), 0, 5);
        grid.add(statusCombo, 1, 5);

        // Валидация: кнопка OK неактивна, пока не заполнены обязательные поля
        Node okButtonNode = getDialogPane().lookupButton(okBtn);
        okButtonNode.setDisable(true);

        Callback<Boolean, Boolean> validator = valid -> {
            boolean allFilled = !nameField.getText().isBlank()
                    && typeCombo.getValue() != null
                    && !locationField.getText().isBlank();
            okButtonNode.setDisable(!allFilled);
            return valid;
        };

        nameField.textProperty().addListener((obs, old, val) -> validator.call(true));
        typeCombo.valueProperty().addListener((obs, old, val) -> validator.call(true));
        locationField.textProperty().addListener((obs, old, val) -> validator.call(true));

        //устанавливаем контент
        getDialogPane().setContent(grid);

        // конвертация результата

        setResultConverter(dialogBtn -> {
            if (dialogBtn == okBtn) {

                // будет объект с временными данными
                return new Instrument(0,
                        ownerUsernameField.getText().trim(),
                        nameField.getText().trim(),
                        typeCombo.getValue().toString(),
                        inventoryField.getText().trim(),
                        locationField.getText().trim(),
                        statusCombo.getValue().toString()
                        );
            }
            return null; // Если отмена TODO БЕЗУМНЫЙ NULL!!!!! ??????
        });
    }

}
