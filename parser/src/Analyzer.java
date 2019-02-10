import java.util.ArrayList;
import java.util.List;

public class Analyzer {
    private final Parser parser;

    public Analyzer(Parser parser) {
        this.parser = parser;
    }

    List<Territory> getTop(int n) {
        List<Territory> result = new ArrayList<Territory>(n + 1);
        for (int i = 0; i < parser.getCensusData().size(); i++) {
            insertToListTop(result, parser.getCensusData().get(i), n);
        }
        return result;
    }

    List<Territory> getTop(int n, int startIndex, int endIndex) {
        List<Territory> result = new ArrayList<Territory>(n + 1);
        for (int i = 0; i < parser.getCensusData().size(); i++) {
            insertToListTop(result, parser.getCensusData().get(i), n, startIndex, endIndex);
        }
        return result;
    }

    List<Territory> getBottom(int n) {
        List<Territory> result = new ArrayList<Territory>(n + 1);
        for (int i = 0; i < parser.getCensusData().size(); i++) {
            insertToListBottom(result, parser.getCensusData().get(i), n);
        }
        return result;
    }

    List<Territory> getBottom(int n, int startIndex, int endIndex) {
        List<Territory> result = new ArrayList<Territory>(n + 1);
        for (int i = 0; i < parser.getCensusData().size(); i++) {
            insertToListBottom(result, parser.getCensusData().get(i), n, startIndex, endIndex);
        }
        return result;
    }

    private static void insertToListTop(List<Territory> list, Territory territory, int limit) {
        int i = 0;
        for (; i < list.size(); i++) {
            if (list.get(i).compare(territory)) {
                break;
            }
        }
        list.add(i, territory);
        if (list.size() > limit) {
            list.remove(list.size() - 1);
        }
    }

    private static void insertToListTop(List<Territory> list, Territory territory, int limit, int start, int end) {
        int i = 0;
        for (; i < list.size(); i++) {
            if (list.get(i).compare(territory, start, end)) {
                break;
            }
        }
        list.add(i, territory);
        if (list.size() > limit) {
            list.remove(list.size() - 1);
        }
    }

    private static void insertToListBottom(List<Territory> list, Territory territory, int limit) {
        int i = 0;
        for (; i < list.size(); i++) {
            if (!list.get(i).compare(territory)) {
                break;
            }
        }
        list.add(i, territory);
        if (list.size() > limit) {
            list.remove(list.size() - 1);
        }
    }

    private static void insertToListBottom(List<Territory> list, Territory territory, int limit, int start, int end) {
        int i = 0;
        for (; i < list.size(); i++) {
            if (!list.get(i).compare(territory, start, end)) {
                break;
            }
        }
        list.add(i, territory);
        if (list.size() > limit) {
            list.remove(list.size() - 1);
        }
    }
}