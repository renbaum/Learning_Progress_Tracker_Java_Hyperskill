package tracker;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class StudentContainer{
    List<Student> students;
    Scanner sc = new Scanner(System.in);

    public StudentContainer(){
        students = new ArrayList<>();
    }

    private void addStudent(String input){
        try {
            Student s = new Student(input);
            students.add(s);
            System.out.println("The student has been added.");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void addStudents(){
        System.out.println("Enter student credentials or 'back' to return:");
        int actNumberOfStudents = students.size();
        String input;
        do {
            input = sc.nextLine();
            if (!input.equals("back")) {
                addStudent(input);
            }
        }while(!input.equals("back"));
        int newNumberOfStudents = students.size();
        System.out.printf("Total %d students have been added.\n", newNumberOfStudents - actNumberOfStudents);
    }
}

public class Student{
    String firstName;
    String lastName;
    String email;

    public Student(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        checkWithException();
    }

    public Student(String oneLiner){
        String[] parts = oneLiner.split(" ");
        if(parts.length < 3){ throw new IllegalArgumentException("Incorrect credentials."); }

        this.firstName = parts[0];
        this.lastName = "";
        for(int i = 1; i < parts.length -1; i++){
            if(parts[i] == null) continue;
            if(parts[i].isEmpty()) continue;
            this.lastName += " " + parts[i];
        }
        this.lastName = this.lastName.trim();
        this.email = parts[parts.length-1];
        checkWithException();
    }
    public boolean checkWithException(){
        if(!checkName(this.firstName)){ throw new IllegalArgumentException("Incorrect first name."); }
        if(!checkName(this.lastName)){ throw new IllegalArgumentException("Incorrect last name."); }
        if(!checkEmail(this.email)){ throw new IllegalArgumentException("Incorrect email."); }
        return true;
    }

    public boolean check(){
        return checkEmail(email) && checkName(firstName) && checkName(lastName);
    }

    public boolean checkEmail(String email){
        Pattern pattern = Pattern.compile("[A-Za-z0-9\\-\\.]+@[A-Za-z0-9\\-\\.]+\\.[A-Za-z0-9]+");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkEmail(){
        return checkEmail(email);
    }

    public boolean checkName(String name){
        if(name.length() <= 1) return false;
        Pattern pattern = Pattern.compile("[A-Za-z]{1}[A-Za-z'\\-\\s]*[A-Za-z]{1}");
        Matcher matcher = pattern.matcher(name);
        Pattern pattern2 = Pattern.compile("(['-]{2,})");
        Matcher matcher2 = pattern2.matcher(name);
        return matcher.matches() && !matcher2.find();
    }

    public boolean checkCredentials(){
        return checkName(firstName) && checkName(lastName);
    }
}
