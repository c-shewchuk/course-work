/* The Game of Pig
Created by: Curtis Shewchuk, Netid: 14cms13
For Assignment 1: CMPE 212
Due: February 3rd, 7pm
This program allows the player to play the two-dice variation of pig against the computer.
The computer only has a very simple strategy.
**Two of my methods are essentially the same thing, humanRules and computerRules.
They were split up because the method became longer then the main method, the way
I had written it.
*/
// Imports
import java.util.Scanner;
import java.util.Random;

public class Pig{
  // create the type suggested in the Assignment description
  public static Random generator = new Random(System.currentTimeMillis());
  public static Scanner sc = new Scanner(System.in);
  //The main method where the game is played. Calls methods for the players turn and computers turn.
  public static void main(String[] args){
    System.out.println("Welcome to Pig! The human goes first.");
    int playerScore = 0;
    int computerScore = 0;
    int turnScore = 0;
    while(true){
      turnScore = 0;
      System.out.println("Player turn, press enter to roll the die.");
      waitForEnter();
      System.out.println("Player Score: " + playerScore + " Computer Score: " + computerScore);
      turnScore = humanTurn(turnScore);
      playerScore = updateScore(turnScore, playerScore);
      if(playerScore >= 100){
        System.out.println("Player wins the game with " + playerScore + " points!");
        break; // want to break and end the game if the player has 100 points
      }
      System.out.println("Player Score: " + playerScore + " Computer Score: " + computerScore);
      turnScore = 0;
      System.out.println("The computer begins it's turn, press enter to continue");
      waitForEnter();
      turnScore = computerTurn(turnScore, computerScore);
      computerScore = updateScore(turnScore, computerScore);
      if(computerScore >= 100){
        System.out.println("The computer wins with " + computerScore +" points!");
        break;
      }
    } // end while
    System.out.println("Thanks for playing my version of Pig!");
  }// end main

// method that takes action when it is a player turn
  public static int humanTurn(int turnScore){
    int firstDie = diceRoll();
    int secondDie = diceRoll();
    turnScore = humanRules(firstDie, secondDie, turnScore);
    return turnScore;
  }// end humanTurn

//how the computer will play it's turn
  public static int computerTurn(int turnScore, int totalScore){
    int firstDie = diceRoll();
    int secondDie = diceRoll();
    turnScore = computerRules(firstDie, secondDie, totalScore, turnScore);
    return turnScore;
  }// end computerTurn

  // Method used to "smooth" gameplay, so that pauses can be made so the game is more readable. Stalls the program until enter is pressed.
  public static void waitForEnter(){
    boolean pressEnter = true;
    while(pressEnter){
      String begin = sc.nextLine();
      if(begin.equals("")){
        pressEnter = false;
      }
    }
  }// end waitForEnter

  // self-explanatory. Updates the score at the end of each turn.
  public static int updateScore(int turnScore, int totalScore){
    if(turnScore == -1)
      totalScore = 0;
    totalScore = totalScore + turnScore;
    return totalScore;
  }

  // method used to "roll the dice"
  public static int diceRoll(){
  int die = 0;
  while(die == 0)
    die = generator.nextInt(7); // die ranges from 0-6 so we must use 7 to get the necessary possibilities
  return die;
  }

// method invoked to ensure the rules are followed for the player.
  public static int humanRules(int firstDie, int secondDie, int turnScore){
    if(firstDie == secondDie){
      if(firstDie == 1 && secondDie == 1){
        turnScore = -1;
        System.out.println("Oh no, two one's were rolled. Score set back to zero");
        return turnScore;
      }
      turnScore = turnScore + firstDie * 2;
      System.out.println("DOUBLE " + numberToWord(firstDie) + "'s Must roll again");
      turnScore = humanTurn(turnScore);
      return turnScore;
    }
    if(firstDie == 1 || secondDie == 1){
      System.out.println("Player rolls " + numberToWord(firstDie) + " and " + numberToWord(secondDie));
      System.out.println("A one was rolled, score set to zero for this turn");
      turnScore = 0;
      return turnScore;
    }
    else{
      turnScore = turnScore + firstDie + secondDie;
      System.out.println("Player rolls " + numberToWord(firstDie) + " and " + numberToWord(secondDie) + ". Turn score is " + turnScore + ".");
      boolean isContinue = continueTurn(turnScore);
      if(isContinue){
        turnScore = humanTurn(turnScore);
        }
      }
      return turnScore;
    }// end humanRules

// method to check whether the player chooses to hold or roll, if they have the option.
  public static boolean continueTurn(int turnScore){
      Scanner sc = new Scanner(System.in);
      boolean isNext = true;
      boolean rollOrHold = false; // assumes the player will hold. True implies rolling, false implies holding.
      System.out.println("Press R to roll again, or H to hold");
      while(isNext){
        String nextTurn = sc.nextLine();
        if(nextTurn.equalsIgnoreCase("R")){
          System.out.println("Player chooses to roll");
          rollOrHold = true;
          isNext = false;
        }
        if(nextTurn.equalsIgnoreCase("H")){
          System.out.println("Player chooses to hold");
          isNext = false;
        }
      } // end while loop
      return rollOrHold;
    } // end continueTurn

// method invoked to ensure the rules are followed. Nearly identical to humanRules()
  public static int computerRules(int firstDie, int secondDie, int totalScore, int turnScore){
    if(firstDie == secondDie){
        if(firstDie == 1 && secondDie == 1){
          turnScore = -1;
          System.out.println("Oh no, two one's were rolled. Score set back to zero");
          return turnScore;
        }
        turnScore = turnScore + firstDie * 2;
        System.out.println("DOUBLE " + numberToWord(firstDie) + "'s. Computer Must roll again");
        turnScore = computerTurn(turnScore, totalScore);
        return turnScore;
      }
    if(firstDie == 1 || secondDie == 1){
      System.out.println("Computer rolls " + numberToWord(firstDie) + " and " + numberToWord(secondDie));
      System.out.println("A one was rolled, score set to zero for this turn");
      turnScore = 0;
      return turnScore;
      }
    else{
      turnScore = turnScore + firstDie + secondDie;
      System.out.println("Computer rolls " + numberToWord(firstDie) + " and " + numberToWord(secondDie) + ". Turn score is " + turnScore + " Computer has " + totalScore + " points");
      boolean isContinue = computerStrategy(turnScore, totalScore);
      if(isContinue){
        turnScore = computerTurn(turnScore, totalScore);
      }
    }
    return turnScore;
  }// end computerRules

  //The AI strategy.
  public static boolean computerStrategy(int turnScore, int totalScore){
    boolean isRoll = false;
    if(totalScore >= 80){
      isRoll = true;
    }
    if(turnScore < 30){
      isRoll = true;
    }
    return isRoll;
    }
  // method used to fill requirement that the rolls must be reported as words, not the integer value.
  public static String numberToWord(int roll){
    String word = "";
    if(roll == 1)
      word = "one";
    if(roll == 2)
      word = "two";
    if(roll == 3)
      word = "three";
    if(roll == 4)
      word = "four";
    if(roll == 5)
      word = "five";
    if(roll == 6)
      word = "six";
    return word;

  }
}
