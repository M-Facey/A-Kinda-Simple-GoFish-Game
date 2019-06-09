
package a_kinda_simple_gofish_game;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

/**
 *
 * @author Murphy Facey
 */
public class Go_Fish_Controller implements Initializable {
    
    //Game Stuff
    
    @FXML private AnchorPane table;
    @FXML private List<Pane> deckedCards = new ArrayList<>();
    @FXML private List<Pane> playerCards = new ArrayList<>();
    private List<String> playerState = new ArrayList<>();
    @FXML private List<Pane> compCards = new ArrayList<>();
    
    private String[] cardNames = {
        "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS",
        "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH",
        "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC",
        "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD"
    };
    
    private List<String> realCardNames = new ArrayList<>(Arrays.asList(cardNames));
    @FXML private JFXButton dealButton;
    @FXML private JFXButton goFishButton;
    @FXML private Label playerPoints;
    @FXML private Label compPoints;
    
    @FXML private Pane gameOverBoard;
    @FXML private JFXButton resetGame; 
    @FXML private JFXButton toggleButton; 
    @FXML private Label gameCounter;
    @FXML private Pane compInfo;
    @FXML private Pane playerInfo;
    
    //Theme elements
    
    
    
    String gamePlay;
    Date date = new Date();
    SimpleDateFormat df = new SimpleDateFormat("E yyyy.MM.dd 'at' hh-mm-ss-SSS");
    
    
    @FXML private EventHandler selectHandler;
    
    @FXML
    private void setCardFace(Pane card) {
        Label l = (Label) card.getChildren().get(0);
        card.setBackground(
            new Background(
                new BackgroundImage(
                    new Image(
                        "/images/" + l.getText() +".png",
                        card.getPrefWidth(),
                        card.getPrefHeight(),
                        false,
                        true
                    ),
                    BackgroundRepeat.REPEAT, 
                    BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT   
                )
            )
        );
    }
    
    @FXML
    private void setCardBack(Pane card) {
        card.setBackground(
            new Background(
                new BackgroundImage(
                    new Image(
                        "/images/red_back_1.png",
                        card.getPrefWidth(),
                        card.getPrefHeight(),
                        false,
                        true
                    ),
                    BackgroundRepeat.REPEAT, 
                    BackgroundRepeat.NO_REPEAT, 
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT   
                )
            )
        );
    }
    
    @FXML
    private void setUpDeck() {
        //System.out.println("-----------------------------------\n" + "Game Started: " + df.format(date) + "\n-----------------------------------\n");
        for(int i = 0; i < 52; i++) {
            //Setting up the card before adding it to the deck
            Pane card = new Pane();
            card.setPrefSize(115.0, 176.0);
            card.setLayoutX(14);
            card.setLayoutY(213);
            setCardBack(card);
            card.setStyle("-fx-effect: dropshadow(three-pass-box, black, 5, 0, 0, 0);");
            
            Label label = new Label();
            label.setPrefSize(80.0, 120.0);
            label.setText(realCardNames.remove(new Random().nextInt(realCardNames.size())));
            label.setFont(Font.font("Times New Romans", 30.0));
            label.setOpacity(0.0);
            
            card.getChildren().add(label);
            deckedCards.add(card);
            table.getChildren().add(deckedCards.get(i));
        }
    }
    
    @FXML
    private void deal() {
        
        dealButton.setDisable(true);
        goFishButton.setDisable(false);
        
        compCards.add(deckedCards.remove(0));
        compCards.get(0).setLayoutX(138.0);
        compCards.get(0).setLayoutY(14.0);
        
        
        playerCards.add(deckedCards.remove(0));
        playerCards.get(0).setLayoutX(138.0);
        playerCards.get(0).setLayoutY(411.0);
        playerState.add("0");
        setCardFace(playerCards.get(0));
        
        for(int i = 1; i < 5; i++) {
            compCards.add(deckedCards.remove(0));
            compCards.get(i).setLayoutX(compCards.get(i - 1).getLayoutX() + 25.0);
            compCards.get(i).setLayoutY(14.0);
            
            
            playerCards.add(deckedCards.remove(0));
            playerCards.get(i).setLayoutX(playerCards.get(i - 1).getLayoutX() + 25.0);
            playerCards.get(i).setLayoutY(411.0);
            playerState.add("0");
            setCardFace(playerCards.get(i));
        }
        setSelectEvent();
    }
    
