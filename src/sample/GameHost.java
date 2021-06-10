package sample;

import javafx.beans.property.SimpleBooleanProperty;
import objects.Deck;
import objects.Hand;

public class GameHost {
  public Deck deck = new Deck();
  public Hand dealer, player;

  public SimpleBooleanProperty playable = new SimpleBooleanProperty(false);


}
