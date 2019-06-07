
package another_card_game;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
import javafx.scene.text.Font;

/**
 *
 * @author Murphy Facey
 */
public class Go_Fish_Controller implements Initializable {
    
    //Game Stuff
    
    @FXML private AnchorPane table;
    @FXML private List<Pane> deckedCards = new ArrayList<>();
    @FXML private List<Pane> userCards = new ArrayList<>();
    private List<String> userState = new ArrayList<>();
    @FXML private List<Pane> enemyCards = new ArrayList<>();
    
    private String[] cardNames = {
        "AS", "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS",
        "AH", "2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH",
        "AC", "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC",
        "AD", "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD"
    };
    
    private List<String> realCardNames = new ArrayList<>(Arrays.asList(cardNames));
    @FXML private Button dealButton;
    @FXML private Button goFishButton;
    @FXML private Label userPoints;
    @FXML private Label enemyPoints;
    
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
            card.setPrefSize(115.0, 175.0);
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
        
        enemyCards.add(deckedCards.remove(0));
        enemyCards.get(0).setLayoutX(138.0);
        enemyCards.get(0).setLayoutY(14.0);
        
        
        userCards.add(deckedCards.remove(0));
        userCards.get(0).setLayoutX(138.0);
        userCards.get(0).setLayoutY(411.0);
        userState.add("0");
        setCardFace(userCards.get(0));
        
