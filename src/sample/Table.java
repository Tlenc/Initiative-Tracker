package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

public class Table {
    private final SimpleStringProperty rInit, rAc, rHp;
    private final SimpleStringProperty rName;


    public String getrInit() {
        return rInit.get();
    }

    public SimpleStringProperty rInitProperty() {
        return rInit;
    }

    public void setrInit(String rInit) {
        this.rInit.set(rInit);
    }

    public String getrAc() {
        return rAc.get();
    }

    public SimpleStringProperty rAcProperty() {
        return rAc;
    }

    public void setrAc(String rAc) {
        this.rAc.set(rAc);
    }

    public String getrHp() {
        return rHp.get();
    }

    public SimpleStringProperty rHpProperty() {
        return rHp;
    }

    public void setrHp(String rHp) {
        this.rHp.set(rHp);
    }

    public String getrName() {
        return rName.get();
    }

    public SimpleStringProperty rNameProperty() {
        return rName;
    }

    public void setrName(String rName) {
        this.rName.set(rName);
    }

    public Table(String rName, String rInit, String rAc, String rHp) {

        this.rName = new SimpleStringProperty(rName);
        this.rInit = new SimpleStringProperty(rInit);
        this.rAc = new SimpleStringProperty(rAc);
        this.rHp = new SimpleStringProperty(rHp);
    }

    static class EditingCell extends TableCell<Table, String> {


        private TextField textField;

        public EditingCell() {
        }

        @Override
        public void startEdit() {
            if (!isEmpty()) {
                super.startEdit();
                createTextField();
                setText(null);
                setGraphic(textField);
                textField.selectAll();
            }
        }

        @Override
        public void cancelEdit() {
            super.cancelEdit();

            setText((String) getItem());
            setGraphic(null);
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(null);
                }
            }
        }

        private void createTextField() {
            textField = new TextField(getString());
            textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
            textField.focusedProperty().addListener(
                    (ObservableValue<? extends Boolean> arg0,
                     Boolean arg1, Boolean arg2) -> {
                        if (!arg2) {
                            commitEdit(textField.getText());
                        }
                    });
        }

        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }

}
