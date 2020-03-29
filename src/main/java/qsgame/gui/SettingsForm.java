package qsgame.gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import qsgame.constant.FileAccess;
import qsgame.constant.SettingStyle;
import qsgame.engine.file.PathDriver;
import qsgame.game.Settings;

public class SettingsForm {
    private static final int MARGIN = 20;
    private static final TextField TXT_NAME = new TextField();
    private static final ObservableList<String> options = FXCollections.observableArrayList("4x4", "6x6", "8x8", "10x10");

    private final Settings settings;
    private Stage window;
    private boolean update = false;
    private String dimension;
    private String name;

    public SettingsForm(Settings settings) {
        this.settings = settings;
        this.dimension = settings.getDimension();
        this.name = settings.getName();
    }

    void open() {
        update = false;
        window = new Stage();
        window.setScene(setScene());
        window.setTitle("Settings");
        window.getIcons().add(new Image(PathDriver.getInstance().getPath(FileAccess.INTRO_ICO)));
        window.initModality(Modality.APPLICATION_MODAL);
        window.showAndWait();
    }

    private void close() {
        window.close();
        window = null;
    }

    private Scene setScene() {
        BorderPane root = new BorderPane();

        Button BTN_OK = new Button("Ok");
        BTN_OK.setStyle(SettingStyle.BTN_STYLE);
        BTN_OK.setOnAction(e -> clickOkButton());

        Button btnCancel = new Button("Cancel");
        btnCancel.setStyle(SettingStyle.BTN_STYLE);
        btnCancel.setOnAction(e -> clickCancelButton());

        HBox topBox = new HBox();
        topBox.setPrefHeight(MARGIN);

        HBox leftBox = new HBox();
        leftBox.setPrefWidth(MARGIN);

        HBox rightBox = new HBox();
        rightBox.setPrefWidth(MARGIN);

        HBox bottomBox = new HBox();
        bottomBox.setStyle(SettingStyle.BOTTOM_POS);
        bottomBox.setSpacing(20);
        bottomBox.getChildren().addAll(BTN_OK, btnCancel);

        root.setTop(topBox);
        root.setLeft(leftBox);
        root.setRight(rightBox);
        root.setBottom(bottomBox);
        root.setCenter(centerContainer());

        return new Scene(root);
    }

    private VBox centerContainer() {
        VBox centerBox = new VBox();
        centerBox.setSpacing(20);

        Label lblName = new Label("Name");
        lblName.setStyle(SettingStyle.LBL_NAME);
        Label lblHeight = new Label("Dimension");
        lblHeight.setStyle(SettingStyle.LBL_NAME);
        Label lblHeightAfter = new Label("icons");
        lblHeightAfter.setStyle(SettingStyle.LBL_NAME);

        TXT_NAME.setStyle(SettingStyle.TXT_NAME);
        TXT_NAME.setText(name);
        TXT_NAME.setPrefColumnCount(20);
        TXT_NAME.textProperty().addListener(e -> {
            name = TXT_NAME.getText();
            update = true;
        });

        ComboBox<String> cmbHeight = prepareCombo(dimension);
        cmbHeight.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            dimension = newValue;
            update = true;
        });

        HBox textBox = new HBox();
        textBox.setStyle(SettingStyle.NAME_SET);
        textBox.getChildren().addAll(lblName, TXT_NAME);

        HBox comboHeight = new HBox();
        comboHeight.setStyle(SettingStyle.NAME_SET);
        comboHeight.getChildren().addAll(lblHeight, cmbHeight, lblHeightAfter);

        centerBox.getChildren().addAll(textBox, comboHeight); //, comboWidth);

        return centerBox;
    }

    private ComboBox<String> prepareCombo(String value) {
        ComboBox<String> combo = new ComboBox<>(options);
        combo.setValue(value);
        combo.setStyle(SettingStyle.TXT_NAME);
        return combo;
    }

    private void clickOkButton() {
        if (update) {
            settings.setName(name);
            settings.setDimension(dimension);
        }
        close();
    }

    private void clickCancelButton() {
        if (update) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Save changes");
            alert.setHeaderText("Do You want to discard changes?");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(PathDriver.getInstance().getPath(FileAccess.HELP_ICO_PATH)));
            alert.showAndWait()
                    .filter(response -> response == ButtonType.OK)
                    .ifPresent(response -> close());
        } else {
            close();
        }
    }

}
