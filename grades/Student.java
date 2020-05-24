package grades;

import java.io.File;
import java.io.Serializable;
import java.util.*;


/** Student Class
 *  @author P. N. Desai
 */
class Student implements Serializable {

    /** Constructor for student using NAME. */
    Student(String name) {
        _name = name;
        _accountLocked = true;
        _courses = new ArrayList<String>();
    }

    /** Returns the name of the student. */
    String name() {
        return _name;
    }

    /** Adds the password for the student. */
    void addPassword(String password) {
        _password = password;
    }

    /** Checks if the password is right. */
    void passwordMatch(String enteredPassword, boolean reverse) {
        if (!reverse) {
            if (_accountLocked && enteredPassword.equals(password())) {
                _accountLocked = false;
                System.out.println("Access Granted to " + name());
            } else {
                System.out.println("Sign-in unsuccessful.");
            }
        } else {
            if (!_accountLocked && enteredPassword.equals(password())) {
                _accountLocked = true;
                System.out.println("Successful sign-out by  " + name());
            } else {
                System.out.println("Sign-out unsuccessful.");
            }
        }
    }

    /** Adds an assignment for the student. */
    void addSubject(String... args) {
        String name = args[1];
        if (!name().equals("None")) {
            if (!Utils.join(_subjectsDirectory, name).exists()) {
                System.out.println("Please enter the total points for the course");
                Scanner courseInputPoints = new Scanner(System.in);
                String coursePoints = courseInputPoints.nextLine();
                int totalPoints = Integer.parseInt(coursePoints);
                System.out.println("Please enter whether the class is curved");
                Scanner curvedInput = new Scanner(System.in);
                String curvedOrnot = curvedInput.nextLine();
                Subject course = new Subject(name, totalPoints, curvedOrnot);
                String fileName = name + "-info";
                File subjectSpecificDirectory = Utils.join(_subjectsDirectory, name);
                subjectSpecificDirectory.mkdir();
                File subjectFile = Utils.join(subjectSpecificDirectory, fileName);
                Utils.writeContents(subjectFile, Utils.serialize(course));
            }
            _courses.add(name);
        } else {
            System.out.println("You need to sign in to add a subject.");
        }
    }

    /** Adds subject using .txt file. */
    void addSubjects(String... args) {
        if (!name().equals("None")) {
            int totalSubjects = Integer.parseInt(args[1]);
            for (int i = 0; i < totalSubjects; i++) {
                System.out.println("Enter Course No. as <Course Name> <Course Number>, like 'Data 8'" + i);
                Scanner courseInput = new Scanner(System.in);
                String course = courseInput.nextLine();
                addSubject("add-subject", course);
            }
        } else {
            System.out.println("You need to sign in to add a subject.");
        }
    }

    /** Add different components associated with different components. */
    void addComponent(String... args) {
        if (!name().equals("None")) {
            String courseName = args[1];
            String componentName = args[2];
            if (_courses.contains(courseName)) {
                File subjectFile = Utils.join(_subjectsDirectory, courseName, courseName + "-info");
                Subject course = Utils.readObject(subjectFile, Subject.class);
                course.addComponent(componentName);
            } else {
                System.out.println("You need to have added the course to which you are trying to add the assignment.");
            }
        } else {
            System.out.println("You need to sign in to add a subject.");
        }
    }

    /** Adds multiple components for a class. */
    void addComponents(String... args) {
        if (!name().equals("None")) {
            int totalComponents = Integer.parseInt(args[2]);
            String courseName = args[1];
            if (_courses.contains(courseName)) {
                for (int i = 0; i < totalComponents; i++) {
                    System.out.println("Enter the name of the component");
                    Scanner componentInput = new Scanner(System.in);
                    String input = componentInput.nextLine();
                    addComponent("add-component", courseName, input);
                }
            } else {
                System.out.println("You need to have added the course to which you are trying to add the assignment.");
            }
        } else {
            System.out.println("You need to sign in to add a subject.");
        }
    }

    /** For command which edits information about different subjects. */
    void editComponent(String... args) {
        if (!name().equals("None")) {
            String courseName = args[1];
            String componentName = args[2];
            String part = args[3];
            String newValue = args[4];
            if (_courses.contains(courseName)) {
                File subjectFile = Utils.join(_subjectsDirectory, courseName, courseName + "-info");
                if (subjectFile.exists()) {
                    Subject course = Utils.readObject(subjectFile, Subject.class);
                    course.editComponent(componentName, part, newValue);
                }
            } else {
                System.out.println("You need to have added the course to which you are trying to add the assignment.");
            }
        } else {
            System.out.println("You need to sign in to add a subject.");
        }

    }

    /** For command which edits information about different subjects' components. */
    void editSubject(String... args) {
        String name = args[1];
        if (!name().equals("None")) {
            if (Utils.join(_subjectsDirectory, name).exists()) {
                System.out.println("Please enter the total points for the course");
                Scanner courseInputPoints = new Scanner(System.in);
                String coursePoints = courseInputPoints.nextLine();
                int totalPoints = Integer.parseInt(coursePoints);
                System.out.println("Please enter whether the class is curved");
                Scanner curvedInput = new Scanner(System.in);
                String curvedOrnot = curvedInput.nextLine();
                Subject course = new Subject(name, totalPoints, curvedOrnot);
                String fileName = name + "-info";
                File subjectSpecificDirectory = Utils.join(_subjectsDirectory, name);
                subjectSpecificDirectory.mkdir();
                File subjectFile = Utils.join(subjectSpecificDirectory, fileName);
                Utils.writeContents(subjectFile, Utils.serialize(course));
            } else {
                System.out.println("You cannot edit a course not added.");
            }
        } else {
            System.out.println("You need to sign in to add a subject.");
        }

    }

    /** Returns the password of the student. */
    String password() {
        return _password;
    }

    /** Returns if the account is locked or not. */
    boolean accountLocked() {
        return _accountLocked;
    }

    /** Name of the student. */
    private String _name;

    /** Password of the student. */
    private String _password;

    /** States the current state of the account. */
    private boolean _accountLocked;

    /** Courses Taken. */
    private ArrayList<String> _courses;

    /** Subjects Directory. */
    private final File _subjectsDirectory = Utils.join(Main._gradesDirectory, ".subjects");
}
