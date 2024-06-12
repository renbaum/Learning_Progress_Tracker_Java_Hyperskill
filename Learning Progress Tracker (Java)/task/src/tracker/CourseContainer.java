package tracker;

import java.util.*;

public class CourseContainer{
    List<Course> courses = new ArrayList<Course>();
    Scanner sc;

    public CourseContainer(){
        sc = new Scanner(System.in);
        courses = new ArrayList<>();
        addCourse("Java");
        addCourse("DSA");
        addCourse("Databases");
        addCourse("Spring");
    }

    public void addCourse(String name){
        Course course = new Course(name);
        courses.add(course);
    }

    public void addPoints(){
        System.out.println("Enter an id and points or 'back' to return:");
        String input;
        do {
            input = sc.nextLine();
            if (!input.equals("back")) {
                addPoints(input);
            }
        }while(!input.equals("back"));

    }

    public void addPoints(String entry){
        try {
            String[] parts = entry.split(" ");
            if(parts.length != 5) throw new IllegalArgumentException("Incorrect points format.");
            int studentId = StudentContainer.existUser(parts[0]);

            int[] points = new int[4];
            try{
                for(int i = 0; i < 4; i++){
                    points[i] = Integer.parseInt(parts[i + 1]);
                    if(points[i] < 0) throw new IllegalArgumentException("Incorrect points format.");
                }
            }catch(IllegalArgumentException e){
                throw new IllegalArgumentException("Incorrect points format.");
            }

            for(int i = 0; i < 4; i++){
                courses.get(i).addPoints(studentId, points[i]);
            }
            System.out.println("Points updated.");
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    public void findAllPoints() {
        System.out.println("Enter an id or 'back' to return:");
        String input;
        do {
            input = sc.nextLine();
            if (!input.equals("back")) {
                findPoints(input);
            }
        }while(!input.equals("back"));
    }

    private void findPoints(String input) {
        try{
            int studentId = StudentContainer.existUser(input);
            System.out.printf("%d points: Java=%d; DSA=%d; Databases=%d; Spring=%d\n", studentId,
                    courses.get(0).getPoints(studentId), courses.get(1).getPoints(studentId),
                    courses.get(2).getPoints(studentId), courses.get(3).getPoints(studentId));
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}

class Course{
    String name;
    Map<Integer, Integer> points;

    public Course(String name){
        this.name = name;
        points = new HashMap<>();
    }

    public void addPoints(int studentId, int points) {
        if(this.points.containsKey(studentId)) {
            points = points + this.points.get(studentId);
        }
        this.points.put(studentId, points);
    }

    public int getPoints(int studentId) {
        return points.getOrDefault(studentId, 0);
    }
}
