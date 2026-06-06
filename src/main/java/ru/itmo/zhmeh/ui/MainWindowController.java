package ru.itmo.zhmeh.ui;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import ru.itmo.zhmeh.domain.*;
import ru.itmo.zhmeh.service.*;
import ru.itmo.zhmeh.storage.DatabaseException;
import ru.itmo.zhmeh.ui.dialogs.AddCalibrationDialog;
import ru.itmo.zhmeh.ui.dialogs.AddInstrumentDialog;
import ru.itmo.zhmeh.ui.dialogs.AddMaintenanceDialog;
import ru.itmo.zhmeh.validation.FileValidator;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Function;

public class MainWindowController {



    public MainWindowController() {}

    public TabPane mainTabPane;
    public Tab instTab;
    public AnchorPane instTab1;
    public AnchorPane calTab1;
    public Tab calTab;
    public Tab maintTab;
    public AnchorPane maintTab1;


    @FXML private Button loadButton;
    @FXML private Button saveButton;

    @FXML private TableView<Instrument> instrumentTable;
    @FXML private TableColumn<Instrument, Long> instIdCol;
    @FXML private TableColumn<Instrument, String> instNameCol;
    @FXML private TableColumn<Instrument, String> instTypeCol;
    @FXML private TableColumn<Instrument, String> instStatusCol;
    @FXML private TableColumn<Instrument, String> instLocationCol;
    @FXML private TableColumn<Instrument, Instant> instLastCol;
    @FXML private Button addButtonInst;
    @FXML private Button refreshButtonInst;

    @FXML private TableView<Calibration> calTable;
    @FXML private TableColumn<Calibration, Long> calIdCol;
    @FXML private TableColumn<Calibration, Long> calInstrumentIdCol; // было calNameCol
    @FXML private TableColumn<Calibration, String> calTypeCol;
    @FXML private TableColumn<Calibration, String> calResultCol;
    @FXML private TableColumn<Calibration, String> colLastCol;
    @FXML private TableColumn<Calibration, String> calUsNameCol; //TODO Instant
    @FXML private Button refreshButtonCal;
    @FXML private Button addButtonCal;

    @FXML private TableView<Maintenance> maintTable;
    @FXML private TableColumn<Maintenance, Long> maintIdCol;
    @FXML private TableColumn<Maintenance, Long> maintInstrumentIdCol;
    @FXML private TableColumn<Maintenance, String> maintTypeCol;
    @FXML private TableColumn<Maintenance, String> maintDetailsCol;
    @FXML private TableColumn<Maintenance, String> maintDoneAtCol;
    @FXML private TableColumn<Maintenance, String> maintOwnerCol;
    @FXML private Button addButtonMaint; // переименуйте в FXML!
    @FXML private Button refreshButtonMaint;

    private InstrumentsManager instrumentsManager;
    private CalibrationManager calibrationManager;
    private MaintenanceManager maintenanceManager;

    private final ObservableList<Instrument> instData = FXCollections.observableArrayList();
    private final ObservableList<Calibration> calData = FXCollections.observableArrayList();
    private final ObservableList<Maintenance> maintData = FXCollections.observableArrayList();


    @FXML
    public void initialize() {
        setupInstrumentColumns();
        setupCalibrationColumns();
        setupMaintenanceColumns();

//        // Загружаем данные при старте
//        loadInstruments();
    }


    public void setManagers(InstrumentsManager im, CalibrationManager cm, MaintenanceManager mm) {
        this.instrumentsManager = im;
        this.calibrationManager = cm;
        this.maintenanceManager = mm;
    }


    private void setupInstrumentColumns() {
        instrumentTable.setItems(instData);
        bindColumn(instIdCol, Instrument::getId);
        bindColumn(instNameCol, Instrument::getName);
        bindColumn(instTypeCol, inst -> inst.getType().toString());
        bindColumn(instStatusCol, inst -> inst.getStatus().toString());
        bindColumn(instLocationCol, Instrument::getLocation);
        // instLastCol убираем — нет метода в Instrument
    }

