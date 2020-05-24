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
import java.util.concurrent.CompletionException;


/** Subject Class
 * @author P. N. Desai
 */
class Subject implements Serializable {

    /** Initializing Subject. */
    Subject(String name, int totalPoints, String curved) {
        _name = name;
        _totalComponentWeightage = totalPoints;
        _totalTillNow = 0;
        _subjectSpecificDirectory = Utils.join(_subjectsDirectory, _name);
        _subjectFile = Utils.join(_subjectSpecificDirectory, _name + "-info");
        _curved = false;
        if (curved.charAt(0) == 'Y' || curved.charAt(0) == 'y') {
            _curved = true;
        }

    }

    /** Adds every component for the class. */
    void addComponent(String name) {
        File componentFile = Utils.join(_subjectSpecificDirectory, name);
        if (componentFile.exists()) {
            System.out.println("The " + name + " component has already been added");
        } else {
            System.out.println("Please enter the weightage for this component");
            Scanner weightageInput = new Scanner(System.in);
            String weight = weightageInput.nextLine();
            int weightage = Integer.parseInt(weight);
            System.out.println("Please enter whether the score for this component is reader-score-adjusted.");
            Scanner readerAdjustmentInput = new Scanner(System.in);
            String readerAdjustment = readerAdjustmentInput.nextLine();
            boolean readerAdjustmentApplied = Boolean.parseBoolean(readerAdjustment);
            System.out.println("Please enter the no. of drops on this assignment");
            Scanner dropInput = new Scanner(System.in);
            String drop = dropInput.nextLine();
            int drops = Integer.parseInt(drop);
            Utils.writeContents(componentFile, Utils.serialize(new Component(name, weightage,
                    readerAdjustmentApplied, drops)));
            _totalComponentWeightage += weightage;

        }
        if (!(_totalComponentWeightage == _totalTillNow)) {
            System.out.println("You need to add more components for the total to add up ");
        }
    }

    /** Allows the editing of different components of the course. */
    void editComponent(String componentName, String part, String value) {
        File componentFile = Utils.join(_subjectSpecificDirectory, componentName);
        if (componentFile.exists()) {
            Component componentObj = Utils.readObject(componentFile, Component.class);
            if (part.equals("name")) {
                componentObj.setName(value);
                Utils.writeContents(Utils.join(_subjectSpecificDirectory, value), Utils.serialize(componentObj));
                Utils.restrictedDelete(componentFile);
            } else if (part.equals("reader-adjustment")) {
                boolean realValue = true;
                if (value.charAt(0) == 'n' || value.charAt(0) == 'N') {
                    realValue = false;
                }
                componentObj.setReaderAdjustment(realValue);
                Utils.writeContents(componentFile, Utils.serialize(componentObj));
            } else if (part.equals("drops")) {
                componentObj.setDrops(Integer.parseInt(value));
                Utils.writeContents(componentFile, Utils.serialize(componentObj));
            } else if (part.equals("weightage")) {
                componentObj.setWeight(Integer.parseInt(value));
                Utils.writeContents(componentFile, Utils.serialize(componentObj));
            }
        } else {
            System.out.println("The component you are trying to edit does not exist in this course.");
        }

    }

    /** Sets the points for the course. */
    void setPoints(int value) {
        _totalComponentWeightage = value;
    }

    /** Sets the curved nature of the class. */
    void setCurved(String val) {
        _curved = false;
        if (val.charAt(0) == 'y' || val.charAt(0) == 'Y') {
            _curved = true;
        }
    }

    /** Returns the name of the course. */
    String name() {
        return _name;
    }

    /** Name of the subject. */
    private String _name;

    /** Total Component Weightage. */
    private int _totalComponentWeightage;

    /** Subjects Directory. */
    private final File _subjectsDirectory = Utils.join(Main._gradesDirectory, ".subjects");

    /** Subjects Directory. */
    private File _subjectFile;

    /** Subjects Specific Directory. */
    private File _subjectSpecificDirectory;

    /** Represents the total till now. */
    private int _totalTillNow;

    /** Grade boundaries for the class. */
    private HashMap<String, Integer> _gradeBoundaries;

    /** Curved or not. */
    private boolean _curved;

}
