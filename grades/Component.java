package grades;


import java.io.Serializable;
import java.util.HashMap;

/** Assignment Class
 * @author P. N. Desai
 */
class Component implements Serializable {

    Component(String name, int weight, boolean readerAdjustment, int drops) {
        _name = name;
        _weight = weight;
        _readerAdjustment = readerAdjustment;
        _drops = drops;
    }

    /** Sets the name of the component. */
    void setName(String name) {
        _name = name;
    }

    /** Sets the weight of the component. */
    void setWeight(int weight) {
        _weight = weight;
    }

    /** Sets the readerAdjustment of the component. */
    void setReaderAdjustment(boolean readerAdjustment) {
        _readerAdjustment = readerAdjustment;
    }

    /** Sets the drops for the component. */
    void setDrops(int drops) {
        _drops = drops;
    }

    /**Name of the Component. */
    private String _name;

    /** Weightage of the component. */
    private int _weight;

    /** Specifies whether the score of this component is impacted by a reader adjustment. */
    private boolean _readerAdjustment;

    /** No. of drops on this component. */
    private int _drops;



}