    @FXML
    private void dealPlayer() {
        playerCards.add(deckedCards.remove(0));
        playerCards.get(0).setLayoutX(138.0);
        playerCards.get(0).setLayoutY(411.0);
        playerState.add("0");
        setCardFace(playerCards.get(0));
        
        if(!deckedCards.isEmpty()) {
            int size;
            
            if(deckedCards.size() >= 5) {
                size = 5;
            } else {
                size = deckedCards.size();
            }
            
            for(int i = 0; i < size; i++) {
                playerCards.add(deckedCards.remove(0));
                playerCards.get(i + 1).setLayoutX(playerCards.get(i).getLayoutX() + 25.0);
                playerCards.get(i + 1).setLayoutY(411.0);
                playerState.add("0");
                setCardFace(playerCards.get(i + 1));
            }
        }
        setSelectEvent();
    }
    
    @FXML
    private void dealComp() {
        compCards.add(deckedCards.remove(0));
        compCards.get(0).setLayoutX(138.0);
        compCards.get(0).setLayoutY(14.0);
        
        if(!deckedCards.isEmpty()) {
            int size;
            
            if(deckedCards.size() >= 5) {
                size = 5;
            } else {
                size = deckedCards.size();
            }
            for(int i = 0; i < size; i++) {
                compCards.add(deckedCards.remove(0));
                compCards.get(i + 1).setLayoutX(compCards.get(i).getLayoutX() + 25.0);
                compCards.get(i + 1).setLayoutY(14.0);
            }
        }
    }
    
    @FXML
    private void setSelectEvent() {
        for(int i = 0; i < playerCards.size(); i++) {
            selectEvent(i);
        } 
    }
    
    @FXML
    private void removeSelectEvent() {
        for(int i = 0; i < playerCards.size() - 1; i++) {
            playerCards.get(i).setOnMouseClicked(selectHandler);
        } 
    }
    
