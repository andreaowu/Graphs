package make;

import graph.DepthFirst;
import graph.Graph;
import graph.Vertex;
import graph.RejectException;
import graph.Edge;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;

/** Depth-First search of Make client.
 *  @author Andrea Wu
 */
public class MakeDepthFirst extends DepthFirst<StringInt, Nothing> {

    /** Positive INFINITY for ints. */
    private static final int INFINITY = 2147483647;
    /** HashMap that stores all targets with their prereqs. */
    private HashMap<String, ArrayList<StringInt>> _tAndP;
    /** Makes a list of already visited vertices. */
    private ArrayList<String> _alreadyVisited;

    /** Constructor for DepthFirst that takes GRAPH and TANDP. */
    public MakeDepthFirst(Graph<StringInt, Nothing> graph,
        HashMap<String, ArrayList<StringInt>> tAndP) {
        super(graph);
        ArrayList<String> alreadyVisited = new ArrayList<String>();
        _alreadyVisited = alreadyVisited;
        _tAndP = tAndP;
    }

    @Override
    /** Sees whether the target for V is built yet. */
    protected void preVisit(Vertex<StringInt> v) {
        _alreadyVisited.add(v.getLabel().getName());
        ArrayList<StringInt> prereqs = _tAndP.get(v.getLabel().getName());
        if (prereqs == null && v.getLabel().getBuild()) {
            _alreadyVisited.remove(v.getLabel().getName());
            throw new RejectException("Target already got built");
        }
    }

    @Override
    /** Builds the target for V. */
    protected void postVisit(Vertex<StringInt> v) {
        if (v.getLabel().getNeedBuild()) {
            if (!_tAndP.containsKey(v.getLabel().getName())) {
                System.err.println("Error: Doesn't know how to build");
                System.exit(1);
            }
            ArrayList<StringInt> rule = _tAndP.get(v.getLabel().getName());
            ArrayList<StringInt> newerThanTarget = new ArrayList<StringInt>();
            if (rule != null) {
                for (int i = 0; i < rule.size(); i += 1) {
                    if (rule.get(i).getTime() > v.getLabel().getTime()) {
                        newerThanTarget.add(rule.get(i));
                    }
                }
            }
            newerThanTarget = sort(newerThanTarget);
            rule = sort(rule);
            String replaceWith = makeString(rule);
            String stringNewerThanTarget = makeString(newerThanTarget);
            String commandSet = v.getLabel().getCS();
            String returnThis = "";
            for (int j = 0; j < commandSet.length(); j += 1) {
                if (j == commandSet.length() - 1 && commandSet.charAt(j) != '^'
                    && commandSet.charAt(j) != '?') {
                    returnThis = returnThis.concat("" + commandSet.charAt(j));
                    break;
                } else if (j == commandSet.length() - 1) {
                    break;
                }
                char c1 = commandSet.charAt(j);
                char c2 = commandSet.charAt(j + 1);
                String s = "" + c1 + c2;
                if (s.equals("$^")) {
                    returnThis = returnThis.concat(replaceWith);
                    j += 1;
                } else if (s.equals("$?")) {
                    returnThis = returnThis.concat(stringNewerThanTarget);
                    j += 1;
                } else {
                    returnThis = returnThis.concat("" + c1);
                }
            }
            returnThis = process(returnThis);
            if (returnThis.equals("")) {
                v.getLabel().setBuild();
                _alreadyVisited.remove(v.getLabel().getName());
                v.getLabel().setTime(INFINITY);
                return;
            }
            System.out.println(returnThis);
            v.getLabel().setBuild();
            _alreadyVisited.remove(v.getLabel().getName());
            v.getLabel().setTime(INFINITY);
        }
    }

    @Override
    /** Sees whether necessary to build the other end of E according to V0. */
    protected void preVisit(Edge<StringInt, Nothing> e, Vertex<StringInt> v0) {
        if (_alreadyVisited.contains(e.getV1().getLabel().getName())) {
            System.out.println("Error: loop");
        } else if (v0.getLabel().getTime() > e.getV1().getLabel().getTime()
            && _tAndP.get(e.getV1().getLabel().getName()) == null) {
            throw new RejectException("Prereq already got built");
        }
    }

    @Override
    /** Continues traversing the other side of E from V0. */
    protected void postVisit(Edge<StringInt, Nothing> e,
        Vertex<StringInt> v0) {
        if (v0.getLabel().getTime() > e.getV1().getLabel().getTime()) {
            v0.getLabel().setNeedBuild();
        }
    }

    /** Returns a sorted arraylist of the given argument NEWERTHANTARGET. */
    private ArrayList<StringInt> sort(ArrayList<StringInt> newerThanTarget) {
        ArrayList<StringInt> sorted = new ArrayList<StringInt>();
        while (newerThanTarget != null && newerThanTarget.size() != 0) {
            int time = newerThanTarget.get(0).getTime();
            StringInt min = newerThanTarget.get(0);
            for (int w = 0; w < newerThanTarget.size(); w += 1) {
                if (newerThanTarget.get(w).getTime() < time) {
                    min = newerThanTarget.get(w);
                    time = newerThanTarget.get(w).getTime();
                }
            }
            newerThanTarget.remove(min);
            sorted.add(min);
        }
        return sorted;
    }

    /** Returns the string form of LST. */
    private String makeString(ArrayList<StringInt> lst) {
        String answer = "";
        for (int i = 0; i < lst.size(); i += 1) {
            answer = answer.concat(lst.get(i).getName() + " ");
        }
        if (answer.endsWith(" ")) {
            answer = answer.substring(0, answer.length() - 1);
        }
        return answer;
    }

    /** Returns the string so that no spaces at beginning of
     * COMMANDSET. */
    private String process(String commandSet) {
        String answer = "";
        BufferedReader in = new BufferedReader(new
            StringReader(commandSet));
        try {
            String line = in.readLine();
            while (line != null) {
                for (int i = 0; i < line.length(); i += 1) {
                    if (!(line.charAt(i) == ' ' || line.charAt(i) == '\t')) {
                        line = line.substring(i);
                        break;
                    }
                }
                answer = answer.concat(line + '\n');
                line = in.readLine();
            }
        } catch (IOException ex) {
            return answer;
        }
        if (answer.equals("")) {
            return answer;
        }
        return answer.substring(0, answer.length() - 1);
    }

    /**MADE FOR JUNIT TESTING. NOT FOR PUBLIC USE.
     * Returns a sorted arraylist of the given argument NEWERTHANTARGET. */
    public ArrayList<StringInt> sortTESTING(ArrayList<StringInt>
        newerThanTarget) {
        return sort(newerThanTarget);
    }

    /**MADE FOR JUNIT TESTING. NOT FOR PUBLIC USE.
     * Returns the string form of LST. */
    public String makeStringTESTING(ArrayList<StringInt> lst) {
        return makeString(lst);
    }

    /**MADE FOR JUNIT TESTING. NOT FOR PUBLIC USE.
     * Returns the string so that no spaces at beginning of
     * COMMANDSET. */
    public String processTESTING(String commandSet) {
        return process(commandSet);
    }
}
