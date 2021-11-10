
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        boolean playing =true;
        Scanner in = new Scanner(System.in);
        Menu menu = new Menu();
        while (playing){
            menu.show();
            System.out.println("do you want to end?");
            String svar = in.next();
            if(svar.equals("yes" + "/no")){
                playing = false;

                System.out.println("P1 set your name: ");
                System.out.print("> ");

            }
        }

    }
}
