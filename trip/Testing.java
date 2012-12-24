package trip;

import graph.UndirectedGraph;
import graph.Vertex;
import graph.Edge;

import org.junit.Test;
import ucb.junit.textui;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Iterator;

/** Unit tests for the trip package. */
public class Testing {

    /** Run all JUnit tests in the graph package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(trip.Testing.class));
    }

    /** Generates a graph for me to use. */
    public UndirectedGraph<String, ArrayList<String>> generateMap() {
        UndirectedGraph<String, ArrayList<String>> map
            = new UndirectedGraph<String, ArrayList<String>>();
        Vertex<String> A = map.add("A");
        Vertex<String> B = map.add("B");
        Vertex<String> C = map.add("C");
        Vertex<String> D = map.add("D");
        Vertex<String> E = map.add("E");
        Vertex<String> F = map.add("F");
        Vertex<String> G = map.add("G");
        ArrayList<String> aB = new ArrayList<String>();
        ArrayList<String> aC = new ArrayList<String>();
        ArrayList<String> bD = new ArrayList<String>();
        ArrayList<String> cD = new ArrayList<String>();
        ArrayList<String> dE = new ArrayList<String>();
        ArrayList<String> dF = new ArrayList<String>();
        ArrayList<String> fG = new ArrayList<String>();
        ArrayList<String> eG = new ArrayList<String>();
        aB.add("AB");
        aB.add("0.1");
        aB.add("WE");
        aC.add("AC");
        aC.add("0.3");
        aC.add("NS");
        bD.add("BD");
        bD.add("1.3");
        bD.add("NS");
        cD.add("CD");
        cD.add("0.8");
        cD.add("WE");
        dE.add("DE");
        dE.add("0.2");
        dE.add("WE");
        dF.add("DF");
        dF.add("0.6");
        dF.add("NS");
        fG.add("FG");
        fG.add("1.6");
        fG.add("WE");
        eG.add("EG");
        eG.add("1.1");
        eG.add("NS");
        map.add(A, B, aB);
        map.add(A, C, aC);
        map.add(B, D, bD);
        map.add(C, D, cD);
        map.add(D, E, dE);
        map.add(D, F, dF);
        map.add(F, G, fG);
        map.add(E, G, eG);
        return map;
    }

    /** Generates a graph for me to use. */
    public UndirectedGraph<String, ArrayList<String>> generateMap2() {
        UndirectedGraph<String, ArrayList<String>> map
            = new UndirectedGraph<String, ArrayList<String>>();
        Vertex<String> A = map.add("A");
        Vertex<String> B = map.add("B");
        Vertex<String> C = map.add("C");
        Vertex<String> D = map.add("D");
        Vertex<String> E = map.add("E");
        Vertex<String> F = map.add("F");
        Vertex<String> G = map.add("G");
        ArrayList<String> aB = new ArrayList<String>();
        ArrayList<String> aC = new ArrayList<String>();
        ArrayList<String> bD = new ArrayList<String>();
        ArrayList<String> cD = new ArrayList<String>();
        ArrayList<String> dE = new ArrayList<String>();
        ArrayList<String> dF = new ArrayList<String>();
        ArrayList<String> fG = new ArrayList<String>();
        ArrayList<String> eG = new ArrayList<String>();
        aB.add("AB");
        aB.add("0.1");
        aB.add("WE");
        aC.add("AC");
        aC.add("0.3");
        aC.add("NS");
        bD.add("BD");
        bD.add("1.3");
        bD.add("NS");
        cD.add("CD");
        cD.add("0.8");
        cD.add("WE");
        dE.add("CD");
        dE.add("0.2");
        dE.add("WE");
        dF.add("DF");
        dF.add("0.6");
        dF.add("NS");
        fG.add("FG");
        fG.add("1.6");
        fG.add("WE");
        eG.add("EG");
        eG.add("1.1");
        eG.add("NS");
        map.add(A, B, aB);
        map.add(A, C, aC);
        map.add(B, D, bD);
        map.add(C, D, cD);
        map.add(D, E, dE);
        map.add(D, F, dF);
        map.add(F, G, fG);
        map.add(E, G, eG);
        return map;
    }

    /** printPath(ArrayList<Edge<String, ArrayList<String>>>
    mapEdges, UndirectedGraph<String, ArrayList<String>> map,
    ArrayList<Vertex<String>> places). */
    @Test
    public void printPath1() {
        Main m = new Main();
        ArrayList<String> points = new ArrayList<String>();
        points.add("A");
        points.add("G");
        UndirectedGraph<String, ArrayList<String>> map = generateMap();
        Iterator<Edge<String, ArrayList<String>>> edgesIt = map.edges();
        ArrayList<Edge<String, ArrayList<String>>> mapEdges
            = new ArrayList<Edge<String, ArrayList<String>>>();
        while (edgesIt.hasNext()) {
            mapEdges.add(edgesIt.next());
        }
        ArrayList<Vertex<String>> places = m.aVTESTING(map, points);
        m.printPathTESTING(map, places);
    }

    @Test
    public void printPath2() {
        Main m = new Main();
        ArrayList<String> points = new ArrayList<String>();
        points.add("G");
        points.add("A");
        UndirectedGraph<String, ArrayList<String>> map = generateMap();
        Iterator<Edge<String, ArrayList<String>>> edgesIt = map.edges();
        ArrayList<Edge<String, ArrayList<String>>> mapEdges
            = new ArrayList<Edge<String, ArrayList<String>>>();
        while (edgesIt.hasNext()) {
            mapEdges.add(edgesIt.next());
        }
        ArrayList<Vertex<String>> places = m.aVTESTING(map, points);
        m.printPathTESTING(map, places);
    }

