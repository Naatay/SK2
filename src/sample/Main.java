package sample;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.util.*;

public class Main extends Application {

    final String IDLE_LABEL_STYLE = "-fx-background-color: transparent;";
    final String HOVERED_LABEL_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";
    ArrayList<String> days = new ArrayList<String>(10);
    ArrayList<String> monthName = new ArrayList<String>(20);
    Map<String, Integer> numberOfDaysInMonth = new HashMap<String, Integer>();
    Scene scene;
    Calendar calendar;
    GridPane grid;
    ComboBox<String> months;
    ComboBox<Integer> years;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Calendar");
        scene = new Scene(root);
        primaryStage.setScene(scene);

        months = (ComboBox) scene.lookup("#month");
        years = (ComboBox) scene.lookup("#year");


        calendar = Calendar.getInstance();

        Collections.addAll(days, "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun");
        Collections.addAll(monthName, "January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December");

        months.getItems().addAll(
                monthName
        );
        months.setValue(calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));

        for (int i = 1995; i < 2025; i++) {
            years.getItems().add(i);
        }
        years.setValue(calendar.get(Calendar.YEAR));

        primaryStage.show();


        numberOfDaysInMonth.put("January", 31);
        numberOfDaysInMonth.put("February", 28);
        numberOfDaysInMonth.put("March", 31);
        numberOfDaysInMonth.put("April", 30);
        numberOfDaysInMonth.put("May", 31);
        numberOfDaysInMonth.put("June", 30);
        numberOfDaysInMonth.put("July", 31);
        numberOfDaysInMonth.put("August", 31);
        numberOfDaysInMonth.put("September", 30);
        numberOfDaysInMonth.put("October", 31);
        numberOfDaysInMonth.put("November", 30);
        numberOfDaysInMonth.put("December", 31);


        create();
        display();


        months.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                clear();
                display();
            }
        });

    }

    public void create() {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                Label label = new Label();
                label.setId(Integer.toString(i) + Integer.toString(j));
                label.setPrefWidth(50);
                label.setAlignment(Pos.CENTER);
                label.setStyle(IDLE_LABEL_STYLE);
                label.setOnMouseEntered(e -> label.setStyle(HOVERED_LABEL_STYLE));
                label.setOnMouseExited(e -> label.setStyle(IDLE_LABEL_STYLE));
                label.setVisible(false);
                grid = (GridPane) scene.lookup("#grid");
                grid.add(label, i, j);

            }
        }
    }

    public void clear() {
        for (int j = 0; j < 6; j++) {
            for (int i = 0; i < 7; i++) {
                String id = "#" + Integer.toString(i) + Integer.toString(j);
                Label label = (Label) scene.lookup(id);
                label.setText("");
                label.setVisible(false);
            }
        }
    }

    public void display() {
        grid = (GridPane) scene.lookup("#grid");
        String currMonth = months.getValue();
        int index = monthName.indexOf(currMonth);
        calendar.set(Calendar.MONTH, index);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDay = days.indexOf(calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.ENGLISH));
        int number = numberOfDaysInMonth.get(currMonth);
        if (months.getValue().equals("February") && years.getValue() % 4 == 0) {
            number++;
        }
        boolean brk = false;
        int temp = 1;
        int temp2 = 0;
        for (int j = 0; j < 6; j++) {
            if (brk) {
                break;
            }
            for (int i = 0; i < 7; i++) {
                if ((firstDay <= temp2) || j > 0) {
                    String id = "#" + Integer.toString(i) + Integer.toString(j);
                    Label label = (Label) scene.lookup(id);
                    label.setText(Integer.toString(temp));
                    label.setVisible(true);
                    number--;
                    temp++;
                    if (number == 0) {
                        brk = true;
                        break;
                    }
                }

                temp2++;
            }
        }
    }

}
