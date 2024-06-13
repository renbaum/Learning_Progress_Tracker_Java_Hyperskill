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
        int points = 0;
        switch(name){
            case "Java":
                points = 600;
                break;
            case "DSA":
                points = 400;
                break;
            case "Databases":
                points = 480;
                break;
            case "Spring":
                points = 550;
                break;
            default:
                throw new IllegalArgumentException("Cant find the type of course");
        }
        Course course = new Course(name, points);
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

    public void statistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");
        printStatistics();
        String input;
        do {
            input = sc.nextLine();
            if (!input.equals("back")) {
                printCourseStat(input);
            }
        }while(!input.equals("back"));
    }

    private void printCourseStat(String input) {
        for(Course course : courses){
            if(course.getName().toLowerCase().equals(input.toLowerCase())){
                course.printTopLearners();
                return;
            }
        }
        System.out.println("Unknown course.");
    }

    private void printStatistics() {
        Course courseMax, courseMin;
        List<Course> sortedPoints = new ArrayList<>(courses);

        // popularity
        sortedPoints.sort(Comparator.comparing(Course::getPopularity));

        String mostPopular = "n/a", leastPopular = "n/a";
        courseMax = sortedPoints.get(sortedPoints.size() - 1);
        courseMin = sortedPoints.get(0);

        if(courseMax.getPopularity() != 0){
            mostPopular = "";
            for(int i = sortedPoints.size() - 1; i >= 0; i--){
                if(courseMax.getPopularity() == sortedPoints.get(i).getPopularity()){
                    mostPopular += " " + sortedPoints.get(i).getName();
                }
                else break;
            }
            mostPopular = mostPopular.trim();
        }
        if(courseMax.getPopularity() != 0 && courseMin.getPopularity() < courseMax.getPopularity()){
            leastPopular = "";
            for (int i = 0; i < sortedPoints.size(); i++) {
                if (courseMin.getPopularity() == sortedPoints.get(i).getPopularity()) {
                    leastPopular += " " + sortedPoints.get(i).getName();
                }else break;
            }
            leastPopular = leastPopular.trim();
        }
        System.out.printf("Most popular: %s\n", mostPopular);
        System.out.printf("Least popular: %s\n", leastPopular);

        // activity
        sortedPoints.sort(Comparator.comparing(Course::getActivity));

        String mostActivity = "n/a", leastActivity = "n/a";
        courseMax = sortedPoints.get(sortedPoints.size() - 1);
        courseMin = sortedPoints.get(0);

        if(courseMax.getActivity() != 0){
            mostActivity = "";
            for(int i = sortedPoints.size() - 1; i >= 0; i--){
                if(courseMax.getActivity() == sortedPoints.get(i).getActivity()){
                    mostActivity += " " + sortedPoints.get(i).getName();
                }
                else break;
            }
            mostActivity = mostActivity.trim();
        }
        if(courseMax.getActivity() != 0 && courseMin.getActivity() < courseMax.getActivity()){
            leastActivity = "";
            for (int i = 0; i < sortedPoints.size(); i++) {
                if (courseMin.getActivity() == sortedPoints.get(i).getActivity()) {
                    leastActivity += " " + sortedPoints.get(i).getName();
                }else break;
            }
            leastActivity = leastActivity.trim();
        }
        System.out.printf("Highest activity: %s\n", mostActivity);
        System.out.printf("Lowest activity: %s\n", leastActivity);

        // hardest
        sortedPoints.sort(Comparator.comparing(Course::getAveragePoints));

        String mostAverage = "n/a", leastAverage = "n/a";
        courseMax = sortedPoints.get(sortedPoints.size() - 1);
        courseMin = sortedPoints.get(0);

        if(courseMax.getAveragePoints() != 0){
            mostAverage = "";
            for(int i = sortedPoints.size() - 1; i >= 0; i--){
                if(courseMax.getAveragePoints() == sortedPoints.get(i).getAveragePoints()){
                    mostAverage += " " + sortedPoints.get(i).getName();
                }
                else break;
            }
            mostAverage = mostAverage.trim();
        }
        if(courseMax.getAveragePoints() != 0 && courseMin.getAveragePoints() < courseMax.getAveragePoints()){
            leastAverage = "";
            for (int i = 0; i < sortedPoints.size(); i++) {
                if (courseMin.getAveragePoints() == sortedPoints.get(i).getAveragePoints()) {
                    leastAverage += " " + sortedPoints.get(i).getName();
                }else break;
            }
            leastAverage = leastAverage.trim();
        }
        System.out.printf("Easiest course: %s\n", mostAverage);
        System.out.printf("Hardest course: %s\n", leastAverage);

    }

    public void notifyStudents() {
        Set<Integer> counter = new HashSet<>();
        for(Course course : courses){
            counter.addAll(course.notifyStudents());
        }
        System.out.printf("Total %d students have been notified.\n", counter.size());

    }
}

class Course{
    String name;
    Map<Integer, Integer> points;
    List<Integer> notifiedStudent;

    int maxPoints = 0;
    int activity = 0;

    public Course(String name, int maxPoints){
        this.name = name;
        this.maxPoints = maxPoints;
        points = new HashMap<>();
        notifiedStudent = new ArrayList<>();
    }

    public void addPoints(int studentId, int points) {
        if(points == 0) return;
        activity ++;
        points += this.points.getOrDefault(studentId, 0);
        this.points.put(studentId, points);
    }

    public int getPoints(int studentId) {
        return points.getOrDefault(studentId, 0);
    }

    public int getPopularity(){
        return points.size();
    }

    public int getActivity(){
        return activity;
    }

    public float getAveragePoints(){
        float totalPoints = 0;
        if (points.size() == 0) return 0;

        for(Map.Entry<Integer, Integer> entry : points.entrySet()){
            totalPoints += entry.getValue();
        }
        return totalPoints / points.size();
    }

    public void printTopLearners(){
        List<Map.Entry<Integer, Integer>> sortedPoints = new ArrayList<>(points.entrySet());
        sortedPoints.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        System.out.println(name);
        System.out.printf("id   points   completed\n");
        for (int i = 0; i < sortedPoints.size(); i++) {
            System.out.printf("%-7d%-7d%.1f%%\n", sortedPoints.get(i).getKey(),
                                                sortedPoints.get(i).getValue(),
                                                sortedPoints.get(i).getValue()/(float)maxPoints*100);
        }
    }

    public String getName() {
        return this.name;
    }

    public Set<Integer> notifyStudents() {
        Set<Integer> studentIds = new HashSet<>()
                ;
        for(Map.Entry<Integer, Integer> entry : points.entrySet()){
            if(entry.getValue() >= maxPoints){
                if(!notifiedStudent.contains(entry.getKey())) {
                    StudentContainer.notifyStudent(entry.getKey(), name);
                    notifiedStudent.add(entry.getKey());
                    studentIds.add(entry.getKey());
                }
            }
        }
        return studentIds;
    }
}
