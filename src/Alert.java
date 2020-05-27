
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class Alert {

    /**
     * Method displays the alert window with desirable title and message.
     * Especially, it is used for validation.
     * 
     * @param title
     * @param message 
     */
    public static void display(String title, String message) {
        Stage window = new Stage();
        window.setTitle(title);
        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-font-size: 1.2em; ");
        label.setWrapText(true);

        Button okBtn = new Button("OK");

        okBtn.setOnAction(e -> {
            window.close();
        });

        VBox layout = new VBox(10, label, okBtn);
        layout.setAlignment(Pos.CENTER);
        VBox.setMargin(label, new Insets(10, 0, 0, 20));

        okBtn.setMinWidth(100);
        okBtn.setStyle("-fx-font-size: 1.2em; ");

        Scene scene = new Scene(layout, 300, 150);
        window.setScene(scene);
        window.showAndWait();
    }
}