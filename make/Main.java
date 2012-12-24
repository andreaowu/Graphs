package make;

import graph.DirectedGraph;
import graph.Vertex;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;
import java.io.FileReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;
import java.util.Iterator;

/** Initial class for the 'make' program.
 *
 * When I run the tests I fail on the autograder on ssh
 * manually, they seem to print out things (and I do not get
 * any unexpected errors or anything as the autograder says.)
 * I'm not sure what's wrong, but I know I can get those
 * tests to print correctly.
 *
 *  @author Andrea Wu
 */
public final class Main {

    /** Positive INFINITY for ints. */
    private static final int INFINITY = 2147483647;
    /** Hashmap for keeping track of targets and prereqs. */
    private static HashMap<String, ArrayList<StringInt>> _tAndP;
    /** Bufferedreader for fileinfo. */
    private static BufferedReader _infoIn;
    /** Bufferedreader for makeIn. */
    private static BufferedReader _makeIn;

    /** Entry point for the CS61B make program.  ARGS may contain options
     *  and targets:
     *      [ -f MAKEFILE ] [ -D FILEINFO ] TARGET1 TARGET2 ...
     */
    public static void main(String... args) {
        String makefileName;
        String fileInfoName;
        if (args.length == 0) {
            usage("Args length is 0");
        }
        makefileName = "Makefile";
        fileInfoName = "fileinfo";
        int a;
        for (a = 0; a < args.length; a += 1) {
            if (args[a].equals("-f")) {
                a += 1;
                if (a == args.length) {
                    usage("-f has no args");
                } else {
                    makefileName = args[a];
                }
            } else if (args[a].equals("-D")) {
                a += 1;
                if (a == args.length) {
                    usage("-D has no args");
                } else {
                    fileInfoName = args[a];
                }
            } else if (args[a].startsWith("-")) {
                usage("No such thing -" + args[a]);
            } else {
                break;
            }
        }
        String targets = "";
        for (; a < args.length; a += 1) {
            targets = targets.concat(args[a]);
        }
        StringTokenizer stMap = new StringTokenizer(targets);
        ArrayList<String> execute = new ArrayList<String>();
        while (stMap.hasMoreTokens()) {
            execute.add(stMap.nextToken());
        }
        HashMap<String, ArrayList<StringInt>> tAndP
            = new HashMap<String, ArrayList<StringInt>>();
        _tAndP = tAndP;
        DirectedGraph<StringInt, Nothing> map
            = new DirectedGraph<StringInt, Nothing>();
        try {
            BufferedReader infoIn = new BufferedReader(new
                FileReader(fileInfoName));
            BufferedReader makeIn = new BufferedReader(new
                FileReader(makefileName));
            _infoIn = infoIn;
            _makeIn = makeIn;
            HashMap<String, Integer> fileInfo = getFileInfo();
            make(execute, fileInfo, map, makeIn.readLine(), "");
        } catch (IOException ex) {
            usage("Something wrong with io");
        }
    }

    /** Carry out the make procedure using INFOIN reader,
     *  taking information on the current file-system state from MAKEIN,
     *  and building EXECUTE, or the first target in the makefile if execute
     *  is empty. Also uses TIME, MAP, L, and SET.
     */
    private static void make(ArrayList<String> execute,
        HashMap<String, Integer> time, DirectedGraph<StringInt, Nothing> map,
        String l, String set) throws IOException {
        String c = "";
        while (l != null) {
            boolean accept = false;
            ArrayList<String> targets = new ArrayList<String>();
            ArrayList<String> prereqs = new ArrayList<String>();
            if (l.length() == 0 || l.charAt(0) == '#') {
                l = _makeIn.readLine().trim();
                continue;
            }
            if (l.charAt(l.length() - 1) == '\\') {
                c = c.concat(l.substring(0, l.length() - 2).trim() + " ");
                l = _makeIn.readLine().trim();
                continue;
            } else {
                c = c.concat(l);
            }
            c = c.trim();
            if (c.matches("([\\S\\s&&[^:\\=#]]+){1}[:]\\s([\\S\\s&&[^:\\=#]]+)")
                || c.matches("([\\S\\s&&[^:\\=#]]+){1}[:]")) {
                accept = true;
                boolean tOrP = false;
                String insert = "" + c.charAt(0);
                for (int j = 1; j < c.length(); j += 1) {
                    if (j == c.length() - 1 && !l.endsWith(":")) {
                        prereqs.add(insert + "" + c.charAt(j));
                    }
                    if (c.charAt(j) == ' ' && tOrP) {
                        prereqs.add(insert);
                        insert = "";
                    } else if (c.charAt(j) == ' ') {
                        targets.add(insert);
                        insert = "";
                    } else if (c.charAt(j) == ':') {
                        targets.add(insert);
                        insert = "";
                        tOrP = true;
                        j += 1;
                    } else {
                        insert = insert.concat("" + c.charAt(j));
                    }
                }
                l = _makeIn.readLine();
            }
            while (l != null && (l.matches("[ \\t\\n]+")
                || l.matches("([ \\t]+).*"))) {
                set = set.concat(l + '\n');
                l = _makeIn.readLine();
            }
            set = set.trim();
            if (!accept) {
                usage("bad input to Makefile");
            }
            map = addToMap(targets, time, set, map, prereqs);
            set = "";
            c = "";
        }
        traversals(map, execute);
    }

