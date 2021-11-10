import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Game {
    private Player currentPlayer = null;
    private ArrayList<Player> currentPlayers = new ArrayList<>();
    private char[] myAnswers = null; //
    private boolean finished;
    private int lives;
    private boolean done;

    //  skickar från menu till game,
    public Game(Player currentPlayer){
        this.currentPlayer = currentPlayer;

        System.out.println("Hi welcome " + currentPlayer.getName() + " to hangman");
        char[] textArray = getWordFromDictionary();
        this.myAnswers = new char[textArray.length];
        this.finished = false;
        this.lives = 6;
        playGame(textArray);
    }
    public Game(ArrayList<Player> currentPlayers){
        System.out.println("Hi welcome to hangman");
        // char[] wordToGuess = getWordFromDictionary();
        char[] wordToGuess = {'q','q','q','q','q'};
        this.currentPlayers = currentPlayers;
        this.finished = false;
        //Starta spelet
        playMultiplayerGame(wordToGuess);
    }
    public void playMultiplayerGame( char[] wordToGuess){
        int index = 0 ;
        Scanner input = new Scanner(System.in);
        Collections.shuffle(currentPlayers);

        this.myAnswers = new char[wordToGuess.length];
        for(int i = 0; i < myAnswers.length; i++){
            myAnswers[i] = '?';
        }

        while(!finished){
            if(index == currentPlayers.size()){ // om loppen når player 4 då blir indexen 0 går tillbaka till första player
                index = 0 ;
            }

            Player currentPlayer = currentPlayers.get(index);

            if(currentPlayer.getDone() == true){ // om spelaren på detta index har gått ut
                index++;
                break;
            }


            System.out.println(currentPlayer.getName() + " Plays now, Good Luck!");
            while(true){
                int lives = currentPlayer.getLives();
                String letter = input.next();

                // check for a valid input
                while(letter.length() != 1 || Character.isDigit(letter.charAt(0))){
                    System.out.println("Error Input - Try again");
                    letter = input.next();
                }
                //check if letter is in the word
                boolean found = false;
                for(int i = 0; i < wordToGuess.length; i++) {
                    if(letter.charAt(0) == wordToGuess[i]) {
                        myAnswers[i] = wordToGuess[i];
                        found = true;
                    }
                }
                boolean done = true;
                for (char myAnswer : myAnswers) {
                    if (myAnswer == '?') {
                        System.out.print(" _");
                        done = false;
                    } else {
                        System.out.print(" " + myAnswer);
                    }
                }

                if(!found){
                    lives--;
                    currentPlayer.setLives(lives);
                    System.out.println("Wrong letter");
                    index++;
                    System.out.println("\n" + "Lives left: " + lives);
                    drawHangman(lives);
                    checkMultiplayerGameResults(done, currentPlayer);
                    break;
                }

                System.out.println("\n" + "Lives left: " + lives);
                drawHangman(lives);
                checkMultiplayerGameResults(done, currentPlayer);
            }


        }

        System.out.println(wordToGuess);

    }


    private void checkMultiplayerGameResults(boolean done, Player player) {
        int counter = 0 ;
        for( Player p : currentPlayers){
            if(p.getLives() <= 0 ) counter++;
        }
        if( counter == currentPlayers.size()) finished = true;
        //Player.done ? ?  kanske den funkar ?
        if(done) {
            System.out.println("Congratulations! You will now get 1 point");
            System.out.println();

            player.setRounds(player.getRounds()+ 1);
            player.setWins(player.getWins() + 1);
            player.setMaxPoint(player.getMaxPoint() +1);
            player.setTotalPoint(player.getTotalPoint() +1);

            //Starta nytt spel
            System.out.println("Starting a new Game ");
            char[] wordToGuess = getWordFromDictionary();
            playMultiplayerGame(wordToGuess);
            //Save datan till textfilen
            //Ett förslag
            // WriteToFile metoden kan ta emot ett Spelar Objekt också
            // Så t.ex i case 1 skriver vi writeTofile(currentPlayer, playerData)

            // Sen här om vi kan komma åt den på nått sätt kör man
            //Loop genom currentPLayers {
            //  writeToFile(player)
            // }
            // Nått sånt.

        }

        if(player.getLives() <= 0) {
            System.out.println("YOU ARE DEAD AND BANISHED  >:D");
            System.out.println();
            player.setDone(false);
            player.setRounds(player.getRounds()+ 1);
            player.setLosses(player.getLosses() + 1);
            //Menu fail = new Menu();
        }
    }

    private char[] getWordFromDictionary()  {

        System.out.print("Guess a letter: ");
        ArrayList<String> words = new ArrayList<>();
        try{

            File dictionary = new File("src/english2.txt");

            Scanner textScanner = new Scanner(dictionary);

            while(textScanner.hasNextLine()) {
                words.add(textScanner.nextLine());
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
        String hiddenText = words.get((int)(Math.random()* words.size()));
        return hiddenText.toCharArray();
    }

    public void playGame(char [] textArray){
        for(int i = 0; i < myAnswers.length; i++){
            myAnswers[i] = '?';
        }

        Scanner input = new Scanner(System.in);
        while(!finished) {
            String letter = input.next();
            // check for a valid input
            while(letter.length() != 1 || Character.isDigit(letter.charAt(0))){
                System.out.println("Error Input - Try again");
                letter = input.next();
            }
            //check if letter is in the word
            boolean found = false;
            for(int i = 0; i < textArray.length; i++) {
                if(letter.charAt(0) == textArray[i]) {
                    myAnswers[i] = textArray[i];
                    found = true;
                }
            }
            if(!found){
                lives--;
                System.out.println("Wrong letter");
            }
            boolean done = true;
            for (char myAnswer : myAnswers) {
                if (myAnswer == '?') {
                    System.out.print(" _");
                    done = false;
                } else {
                    System.out.print(" " + myAnswer);
                }
            }
            System.out.println("\n" + "Lives left: " + lives);
            drawHangman(lives); //????? fråga lärare
            checkGameResults(done);
        }
        System.out.println(textArray);
    }
    //check if the game ends
    private void checkGameResults(boolean done) {
        this.done = done;

        if(done) {
            System.out.println("Congratulations! You are now back to the menu");
            System.out.println();
            finished = true;
            currentPlayer.setRounds(currentPlayer.getRounds()+ 1);
            currentPlayer.setWins(currentPlayer.getWins() + 1);
            currentPlayer.setMaxPoint(currentPlayer.getMaxPoint() +1);
            currentPlayer.setTotalPoint(currentPlayer.getTotalPoint() +1);

            //Menu fail = new Menu();

        }

        if(lives <= 0) {
            System.out.println("YOU ARE DEAD AND BANISHED BACK TO THE MENU! >:D");
            System.out.println();
            finished = true;
            currentPlayer.setRounds(currentPlayer.getRounds()+ 1);
            currentPlayer.setLosses(currentPlayer.getLosses() + 1);

            //Menu fail = new Menu();
        }
    }

    //detta gör bara en sak och ska inte behöva göra två eller flera metoder för att dela koden.
    public void drawHangman(int l) {
        if(l == 6) {
            System.out.println("|----------");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 5) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 4) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|    |");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 3) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 2) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else if(l == 1) {
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|   /");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
        else{
            System.out.println("|----------");
            System.out.println("|    O");
            System.out.println("|   -|-");
            System.out.println("|   /|");
            System.out.println("|");
            System.out.println("|");
            System.out.println("|");

        }
    }
    }