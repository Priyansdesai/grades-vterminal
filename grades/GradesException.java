package grades;

/** General exception indicating a Gitlet error.  For fatal errors, the
 *  result of .getMessage() is the error message to be printed.
 *  @author P. N. Hilfinger
 */
class GradesException extends RuntimeException {


    /** A GitletException with no message. */
    GradesException() {
        super();
    }

    /** A GitletException MSG as its message. */
    GradesException(String msg) {
        super(msg);
    }

}
