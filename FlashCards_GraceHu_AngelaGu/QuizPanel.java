/********************************************************************
  * FILENAME:  QuizPanel.java
  * WHO:  Angela Gu and Grace Hu
  * WHEN:  May 11, 2014
  ********************************************************************/

/********************************************************************
  * WHAT:  The QuizPanel is where the user will be tested. Each flashcard
  * will display in order of highest probability to lowest. When the user
  * answers the question correctly, a green check mark will appear. Otherwise
  * the correct answer will be displayed. We will continue to show cards until
  * the user has mastered them.
  * ********************************************************************/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javafoundations.exceptions.*;

public class QuizPanel extends JPanel { 
  protected JPanel introPanel, directionsPanel, cardPanel, answerPanel, statusPanel; 
  protected JTextField answerText;
  protected JButton submitButton, nextButton;
  protected ButtonGroup group;
  protected JRadioButton trueButton, falseButton;
  private JLabel questionLabel, answerLabel, statusLabel;
  private Deck myDeck;
  
  public QuizPanel(Deck pile) {
    myDeck = pile; //initialize myDeck object
    
    //overall layout follows a vertical axis
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  
    setBackground (new Color(173, 244, 230));
    setPreferredSize(new Dimension(800,700));
    setBorder(BorderFactory.createMatteBorder(35,35,35,35, new Color(173, 244, 230)));
    
    //create title
    JLabel titleLabel = new JLabel ("<html><center>Quiz Yourself</center></html>"); 
    titleLabel.setFont(new Font("Georgia", Font.PLAIN, 80));
    
    //create directions
    JLabel directionsLabel = 
      new JLabel ("<html><center><br>Type or select your answer below.<br>"
                    + "We'll keep testing you until you master your cards!<br></center></html>");  
    directionsLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    JLabel checkPic = new JLabel(); //create picture of check
    ImageIcon checkImg = createImageIcon("Images/greenCheck.png","green check");
    
    JLabel xPic = new JLabel(); //create picture of x mark
    ImageIcon xImg = createImageIcon("Images/redX.png","red X");

    //create labels for the question and answer
    questionLabel 
      = new JLabel("<html><center>Your deck of flashcards has not been prepared yet.</center></html>", 
                   JLabel.CENTER);
    questionLabel.setPreferredSize(new Dimension(1, 1)); 
    questionLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 40));
    questionLabel.setLocation(20, 20);
    
    answerLabel = new JLabel();
    answerLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    answerText = new JTextField(15);
    
    //create radio buttons for true and false
    group = new ButtonGroup();
    trueButton = new JRadioButton ("True");
    group.add(trueButton);
    trueButton.setBackground (new Color(173, 244, 230));
    group.add(falseButton = new JRadioButton ("False"));
    falseButton.setBackground (new Color(173, 244, 230));
    
    //create action listener
    GroupListener groupListener = new GroupListener();
    trueButton.addActionListener(groupListener);
    falseButton.addActionListener(groupListener);
    
    //create button listeners 
    ButtonListener listener = new ButtonListener();
    submitButton = new JButton("SUBMIT"); 
    submitButton.addActionListener (listener); 
    
    nextButton = new JButton ("NEXT CARD");
    nextButton.addActionListener (listener ); 
    
    statusLabel = new JLabel();                            
    
    
    //create and organize information into the three panels
    introPanel = new JPanel(); //create title panel
    introPanel.setBackground (new Color(173, 244, 230));
    introPanel.add (titleLabel);
    
    directionsPanel = new JPanel(); //create directions panel
    directionsPanel.setBackground (new Color(173, 244, 230));
    directionsPanel.add (directionsLabel);
    
    //will have answerLabel, text box/radio buttons and submit button
    answerPanel = new JPanel(); 
    answerPanel.setBackground (new Color(173, 244, 230));
    answerPanel.add(statusLabel);
    answerPanel.add(answerLabel); 
    answerPanel.add(nextButton);
    
    nextButton.setVisible(false);
   
    statusPanel = new JPanel();
    statusPanel.setBackground (new Color(173, 244, 230));
    
    cardPanel = new JPanel(); 
    cardPanel.setBackground (new Color(173, 244, 230));
    JLabel cardPic = new JLabel(new ImageIcon("Images/indexCard.png"));
    cardPic.setLayout(new BorderLayout());
    cardPic.add(questionLabel); 
    cardPanel.add(cardPic);
    
    //combine panels
    add(introPanel);
    add(directionsPanel);
    add(cardPanel);
    add(answerPanel);
  }
  
  
  
 //*****************************************************************
  //this method reads the images from its file and displays it
  //*****************************************************************
  private static ImageIcon createImageIcon(String path, String description) {
    java.net.URL imgURL = Deck.class.getResource(path);
    if (imgURL != null) {
      return new ImageIcon(imgURL, description);
    } else {
      System.err.println("Couldn't find file: " + path);
      return null;
    }
  }
  
 //*****************************************************************
  //  Helper method to update deck
  //*****************************************************************
  public void setDeck(Deck deck) {
    myDeck = deck;
  }
  

