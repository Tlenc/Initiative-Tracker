package sample;


import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    public Button NextButton;
    public Button PreviousButton;
    public Button AddButton;
    public Button DeleteButton;
    public Button IntReset;
    public TableView<Table> TableId;
    public TableColumn<Table, String> CollumName;
    public TableColumn<Table, String> CollumInit;
    public TableColumn<Table, String> CollumHp;
    public TableColumn<Table, String> CollumAc;
    public TextField Name;
    public TextField Initiative;
    public TextField Hp;
    public TextField Ac;

    public TextArea output;
    public TextField input;


    public int i = 0;
    static String name;
    static String Init;
    static String health;
    static String ac;
    public String InputMessage;

    public void OnIntReset() {
        i = 0;

    }

    public void OnNextButton() {
        TableColumn<Table, String> column = CollumInit;
        List<String> columnData = new ArrayList<>();
        for (Table item : TableId.getItems()) {
            columnData.add(column.getCellObservableValue(item).getValue());
        }
        System.out.print(columnData.size());
        if (i < columnData.size() && i != columnData.size()) {

            TableId.requestFocus();
            TableId.getSelectionModel().select(i);
            TableId.getFocusModel().focus(i);
            TableId.setStyle("-fx-background-color:lightcoral");
            i++;
        } else {
            i = 0;
        }


    }

    public void OnPreviousButton() {
        TableColumn<Table, String> column = CollumInit;
        List<String> columnData = new ArrayList<>();
        for (Table item : TableId.getItems()) {
            columnData.add(column.getCellObservableValue(item).getValue());
        }
        if (i < columnData.size() && i != columnData.size()) {
            i = i - 1;
            TableId.requestFocus();
            TableId.getSelectionModel().select(i);
            TableId.getFocusModel().focus(i);
            TableId.setStyle("-fx-background-color:lightcoral");

        } else {
            i = 0;
        }

    }


    public void OnAddButton() {

        name = Name.getText();
        Init = Initiative.getText();
        health = Hp.getText();
        ac = Ac.getText();

        Table table = new Table(name, Init, ac, health);
        TableId.getItems().add(table);

    }

    public void OnDeleteButton() {
        Table selectedItem = TableId.getSelectionModel().getSelectedItem();
        TableId.getItems().remove(selectedItem);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        CollumName.setCellValueFactory(new PropertyValueFactory<Table, String>("rName"));
        CollumName.setCellFactory(cellFactory);
        CollumName.setOnEditCommit(
                (TableColumn.CellEditEvent<Table, String> t) -> {
                    ((Table) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setrName(t.getNewValue());
                });
        CollumInit.setCellValueFactory(new PropertyValueFactory<Table, String>("rInit"));
        CollumInit.setCellFactory(cellFactory);
        CollumInit.setOnEditCommit(
                (TableColumn.CellEditEvent<Table, String> t) -> {
                    ((Table) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setrInit(t.getNewValue());
                });
        CollumHp.setCellValueFactory(new PropertyValueFactory<Table, String>("rHp"));
        CollumHp.setCellFactory(cellFactory);
        CollumHp.setOnEditCommit(
                (TableColumn.CellEditEvent<Table, String> t) -> {
                    ((Table) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setrHp(t.getNewValue());
                });
        CollumAc.setCellValueFactory(new PropertyValueFactory<Table, String>("rAc"));
        CollumAc.setCellFactory(cellFactory);
        CollumAc.setOnEditCommit(
                (TableColumn.CellEditEvent<Table, String> t) -> {
                    ((Table) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setrAc(t.getNewValue());
                });
        TableId.setEditable(true);
        output.setEditable(false);


    }

    Callback<TableColumn<Table, String>,
            TableCell<Table, String>> cellFactory
            = (TableColumn<Table, String> p) -> new Table.EditingCell();


    public void OnInput(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            InputMessage = input.getText();
            if (InputMessage.charAt(0) == '-') {
                Table selectedItem = TableId.getSelectionModel().getSelectedItem();
                int currentHealth = (Integer.valueOf(selectedItem.getrHp()) + Integer.valueOf(InputMessage));
                String curHealth = Integer.toString(currentHealth);
                selectedItem.setrHp(curHealth);
                output.appendText(InputMessage + " Damage done to " + selectedItem.getrName() + "\n");
                input.clear();

            } else if (InputMessage.charAt(0) == '+') {
                Table selectedItem = TableId.getSelectionModel().getSelectedItem();
                if (Integer.valueOf(selectedItem.getrHp()) < 0) {
                    selectedItem.setrHp("0");
                }
                int currentHealth = (Integer.valueOf(selectedItem.getrHp()) + Integer.valueOf(InputMessage));
                String curHealth = Integer.toString(currentHealth);
                selectedItem.setrHp(curHealth);
                output.appendText(InputMessage + " Healing done to " + selectedItem.getrName() + "\n");
                input.clear();

            } else {
                output.setText("Uknown command");
                input.clear();
            }
        }
    }

}
