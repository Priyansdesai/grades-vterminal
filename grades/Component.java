package grades;


import java.io.Serializable;

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

    /**Name of the Component. */
    private String _name;

    /** Weightage of the component. */
    private int _weight;

    /** Specifies whether the score of this component is impacted by a reader adjustment. */
    private boolean _readerAdjustment;

    /** No. of drops on this component. */
    private int _drops;


}