    @FXML
    private void selectEvent(int pos) {
        final int i = pos;
        
        selectHandler = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            if(playerState.get(i).equals("0")){
                playerCards.get(i).setLayoutY(playerCards.get(i).getLayoutY() - 150);
                playerState.set(i, "1");
                playerCards.get(i).setStyle("-fx-effect: dropshadow(three-pass-box, rgb(70, 287, 217), 10, 0, 0, 0)");
            } else {
                playerCards.get(i).setLayoutY(playerCards.get(i).getLayoutY() + 150);
                playerState.set(i, "0");
                playerCards.get(i).setStyle("-fx-effect: dropshadow(three-pass-box, black, 5, 0, 0, 0)");
            } 
        };
        playerCards.get(i).setOnMouseClicked(selectHandler);
    }
    
    private int getFirstSelected() {
        return playerState.indexOf("1");
    }
    
    @FXML
    private void goFishPlayer() {
        if(!playerCards.isEmpty()) {
            int pos = getFirstSelected();
            if(pos != -1) {
                Label l = (Label) playerCards.get(pos).getChildren().get(0);
                List<Integer> positionList = checkCards(l.getText(), compCards);
                if(!positionList.isEmpty()) {
                    for(int num = 0; num < positionList.size(); num++) {
                        int cardPos = positionList.get(num) - num * 1;
                        int tablePos = table.getChildren().indexOf(compCards.get(cardPos));
                        
                        table.getChildren().add(table.getChildren().remove(tablePos));
                        compCards.get(cardPos).setOnMouseClicked(null);
                        
                        
                        playerCards.add(compCards.remove(cardPos));
                        playerCards.get(playerCards.size() - 1).setLayoutX(playerCards.get(playerCards.size() - 2).getLayoutX() + 25.0);
                        
                        if(playerState.get(playerCards.size() - 2).equals("1")) {
                            playerCards.get(playerCards.size() - 1).setLayoutY(playerCards.get(playerCards.size() - 2).getLayoutY() + 150.0);
                        } else {
                            playerCards.get(playerCards.size() - 1).setLayoutY(playerCards.get(playerCards.size() - 2).getLayoutY());
                        }
                        
                        setCardFace(playerCards.get(playerCards.size() - 1));
                        
                        if(cardPos == 0) {
                            for(int i = cardPos; i < compCards.size(); i++) {
                                compCards.get(i).setLayoutX(compCards.get(i).getLayoutX() - 25.0);
                            }
                        } else {
                            for(int i = cardPos; i < compCards.size(); i++) {
                                compCards.get(i).setLayoutX(compCards.get(i).getLayoutX() - 25.0);
                            }
                        }
                        
                        playerState.add("0");
                    }
                    
                    removeSelectEvent();
                    setSelectEvent();
                    getSetsOfFour(playerCards);
                    
                    
                } else if(!deckedCards.isEmpty()) {
                    int tablePos = table.getChildren().indexOf(deckedCards.get(0));
                    table.getChildren().add(table.getChildren().remove(tablePos));
                    
                    playerCards.add(deckedCards.remove(0));
                    playerCards.get(playerCards.size() - 1).setLayoutX(playerCards.get(playerCards.size() - 2).getLayoutX() + 25.0);
                    
                    if(playerState.get(playerCards.size() - 2).equals("1")) {
                        playerCards.get(playerCards.size() - 1).setLayoutY(playerCards.get(playerCards.size() - 2).getLayoutY() + 150);
                    } else {
                        playerCards.get(playerCards.size() - 1).setLayoutY(playerCards.get(playerCards.size() - 2).getLayoutY());
                    }
                    
                    setCardFace(playerCards.get(playerCards.size() - 1));
                    
                    playerState.add("0");
                    removeSelectEvent();
                    setSelectEvent();
                    getSetsOfFour(playerCards);
                    
                    goFishComp();
                }
            }    
        } else if(!deckedCards.isEmpty() && playerCards.isEmpty()) {
            dealPlayer();
        }
        
        if(isGameOver()) {
            gameOverBoard();
        }
    }
    
    @FXML
    private void goFishComp() {
        if(!compCards.isEmpty()) {
            int pos = new Random().nextInt(compCards.size());
            Label l = (Label) compCards.get(pos).getChildren().get(0);
            
            if(checkPlayerCards(l.getText(), playerCards) != -1) {
                
                    int cardPos = checkPlayerCards(l.getText(), playerCards);
                    int tablePos = table.getChildren().indexOf(playerCards.get(cardPos));
                    
                    table.getChildren().add(table.getChildren().remove(tablePos));
                    playerCards.get(cardPos).setOnMouseClicked(null);
                    
                    compCards.add(playerCards.remove(cardPos));

                    playerState.remove(cardPos);

                    compCards.get(compCards.size() - 1).setLayoutX(compCards.get(compCards.size() - 2).getLayoutX() + 25.0);
                    compCards.get(compCards.size() - 1).setLayoutY(compCards.get(compCards.size() - 2).getLayoutY());
                    
                    setCardBack(compCards.get(compCards.size() - 1));
                    
                    if(cardPos == 0) {
                        for(int i = cardPos; i < playerCards.size(); i++) {
                            playerCards.get(i).setLayoutX(playerCards.get(i).getLayoutX() - 25.0);
                        }
                    } else {
                        for(int i = cardPos; i < playerCards.size(); i++) {
                            playerCards.get(i).setLayoutX(playerCards.get(i).getLayoutX() - 25.0);
                        }
                    }
                   
                    goFishComp();
            } else if(!deckedCards.isEmpty()) {
                int tablePos = table.getChildren().indexOf(deckedCards.get(0));
                table.getChildren().add(table.getChildren().remove(tablePos));
                
                compCards.add(deckedCards.remove(0));
                compCards.get(compCards.size() - 1).setLayoutX(compCards.get(compCards.size() - 2).getLayoutX() + 25.0);
                compCards.get(compCards.size() - 1).setLayoutY(compCards.get(compCards.size() - 2).getLayoutY());
            }
            
            removeSelectEvent();
            setSelectEvent();
            getSetsOfFour(compCards);
            
        } else if(!deckedCards.isEmpty() && compCards.isEmpty()) {
            dealComp();
        }
        if(isGameOver()) {
            gameOverBoard();
        }
    }
    
    @FXML
    private List<Integer> checkCards(String cardName, List<Pane> cards) {
        List<Integer> positions = new ArrayList<>();
        for(int i = 0; i < cards.size(); i++) {
            Label l = (Label) cards.get(i).getChildren().get(0);
            if(l.getText().substring(0, 1).equals(cardName.substring(0, 1))) {
                positions.add(i);
            }
        }
        return positions;
    }
    
    @FXML
    private int checkPlayerCards(String cardName, List<Pane> cards) {
        for(int i = 0; i < cards.size(); i++) {
            Label l = (Label) cards.get(i).getChildren().get(0);
            if(l.getText().substring(0, 1).equals(cardName.substring(0, 1))) {
                return i;
            }
        }
        return -1;
    }
    
    @FXML
    private void getSetsOfFour(List<Pane> cards) {
        int pos = 0, k = 0;
        int[] poss = {-1,-1,-1,-1};
        for(int i = 0; i < cards.size(); i++) {
            for(int j = 0; j < cards.size(); j++) {
                Label l1 = (Label) cards.get(i).getChildren().get(0);
                Label l2 = (Label) cards.get(j).getChildren().get(0);
                
                if(l1.getText().substring(0, 1).equals(l2.getText().substring(0, 1))) {
                    pos++;
                    poss[k] = j;
                    k++;
                }
            }
            if(pos == 4) {
                removeCards(poss[0], poss[1], poss[2], poss[3], cards);
            }
            
            pos = k = 0;
            Arrays.fill(poss, -1);
        }
    }
    
    @FXML
    private void removeCards(int a, int b, int c, int d, List<Pane> cards) {
        
        if(playerCards.equals(cards)) {
            table.getChildren().remove(cards.remove(a - 0));
            playerState.remove(a - 0);
            table.getChildren().remove(cards.remove(b - 1));
            playerState.remove(b - 1);
            table.getChildren().remove(cards.remove(c - 2));
            playerState.remove(c - 2);
            table.getChildren().remove(cards.remove(d - 3));
            playerState.remove(d - 3);
            
            removeSelectEvent();
            setSelectEvent();
        } else {
            table.getChildren().remove(cards.remove(a - 0));
            table.getChildren().remove(cards.remove(b - 1));
            table.getChildren().remove(cards.remove(c - 2));
            table.getChildren().remove(cards.remove(d - 3));
        }
        
        shuffleDown(a - 0, cards);
        shuffleDown(b - 1, cards);
        shuffleDown(c - 2, cards);
        shuffleDown(d - 3, cards);
        
        if(playerCards.equals(cards)) {
            addPoints();
        } else {
            addPointsComp();
        }
    }
    
    @FXML
    private void shuffleDown(int pos, List<Pane> cards) {
        for(int i = pos; i < cards.size(); i++) {
            cards.get(i).setLayoutX(cards.get(i).getLayoutX() - 25.0);
        }
    } 
    
    @FXML
    private void addPoints() {
        int point = Integer.parseInt(playerPoints.getText());
        playerPoints.setText(String.valueOf(point + 1));
    }
    
    @FXML
    private void addPointsComp() {
        int point = Integer.parseInt(compPoints.getText());
        compPoints.setText(String.valueOf(point + 1));
    }
    
    private boolean isGameOver() {
        return deckedCards.isEmpty() && playerCards.isEmpty() && compCards.isEmpty();
    }
    
    @FXML
    private void gameOverBoard() {
        String winnerName;
        
        goFishButton.setDisable(true);
        
        if(Integer.parseInt(playerPoints.getText()) > Integer.parseInt(compPoints.getText())) {
            winnerName = "Player 1";
        } else {
            winnerName = "Player 2";
        }
        
        gameOverBoard = new Pane();
        gameOverBoard.setPrefSize(651.0, 572.0);
        gameOverBoard.setLayoutX(141.0);
        gameOverBoard.setLayoutY(14.0);
        gameOverBoard.setStyle("-fx-background-color: red");
       
        Label gameOver = new Label();
        
        gameOver.setText("Game Over");
        gameOver.setFont(Font.font("System", 72.0));
        
        Label winner = new Label();
        
        winner.setText("Winner : " + winnerName);
        winner.setPrefSize(500.0, 85.0);
        winner.setFont(Font.font("System", 58.0));
        
        resetGame = new JFXButton();
        resetGame.setText("Reset Game");
        resetGame.setStyle("-fx-background-color: rgba(255, 255, 255, 0.3)");
        resetGame.setButtonType(JFXButton.ButtonType.RAISED);
        resetGame.setFont(Font.font("System", 46.0));
        resetGame.setPrefSize(605.0, 100.0);
        resetGame.setRipplerFill(Paint.valueOf("black"));
        resetGame.setOnMouseClicked((MouseEvent event) -> {
            try {
                resetGame();
            } catch (InterruptedException ex) {
                Logger.getLogger(Go_Fish_Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        table.getChildren().add(gameOverBoard);
        gameOverBoard.getChildren().add(winner);
        gameOverBoard.getChildren().add(gameOver);
        gameOverBoard.getChildren().add(resetGame);
        
        gameOver.setLayoutX(147.0);
        gameOver.setLayoutY(22.0);
        
        winner.setLayoutX(37.0);
        winner.setLayoutY(127.0);
        
        resetGame.setLayoutX(23.0);
        resetGame.setLayoutY(451.0);
    }
    
    @FXML
    private void resetGame() throws InterruptedException {
        //This does not work so properly 
        //The pane seems to still appear where it is removed from the table
        //And that kinda make sense since it is not technically destroy
        int tablePos = table.getChildren().indexOf(gameOverBoard);
        table.getChildren().remove(tablePos);
        
        //This is if you want to just make it sure or something ...I don't really know anymore
        //gameOverBoard.setDisbale(true);
        //gameOverBoard.setOpacity(0);
        
        
        dealButton.setDisable(false);
        compPoints.setText("0");
        playerPoints.setText("0");
        gameCounter.setText(String.valueOf(Integer.parseInt(gameCounter.getText()) + 1));
        realCardNames.addAll(Arrays.asList(cardNames));
        setUpDeck();
    }
    
    @FXML
    private void toggleTheme() {
        String colorBlack = "black";
        String colorWhite = "white";
        String colorKindaBlack = "rgba(0, 0, 0, 0.5)";
        String colorKindaWhite = "rgba(255, 255, 255, 0.5)";
        
        if(toggleButton.getText().equals("DARK")) {
            setUpButton(toggleButton, colorWhite, colorBlack, colorWhite, "LIGHT");
            setUpButton(dealButton, colorWhite, colorBlack, colorWhite, dealButton.getText());
            setUpButton(goFishButton, colorWhite, colorBlack, colorWhite, goFishButton.getText());
            setUpPane(compInfo, colorKindaBlack, colorWhite);
            setUpPane(playerInfo, colorKindaBlack, colorWhite);
        } else {
            setUpButton(toggleButton, colorBlack, colorKindaWhite, colorWhite, "DARK");
            setUpButton(dealButton, colorBlack, colorKindaWhite, colorWhite, dealButton.getText());
            setUpButton(goFishButton, colorBlack, colorKindaWhite, colorWhite, goFishButton.getText());
            setUpPane(compInfo, colorKindaWhite, colorBlack);
            setUpPane(playerInfo, colorKindaWhite, colorBlack);
            table.requestFocus();
        }
    }
    
    @FXML
    private void setUpButton(JFXButton btn, String sh, String bk, String tc, String txt) {
        btn.setStyle("-fx-background-color: " + bk + ";" + 
                     "-fx-effect: dropshadow(three-pass-box, " + sh + ", 10, 0, 0, 0);");
        btn.setTextFill(Paint.valueOf(tc));
        btn.setText(txt);
    }
    
    @FXML
    private void setUpPane(Pane info, String bk, String tc) {
        info.setStyle("-fx-background-color: " + bk + ";");
        
        for(int i = 0; i < 3; i++) {
            Label l = (Label) info.getChildren().get(i);
            l.setTextFill(Paint.valueOf(tc));
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        Image img = new Image("/images/wood2.jpg");
        BackgroundImage bk = new BackgroundImage(
                img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, 
                BackgroundPosition.DEFAULT, 
                new BackgroundSize(table.getPrefWidth() + 20, table.getPrefHeight() + 20, false, false, false, false)
        );
        table.setBackground(new Background(bk));
        setUpDeck();
    }    
}
