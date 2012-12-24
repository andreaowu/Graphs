package make;

/** Class made for storing VLabels for the Make client.
 * @author andreawu
 */
public class StringInt {

    /** String instance variable. */
    private String _s;
    /** Time this was built. */
    private int _time;
    /** Boolean that tells whether the Make target existed in info file. */
    private boolean _build;
    /** The command set given to the vertex. */
    private String _commandSet;
    /** Need to be built? */
    private boolean _needBuild;

    /** Constructor for StringInt that stores S (name of this),
     * TIME built, COMMANDSET, and whether it's been BUILD. */
    public StringInt(String s, int time, String commandSet, boolean build) {
        _s = s;
        _time = time;
        _commandSet = commandSet;
        _build = build;
        _needBuild = true;
    }

    /** Returns the string of this. */
    public String getName() {
        return _s;
    }

    /** Returns the time of this being built. */
    public int getTime() {
        return _time;
    }

    /** Sets the time to TIME. */
    public void setTime(int time) {
        _time = time;
    }

    /** Returns the whether the target exists already. */
    public boolean getBuild() {
        return _build;
    }

    /** Sets that the target has already been built. */
    public void setBuild() {
        _build = false;
    }

    /** Returns whether the target needs to be built. */
    public boolean getNeedBuild() {
        return _needBuild;
    }

    /** Gets build. */
    public void setNeedBuild() {
        _needBuild = false;
    }

    /** Returns the command set. */
    public String getCS() {
        return _commandSet;
    }

    /** Sets the command set to CS. */
    public void setCS(String cs) {
        _commandSet = cs;
    }
}
