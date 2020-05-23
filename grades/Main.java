package grades;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/** Driver class for the terminal-based grading calculator
 *  @author P. N. Desai
 */
class Main {
    /** Usage: java grades.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            exitWithoutError("Enter a command.");
        } else if (args[0].equals("init")) {
            setUp();
        } else if (_gradesDirectory.exists()) {
            retrieveCurrentStudent();
            if (args[0].equals("sign-up")) {
                String name = args[1];
                studentexist(name);
            } else if (args[0].equals("sign-in")) {
                signIn(args[1], Utils.join(_studentsDirectory, args[1]));
            } else if (args[0].equals("sign-out")) {
                signOut(args[1]);
            } else if (args[0].equals("add-subject")) {
                _currentStudent.addSubject(args);
            } else if (args[0].equals("add-subjects")) {
                _currentStudent.addSubjects(args);
            } else if (args[0].equals("add-component")) {
                _currentStudent.addComponent(args);
            } else if(args[0].equals("add-components")) {
                _currentStudent.addComponents(args);
            }
            updateCurrentStudent();
        } else {
            System.out.println("You need to run init to initialize the grading system.");
        }
    }

    /** Exits the system after printing MESSAGE. */
    public static void exitWithoutError(String message) {
        if (message != null && !message.equals("")) {
            System.out.println(message);
        }
        System.exit(0);
    }

    /** Checks for incorrect operands. */
    static void setUp() {
        boolean gCreated = _gradesDirectory.mkdir();
        boolean sCreated = _studentsDirectory.mkdir();
        _currentStudent = new Student("None");
        Utils.writeContents(Utils.join(_gradesDirectory, "currentStudent"),
                Utils.serialize(_currentStudent));
        boolean subjectsDirectory = Utils.join(Main._gradesDirectory, ".subjects").mkdir();
    }

    /** Setup

    /** Checks if the student exists in the system. */
    static void studentexist(String name) {
        String cwd = System.getProperty("user.dir");
        File gradesDirectory = Utils.join(cwd, ".grades");
        File studentsDirectory = Utils.join(gradesDirectory, ".students");
        File studentNameDirectory = Utils.join(studentsDirectory, name);
        if (gradesDirectory.exists()) {
            if (studentsDirectory.exists()) {
                if (studentNameDirectory.exists()) {
                    System.out.println("An account with this name already exists; Enter your password to continue");
                    signIn(name, studentNameDirectory);
                } else {
                    boolean studentND = studentNameDirectory.mkdir();
                    Student s = new Student(name);
                    studentCreation(s, studentNameDirectory);
                }
            }
        } else {
            System.out.println("Grades track not initialized.");
        }
    }


    /** Creates the student name directory and ask them for their password. */
    public static void studentCreation(Student s, File studentDirectory) {
        studentDirectory.mkdir();
        Scanner passwordEntry = new Scanner(System.in);
        System.out.println("Enter the password.");
        String password = passwordEntry.nextLine();
        File studentFile = Utils.join(studentDirectory, s.name());
        s.addPassword(password);
        Utils.writeContents(studentFile, Utils.serialize(s));
    }

    /** Signs-in a student. */
    public static void signIn(String name, File studentNameDirectory) {
        File studentFile = Utils.join(studentNameDirectory, name);
        if (_currentStudent.name().equals("None")) {
            if (studentNameDirectory.exists()) {
                System.out.println("Enter the password for sign-in.");
                Scanner passwordEntry = new Scanner(System.in);
                String passwordEntered = passwordEntry.nextLine();
                Student s = Utils.readObject(studentFile, Student.class);
                s.passwordMatch(passwordEntered, false);
                if (!s.accountLocked()) {
                    _currentStudent = s;
                } else {
                    _currentStudent = new Student("None");
                }
            } else {
                System.out.println("Please create your account first by running the command 'sign-up " + name + "'");
            }
        } else {
            System.out.println(_currentStudent.name() + " needs to sign-out first.");
        }
    }

    /** Signs-out a student. */
    public static void signOut(String name) {
        File studentNameDirectory = Utils.join(_studentsDirectory, name);
        if (!_currentStudent.name().equals("None")) {
            if (studentNameDirectory.exists()) {
                if (_currentStudent.name().equals(name)) {
                    System.out.println("Enter the password for sign-in.");
                    Scanner passwordEntry = new Scanner(System.in);
                    String passwordEntered = passwordEntry.nextLine();
                    _currentStudent.passwordMatch(passwordEntered, true);
                    if (_currentStudent.accountLocked()) {
                        _currentStudent = new Student("None");
                    }
                } else {
                    System.out.println("You cannot sign-out for" + _currentStudent.name());
                }
            } else {
                System.out.println("Please create your account first by running the command 'sign-up " + name + "'");
            }
        } else {
            System.out.println("Someone needs to be signed-in to sign-out.");
        }
    }

    /** Retrieves the current student.*/
    static void retrieveCurrentStudent() {
        Student cS = Utils.readObject(Utils.join(_gradesDirectory, "currentStudent"), Student.class);
        _currentStudent = cS;
    }

    /** Updates the current student Field. */
    static void updateCurrentStudent() {
        File currentStudent = Utils.join(_gradesDirectory, "currentStudent");
        Utils.writeContents(currentStudent, Utils.serialize(_currentStudent));
    }
    

    /** Defines the CWD. */
    static String _cwd = System.getProperty("user.dir");

    /** Defines the grade directory. */
    static File _gradesDirectory = Utils.join(_cwd, ".grades");

    /** Defines the student directory. */
    static File _studentsDirectory = Utils.join(_gradesDirectory, ".students");

    /** Defines the current student who is logged in. */
    static Student _currentStudent;

}
