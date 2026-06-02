package ru.itmo.zhmeh.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.itmo.zhmeh.service.*;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // 1. Создаём менеджеры
        InstrumentsManager instrumentsManager = new InstrumentsManager();
        CalibrationManager calibrationManager = new CalibrationManager(instrumentsManager);
        MaintenanceManager maintenanceManager = new MaintenanceManager(instrumentsManager);

        // 2. Загружаем FXML (контроллер создастся автоматически благодаря fx:controller)
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/ru/itmo/zhmeh/ui/MainWindow.fxml")
        );

        Parent root = loader.load();  // ← Здесь создаётся MainWindowController

        // 3. Получаем созданный контроллер и передаём зависимости
        MainWindowController controller = loader.getController();
        controller.setManagers(instrumentsManager, calibrationManager, maintenanceManager);

        // 4. Показываем окно
        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Лабораторное оборудование");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