    @Test
    public void printPath3() {
        Main m = new Main();
        ArrayList<String> points = new ArrayList<String>();
        points.add("A");
        points.add("D");
        points.add("G");
        UndirectedGraph<String, ArrayList<String>> map = generateMap();
        Iterator<Edge<String, ArrayList<String>>> edgesIt = map.edges();
        ArrayList<Edge<String, ArrayList<String>>> mapEdges
            = new ArrayList<Edge<String, ArrayList<String>>>();
        while (edgesIt.hasNext()) {
            mapEdges.add(edgesIt.next());
        }
        ArrayList<Vertex<String>> places = m.aVTESTING(map, points);
        m.printPathTESTING(map, places);
    }

    @Test
    public void printPath4() {
        Main m = new Main();
        ArrayList<String> points = new ArrayList<String>();
        points.add("A");
        points.add("G");
        UndirectedGraph<String, ArrayList<String>> map = generateMap2();
        Iterator<Edge<String, ArrayList<String>>> edgesIt = map.edges();
        ArrayList<Edge<String, ArrayList<String>>> mapEdges
            = new ArrayList<Edge<String, ArrayList<String>>>();
        while (edgesIt.hasNext()) {
            mapEdges.add(edgesIt.next());
        }
        ArrayList<Vertex<String>> places = m.aVTESTING(map, points);
        m.printPathTESTING(map, places);
    }

    @Test
    public void printPath5() {
        Main m = new Main();
        ArrayList<String> points = new ArrayList<String>();
        points.add("G");
        points.add("A");
        UndirectedGraph<String, ArrayList<String>> map = generateMap2();
        Iterator<Edge<String, ArrayList<String>>> edgesIt = map.edges();
        ArrayList<Edge<String, ArrayList<String>>> mapEdges
            = new ArrayList<Edge<String, ArrayList<String>>>();
        while (edgesIt.hasNext()) {
            mapEdges.add(edgesIt.next());
        }
        ArrayList<Vertex<String>> places = m.aVTESTING(map, points);
        m.printPathTESTING(map, places);
    }

    @Test
    public void printPath6() {
        Main m = new Main();
        ArrayList<String> points = new ArrayList<String>();
        points.add("A");
        points.add("D");
        points.add("G");
        UndirectedGraph<String, ArrayList<String>> map = generateMap2();
        Iterator<Edge<String, ArrayList<String>>> edgesIt = map.edges();
        ArrayList<Edge<String, ArrayList<String>>> mapEdges
            = new ArrayList<Edge<String, ArrayList<String>>>();
        while (edgesIt.hasNext()) {
            mapEdges.add(edgesIt.next());
        }
        ArrayList<Vertex<String>> places = m.aVTESTING(map, points);
        m.printPathTESTING(map, places);
    }

    @Test
    public void printPath7() {
        Main m = new Main();
        ArrayList<String> points = new ArrayList<String>();
        points.add("G");
        points.add("D");
        points.add("A");
        UndirectedGraph<String, ArrayList<String>> map = generateMap2();
        Iterator<Edge<String, ArrayList<String>>> edgesIt = map.edges();
        ArrayList<Edge<String, ArrayList<String>>> mapEdges
            = new ArrayList<Edge<String, ArrayList<String>>>();
        while (edgesIt.hasNext()) {
            mapEdges.add(edgesIt.next());
        }
        ArrayList<Vertex<String>> places = m.aVTESTING(map, points);
        m.printPathTESTING(map, places);
    }

    /** private static ArrayList<Vertex<String>>
        allVertices(UndirectedGraph<String,
        ArrayList<String>> map, ArrayList<String> places). */
    @Test
    public void allVertices1() {
        Main m = new Main();
        UndirectedGraph<String, ArrayList<String>> map = generateMap();
        ArrayList<String> places = new ArrayList<String>();
        places.add("A");
        places.add("G");
        System.out.println(m.aVTESTING(map, places)
            + " is supposed to have A, G");
        assertEquals(2, m.aVTESTING(map, places).size());
    }

    /** toFrom(). */
    @Test
    public void toFrom1() {
        Main m = new Main();
        ArrayList<String> answer = new ArrayList<String>();
        answer.add("Berkeley");
        answer.add("San_Francisco");
        answer.add("Santa_Cruz");
        assertEquals(answer, m.toFromTESTING("Berkeley "
            + "San_Francisco Santa_Cruz"));
    }

    @Test
    public void toFrom2() {
        Main m = new Main();
        ArrayList<String> answer = new ArrayList<String>();
        assertNotNull(m.toFromTESTING("berkeley"));
    }

    /** space(String s). */
    @Test
    public void space1() {
        Main m = new Main();
        String s = "Montaro";
        assertEquals("Montaro", m.spaceTESTING(s));
    }

    @Test
    public void space2() {
        Main m = new Main();
        String s = "Santa_Cruz";
        assertEquals("Santa Cruz", m.spaceTESTING(s));
    }

    /** convert(String s). */
    @Test
    public void convert1() {
        String n = "north";
        Main m = new Main();
        assertEquals(m.convertTESTING("N"), n);
    }

    @Test
    public void convert2() {
        String s = "south";
        Main m = new Main();
        assertEquals(m.convertTESTING("S"), s);
    }

    @Test
    public void convert3() {
        String w = "west";
        Main m = new Main();
        assertEquals(m.convertTESTING("W"), w);
    }

    @Test
    public void convert4() {
        String e = "east";
        Main m = new Main();
        assertEquals(m.convertTESTING("E"), e);
    }

}
