
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;

public class Menu {
    public static int removePlayers;
    public static boolean gameLoaded;
    ArrayList<Player>  playerArrayList = new ArrayList<Player>(); //All players
    ArrayList<Player>  currentPlayers = new ArrayList<Player>();
    Player currentPlayer;

    public Menu(){
        init();
    }

    private void init(){
        System.out.println(getString());
        File playerData = new File("src/PlayerData.txt");
        boolean running;
        running = true;
        show();
        while (running){

//       // den skapar ett nytt spel, när man startar game och skickar med currentplayer med så att game klassen får veta vem spelaren är.

            switch (getInt(1,6)){
                case 1:
                    if(currentPlayer != null) {
                        new Game(currentPlayer);
                        writeToFile(currentPlayer, playerData);
                       // show();
                    }
                    else {
                        System.out.println("Please choose a player before starting a Game");
                        //show();
                    }
                    break;
                case 2:
                    readFromFIle(playerData); // ska kunna välja spelare
                    //show();
                    break;
                case 3:
                    addNewPlayer(playerData); // ska kunna säga vilken spelare som du lägger till
                    //show();
                    break;
                case 4:
                    if(currentPlayer==null){
                        System.out.println("Please choose a player!");
                    }else {
                        playerStats(); // vill vill komma åt den här datan från write och read också
                    }
                    case 5:
                    //startar spelet när man har valt 2 - 4 spelare
                    if(currentPlayers.size() >= 2 && currentPlayers.size() <= 4) {
                        new Game(currentPlayers);
                        for (Player player : currentPlayers) {
                            writeToFile(player, playerData);
                        }
                    }
                    else {
                        System.out.println("Please choose 2 to 4 players before starting a Game");
                        choosePlayers(playerData);
                    }
                    break;
                case 6:
                    running = false;
                    break;
                default:
                    System.out.println("incorrect input");
                    break;
            }
            show();
        }
    }

