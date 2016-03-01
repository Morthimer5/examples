package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Morthimer on 3/1/2016.
 */
public class Game {
    public static void main(String [] args){

        int number = (int) (Math.random()*20);

        System.out.println("I have number! Guess what.");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String userNum;

        while(true){
            try {
                userNum = br.readLine();

                if(userNum.equals("q")){
                    System.out.println("Bye!");
                    System.exit(0);
                }else if(Integer.parseInt(userNum) > number){
                    System.out.println("It is more then I have");
                }else if(Integer.parseInt(userNum) < number){
                    System.out.println("It is less then I have");
                }else if(Integer.parseInt(userNum) == number){
                    System.out.println("Bingo!");
                    System.out.println("Do you want to play another game? Tipe \"N\" for exit");
                    if(br.readLine().equals("N")){
                        System.out.println("Bye!");
                        System.exit(0);
                    }else{
                        number = (int) (Math.random()*20);
                    }
                    System.out.println("I have another number!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Your number is bad");
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Your input is bad");
            }
        }
    }

}