        for(int i = 1; i < 5; i++) {
            enemyCards.add(deckedCards.remove(0));
            enemyCards.get(i).setLayoutX(enemyCards.get(i - 1).getLayoutX() + 25.0);
            enemyCards.get(i).setLayoutY(14.0);
            
            
            userCards.add(deckedCards.remove(0));
            userCards.get(i).setLayoutX(userCards.get(i - 1).getLayoutX() + 25.0);
            userCards.get(i).setLayoutY(411.0);
            userState.add("0");
            setCardFace(userCards.get(i));
        }
        setSelectEvent();
    }
    
    @FXML
    private void dealUser() {
        userCards.add(deckedCards.remove(0));
        userCards.get(0).setLayoutX(138.0);
        userCards.get(0).setLayoutY(411.0);
        userState.add("0");
        setCardFace(userCards.get(0));
        
        if(!deckedCards.isEmpty()) {
            int size;
            
            if(deckedCards.size() >= 5) {
                size = 5;
            } else {
                size = deckedCards.size();
            }
            
            for(int i = 0; i < size; i++) {
                userCards.add(deckedCards.remove(0));
                userCards.get(i + 1).setLayoutX(userCards.get(i).getLayoutX() + 25.0);
                userCards.get(i + 1).setLayoutY(411.0);
                userState.add("0");
                setCardFace(userCards.get(i + 1));
            }
        }
        setSelectEvent();
    }
    
    @FXML
    private void dealEnemy() {
        enemyCards.add(deckedCards.remove(0));
        enemyCards.get(0).setLayoutX(138.0);
        enemyCards.get(0).setLayoutY(14.0);
        
        if(!deckedCards.isEmpty()) {
            int size;
            
            if(deckedCards.size() >= 5) {
                size = 5;
            } else {
                size = deckedCards.size();
            }
            for(int i = 0; i < size; i++) {
                enemyCards.add(deckedCards.remove(0));
                enemyCards.get(i + 1).setLayoutX(enemyCards.get(i).getLayoutX() + 25.0);
                enemyCards.get(i + 1).setLayoutY(14.0);
            }
        }
        
    }
    
    @FXML
    private void setSelectEvent() {
        for(int i = 0; i < userCards.size(); i++) {
            selectEvent(i);
        } 
    }
    
    @FXML
    private void removeSelectEvent() {
        for(int i = 0; i < userCards.size() - 1; i++) {
            userCards.get(i).setOnMouseClicked(selectHandler);
        } 
    }
    
    @FXML
    private void selectEvent(int pos) {
        final int i = pos;
        
        selectHandler = (EventHandler<MouseEvent>) (MouseEvent event) -> {
            if(userState.get(i).equals("0")){
                userCards.get(i).setLayoutY(userCards.get(i).getLayoutY() - 150);
                userState.set(i, "1");
                userCards.get(i).setStyle("-fx-effect: dropshadow(three-pass-box, rgb(70, 287, 217), 10, 0, 0, 0)");
            } else {
                userCards.get(i).setLayoutY(userCards.get(i).getLayoutY() + 150);
                userState.set(i, "0");
                userCards.get(i).setStyle("-fx-effect: dropshadow(three-pass-box, black, 5, 0, 0, 0)");
            } 
        };
        userCards.get(i).setOnMouseClicked(selectHandler);
    }
    
    private int getFirstSelected() {
        return userState.indexOf("1");
    }
    
    @FXML
    private void goFishUser() {
        if(!userCards.isEmpty()) {
            int pos = getFirstSelected();
            if(pos != -1) {
                Label l = (Label) userCards.get(pos).getChildren().get(0);
                List<Integer> positionList = checkCards(l.getText(), enemyCards);
                if(!positionList.isEmpty()) {
                    for(int num = 0; num < positionList.size(); num++) {
                        int cardPos = positionList.get(num) - num * 1;
                        int tablePos = table.getChildren().indexOf(enemyCards.get(cardPos));
                        
                        table.getChildren().add(table.getChildren().remove(tablePos));
                        enemyCards.get(cardPos).setOnMouseClicked(null);
                        
                        
                        userCards.add(enemyCards.remove(cardPos));
                        userCards.get(userCards.size() - 1).setLayoutX(userCards.get(userCards.size() - 2).getLayoutX() + 25.0);
                        
                        if(userState.get(userCards.size() - 2).equals("1")) {
                            userCards.get(userCards.size() - 1).setLayoutY(userCards.get(userCards.size() - 2).getLayoutY() + 150.0);
                        } else {
                            userCards.get(userCards.size() - 1).setLayoutY(userCards.get(userCards.size() - 2).getLayoutY());
                        }
                        
                        setCardFace(userCards.get(userCards.size() - 1));
                        
                        if(cardPos == 0) {
                            for(int i = cardPos; i < enemyCards.size(); i++) {
                                enemyCards.get(i).setLayoutX(enemyCards.get(i).getLayoutX() - 25.0);
                            }
                        } else {
                            for(int i = cardPos; i < enemyCards.size(); i++) {
                                enemyCards.get(i).setLayoutX(enemyCards.get(i).getLayoutX() - 25.0);
                            }
                        }
                        
                        userState.add("0");
                    }
                    
                    removeSelectEvent();
                    setSelectEvent();
                    getSetsOfFour(userCards);
                    
                    
                } else if(!deckedCards.isEmpty()) {
                    int tablePos = table.getChildren().indexOf(deckedCards.get(0));
                    table.getChildren().add(table.getChildren().remove(tablePos));
                    
                    userCards.add(deckedCards.remove(0));
                    userCards.get(userCards.size() - 1).setLayoutX(userCards.get(userCards.size() - 2).getLayoutX() + 25.0);
                    
                    if(userState.get(userCards.size() - 2).equals("1")) {
                        userCards.get(userCards.size() - 1).setLayoutY(userCards.get(userCards.size() - 2).getLayoutY() + 150);
                    } else {
                        userCards.get(userCards.size() - 1).setLayoutY(userCards.get(userCards.size() - 2).getLayoutY());
                    }
                    
                    setCardFace(userCards.get(userCards.size() - 1));
                    
                    userState.add("0");
                    removeSelectEvent();
                    setSelectEvent();
                    getSetsOfFour(userCards);
                    
                    goFishEnemy();
                }
            }    
        } else if(!deckedCards.isEmpty() && userCards.isEmpty()) {
            dealUser();
        }
        
        if(isGameOver()) {
            gameOverBoard();
        }
    }
    
    @FXML
    private void goFishEnemy() {
        if(!enemyCards.isEmpty()) {
            int pos = new Random().nextInt(enemyCards.size());
            Label l = (Label) enemyCards.get(pos).getChildren().get(0);
            
            if(checkUserCards(l.getText(), userCards) != -1) {
                
                    int cardPos = checkUserCards(l.getText(), userCards);
                    int tablePos = table.getChildren().indexOf(userCards.get(cardPos));
                    
                    table.getChildren().add(table.getChildren().remove(tablePos));
                    userCards.get(cardPos).setOnMouseClicked(null);
                    
                    enemyCards.add(userCards.remove(cardPos));
                
                    
                    
                    userState.remove(cardPos);
                    
                    
                    enemyCards.get(enemyCards.size() - 1).setLayoutX(enemyCards.get(enemyCards.size() - 2).getLayoutX() + 25.0);
                    enemyCards.get(enemyCards.size() - 1).setLayoutY(enemyCards.get(enemyCards.size() - 2).getLayoutY());
                    
                    setCardBack(enemyCards.get(enemyCards.size() - 1));
                    
                    if(cardPos == 0) {
                        for(int i = cardPos; i < userCards.size(); i++) {
                            userCards.get(i).setLayoutX(userCards.get(i).getLayoutX() - 25.0);
                        }
                    } else {
                        for(int i = cardPos; i < userCards.size(); i++) {
                            userCards.get(i).setLayoutX(userCards.get(i).getLayoutX() - 25.0);
                        }
                    }
                    
                    
                    goFishEnemy();
            } else if(!deckedCards.isEmpty()) {
                int tablePos = table.getChildren().indexOf(deckedCards.get(0));
                table.getChildren().add(table.getChildren().remove(tablePos));
                
                enemyCards.add(deckedCards.remove(0));
                enemyCards.get(enemyCards.size() - 1).setLayoutX(enemyCards.get(enemyCards.size() - 2).getLayoutX() + 25.0);
                enemyCards.get(enemyCards.size() - 1).setLayoutY(enemyCards.get(enemyCards.size() - 2).getLayoutY());
            }
            
            removeSelectEvent();
            setSelectEvent();
            getSetsOfFour(enemyCards);
            
        } else if(!deckedCards.isEmpty() && enemyCards.isEmpty()) {
            dealEnemy();
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
    private int checkUserCards(String cardName, List<Pane> cards) {
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
        
        if(userCards.equals(cards)) {
            table.getChildren().remove(cards.remove(a - 0));
            userState.remove(a - 0);
            table.getChildren().remove(cards.remove(b - 1));
            userState.remove(b - 1);
            table.getChildren().remove(cards.remove(c - 2));
            userState.remove(c - 2);
            table.getChildren().remove(cards.remove(d - 3));
            userState.remove(d - 3);
            
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
        
        if(userCards.equals(cards)) {
            addPoints();
        } else {
            addPointsEnemy();
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
        int point = Integer.parseInt(userPoints.getText());
        userPoints.setText(String.valueOf(point + 1));
    }
    
    @FXML
    private void addPointsEnemy() {
        int point = Integer.parseInt(enemyPoints.getText());
        enemyPoints.setText(String.valueOf(point + 1));
    }
    
    private boolean isGameOver() {
        return deckedCards.isEmpty() && userCards.isEmpty() && enemyCards.isEmpty();
    }
    
    @FXML
    private void gameOverBoard() {
        String winnerName;
        
        goFishButton.setDisable(true);
        
        if(Integer.parseInt(userPoints.getText()) > Integer.parseInt(enemyPoints.getText())) {
            winnerName = "User";
        } else {
            winnerName = "Enemy";
        }
        
        Pane gameOverBoard = new Pane();
        gameOverBoard.setPrefSize(651.0, 572.0);
        gameOverBoard.setLayoutX(141.0);
        gameOverBoard.setLayoutY(14.0);
        gameOverBoard.setStyle("-fx-background-color: red");
       
        Label gameOver = new Label();
        
        gameOver.setText("Game Over");
        gameOver.setFont(Font.font("System", 72.0));
        
        Label winner = new Label();
        
        winner.setText("Winner : " + winnerName);
        winner.setPrefSize(400.0, 85.0);
        winner.setFont(Font.font("System", 58.0));
        
        table.getChildren().add(gameOverBoard);
        gameOverBoard.getChildren().add(winner);
        gameOverBoard.getChildren().add(gameOver);
        
        gameOver.setLayoutX(147.0);
        gameOver.setLayoutY(22.0);
        winner.setLayoutX(37.0);
        winner.setLayoutY(127.0);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        Image img = new Image("/images/wood3.jpg");
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
