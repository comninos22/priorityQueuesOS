/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package priorityQueues;

import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author CHRISTOS
 */
public class Controller implements Initializable {

    @FXML
    private ComboBox<?> priorityBox;

    @FXML
    private TextField arrivalTimeField;

    @FXML
    private TextField burstTimeField;
    @FXML
    private VBox displayBeforeExecute;

    @FXML
    private TextField quantumField;
    LinkedList<Process> toBeExecutedProcesses = new LinkedList();
    @FXML
    private VBox displayAfterExecution;

    @FXML
    void addProcess(ActionEvent event) {
        try {
            int priority = Integer.parseInt(priorityBox.getValue().toString());
            int burstTime = Integer.parseInt(burstTimeField.getText());
            int arrivalTime = Integer.parseInt(arrivalTimeField.getText());
            HBox h = new HBox();
            Process p = new Process(arrivalTime, burstTime, priority);
            displayBeforeExecute.getChildren().add(new Text(p.miniToString()));

            displayBeforeExecute.alignmentProperty().set(Pos.CENTER);
            burstTimeField.setText("");
            arrivalTimeField.setText("");
            toBeExecutedProcesses.add(p);

        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Input only numbers", ButtonType.CLOSE);
            a.show();
        }

    }

    @FXML
    void clear(ActionEvent event) {
        burstTimeField.setText("");
        arrivalTimeField.setText("");
        quantumField.setText("");
        displayAfterExecution.getChildren().removeAll(displayAfterExecution.getChildren());
        displayBeforeExecute.getChildren().removeAll(displayBeforeExecute.getChildren());
        Process.restart();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        LinkedList s = new LinkedList();
        for (int i = 0; i < 7; i++) {
            s.add(i + 1);
        }
        priorityBox.setItems(FXCollections.observableArrayList(s));
        displayAfterExecution.alignmentProperty().set(Pos.TOP_CENTER);
    }

    @FXML
    void start(ActionEvent event) {

        try {
            PriorityQueuesScheduler scheduler = new PriorityQueuesScheduler(toBeExecutedProcesses, Integer.parseInt(quantumField.getText()));
            LinkedList<Process> afterExecute = scheduler.executeProcesses();
            String tat = "average Turn Around Time: " + scheduler.averageTurnaroundTime();
            String wt = "average Waiting Time: " + scheduler.averageWaitingTime();
            String rt = "average Response Time: " + scheduler.averageResponseTime();
            while (!afterExecute.isEmpty()) {
                displayAfterExecution.getChildren().addAll(new Text(afterExecute.poll().toString()));
            }
            displayAfterExecution.getChildren().addAll(new Text(tat), new Text(wt), new Text(rt));
        } catch (NumberFormatException e) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Input only numbers", ButtonType.CLOSE);
            a.show();
        }

    }
}
