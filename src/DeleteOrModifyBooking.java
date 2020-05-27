
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class DeleteOrModifyBooking {

    static TextField confNoText = new TextField("");

    /**
     * Method displays tiny window with the text field.
     * Confirmation # to be entered by the user.
     * 
     * @param title
     * @param btnText 
     */
    public static void confirmationRequest(String title, String btnText) {
        Stage window = new Stage();
        window.setTitle(title);
        Label confNo = new Label("Confirmation number");
        Button submitBtn = new Button(btnText);
        Button backBtn = new Button("Back");

        submitBtn.setOnAction(e -> {
            if (btnText.equals("Delete")) {
                MyClient.sendRequestForDeletion(confNoText.getText());
            } else {
                MyClient.sendRequestForRecord(confNoText.getText());
            }
            DeleteOrModifyBooking.confNoText.setText("");
            window.close();
        });

        backBtn.setOnAction(e -> {
            DeleteOrModifyBooking.confNoText.setText("");
            window.close();
        });

        HBox submit = new HBox(10, submitBtn, backBtn);
        VBox pane = new VBox(10, confNo, confNoText, submit);
        
        confNoText.setMaxWidth(150);
        submitBtn.setMinWidth(100);
        backBtn.setMinWidth(100);
        confNo.setStyle("-fx-font-size: 1.2em; ");
        submitBtn.setStyle("-fx-font-size: 1.2em; ");
        backBtn.setStyle("-fx-font-size: 1.2em; ");
        pane.setAlignment(Pos.CENTER);
        submit.setAlignment(Pos.CENTER);

        Scene scene = new Scene(pane, 320, 150);
        window.setScene(scene);
        window.showAndWait();
    }
}
