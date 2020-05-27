
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class SearchBooking {

    public static String request;

    public static RadioButton radioButton1;
            
    /**
     * Method displays a window with four radio buttons. 
     * User can choose what data should be retrieved.
     */
    public static void displaySearch() {
        Stage window = new Stage();

        window.setTitle("Search Booking");

         radioButton1 = new RadioButton("List all existing bookings");
        RadioButton radioButton2 = new RadioButton("List given member's bookings");
        RadioButton radioButton3 = new RadioButton("List given personal trainer's bookings");
        RadioButton radioButton4 = new RadioButton("List all the bookings for the date");

        ToggleGroup radioGroup = new ToggleGroup();

        radioButton1.setToggleGroup(radioGroup);
        radioButton2.setToggleGroup(radioGroup);
        radioButton3.setToggleGroup(radioGroup);
        radioButton4.setToggleGroup(radioGroup);

        VBox choice = new VBox(10, radioButton1, radioButton2, radioButton3, radioButton4);

        Button searchBtn = new Button("Search");
        Button backBtn = new Button("Back");

        searchBtn.setOnAction(e -> {
            if (radioGroup.getSelectedToggle() == radioButton4) {
                request = "listDate";
                DateChoice.display();
            } else if (radioGroup.getSelectedToggle() == radioButton1) {
                request = "listAll";
                MyClient.sendRequestForMessage("List all existing bookings", "", "", "");
            } else if (radioGroup.getSelectedToggle() == radioButton3) {
                request = "listPT";
                EnterId.display("Enter Personal Trainer's id");
            } else {
                request = "listMember";
                EnterId.display("Enter Member's id");
            }
        });

        backBtn.setOnAction(e -> {
            window.close();
        });

        HBox submit = new HBox(10, searchBtn, backBtn);
        VBox pane = new VBox(choice, submit);
        searchBtn.setMinWidth(100);
        backBtn.setMinWidth(100);

        VBox.setMargin(choice, new Insets(40, 0, 0, 20));
        VBox.setMargin(submit, new Insets(30, 0, 0, 0));

        choice.setStyle("-fx-font-size: 1.2em; ");
        searchBtn.setStyle("-fx-font-size: 1.2em; ");
        backBtn.setStyle("-fx-font-size: 1.2em; ");

        submit.setAlignment(Pos.BASELINE_CENTER);
        Scene scene = new Scene(pane, 350, 280);
        window.setScene(scene);
        window.showAndWait();
    }
}