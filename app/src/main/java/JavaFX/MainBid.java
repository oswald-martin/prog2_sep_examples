package JavaFX;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MainBid extends Application {
    private Item itemToBid;
    private TextField fieldMyBid;
    private TextField fieldBestBid;
    private Button sendBid;
    private Button closeWindow;

    @Override
    public void start(Stage primaryStage) {
        itemToBid = new Item("Bild", 100 );
        // Initialisieren der verwendeten Steuerelemente
        // ...
        fieldBestBid.setText(String.valueOf(itemToBid.getBestBid()));

        // Anordnen der Steuerelemente - SceneGraph (not to be implemented)
        //...

        // Ereignisbehandlung hinzufügen
        addListenerToBestBid();
        addHandlerToSendBid();

        // Anzeigen des Fensters (not to be implemented)
        //...
        primaryStage.show();
    }

    // todo: Hinzufügen eines Listeners der das bestBid Textfeld aktualisiert, wenn er im Item ändert.
    private void addListenerToBestBid() {
        itemToBid.getBestBid().addListener(
            (observable, oldValue, newValue) -> fieldBestBid.textProperty().set("" + newValue)
        );
    }

    // todo: Hinzufügen des Handlers, der Item.bestBid aktualisiert, wenn ein neues Gebot gesendet wird.
    private void addHandlerToSendBid() {
        sendBid.setOnMouseClicked(
            event -> {
                try {
                    itemToBid.setBestBid(Double.parseDouble(fieldBestBid.textProperty().get()));
                } catch (Exception e) {
                    System.out.println("other bid is higher...");
                }
           });
        
        }
    
}


class Item {
    private String name;
    private DoubleProperty bestBid;

    public Item(String name, double startBid) {
        this.name = name;
        this.bestBid = new SimpleDoubleProperty();
        bestBid.set(startBid);
    }

    public synchronized void setBestBid(double value) throws IllegalArgumentException {
        if (value <= bestBid.get()) {
            throw new IllegalArgumentException("Value smaller than best bid.");
        }
        bestBid.set(value); 
    }

    public synchronized DoubleProperty getBestBid() { return bestBid; }
    public String getName() { return name; }
    
}
