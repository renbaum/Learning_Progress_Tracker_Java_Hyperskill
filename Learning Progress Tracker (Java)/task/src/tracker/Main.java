package tracker;

import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StudentContainer students = new StudentContainer();

        System.out.println("Learning Progress Tracker");
        String input;
        do {
            input = sc.nextLine();
            input = input.trim();

            if(input.isEmpty()){
                System.out.println("No input");
                continue;
            }
            switch (input) {
                case "exit":
                    break;
                case "add students":
                    students.addStudents();
                    break;
                case "back":
                    System.out.println("Enter 'exit' to exit the program.");
                    break;
                default:
                    System.out.println("Error: unknown command!");
                    break;
            }
        }while(!input.equals("exit"));

        System.out.println("Bye!");
    }
}
