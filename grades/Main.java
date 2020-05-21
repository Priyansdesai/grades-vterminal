package grades;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

/** Driver class for the terminal-based grading calculator
 *  @author P. N. Desai
 */
class Main {
    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            exitWithoutError("Enter a command.");
        } else if (args[0].equals("student")) {
            //checkParam(args);
            String name = args[1];
            studentexist(name);
        } else if (args[0].equals("sign-in")) {
            checkParam(args);
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
    static void checkParam(String... args) {
        if (args.length != 1) {
            exitWithoutError("Missing or excessive information found.");
        }
    }

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
            boolean gCreated = gradesDirectory.mkdir();
            boolean sCreated = studentsDirectory.mkdir();
            boolean studentNDirectory = studentNameDirectory.mkdir();
            Student s = new Student(name);
            studentCreation(s, studentNameDirectory);

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
        Scanner passwordEntry = new Scanner(System.in);
        String passwordEntered = passwordEntry.nextLine();
        Student s = Utils.readObject(studentFile, Student.class);
        s.passwordMatch(passwordEntered);
    }


}
