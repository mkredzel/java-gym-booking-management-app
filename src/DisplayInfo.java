
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

public class DisplayInfo {

    /**
     * Method displays a window with search results. 
     * Search being a result.
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

        VBox layout = new VBox(20, label, okBtn);
        VBox.setMargin(label, new Insets(20, 0, 0, 0));
        layout.setAlignment(Pos.TOP_CENTER);
        okBtn.setMinWidth(100);
        okBtn.setStyle("-fx-font-size: 1.2em; ");

        Scene scene = new Scene(layout, 800, 450);
        window.setScene(scene);
        window.showAndWait();
    }
}