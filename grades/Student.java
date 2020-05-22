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
import java.util.*;


/** Student Class
 *  @author P. N. Desai
 */
class Student implements Serializable {

    /** Constructor for student using NAME. */
    Student(String name) {
        _name = name;
        _accountLocked = true;
        _courses = new ArrayList<Subject>();
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
            _courses.add(new Subject(name));
        } else {
            System.out.println("You need to sign in to add a subject.");
        }
    }

    /** Adds subject using .txt file. */
    void addSubjects(String... args) {
        if (!name().equals("None")) {
            int totalSubjects = Integer.parseInt(args[1]);
            for (int i = 0; i < totalSubjects; i++) {
                System.out.println("Enter Course No." + i);
                Scanner courseInput = new Scanner(System.in);
                String course = courseInput.nextLine();
                addSubject("add-subject" + course);
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
    private ArrayList<Subject> _courses;
}
