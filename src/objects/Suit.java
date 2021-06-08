package objects;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public enum Suit {
  HEARTS, DIAMONDS, CLUBS, SPADES;

  Image image;

  Suit() {
    FileInputStream fileInputStream;
    File f;
    try {
      switch (this.name().toLowerCase()) {
        case "hearts":
           f = new File("C:\\Users\\Vassily Aliseyko\\Documents\\Git\\balance\\blackjack-with-javafx\\res\\hearts.png");
          break;
        case "diamonds":
           f = new File("C:\\Users\\Vassily Aliseyko\\Documents\\Git\\balance\\blackjack-with-javafx\\res\\diamonds.png");
          break;
        case "clubs":
           f = new File("C:\\Users\\Vassily Aliseyko\\Documents\\Git\\balance\\blackjack-with-javafx\\res\\clubs.png");
          break;
        case "spades":
           f = new File("C:\\Users\\Vassily Aliseyko\\Documents\\Git\\balance\\blackjack-with-javafx\\res\\spades.png");
          break;
        default:
          f = new File("C:\\Users\\Vassily Aliseyko\\Documents\\Git\\balance\\blackjack-with-javafx\\res\\clubs.png");
          break;
      }
      fileInputStream = new FileInputStream(f);
      this.image = new Image(fileInputStream,
          32, 32, true, true);
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }

  }
}
