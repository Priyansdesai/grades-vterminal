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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.List;


/** Subject Class
 * @author P. N. Desai
 */
class Subject implements Serializable {

    /** Initializing Subject. */
    Subject(String name) {
        _name = name;
        _components = new ArrayList<Assignment>();
        _totalComponentWeightage = 0;
        _readerScoreAdjustmentRequired = false;
    }

    /** Adds every component for the class. */
    void addComponent(String name, int weight) {


    }

    /** Returns the name of the course. */
    String name() {
        return _name;
    }

    /** Name of the subject. */
    private String _name;

    /** List of components. */
    private ArrayList<Assignment> _components;

    /** Total Component Weightage. */
    private int _totalComponentWeightage;

    /** Reader Adjustment Factor Required. */
    private boolean _readerScoreAdjustmentRequired;

}
