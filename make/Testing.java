package make;

import graph.DirectedGraph;
import graph.Vertex;

import org.junit.Test;
import ucb.junit.textui;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.HashMap;

/** Unit tests for the make package.
 * @author andreawu*/
public class Testing {

    /** Run all JUnit tests in the make package. */
    public static void main(String[] ignored) {
        System.exit(textui.runClasses(make.Testing.class));
    }

    /** Makes a graph for make testing to use. */
    public DirectedGraph<StringInt, Nothing> generateMap1() {
        DirectedGraph<StringInt, Nothing> dg
            = new DirectedGraph<StringInt, Nothing>();
        StringInt a = new StringInt("A", 1, "", true);
        StringInt b = new StringInt("B", 2, "Make C", true);
        StringInt c = new StringInt("C", 3, "Make D", true);
        StringInt d = new StringInt("D", 4, "Make E", true);
        StringInt e = new StringInt("E", 5, "", true);
        Vertex<StringInt> aV = dg.add(a);
        Vertex<StringInt> bV = dg.add(b);
        Vertex<StringInt> cV = dg.add(c);
        Vertex<StringInt> dV = dg.add(d);
        Vertex<StringInt> eV = dg.add(e);
        Nothing n = new Nothing();
        dg.add(aV, bV);
        dg.add(bV, cV);
        dg.add(cV, dV);
        dg.add(dV, eV);
        return dg;
    }

    /** MakeDepthFirst Constructor. */
    @Test
    public void makeDF() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        assertNotNull(df);
    }

    /** MakeDepthFirst sort. */
    @Test
    public void sort1() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        ArrayList<StringInt> lst = new ArrayList<StringInt>();
        StringInt a = new StringInt("A", 1, "", true);
        StringInt b = new StringInt("B", 3, "Make C", true);
        StringInt c = new StringInt("C", 4, "Make D", true);
        StringInt d = new StringInt("D", 2, "Make E", true);
        StringInt e = new StringInt("E", 5, "", true);
        lst.add(a);
        lst.add(b);
        lst.add(c);
        lst.add(d);
        lst.add(e);
        ArrayList<StringInt> answer = new ArrayList<StringInt>();
        answer.add(a);
        answer.add(d);
        answer.add(b);
        answer.add(c);
        answer.add(e);
        assertEquals(df.sortTESTING(lst), answer);
    }

    @Test
    public void sort2() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        ArrayList<StringInt> lst = new ArrayList<StringInt>();
        StringInt a = new StringInt("A", 5, "", true);
        StringInt b = new StringInt("B", 4, "Make C", true);
        StringInt c = new StringInt("C", 3, "Make D", true);
        StringInt d = new StringInt("D", 2, "Make E", true);
        StringInt e = new StringInt("E", 1, "", true);
        lst.add(a);
        lst.add(b);
        lst.add(c);
        lst.add(d);
        lst.add(e);
        ArrayList<StringInt> answer = new ArrayList<StringInt>();
        answer.add(e);
        answer.add(d);
        answer.add(c);
        answer.add(b);
        answer.add(a);
        assertEquals(df.sortTESTING(lst), answer);
    }

    @Test
    public void sort3() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        ArrayList<StringInt> lst = new ArrayList<StringInt>();
        ArrayList<StringInt> answer = new ArrayList<StringInt>();
        assertEquals(df.sortTESTING(lst), answer);
    }

    /** MakeDepthFirst makeString. */
    @Test
    public void makeString1() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        ArrayList<StringInt> lst = new ArrayList<StringInt>();
        StringInt a = new StringInt("A", 1, "", true);
        StringInt b = new StringInt("B", 2, "Make C", true);
        StringInt c = new StringInt("C", 3, "Make D", true);
        StringInt d = new StringInt("D", 4, "Make E", true);
        StringInt e = new StringInt("E", 5, "", true);
        lst.add(a);
        lst.add(b);
        lst.add(c);
        lst.add(d);
        lst.add(e);
        assertEquals(df.makeStringTESTING(lst), "A B C D E");
    }

    @Test
    public void makeString2() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        ArrayList<StringInt> lst = new ArrayList<StringInt>();
        StringInt a = new StringInt(" ", 1, "", true);
        lst.add(a);
        assertEquals(df.makeStringTESTING(lst), " ");
    }

    /** MakeDepthFirst process. */
    @Test
    public void process1() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        assertEquals(df.processTESTING("        hi"), "hi");
    }

    @Test
    public void process2() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        assertEquals(df.processTESTING("hi"), "hi");
    }

    @Test
    public void process3() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        assertEquals(df.processTESTING(""), "");
    }

    @Test
    public void process4() {
        HashMap<String, ArrayList<StringInt>> hm
            = new HashMap<String, ArrayList<StringInt>>();
        MakeDepthFirst df = new MakeDepthFirst(generateMap1(), hm);
        assertEquals(df.processTESTING("       \\n   hi"), "\\n   hi");
    }

}
