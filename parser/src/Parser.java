/**
 * Reads US-Census-2017.csv and calls a Python script to display relevant information about it
 * @author Justin Zhu
 * @version 1.0 2/9/19
 */

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Parser{

    private static String[] columns = {"ID1", "ID2", "Name", "Census",
            "2010Apr", "2010Jun", "2011", "2012", "2013", "2014", "2015", "2016", "2017"}; // 2010-2017 are estimates
    private static int skipLines = 2;

    private Scanner input;
    private List<Territory> censusData;

    public Parser(String fileName)  throws IOException {
        input = new Scanner(new File(fileName));
        parseFile();
    }

    public static String[] getFormattedLine(String row) {
        return row.split(",");
    }

    private void parseFile() {
        censusData = new ArrayList<Territory>();
        int count = 0;
        while(input.hasNextLine()) {
            String line = input.nextLine();
            if(count++ < skipLines)
                continue;
            if(getFormattedLine(line).length == 0)
                continue;
            censusData.add(new Territory(line));
        }
    }

    public String toString() {
        String result = "";
        for(int i = 0; i < censusData.size(); i++) {
            result += censusData.get(i).toString() + "\n";
        }
        return result;
    }

    private List<Territory> getTop(int n) {
        List<Territory> result = new ArrayList<>(n + 1);
        for(int i = 0; i < censusData.size(); i++) {
            Parser.insertToList(result, censusData.get(i), n);
        }
        return result;
    }

    private List<Territory> getTop(int n, int startIndex, int endIndex) {
        List<Territory> result = new ArrayList<>(n + 1);
        for(int i = 0; i < censusData.size(); i++) {
            Parser.insertToList(result, censusData.get(i), n, startIndex, endIndex);
        }
        return result;
    }

    private static void insertToList(List<Territory> list, Territory territory, int limit) {
        int i = 0;
        for(; i < list.size(); i++) {
            if(!list.get(i).compare(territory)) {
                break;
            }
        }
        list.add(i, territory);
        if(list.size() > limit) {
            list.remove(list.size() - 1);
        }
    }

    private static void insertToList(List<Territory> list, Territory territory, int limit, int start, int end) {
        int i = 0;
        for(; i < list.size(); i++) {
            if(!list.get(i).compare(territory, start, end)) {
                break;
            }
        }
        list.add(i, territory);
        if(list.size() > limit) {
            list.remove(list.size() - 1);
        }
    }

    public static void main(String[] args) throws IOException{
        Parser parser = new Parser("US-Census-2017.csv");
        //System.out.println(parser);
        System.out.println(parser.getTop(5));
        System.out.println(parser.getTop(5, 3, 12));
    }

    private class Territory {

        private List<Object> data;

        public Territory(String row) {
            this(Parser.getFormattedLine(row));
        }

        public Territory(String[] row) {
            data = new ArrayList<>();
            for(int i = 0; i < row.length; i++) {
                Object obj = row[i];
                try {
                    obj = Integer.parseInt(row[i]);
                }
                catch(Exception e) {
                    // do nothing
                }
                data.add(obj);
            }
            if(data.size() != Parser.columns.length) {
                throw new IllegalArgumentException("Input row length does not match the expected length");
            }
        }

        public List<Object> getData() {
            return data;
        }

        public String toString() {
            String result = "";
            for(int i = 0; i < Parser.columns.length; i++) {
                result += Parser.columns[i] + ": " + data.get(i) + "\n";
            }
            return result;
        }

        public boolean compareIndex(Territory other, int index) {
            Object a = data.get(index), b = other.data.get(index);
            if(a instanceof Integer && b instanceof Integer)
                return (int) a > (int) b;
            throw new IllegalArgumentException("Cannot compare non-Integers");
        }

        public boolean compare(Territory other) {
            // compares population
            return compareIndex(other, data.size() - 1);
        }

        public boolean compare(Territory other, int start, int end) {
            // compares population growth over that period of time
            Object a = data.get(start), b = data.get(end);
            Object c = other.data.get(start), d = other.data.get(end);

            if(a instanceof Integer && b instanceof Integer && c instanceof Integer && d instanceof Integer) {
                return (int) b - (int) a > (int) d - (int) c;
            }
            throw new IllegalArgumentException("Cannot compare non-Integers");
        }
    }

}

