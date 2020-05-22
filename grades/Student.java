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


/** Student Class
 *  @author P. N. Desai
 */
class Student implements Serializable {

    /** Constructor for student using NAME. */
    Student(String name) {
        _name = name;
        _accountLocked = true;
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

}
