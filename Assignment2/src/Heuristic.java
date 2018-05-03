/*
 *   Logan Thomas
 *   Artificial Intelligence Lab
 *   Assignment 2
 */
import java.util.function.Function;

public class Heuristic {
    private Function<KeyValuePair, Integer> h;

    public Heuristic(Function<KeyValuePair, Integer> hFunction){ this.h = hFunction; }

    public int h(KeyValuePair pair){ return this.h.apply(pair); }
}
