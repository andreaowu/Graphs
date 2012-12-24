package trip;

import graph.Graphs;
import graph.UndirectedGraph;
import graph.Vertex;
import graph.Edge;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileNotFoundException;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.regex.Pattern;
import java.util.Iterator;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

/** Initial class for the 'trip' program.
 *  @author Andrea Wu
 */
public final class Main {

    /** Entry point for the CS61B trip program.  ARGS may contain options
     *  and targets:
     *      [ -m MAP ] [ -o OUT ] [ REQUEST ]
     *  where MAP (default Map) contains the map data, OUT (default standard
     *  output) takes the result, and REQUEST (default standard input) contains
     *  the locations along the requested trip.
     */
    public static void main(String... args) {
        String mapFileName;
        String outFileName;
        String requestFileName;

        mapFileName = "Map";
        outFileName = requestFileName = null;

        int a;
        for (a = 0; a < args.length; a += 1) {
            if (args[a].equals("-m")) {
                a += 1;
                if (a == args.length) {
                    usage("-m problem");
                } else {
                    mapFileName = args[a];
                }
            } else if (args[a].equals("-o")) {
                a += 1;
                if (a == args.length) {
                    usage("-o problem");
                } else {
                    outFileName = args[a];
                }
            } else if (args[a].startsWith("-")) {
                usage("starts with - randomly");
            } else {
                break;
            }
        }

        if (a == args.length - 1) {
            requestFileName = args[a];
        } else if (a > args.length) {
            usage("a > args.length");
        }

        if (requestFileName != null) {
            try {
                System.setIn(new FileInputStream(requestFileName));
            } catch  (FileNotFoundException e) {
                System.err.printf("Could not open %s.%n", requestFileName);
                System.exit(0);
            }
        }

        if (outFileName != null) {
            try {
                System.setOut(new PrintStream(new FileOutputStream(outFileName),
                                              true));
            } catch  (FileNotFoundException e) {
                System.err.printf("Could not open %s for writing.%n",
                                  outFileName);
                System.exit(0);
            }
        }

        trip(mapFileName);
    }