    private void choosePlayers(File playerData) {
        //TODO: se till att man väljer 2 till 4
        System.out.println("Choose the number of players (between 2 and 4):");
        Scanner sc = new Scanner(System.in);
        int numberOfPlayers = sc.nextInt();

        try {
            Scanner reader = new Scanner(playerData);


            int playerNumber = 1;
            playerArrayList.clear();


            while (reader.hasNext()) {

                String playerName = reader.next();
                int playerRound = Integer.parseInt(reader.next());
                int playerWins = Integer.parseInt(reader.next());
                int playerLosses = Integer.parseInt(reader.next());
                int playerMaxPoint =  Integer.parseInt(reader.next());
                int playerTotalPoint=  Integer.parseInt(reader.next());
                Player p = new Player(playerName, playerRound, playerWins, playerLosses, playerMaxPoint,playerTotalPoint);
                System.out.print(playerNumber + " ");
                System.out.println(p.getName());
                this.playerArrayList.add(p);
                playerNumber ++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("catch");
        }

        for( int i = 0;  i < numberOfPlayers ; i++){
            System.out.println(" Choose one player from the list");
            System.out.println("Enter a number");
            int chosenPlayer = sc.nextInt();
            currentPlayers.add(this.playerArrayList.get(chosenPlayer-1));
        }
        System.out.println(currentPlayers.toString());
    }

    public void show() {
        // detta ska visa menyn
        System.out.println();
        System.out.println("1. Start game!");
        System.out.println("2. Chose your character");
        System.out.println("3. Make new character");
        System.out.println("4. Character stats");
        System.out.println("5. Multiplayer");
        System.out.println("6. Quit");


    }
    public void readFromFIle(File playerData) {

        try {

            Scanner reader = new Scanner(playerData);

            // vi tömmar listan

            int playerNumber = 1;
            playerArrayList.clear();
            while (reader.hasNext()) {
                String playerName = reader.next();
                int playerRound = Integer.parseInt(reader.next());
                int playerWins = Integer.parseInt(reader.next());
                int playerLosses = Integer.parseInt(reader.next());
                int playerMaxPoint =  Integer.parseInt(reader.next());
                int playerTotalPoint=  Integer.parseInt(reader.next());
                Player p = new Player(playerName, playerRound, playerWins, playerLosses, playerMaxPoint,playerTotalPoint);
                System.out.print(playerNumber + " ");
                System.out.println(p.getName());
                this.playerArrayList.add(p);
                playerNumber ++;

            }
            choosePlayer();


        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("catch");
        }
        System.out.println("Current player: " + this.currentPlayer.getName());
    }

    public void choosePlayer(){ // Skapade en ny metod för att välja karaktär

        System.out.println(" Choose one player from the list");
        System.out.println("Enter a number");

        Scanner sc = new Scanner(System.in);


        int i = sc.nextInt();
        this.currentPlayer = this.playerArrayList.get(i-1);

    }
    public void addNewPlayer(File playerData) {
        System.out.println("Add new player!");
        System.out.print("> ");

        try {
            Scanner saveData = new Scanner(playerData);
            ArrayList<String> tempPlayers = new ArrayList<>();
            while (saveData.hasNextLine()) {
                tempPlayers.add(saveData.nextLine());//

            }
            PrintWriter writer = new PrintWriter(playerData);

            Scanner keyboard = new Scanner(System.in);
            String playerName = keyboard.nextLine(); //man läser in input från aanvändaren från konsolen
            this.currentPlayer = new Player(playerName);
            System.out.println("Welcome: " + this.currentPlayer.getName() + ","+" Enter a number from the menu");

            for (String tempPlayer:tempPlayers){
                writer.println(tempPlayer); // här skriver

            }

            String name = this.currentPlayer.getName();
            int rounds = this.currentPlayer.getRounds();
            int wins = this.currentPlayer.getWins();
            int losses = this.currentPlayer.getLosses();
            int maxPoint = this.currentPlayer.getMaxPoint();
            int totalPoint = this.currentPlayer.getTotalPoint();

            writer.println(name + " "  + rounds+ " " + wins + " " + losses + " " +maxPoint + " "  + totalPoint);



            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IException");
        }
    }

    public void playerStats(){

        // Här under är statistik för den spelaren man har valt

        try {

            File playerD = new File("src/PlayerData.txt");
            Scanner playerStat = new Scanner(playerD);
            while (playerStat.hasNextLine()){
                System.out.println(playerStat.next()); // Plockar ut första ordet
                playerStat.nextLine(); // plockar bort nollorna från utskriften
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public String getString(){ //ska ta inputs från spelet
        String s;
        s = "Welcome to Hangman";
        return s;
    }
    public int getInt(int min, int max){
        Scanner menuScanner = new Scanner(System.in);


        int x = menuScanner.nextInt();

        return x;
    }

    public void writeToFile(Player currentPlayer , File  playerData){
        System.out.println(currentPlayer.toString());

        try {
            Scanner saveData = new Scanner(playerData);
            ArrayList<String> tempPlayers = new ArrayList<>();
            while (saveData.hasNextLine()) {
                tempPlayers.add(saveData.nextLine());//

            }
            PrintWriter writer = new PrintWriter(playerData);

            // templayer = name 0 0 0 0 0
            for (String tempPlayer:tempPlayers){
                String[] temPlayerArray =  tempPlayer.split(" ");
                String temPlayerName =temPlayerArray[0];
                if(temPlayerName.equals(currentPlayer.getName())){
                    String name = currentPlayer.getName();
                    int rounds = currentPlayer.getRounds();
                    int wins = currentPlayer.getWins();
                    int losses = currentPlayer.getLosses();
                    int maxPoint = currentPlayer.getMaxPoint();
                    int totalPoint = currentPlayer.getTotalPoint();

                    writer.println(name + " "  + rounds+ " " + wins + " " + losses + " " +maxPoint + " "  + totalPoint);
                }
                else{
                    writer.println(tempPlayer); // här skriver

                }

            }


            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IException");
        }

    }
}