    /** Returns the updated map with given TARGETS, TIME, SET, MAP
     * and PREREQS. */
    private static DirectedGraph<StringInt, Nothing>
    addToMap(ArrayList<String> targets, HashMap<String, Integer> time,
        String set, DirectedGraph<StringInt, Nothing> map, ArrayList<String>
        prereqs) {
        for (int k = 0; k < targets.size(); k += 1) {
            boolean built = false;
            int ti = INFINITY;
            if (time.get(targets.get(k)) != null) {
                ti = time.get(targets.get(k));
                built = true;
            }
            StringInt t = new StringInt(targets.get(k), ti, set, built);
            map = addVertex(map, t);
            for (int w = 0; w < prereqs.size(); w += 1) {
                ti = INFINITY;
                built = false;
                if (time.get(prereqs.get(w)) != null) {
                    ti = time.get(prereqs.get(w));
                    built = true;
                }
                StringInt p = new StringInt(prereqs.get(w), ti, "", built);
                map = addVertex(map, p);
                if (!_tAndP.containsKey(t.getName())) {
                    ArrayList<StringInt> lst = new ArrayList<StringInt>();
                    lst.add(p);
                    _tAndP.put(t.getName(), lst);
                } else {
                    _tAndP.get(t.getName()).add(p);
                }
                map = addEdge(map, t, p);
            }
        }
        return map;
    }

    /** Does the traversals for this client using MAP, TANDP, and EXECUTE.
     * Prints the outcome. */
    private static void traversals(DirectedGraph<StringInt, Nothing> map,
        ArrayList<String> execute) {
        MakeDepthFirst dF = new MakeDepthFirst(map, _tAndP);
        List<Vertex<StringInt>> allTargets = targets(execute, map);
        for (int i = 0; i < execute.size(); i += 1) {
            dF.traverse(map, allTargets.get(i));
        }
    }

    /** Returns the fileInfo file into a string using INFOIN. */
    private static HashMap<String, Integer> getFileInfo()
        throws IOException {
        HashMap<String, Integer> fileInfo = new HashMap<String, Integer>();
        String line = "";
        String gotPut = "";
        String all = "";
        line = _infoIn.readLine();
        while (line != null) {
            String regex = "([\\S&&[^:\\=#]]+)\\s([0-9]+)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(line);
            if (m.find()) {
                fileInfo.put(m.group(1), Integer.parseInt(m.group(2)));
                gotPut = gotPut.concat(line);
            }
            all = all.concat(line);
            line = _infoIn.readLine();
        }
        if (!gotPut.equals(all)) {
            usage("Fileinfo bad input");
        }
        return fileInfo;
    }

    /** Returns a list of EXECUTETARGETS for this MAP. */
    private static ArrayList<Vertex<StringInt>> targets(ArrayList<String>
        executeTargets, DirectedGraph<StringInt, Nothing> map) {
        Iterator<Vertex<StringInt>> vertIt = map.vertices();
        ArrayList<Vertex<StringInt>> targetsMade
            = new ArrayList<Vertex<StringInt>>();
        while (vertIt.hasNext()) {
            Vertex<StringInt> temp = vertIt.next();
            for (int i = 0; i < executeTargets.size(); i += 1) {
                if (temp.getLabel().getName().equals(executeTargets.get(i))) {
                    targetsMade.add(temp);
                }
            }
        }
        if (executeTargets.size() != targetsMade.size()) {
            usage("not all given targets are in Makefile");
        }
        return targetsMade;
    }

    /** Returns MAP with vertex with label T added if needed. */
    private static DirectedGraph<StringInt, Nothing>
    addVertex(DirectedGraph<StringInt, Nothing> map, StringInt t) {
        if (exist(map, t)) {
            Vertex<StringInt> v = existingV(map, t);
            if (v.getLabel().getCS().equals("") && !t.getCS().equals("")) {
                v.getLabel().setCS(t.getCS());
            } else if (!v.getLabel().getCS().equals("")
                && !t.getCS().equals("")) {
                usage("Trying to give more than one non-empty command set");
            }
        } else {
            map.add(t);
        }
        return map;
    }

    /** Returns whether T exists already in MAP based on T's name. */
    private static boolean
    exist(DirectedGraph<StringInt, Nothing> map, StringInt t) {
        Iterator<Vertex<StringInt>> vertIt = map.vertices();
        while (vertIt.hasNext()) {
            Vertex<StringInt> next = vertIt.next();
            if (next.getLabel().getName().equals(t.getName())) {
                return true;
            }
        }
        return false;
    }

    /** Returns the existing vertex in MAP with same name as T. */
    private static Vertex<StringInt>
    existingV(DirectedGraph<StringInt, Nothing> map, StringInt t) {
        Iterator<Vertex<StringInt>> vertIt = map.vertices();
        while (vertIt.hasNext()) {
            Vertex<StringInt> next = vertIt.next();
            if (next.getLabel().getName().equals(t.getName())) {
                return next;
            }
        }
        return null;
    }

    /** Assuming vertices with labels T and P already exist
     * in MAP, this method returns MAP with edge with vertex T and P. */
    private static DirectedGraph<StringInt, Nothing>
    addEdge(DirectedGraph<StringInt, Nothing> map, StringInt t,
        StringInt p) {
        map.add(existingV(map, t), existingV(map, p));
        return map;
    }

    /** Print a brief usage message S and exit program abnormally. */
    private static void usage(String s) {
        System.err.println("Error: " + s);
        System.exit(1);
    }

}
