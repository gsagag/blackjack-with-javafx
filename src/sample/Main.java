package sample;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import objects.Deck;
import objects.Hand;

import java.net.URL;

public class Main extends Application {

  private Deck deck = new Deck();
  private Hand dealer, player;
  private Text message = new Text();

  private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

  private HBox dealerCards = new HBox(20);
  private HBox playerCards = new HBox(20);
  private AnchorPane rootPane;

  private void startNewGame() {
    playable.set(true);
    message.setText("");

    deck.create();

    dealer.reset();
    player.reset();

    dealer.takeCard(deck.drawCard());
    dealer.takeCard(deck.drawCard());
    player.takeCard(deck.drawCard());
    player.takeCard(deck.drawCard());
  }

  private void endGame() {
    playable.set(false);

    int dealerValue = dealer.valueProperty().get();
    int playerValue = player.valueProperty().get();
    String winner;

    if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
        || (dealerValue < 21 && dealerValue > playerValue)) {
      winner = "DEALER";
    }
    else {
      winner = "PLAYER";
    }

    message.setText(winner + " WON");
  }

  private void fillAnchorPane() {
    dealer = new Hand(dealerCards.getChildren());
    player = new Hand(playerCards.getChildren());

    HBox dealerCardHBox = (HBox) rootPane.getChildren().get(0);
    HBox menuHBox = (HBox) rootPane.getChildren().get(1);
    HBox playerCardsHBox = (HBox) rootPane.getChildren().get(2);

    rootPane.setStyle("-fx-background-color: rgba(153,153,153,0)");

    Text dealerScore = new Text("Dealer: ");
    Text playerScore = new Text("Player: ");

    // RIGHT

    VBox rightVBox = new VBox(20);
    rightVBox.setAlignment(Pos.CENTER);

    Button btnPlay = new Button("PLAY");
    Button btnHit = new Button("HIT");
    Button btnStand = new Button("STAND");


    HBox buttonsHBox = new HBox(15, btnHit, btnStand);
    buttonsHBox.setAlignment(Pos.CENTER);

    playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
    dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));

    player.valueProperty().addListener((obs, old, newValue) -> {
      if (newValue.intValue() >= 21) {
        endGame();
      }
    });

    dealer.valueProperty().addListener((obs, old, newValue) -> {
      if (newValue.intValue() >= 21) {
        endGame();
      }
    });

    // INIT BUTTONS

    btnPlay.setOnAction(event -> {
      startNewGame();
    });

    btnHit.setOnAction(event -> {
      player.takeCard(deck.drawCard());
    });

    btnStand.setOnAction(event -> {
      while (dealer.valueProperty().get() < 17) {
        dealer.takeCard(deck.drawCard());
      }

      endGame();
    });

    dealerCardHBox.getChildren().addAll(dealerCards);
    playerCardsHBox.getChildren().addAll(playerCards);
    menuHBox.getChildren().addAll(dealerScore, playerScore, message, btnPlay, buttonsHBox);

  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader loader = new FXMLLoader();
    loader.setLocation(new URL("file:///C:\\Users\\Vassily Aliseyko\\Documents\\Git\\balance\\blackjack-with-javafx\\src\\sample\\sample.fxml"));
    rootPane = loader.<AnchorPane>load();
    fillAnchorPane();
    Scene scene = new Scene(rootPane);
    primaryStage.setScene(scene);
    primaryStage.setWidth(600);
    primaryStage.setHeight(400);
    primaryStage.setResizable(true);
    primaryStage.setTitle("BlackJack");
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