//*****************************************************************
  //  Based on what type of question the card is, this method will
  //  display either radio buttons or a text box.
  //*****************************************************************
  public void setQuestionLabel(String s) {
    questionLabel.setText("<html><center>" + s + "</center></html>");
    questionLabel.setPreferredSize(new Dimension(1, 1)); 
  }
  

//*****************************************************************
  //  Based on what type of question the card is, this method will
  //  display either radio buttons or a text box.
  //*****************************************************************
  public void isTFQuestion() {
    //if true false question, then display radio buttons
    if (myDeck.getCurrentCard().getTFQuestion()) {
      answerText.setVisible(false); 
      submitButton.setVisible(false); //dont show submit buttons
      trueButton.setVisible(true);
      falseButton.setVisible(true);
    } else { //display text box and submit button for written answers
      trueButton.setVisible(false);
      falseButton.setVisible(false);
      answerText.setVisible(true);
      submitButton.setVisible(true);
    }
  }
  
  
   //*****************************************************************
  //  Determines whether the user's answer is correct and shows the 
  // the corresponding image. If the answer is incorrect, the correct
  // answer will be displayed.
  //*****************************************************************
  public void isCorrect() {
    if (myDeck.getCurrentCard().isCorrect()){  //correct answer
      statusLabel.setIcon(new ImageIcon("Images/greenCheck.png"));
    } else { //incorrect answer
      statusLabel.setIcon(new ImageIcon("Images/redX.png"));
      answerLabel.setVisible(true);
      answerLabel.setText("The correct answer is: " + myDeck.getCurrentCard().getAnswer());
    }
  }
  
  
  //*****************************************************************
  //  Represents a listener for button push (action) events.
  //*****************************************************************
  private class ButtonListener implements ActionListener
  {
    //--------------------------------------------------------------
    //  Updates the counter and label when the button is pushed.
    //--------------------------------------------------------------
    public void actionPerformed (ActionEvent event){ 
      
      if(event.getSource() == submitButton){
        String response = answerText.getText(); //grab user answer
        myDeck.getCurrentCard().setUserAnswer(response);
        isCorrect(); //print appropriate reaction
        nextButton.setVisible(true); //let user press next 
        statusLabel.setVisible(true);
        
        submitButton.setVisible(false);  //dont show submit button and text box
        answerText.setVisible(false); 
      }
      
      if (event.getSource() == nextButton) {
        answerLabel.setVisible(false); //reset the label when new card appears
        nextButton.setVisible(false); //make it invisible while user has to enter answer
        statusLabel.setVisible(false); //image for previous card should not show
        submitButton.setVisible(true);  // show submit button and text box
        answerText.setVisible(true);

        myDeck.setMaxHeap(); //remove card from linkedlist and add to max heap
        System.out.println(myDeck.getTestingDeck()); //should be smaller by 1
        answerText.setText(null); //clear the text field
        
        if (myDeck.getTestingDeck().isEmpty()){ //if testing deck is empty, then refill
          myDeck.setTestingDeck(); //refill by extracting cards from max heap
          
          //if nothing is extracted from max heap, then user is done!
          if (myDeck.getTestingDeck().isEmpty()) { //if user finished all the cards
            answerText.setVisible(false); //so we can't see it
            submitButton.setVisible(false);
            statusLabel.setVisible(false);
            trueButton.setVisible(false);
            falseButton.setVisible(false);
            answerLabel.setVisible(false);
            setQuestionLabel("Congratulations! You'll ace that exam!");
          }else{ //if there are more rounds of testing
            System.out.println("empty"+ myDeck.getTestingSize() + myDeck.getTestingDeck()); //delete later
            setQuestionLabel(myDeck.getCurrentCard().getQuestion());   
          }
        }else { //if there are still cards in testing deck, print next question
          setQuestionLabel(myDeck.getCurrentCard().getQuestion());
        }
        //if there are still more cards to test, then determine whether to show radio buttons or text box
        if (!myDeck.getTestingDeck().isEmpty())
          isTFQuestion();
      }
    }
  }
  
  //*****************************************************************
  //  Represents the listener for all radio buttons
  //*****************************************************************
  private class GroupListener implements ActionListener
  {
    //--------------------------------------------------------------
    //  Sets the text of the label depending on which radio
    //  button was pressed.
    //--------------------------------------------------------------
    public void actionPerformed (ActionEvent event)
    {
      Object source = event.getSource();
  
      if (trueButton.isSelected()) {
        myDeck.getCurrentCard().setUserAnswer("true");     
      }else {
        myDeck.getCurrentCard().setUserAnswer("false");
      }
      
      //allow pictures and the NEXT button to show up again
      trueButton.setVisible(false);  //dont show radio buttons
      falseButton.setVisible(false);
      statusLabel.setVisible(true); 
      nextButton.setVisible(true);
      group.clearSelection(); //clear it up
      isCorrect();

    }
  }  
}