    private void setupCalibrationColumns() {
        calTable.setItems(calData);
        bindColumn(calIdCol, Calibration::getId);
        bindColumn(calInstrumentIdCol, Calibration::getInstrumentId);
        bindColumn(calTypeCol, cal -> cal.getType().toString());
        bindColumn(calResultCol, cal -> cal.getResult().toString());
        bindColumn(colLastCol, cal -> cal.getCalibratedAt().toString());
        bindColumn(calUsNameCol, Calibration::getOwnerUsername);
    }

    private void setupMaintenanceColumns() {
            maintTable.setItems(maintData);

            // Привязываем ВСЕ поля Maintenance
            bindColumn(maintIdCol, Maintenance::getId);
            bindColumn(maintInstrumentIdCol, Maintenance::getInstrumentId);
            bindColumn(maintTypeCol, maint -> maint.getType() != null ? maint.getType().toString() : "");
            bindColumn(maintDetailsCol, Maintenance::getDetails);
            bindColumn(maintDoneAtCol, maint ->
                    maint.getDoneAt() != null ? maint.getDoneAt().toString() : ""
            );
            bindColumn(maintOwnerCol, Maintenance::getOwnerUsername);
    }


    private <O, T> void bindColumn(TableColumn<O, T> column, Function<O, T> extractor) {
        column.setCellValueFactory(cell ->
                new SimpleObjectProperty<>(extractor.apply(cell.getValue()))
        );
    }


    @FXML
    private void loadInstruments() {
        try {
            instData.setAll(instrumentsManager.getColInstruments());
        } catch (Exception e) {
            showError("Ошибка загрузки приборов: " + e.getMessage());
        }
    }

    @FXML
    private void loadCalibrations() {
        try {
            calData.setAll(calibrationManager.getColCalibrations());
        } catch (Exception e) {
            showError("Ошибка загрузки калибровок: " + e.getMessage());
        }
    }

    @FXML
    private void loadMaintenances() {
        try {
            maintData.setAll(maintenanceManager.getColMaintenance());
        } catch (Exception e) {
            showError("Ошибка загрузки обслуживаний: " + e.getMessage());
        }
    }

    // === КНОПКИ ===

    @FXML
    private void onLoadClick(ActionEvent event) {
        // Простой выбор файла через FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Загрузить данные из файла");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("JSON Files", "*.json")
        );

        java.io.File selectedFile = fileChooser.showOpenDialog(
                loadButton.getScene().getWindow()
        );

