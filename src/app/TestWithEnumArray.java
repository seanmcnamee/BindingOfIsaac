package app;

import java.util.EnumMap;
import java.util.Map;

/**
 * testWithEnumArray
 */
public class TestWithEnumArray {

    public enum TE {
        n1, n2, n3, n4;
    }
    public static void main(String[] args) {
        Map<TE, String> table = new EnumMap<TE, String>(TE.class);

        table.put(TE.n1, "TEST1");
        table.put(TE.n2, "TEST2");
        table.put(TE.n3, "TEST3");
        table.put(TE.n4, "TEST4");
        
        for (TE e : TE.values()) {
            System.out.println(table.get(e));
        }
    }
}