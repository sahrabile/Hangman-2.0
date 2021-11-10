public class Player {

    private int rounds;
    private int wins;
    private int losses;
    private String name;
    private int maxPoint;
    private int  totalPoint;
    private boolean done;
    private int lives ;
    //användas för att skapa en ny spelare
    public Player (String name){
        this.name = name;
        this.rounds = 0;
        this.wins =   0;
        this.losses =  0;
        this.maxPoint= 0;
        this.totalPoint=0;
        this.done = false;
        this.lives = 6;
    }
    public Player (String name, int rounds, int wins, int losses, int maxPoint,int totalPoint){
        this.name = name;
        this.rounds = rounds;
        this.wins = wins;
        this.losses = losses;
        this.maxPoint= maxPoint;
        this.totalPoint= totalPoint;
        this.done = false;
        this.lives = 6;
    }
    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPoint() { return maxPoint;}

    public void setMaxPoint(int maxPoint) {
        this.maxPoint = maxPoint;}

    public int getTotalPoint() {return totalPoint;}

    public void setTotalPoint(int totalPoint) {this.totalPoint = totalPoint;}
    public boolean getDone(){
        return  this.done;
    }
    public void setDone(boolean done){
        this.done = done;
    }

    public int getLives() { return lives; }

    public void setLives(int lives) {this.lives = lives;}

    @Override
    public String toString() {
        System.out.println("Namn på spelare: " + this.name);
        System.out.println("Antalet omgångar körda: " + this.rounds);
        System.out.println("Antalet omgångar vunna: " + this.wins);
        System.out.println("Antalet omgångar förlorade " + this.losses);
        return  name + ": " +
                " Rounds:" + rounds +
                " Wins:" + wins +
                " Losses:" + losses;

    }

}