        if (selectedFile != null) {
//            try {
//                // Загружаем через storage
////                JsonDataStorage storage = new JsonDataStorage();
////                DataContainer container = storage.load(selectedFile.toPath());
//
////                // Валидируем
////                new FileValidator().validate(container);
////
////                // Атомарно заменяем данные в менеджерах
////                instrumentsManager.replaceAll(container.getInstruments());
////                calibrationManager.replaceAll(container.getCalibrations());
////                maintenanceManager.replaceAll(container.getMaintenances());
//
//                // Обновляем все таблицы
//                loadInstruments();
//                loadCalibrations();
//                loadMaintenances();
//
//                new Alert(Alert.AlertType.INFORMATION, "✅ Данные загружены из " + selectedFile.getName()).show();
//
//            } catch (DatabaseException e) {
//                showError("Ошибка чтения файла: " + e.getMessage());
//            } catch (ru.itmo.zhmeh.validation.FileValidationException e) {
//                showError("Ошибка валидации: " + e.getMessage());
//            } catch (Exception e) {
//                showError("Неизвестная ошибка: " + e.getMessage());
//            }
        }
    }

    @FXML
    private void onRefreshClick(ActionEvent event) {
        // Определяем, какая кнопка нажата, и обновляем нужную таблицу
        if (event.getSource() == refreshButtonInst) loadInstruments();
        else if (event.getSource() == refreshButtonCal) loadCalibrations();
        else if (event.getSource() == refreshButtonMaint) loadMaintenances();
    }

    @FXML
    private void onAddClick(ActionEvent event) {
        try {
            ;

            // Определяем, какая вкладка активна, и открываем нужный диалог
            if (event.getSource() == addButtonMaint) {
                Optional<Maintenance> result = Optional.empty();
                AddMaintenanceDialog dialog = new AddMaintenanceDialog();
                result = dialog.showAndWait();

                result.ifPresent(maintenance -> {
                    // Вызываем сервис
                    maintenanceManager.addNew(
                            maintenance.getInstrumentId(),
                            maintenance.getType().toString(),
                            maintenance.getDetails(),
                            maintenance.getDoneAt().toString(),
                            maintenance.getOwnerUsername()
                    );
                    loadMaintenances(); // Обновляем таблицу
                });

            } else if (event.getSource() == addButtonInst) {
                Optional<Instrument> result = Optional.empty();
                AddInstrumentDialog dialog = new AddInstrumentDialog();
                result = dialog.showAndWait();

                result.ifPresent(instrument -> {
                    // Вызываем сервис
                    instrumentsManager.addNew(
                            instrument.getOwnerUsername(),
                            instrument.getName(),
                            instrument.getType().toString(),
                            instrument.getInventoryNumber(),
                            instrument.getLocation(),
                            instrument.getStatus().toString()
                    );
                    loadMaintenances(); // Обновляем таблицу
                });


            } else if (event.getSource() == addButtonCal) {
                AddCalibrationDialog dialog = new AddCalibrationDialog();
                Optional<Calibration> result = dialog.showAndWait();

                result.ifPresent(calibration -> {
                    calibrationManager.addNew(
                            calibration.getType().toString(),
                            calibration.getInstrumentId(),
                            calibration.getResult().toString(),
                            calibration.getComment(),
                            calibration.getCalibratedAt().toString(),
                            calibration.getOwnerUsername()
                    );
                    loadCalibrations(); // Обновляем таблицу
                });
            }

        } catch (NumberFormatException e) {
            showError("Ошибка: ID прибора должен быть числом");
        } catch (IllegalArgumentException e) {
            showError("Ошибка валидации: " + e.getMessage());
        } catch (Exception e) {
            showError("Неизвестная ошибка: " + e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait();
    }


    @FXML
    private void onSaveClick(ActionEvent event) {
        // 1. Открываем диалог выбора файла
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить данные в файл");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        fileChooser.setInitialFileName("data.json");

        // Показываем окно и ждём выбор пользователя
        File selectedFile = fileChooser.showSaveDialog(saveButton.getScene().getWindow());

        // Если пользователь нажал "Отмена" выходим
        if (selectedFile == null) {
            return;
        }

//        try {
//            DataContainer container = new DataContainer();
//
//            // независимая копия
//            container.setInstruments(new ArrayList<>(instrumentsManager.getColInstruments()));
//            container.setCalibrations(new ArrayList<>(calibrationManager.getColCalibrations()));
//            container.setMaintenances(new ArrayList<>(maintenanceManager.getColMaintenance()));
//
//            // слой хранения для записи на диск
//            JsonDataStorage storage = new JsonDataStorage();
//            storage.save(container, selectedFile.toPath());
//
//            // успех
//            Alert successAlert = new Alert(Alert.AlertType.INFORMATION, "Данные успешно сохранены в " + selectedFile.getName());
//            successAlert.showAndWait();
//
//        } catch (DatabaseException e) {
//            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Ошибка сохранения: " + e.getMessage());
//            errorAlert.showAndWait();
//        } catch (Exception e) {
//            Alert errorAlert = new Alert(Alert.AlertType.ERROR, "Неизвестная ошибка при сохранении: " + e.getMessage());
//            errorAlert.showAndWait();
        }
    }
