package grades;


import java.io.Serializable;

/** Assignment Class
 * @author P. N. Desai
 */
class Component implements Serializable {

    Component(String name, int weight) {
        _name = name;
        _weight = weight;
    }

    /**Name of the Component. */
    private String _name;

    /** Weightage of the component. */
    private int _weight;


}