    /** Print a trip for the request on the standard input to the standard
     *  output, using the map data in MAPFILENAME.
     */
    private static void trip(String mapFileName) {
        String file = "";
        String line = "";
        try {
            BufferedReader in = new BufferedReader(new FileReader(mapFileName));
            line = in.readLine();
            while (line != null) {
                file = file.concat(line + " ");
                line = in.readLine();
            }
        } catch (FileNotFoundException e) {
            usage("Cannot find mapFileName");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        UndirectedGraph<String, ArrayList<String>> map = makeMap(file);
        ArrayList<String> places = toFrom();
        check(places, map);
        if (places.size() < 2) {
            usage("where to?");
        }
        WeighterTrip<String> vweighter = new WeighterTrip<String>();
        WeightingTrip<ArrayList<String>> eweighter
            = new WeightingTrip<ArrayList<String>>();
        ArrayList<Vertex<String>> mid
            = new ArrayList<Vertex<String>>();
        ArrayList<Vertex<String>> mapVertices = allVertices(map, places);
        printPath(map, mapVertices, "", 0, 1, vweighter, eweighter, mid);
    }

    /** Returns the map (graph) using FILE. */
    private static UndirectedGraph<String,
        ArrayList<String>> makeMap(String file) {
        UndirectedGraph<String, ArrayList<String>> map
            = new UndirectedGraph<String, ArrayList<String>>();
        StringTokenizer stMap = new StringTokenizer(file);
        String matchThis = "";
        int counter = 0;
        while (stMap.hasMoreTokens()) {
            String temp = stMap.nextToken();
            matchThis = matchThis.concat(temp + " ");
            counter += 1;
            if (counter == 5 && !matchThis.matches("([A-Za-z0-9_-]+)\\s+([-\\w"
                + "]+)\\s+(\\d+\\.?\\d*)\\s+(NS|EW|SN|WE)\\s+([-\\w]+)\\"
                + "s*")) {
                usage(matchThis + "is bad args in Mapfile");
            } else if (counter == 5) {
                matchThis = matchThis.substring(0, matchThis.length() - 1);
                String regex = "([A-Za-z0-9-_]+)\\s+([-\\w]+)\\s+(\\d+\\.?\\d*"
                    + ")\\s+(NS|EW|SN|WE)\\s+([-\\w]+)";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(matchThis);
                if (m.matches()) {
                    ArrayList<String> components = new ArrayList<String>();
                    Iterator<Vertex<String>> vertIt = map.vertices();
                    boolean v0TF = false;
                    boolean v1TF = false;
                    Vertex<String> v0 = null;
                    Vertex<String> v1 = null;
                    while (vertIt.hasNext()) {
                        Vertex<String> next = vertIt.next();
                        if (m.group(1).equals(next.getLabel())) {
                            v0TF = true;
                            v0 = next;
                        } else if (m.group(5).equals(next.getLabel())) {
                            v1TF = true;
                            v1 = next;
                        }
                    }
                    if (!v0TF || v0 == null) {
                        v0 = map.add(m.group(1));
                    }
                    if (!v1TF || v1 == null) {
                        v1 = map.add(m.group(5));
                    }
                    components.add(m.group(2));
                    components.add(m.group(3));
                    components.add(m.group(4));
                    map.add(v0, v1, components);
                    counter = 0;
                    matchThis = "";
                }
            }
        }
        return map;
    }

    /** Prints the path using MAP, PLACES, DIR, LENGTH, TRACK, VW, EW,
     * AND MID. */
    private static void printPath(UndirectedGraph<String, ArrayList<String>>
        map, ArrayList<Vertex<String>> places, String dir, double length,
        int track, WeighterTrip<String> vw, WeightingTrip<ArrayList<String>>
        ew, ArrayList<Vertex<String>> mid) {
        for (int i = 0; i < places.size() - 1; i += 1) {
            Vertex<String> v0 = places.get(i);
            Vertex<String> v1 = places.get(i + 1);
            Graphs.computeShortestPathLengths(map, places.get(i), vw, ew);
            List<Edge<String, ArrayList<String>>> path = Graphs.shortestPath(
                map, places.get(i), places.get(i + 1), vw, ew);
            Vertex<String> before = null;
            Vertex<String> after = null;
            for (int p = 0; p < path.size(); p += 1) {
                if (p == 0) {
                    mid.add(places.get(i));
                    before = places.get(i);
                }
                if (path.get(p).getV0() == before) {
                    after = path.get(p).getV1();
                } else {
                    after = path.get(p).getV0();
                }
                mid.add(after);
                before = after;
            }
            printFrom(i, v0);
            Vertex<String> toHere = null;
            for (int j = 0; j < path.size(); j += 1) {
                ArrayList<String> elabel = path.get(j).getLabel();
                ArrayList<String> elabel2 = determine(path, j);
                Vertex<String> eV0 = path.get(j).getV0();
                Vertex<String> eV1 = path.get(j).getV1();
                dir = thisDir(mid, eV0, eV1, elabel, toHere, dir);
                toHere = thistoHere(mid, eV0, eV1, elabel, toHere, dir);
                if (length == 0) {
                    length += Double.parseDouble(elabel.get(1));
                }
                if (elabel2 != null && elabel.get(0).equals(elabel2.get(0))
                    && (elabel.get(2).equals(elabel2.get(2))
                    || backwards(elabel, elabel2))) {
                    length += Double.parseDouble(elabel2.get(1));
                    continue;
                }
                if (toHere == places.get(i + 1) && i + 1 != places.size() - 1) {
                    track = print1(track, elabel, dir, length, places, i);
                    length = 0;
                } else {
                    if (length == 0) {
                        length = Double.parseDouble(elabel.get(1));
                    }
                    track = print2(length, elabel, dir, track);
                    length = 0;
                }
                if (i == places.size() - 2 && j == path.size() - 1) {
                    print3(places, i);
                }
            }
        }
    }

    /** Returns whether ELABEL's direction is backwards of
     * ELABEL2's direction. */
    private static boolean
    backwards(ArrayList<String> elabel, ArrayList<String> elabel2) {
        String dir1 = elabel.get(2);
        String dir2 = elabel2.get(2);
        if (dir1.charAt(0) == dir2.charAt(1)
            && dir1.charAt(1) == dir2.charAt(0)) {
            return true;
        }
        return false;
    }

    /** Returns elabel2 with PATH and J. */
    private static ArrayList<String>
    determine(List<Edge<String, ArrayList<String>>> path, int j) {
        ArrayList<String> elabel2 = null;
        if (j < path.size() - 1) {
            elabel2 = path.get(j + 1).getLabel();
        }
        return elabel2;
    }

    /** Returns dir string in the printPath method by using MID, EV0, EV1,
     * ELABEL, TOHERE, and DIR. */
    private static String thisDir(ArrayList<Vertex<String>> mid,
        Vertex<String> eV0, Vertex<String> eV1, ArrayList<String> elabel,
        Vertex<String> toHere, String dir) {
        for (int p = 0; p < mid.size() - 1; p += 1) {
            if (mid.get(p) == eV0 && mid.get(p + 1) == eV1) {
                dir = elabel.get(2).substring(1, 2);
                toHere = eV1;
            } else if (mid.get(p) == eV1 && mid.get(p + 1) == eV0) {
                dir = elabel.get(2).substring(0, 1);
                toHere = eV0;
            }
        }
        return dir;
    }

    /** Returns Vertex toHere string in the printPath method by using MID,
     * EV0, EV1, ELABEL, TOHERE, and DIR. */
    private static Vertex<String> thistoHere(ArrayList<Vertex<String>> mid,
        Vertex<String> eV0, Vertex<String> eV1, ArrayList<String> elabel,
        Vertex<String> toHere, String dir) {
        for (int p = 0; p < mid.size() - 1; p += 1) {
            if (mid.get(p) == eV0 && mid.get(p + 1) == eV1) {
                dir = elabel.get(2).substring(1, 2);
                toHere = eV1;
            } else if (mid.get(p) == eV1 && mid.get(p + 1) == eV0) {
                dir = elabel.get(2).substring(0, 1);
                toHere = eV0;
            }
        }
        return toHere;
    }

    /** Helper print function that returns track and takes
     * I and V0. */
    private static void printFrom(int i, Vertex<String> v0) {
        if (i == 0) {
            System.out.println("From " + space(v0.getLabel()) + ":");
            System.out.println();
        }
    }

    /** Helper print function that returns track and takes
     * TRACK, ELABEL, DIR, LENGTH, PLACES, and I. */
    private static int print1(int track, ArrayList<String> elabel, String dir,
        double length, ArrayList<Vertex<String>> places, int i) {
        System.out.println(track + ". Take " + space(elabel.get(0))
            + " " + convert(dir) + " for " + round(length) + " miles to "
            + space(places.get(i + 1).getLabel()) + ".");
        length = 0;
        track += 1;
        return track;
    }

    /** Returns LENGTH that is rounded. */
    private static Double round(double length) {
        DecimalFormat twoDForm = new DecimalFormat("#.#");
        return Double.valueOf(twoDForm.format(length));
    }

    /** Helper print function that returns track and takes
     * LENGTH, ELABEL, DIR, and TRACK. */
    private static int print2(double length, ArrayList<String> elabel,
        String dir, int track) {
        System.out.println(track + ". Take " + space(elabel.get(0))
            + " " + convert(dir) + " for " + round(length) + " miles.");
        length = 0;
        track += 1;
        return track;
    }

    /** Helper print function that returns track and takes
     * PLACES and I. */
    private static void print3(ArrayList<Vertex<String>> places, int i) {
        System.out.println();
        System.out.print("To "
            + space(places.get(i + 1).getLabel()) + ".");
        System.out.println();
    }

    /** Returns list of vertices of PLACES that need to be traveled
     * over in MAP. */
    private static ArrayList<Vertex<String>> allVertices(UndirectedGraph<String,
        ArrayList<String>> map, ArrayList<String> places) {
        ArrayList<Vertex<String>> mapVertices
            = new ArrayList<Vertex<String>>();
        for (int k = 0; k < places.size(); k += 1) {
            Iterator<Vertex<String>> vertIt = map.vertices();
            while (vertIt.hasNext()) {
                Vertex<String> next = vertIt.next();
                if (next.getLabel().equals(places.get(k))) {
                    mapVertices.add(next);
                    break;
                }
            }
        }
        return mapVertices;
    }

    /** Returns list of places that the trip package has to find a path for. */
    private static ArrayList<String> toFrom() {
        ArrayList<String> places = new ArrayList<String>();
        String file = "";
        String line = "";
        InputStreamReader in = new InputStreamReader(System.in);
        BufferedReader read = new BufferedReader(in);
        try {
            line = read.readLine();
            if (line == null) {
                usage("No input");
            }
            while (line != null) {
                file = file.concat(line + " ");
                line = read.readLine();
            }
        } catch (IOException ex) {
            usage("in typing where to/from");
        }
        StringTokenizer st = new StringTokenizer(file);
        while (st.hasMoreTokens()) {
            String next = st.nextToken();
            places.add(next);
        }
        return places;
    }

    /** Returns whether all the PLACES requested are actually in
     * the MAP. */
    private static boolean check(ArrayList<String> places,
        UndirectedGraph<String, ArrayList<String>> map) {
        ArrayList<String> mapPlaces = new ArrayList<String>();
        ArrayList<String> allVertices = new ArrayList<String>();
        Iterator<Vertex<String>> vertIt = map.vertices();
        while (vertIt.hasNext()) {
            allVertices.add(vertIt.next().getLabel());
        }
        for (int i = 0; i < places.size(); i += 1) {
            if (allVertices.contains(places.get(i))) {
                mapPlaces.add(places.get(i));
            }
        }
        if (places.size() != mapPlaces.size()) {
            usage("Requested places not in map");
        }
        return true;
    }

    /** Returns given S with spaces instead of underscores. */
    private static String space(String s) {
        if (!s.contains("_")) {
            return s;
        } else {
            s = s.replace('_', ' ');
            return s;
        }
    }

    /** Returns the proper direction for S. */
    private static String convert(String s) {
        if (s.equals("N")) {
            return "north";
        } else if (s.equals("S")) {
            return "south";
        } else if (s.equals("E")) {
            return "east";
        } else if (s.equals("W")) {
            return "west";
        } else {
            usage("No such direction");
            return "";
        }
    }

    /** Print a brief usage message S and exit program abnormally. */
    private static void usage(String s) {
        System.err.println("Error: " + s);
        System.exit(1);
    }

    /** JUNIT TESTING METHOD ONLY. NOT PUBLIC FOR USE.
     *  Prints the path using MAP, PLACES. */
    public static void printPathTESTING(UndirectedGraph<String,
        ArrayList<String>> map, ArrayList<Vertex<String>> places) {
        WeighterTrip<String> vweighter = new WeighterTrip<String>();
        WeightingTrip<ArrayList<String>> eweighter
            = new WeightingTrip<ArrayList<String>>();
        ArrayList<Vertex<String>> mid
            = new ArrayList<Vertex<String>>();
        printPath(map, places, "", 0, 1, vweighter, eweighter, mid);
    }

    /** JUNIT TESTING METHOD ONLY. NOT PUBLIC FOR USE.
     * Returns list of vertices of PLACES that need to be traveled
     * over in MAP. */
    public static ArrayList<Vertex<String>> aVTESTING(UndirectedGraph<String,
        ArrayList<String>> map, ArrayList<String> places) {
        return allVertices(map, places);
    }

    /** JUNIT TESTING METHOD ONLY. NOT PUBLIC FOR USE.
     * Returns list of places that S has to find a path for. */
    public static ArrayList<String> toFromTESTING(String s) {
        ArrayList<String> places = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(s);
        while (st.hasMoreTokens()) {
            String next = st.nextToken();
            places.add(next);
        }
        return places;
    }


    /** JUNIT TESTING METHOD ONLY. NOT PUBLIC FOR USE.
     * Returns given S with spaces instead of underscores. */
    public static String spaceTESTING(String s) {
        return space(s);
    }

    /** JUNIT TESTING METHOD ONLY. NOT PUBLIC FOR USE.
     *  Returns the proper direction for S. */
    public static String convertTESTING(String s) {
        return convert(s);
    }

}
