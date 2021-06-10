package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import objects.Hand;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {

  private GameHost gameHost;

  @FXML
  private Button playButton;

  @FXML
  private Button standButton;

  @FXML
  private Button hitButton;

  @FXML
  private Label dealerScore;

  @FXML
  private Label playerScore;

  @FXML
  private Label dealerWinCount;

  @FXML
  private Label playerWinCount;

  @FXML
  private Label message;

  @FXML
  private HBox dealerCards;

  @FXML
  private HBox playerCards;

  @FXML
  private URL location;

  @FXML
  private ResourceBundle resources;

  private static int dealerWinCountInt = 1;
  private static int playerWinCountInt = 1;

  public Controller() {
  }

  @FXML
  private void initialize() {

    gameHost = new GameHost();
    dealerWinCount.setText("Dealer wins: ");
    playerWinCount.setText("Dealer wins: ");
    fillAnchorPane();

    playButton.setOnAction(event -> {
      startNewGame();
    });

    hitButton.setOnAction(event -> {
      gameHost.player.takeCard(gameHost.deck.drawCard());
    });

    standButton.setOnAction(event -> {
      while (gameHost.dealer.valueProperty().get() < 18) {
        gameHost.dealer.takeCard(gameHost.deck.drawCard());
      }

      endGame();
    });
  }

  @FXML
  private void startNewGame() {
    gameHost.playable.set(true);
    message.setText("");
    gameHost.deck.create();
    gameHost.dealer.reset();
    gameHost.player.reset();
    gameHost.dealer.takeCard(gameHost.deck.drawCard());
    gameHost.dealer.takeCard(gameHost.deck.drawCard());
    gameHost.player.takeCard(gameHost.deck.drawCard());
    gameHost.player.takeCard(gameHost.deck.drawCard());
  }

  private void endGame() {
    gameHost.playable.set(false);

    int dealerValue = gameHost.dealer.valueProperty().get();
    int playerValue = gameHost.player.valueProperty().get();
    String winner;

    if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
        || (dealerValue < 21 && dealerValue > playerValue)) {
      winner = "DEALER";
      dealerWinCount.setText("Dealer wins: " + dealerWinCountInt++);
    }
    else {
      winner = "PLAYER";
      playerWinCount.setText("Player wins: " + playerWinCountInt++);
    }
    gameHost.dealer.reset();
    gameHost.player.reset();
    message.setText(winner + " WON");
  }

  private void fillAnchorPane() {
    gameHost.dealer = new Hand(dealerCards.getChildren());
    gameHost.player = new Hand(playerCards.getChildren());

    VBox rightVBox = new VBox(20);
    rightVBox.setAlignment(Pos.CENTER);

    playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(gameHost.player.valueProperty().asString()));
    dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(gameHost.dealer.valueProperty().asString()));

    gameHost.player.valueProperty().addListener((obs, old, newValue) -> {
      if (newValue.intValue() >= 21) {
        endGame();
      }
    });

    gameHost.dealer.valueProperty().addListener((obs, old, newValue) -> {
      if (newValue.intValue() >= 21) {
        endGame();
      }
    });
  }
}
