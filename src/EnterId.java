
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class EnterId {

    /**
     * Method displays a window with a text field. Member ID OR PT ID to be
     * entered by user.
     *
     * @param label
     */
    public static void display(String label) {
        Stage window = new Stage();
        window.setTitle("Enter id");
        Label info = new Label(label);
        TextField idText = new TextField();
        Button searchBtn = new Button("Search");
        Button backBtn = new Button("Back");
        HBox submit = new HBox(10, searchBtn, backBtn);

        searchBtn.setOnAction(e -> {
            if (label.equals("Enter Personal Trainer's id")) {
                MyClient.sendRequestForMessage("Bookings for given PT", "", "", idText.getText());
            } else {
                MyClient.sendRequestForMessage("Bookings for given member", "", idText.getText(), "");
            }
        });

        backBtn.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10, info, idText, submit);
        VBox.setMargin(submit, new Insets(0, 0, 0, 50));
        backBtn.setMinWidth(100);
        idText.setMaxWidth(100);
        searchBtn.setMinWidth(100);
        info.setStyle("-fx-font-size: 1.2em; ");
        backBtn.setStyle("-fx-font-size: 1.2em; ");
        searchBtn.setStyle("-fx-font-size: 1.2em; ");
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
}