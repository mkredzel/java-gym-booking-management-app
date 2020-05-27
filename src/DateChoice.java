
import java.time.LocalDate;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class DateChoice {

    /**
     * Method displays a small window with a date picker and two buttons. It is
     * used to search for the booking for the given date.
     */
    public static void display() {
        Stage window = new Stage();
        window.setTitle("Pick a date");

        Label info = new Label("Select the date");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now());
        Button searchBtn = new Button("Search");
        Button backBtn = new Button("Back");
        HBox submit = new HBox(10, searchBtn, backBtn);

        searchBtn.setOnAction(e -> {
            MyClient.sendRequestForMessage("Bookings for given date", datePicker.getValue().toString(), "", "");
        });

        backBtn.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10, info, datePicker, submit);
        VBox.setMargin(submit, new Insets(0, 0, 0, 50));
        layout.setAlignment(Pos.CENTER);

        //Lambda method to disable past dates from date picker
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        backBtn.setMinWidth(100);
        searchBtn.setMinWidth(100);
        info.setStyle("-fx-font-size: 1.2em; ");
        searchBtn.setStyle("-fx-font-size: 1.2em; ");
        backBtn.setStyle("-fx-font-size: 1.2em; ");

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
}
