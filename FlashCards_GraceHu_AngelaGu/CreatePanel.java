/********************************************************************
  * FILENAME:  CreatePanel.java  
  * WHO:  Angela Gu and Grace Hu
  * WHEN:  May 11, 2014
  ********************************************************************/

/********************************************************************
  * WHAT:  The CreatePanel is where the user will either feed a program
  * a text file with all the flashcards or manually input all the flashcards.
  * The panel provides the user directions on how to input the flashcards.
  * ********************************************************************/


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class CreatePanel extends JPanel { 
  private JTextField fileTextBox, deckNameText, questionText, answerText;
  private JButton submitFileButton, addButton, doneButton; 
  private JLabel fileStatusLabel, manualStatusLabel; //updates user on what to do next
  private Deck myDeck; 
  private QuizPanel quiz; //send the created deck to the Quiz Panel
  private int count;
  
  public CreatePanel(Deck pile, QuizPanel test) {
    myDeck = pile;
    quiz = test;
    
    count = 0; //count the number of cards created
    
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));  //vertical layout
    setBackground (new Color(173, 244, 230));
    setPreferredSize(new Dimension(800,700));
    setBorder(BorderFactory.createMatteBorder(35,35,35,35, new Color(173, 244, 230))); 
    
    //create title of panel 
    JLabel titleLabel 
      = new JLabel ("<html><center>Create Your Deck</center></html>"); 
    titleLabel.setFont(new Font("Georgia", Font.PLAIN, 90));
    
    //create directions for option 1, which is inputting a file
    JLabel fileDirectionsLabel = 
      new JLabel ("<html>OPTION 1: Type in the name of the file with your flashcards.<br> "
                    + "Your file must follow the format specified in the picture.<br></html>"); 
    fileDirectionsLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    JLabel pic = new JLabel(); //create picture of instructions
    ImageIcon cardsImg = createImageIcon("Images/instructions.png","instructions");
    pic.setIcon(cardsImg);
    
    //creates a label to specify where file should be entered
    JLabel fileNameLabel = new JLabel("<html><br>File name: <html>");
    fileNameLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    //where the user inputs the file
    fileTextBox = new JTextField(10);
    
    //create button to submit file
    ButtonListener listener = new ButtonListener();
    submitFileButton = new JButton("CREATE"); 
    submitFileButton.addActionListener (listener );
    
    //updates the user on whether the file is uploaded
    fileStatusLabel = new JLabel();
    fileStatusLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    //separates options
    JLabel dividerLabel = 
      new JLabel("<html>----------------- OR -----------------<br></html>");
    dividerLabel.setFont(new Font("Palatino Linotype", Font.BOLD, 20));
    
    //directions for inputting cards manually
    JLabel option2Label = new JLabel("OPTION 2: Manually input the deck's name and each card."); 
    option2Label.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    //entering the deck name, question, and answer
    JLabel nameLabel = new JLabel("Deck Name:");
    nameLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    JLabel questionLabel = new JLabel("Question:");
    questionLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    JLabel answerLabel = new JLabel("Answer:");
    answerLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    deckNameText = new JTextField(15); //textfields for each section
    questionText = new JTextField(30);     
    answerText = new JTextField(30); 
    
    addButton = new JButton("ADD"); //adds that card to the deck
    addButton.addActionListener (listener);
    
    doneButton = new JButton("DONE"); //when user completes adding cards
    doneButton.addActionListener (listener );
    
    manualStatusLabel = new JLabel(); //tells user what to do next
    manualStatusLabel.setFont(new Font("Palatino Linotype", Font.PLAIN, 20));
    
    
    //create and organize information into the three panels
    JPanel introPanel = new JPanel(); //create title panel
    introPanel.add (titleLabel);
    introPanel.setBackground (new Color(173, 244, 230));
    
    //filePanel contains all components relating to inputting a file
    JPanel filePanel = new JPanel(); 
    filePanel.setBackground (new Color(173, 244, 230));
    filePanel.setLayout(new BorderLayout());
    filePanel.add(fileDirectionsLabel, BorderLayout.NORTH);
    filePanel.add(fileNameLabel, BorderLayout.WEST);
    filePanel.add(fileTextBox, BorderLayout.CENTER);
    filePanel.add(submitFileButton, BorderLayout.EAST);
    filePanel.add(fileStatusLabel, BorderLayout.SOUTH);
    
    JPanel fileImagePanel = new JPanel(); //filePanel with image
    fileImagePanel.setBackground (new Color(173, 244, 230));
    fileImagePanel.add(filePanel);
    fileImagePanel.add(pic);
    
    
    //the following panels are related to inputting information manually
    JPanel dividerPanel = new JPanel(); //filePanel with image
    dividerPanel.setBackground (new Color(173, 244, 230));
    dividerPanel.add(dividerLabel);
    
    JPanel option2Panel = new JPanel(); //filePanel with image
    option2Panel.setBackground (new Color(173, 244, 230));
    option2Panel.add(option2Label);
    
    JPanel namePanel = new JPanel(); //filePanel with image
    namePanel.setBackground (new Color(173, 244, 230));
    namePanel.add(nameLabel);
    namePanel.add(deckNameText);
    
    JPanel questionPanel = new JPanel(); //questions components
    questionPanel.setBackground (new Color(173, 244, 230));
    questionPanel.add(questionLabel);
    questionPanel.add(questionText);
    
    JPanel answerPanel = new JPanel(); //answers components
    answerPanel.setBackground (new Color(173, 244, 230));
    answerPanel.add(answerLabel);
    answerPanel.add(answerText);
    
    JPanel buttonPanel = new JPanel(); //button components
    buttonPanel.setBackground (new Color(173, 244, 230));
    buttonPanel.add(addButton);
    buttonPanel.add(doneButton);
    buttonPanel.add(manualStatusLabel);
    
    //add all the three panels together in box layout format
    add(introPanel);
    add(fileImagePanel);
    add(dividerPanel);
    add(Box.createVerticalGlue());
    add(option2Panel);
    add(namePanel);
    add(questionPanel);
    add(answerPanel);
    add(buttonPanel);
  }
  
  
  //this method reads the images from its file and displays it
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
  //  Represents a listener for button push (action) events.
  //*****************************************************************
  private class ButtonListener implements ActionListener
  {
    //--------------------------------------------------------------
    //  Updates the counter and label when the button is pushed.
    //--------------------------------------------------------------
    public void actionPerformed (ActionEvent event){ 
      
      //when user enters the name of the text file, read it in and populate the deck
      if (event.getSource() == submitFileButton && !fileTextBox.getText().equals("")) {
        
        
        String fileName = fileTextBox.getText(); //read in the file name
        myDeck = new Deck(fileName); //call to Deck's constructor
        if(!myDeck.isFileThere() ) //if file doesn't exist, throw error message
          fileStatusLabel.setText("<html><i>File does not exists. Try again. "
                                    + "</i></html>"); //let user know
        else{ //if file exists
          quiz.setDeck(myDeck); //pass this deck into quizPanel
          //the first card of quizPanel will already be printed
          quiz.setQuestionLabel(myDeck.getCurrentCard().getQuestion()); 
          
          //add the components to quiz panel so that it is already there when user switches panels
          quiz.answerPanel.add(quiz.answerText);
          quiz.answerPanel.add(quiz.submitButton);
          quiz.answerPanel.add(quiz.trueButton);
          quiz.answerPanel.add(quiz.falseButton);
          quiz.isTFQuestion(); //determine which components to display
          
          fileTextBox.setText(null); //empty the text field
          fileStatusLabel.setText("<html><i>Your deck has been created! "
                                    + "Continue to the next tab.</i></html>"); //let user know
        }
      }
      
      
      
      //if user enters cards manually and leaves no blank spaces
      if (event.getSource() == addButton && !questionText.getText().equals("") 
            && !answerText.getText().equals("") && !deckNameText.getText().equals("")) {
        
        if (count == 0) { //if this is the first card, initialize the name of deck  
          myDeck = new Deck(); 
          String nameOfDeck = deckNameText.getText();  //read name of deck
          myDeck.setName(nameOfDeck);      
        } 
        
        String question = questionText.getText();  //read question
        String answer = answerText.getText();  //read answer 
        Card card = new Card(question, answer); //create card object
        myDeck.addCard(card); //add card to both deck and testing deck
        myDeck.addToTesting(card);
        
        //clear the text
        questionText.setText(null); 
        answerText.setText(null); 
        manualStatusLabel.setText("Card " + myDeck.getSize() 
                                    + " has been added to: " + myDeck.getName()); 
        count++;
        
        quiz.setDeck(myDeck); //pass this deck into quizPanel
        //the first card of quizPanel will already be printed
        quiz.setQuestionLabel(myDeck.getCurrentCard().getQuestion()); 
        
        //initialize all these buttons so that they will appear once the user
        //moves to the next panel
        quiz.answerPanel.add(quiz.answerText);
        quiz.answerPanel.add(quiz.submitButton);
        quiz.answerPanel.add(quiz.trueButton);
        quiz.answerPanel.add(quiz.falseButton); 
        
        //if the first question is a true false question, have the radio buttons
        //otherwise, have a text box appear in the quiz panel
        quiz.isTFQuestion(); 
      }
      
      
      //if user is done and there are cards in the deck
      if (event.getSource() == doneButton && myDeck.getSize() != 0) {
        count = 0;
        myDeck.saveDeck(myDeck.getName() + ".txt"); //save these cards into a new deck
        manualStatusLabel.setText("<html><center><i>Continue to the Quiz Yourself tab</i></center></html>");
      } 
    }
  }
}
