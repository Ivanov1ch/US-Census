import java.util.ArrayList;
import java.util.List;

public class Territory {

    private List<Object> data;

    public Territory(String row) {
        this(Parser.getFormattedLine(row));
    }

    public Territory(String[] row) {
        data = new ArrayList<Object>();
        for (int i = 0; i < row.length; i++) {
            Object obj = row[i];
            try {
                obj = Integer.parseInt(row[i]);
            } catch (Exception e) {
                // do nothing
            }
            data.add(obj);
        }
        if (data.size() != Parser.columns.length) {
            throw new IllegalArgumentException("Input row length does not match the expected length");
        }
    }

    public List<Object> getData() {
        return data;
    }

    public String getName() {
        Object name = getData().get(2);
        if(!(name instanceof String)) {
            return null;
        }
        return (String) name;
    }

    public Integer getPop() {
        Object a = data.get(data.size() - 1);
        if (a instanceof Integer)
            return (Integer) a;
        throw new IllegalArgumentException("Cannot compare non-Integers");
    }

    public Integer getPopRate(int start, int end) {
        // returns in units of people / year, assuming (end - start) is the number of years
        Object a = data.get(start), b = data.get(end);

        if (a instanceof Integer && b instanceof Integer) {
            return ((int) b - (int) a) / (end - start);
        }
        throw new IllegalArgumentException("Cannot compare non-Integers");
    }

    public String toString() {
        String result = "";
        for (int i = 0; i < Parser.columns.length; i++) {
            result += Parser.columns[i] + ": " + data.get(i) + "\n";
        }
        return result;
    }

    public boolean compareIndex(Territory other, int index) {
        return getPop() < other.getPop();
    }

    public boolean compare(Territory other) {
        // compares population
        return compareIndex(other, data.size() - 1);
    }

    public boolean compare(Territory other, int start, int end) {
        // compares population growth over that period of time
        return getPopRate(start, end) < other.getPopRate(start, end);
    }
}