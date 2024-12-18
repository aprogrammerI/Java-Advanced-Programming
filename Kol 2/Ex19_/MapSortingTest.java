package Ex19_;

import java.util.*;

public class MapSortingTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        List<String> l = readMapPairs(scanner);
        if(n==1){
            Map<String, Integer> map = new HashMap<>();
            fillStringIntegerMap(l, map);
            SortedSet<Map.Entry<String, Integer>> s = entriesSortedByValues(map);
            System.out.println(s);
        } else {
            Map<Integer, String> map = new HashMap<>();
            fillIntegerStringMap(l, map);
            SortedSet<Map.Entry<Integer, String>> s = entriesSortedByValues(map);
            System.out.println(s);
        }

    }

    private static List<String> readMapPairs(Scanner scanner) {
        String line = scanner.nextLine();
        String[] entries = line.split("\\s+");
        return Arrays.asList(entries);
    }

    static void fillStringIntegerMap(List<String> l, Map<String,Integer> map) {
        l.stream()
                .forEach(s -> map.put(s.substring(0, s.indexOf(':')), Integer.parseInt(s.substring(s.indexOf(':') + 1))));
    }

    static void fillIntegerStringMap(List<String> l, Map<Integer, String> map) {
        l.stream()
                .forEach(s -> map.put(Integer.parseInt(s.substring(0, s.indexOf(':'))), s.substring(s.indexOf(':') + 1)));
    }

    //вашиот код овде
    public static <T extends Comparable<T>, E extends Comparable<E>> SortedSet<Map.Entry<T, E>>entriesSortedByValues(Map<T, E> map){
        Comparator<Map.Entry<T,E>> comparator = (a,b)->{
            if (a.getValue().compareTo(b.getValue())==0)return 1;
            else return a.getValue().compareTo(b.getValue());
        };
        SortedSet<Map.Entry<T, E>> set = new TreeSet<>(comparator.reversed());
        map.entrySet().forEach(set::add);
        System.out.println(map);
        return set;
    }
}
