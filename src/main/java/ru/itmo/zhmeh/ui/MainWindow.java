package ru.itmo.zhmeh.ui;

import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import ru.itmo.zhmeh.service.CalibrationManager;
import ru.itmo.zhmeh.service.InstrumentsManager;
import ru.itmo.zhmeh.service.MaintenanceManager;

public class MainWindow {
    private final TabPane tabPane = new TabPane();
    private final BorderPane root = new BorderPane(tabPane);

    public MainWindow() {
        // Инициализируем менеджеры
        InstrumentsManager instrumentsManager = new InstrumentsManager();
        CalibrationManager calibrationManager = new CalibrationManager(instrumentsManager);
        MaintenanceManager maintenanceManager = new MaintenanceManager(instrumentsManager);

        // Создаём вкладки
//        tabPane.getTabs().add(new InstrumentsTab(instService).getTab());
//        tabPane.getTabs().add(new CalibrationsTab(calService).getTab());
//        tabPane.getTabs().add(new MaintenancesTab(maintenanceManager).getTab());
    }

    public BorderPane getView() {
        return root;
    }
}
