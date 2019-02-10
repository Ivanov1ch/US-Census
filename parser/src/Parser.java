/**
 * Reads US-Census-2017.csv and calls a Python script to display relevant information about it
 * @author Justin Zhu
 * @version 1.0 2/9/19
 */

import java.io.*;
import java.util.*;

public class Parser{

    public static final String[] columns = {"ID1", "ID2", "Name", "Census",
            "2010Apr", "2010Jun", "2011", "20columns.length - 1", "2013",
            "2014", "2015", "2016", "2017"}; // 2010-2017 are estimates
    public static final int skipLines = 2;
    public static final int skipColumns = 5; // first column with population data, and skip the extraneous 2010 data
    private final Analyzer analyzer;
    public static final NameConverter nameConverter = new NameConverter();;

    private Scanner input;

    private List<Territory> censusData;

    public Parser(String fileName)  throws IOException {
        input = new Scanner(new File(fileName));
        parseFile();
        analyzer = new Analyzer(this);
    }

    public List<Territory> getCensusData() {
        return censusData;
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

    private void outputToFile(String filePath) throws IOException {
        Writer output = new BufferedWriter(new FileWriter(filePath));
        int start = skipColumns, end = columns.length - 1;
        appendToFile(analyzer.getTop(censusData.size()), "Population " + columns[columns.length - 1], output);
        appendToFile(analyzer.getTop(censusData.size(), start, end), "Population Growth", output, start, end);
        /*appendToFile(analyzer.getTop(5), "Most Populous Territories", output);
        appendToFile(analyzer.getTop(5, start, end),
                "Fastest Growing Territories", output, start, end);
        appendToFile(analyzer.getBottom(5), "Least Populous Territories", output);
        appendToFile(analyzer.getBottom(5, start, end),
                "Slowest-Growing/Shrinking Territories", output, start, end);*/
        output.close();
    }

    private void appendToFile(List<Territory> list, String title, Writer output)  throws IOException {
        output.append(title + "\n");
        for(int i = 0; i < list.size(); i++) {
            output.append(formatTerritory(list.get(i)));
        }
        output.append("\n");
    }

    private void appendToFile(List<Territory> list, String title, Writer output, int start, int end)  throws IOException {
        output.append(title + "\n");
        for(int i = 0; i < list.size(); i++) {
            output.append(formatTerritory(list.get(i), start, end));
        }
        output.append("\n");
    }

    private static String formatTerritory(Territory territory) {
        return String.format("%s %d\n", nameConverter.toStateCode(territory.getName()), territory.getPop());
    }

    private static String formatTerritory(Territory territory, int start, int end) {
        return String.format("%s %d\n", nameConverter.toStateCode(territory.getName()), territory.getPopRate(start, end));
    }

    public String toString() {
        String result = "";
        for(int i = 0; i < censusData.size(); i++) {
            result += censusData.get(i).toString() + "\n";
        }
        return result;
    }

    public static void main(String[] args) throws IOException{
        Parser parser = new Parser("../../US-Census-2017.csv");
        //System.out.println(parser);
        //System.out.println(parser.analyzer.getTop(5));
        //System.out.println(parser.analyzer.getTop(5, skipColumns, columns.length - 1));

        //System.out.println(parser.analyzer.getBottom(5));
        //System.out.println(parser.analyzer.getBottom(5, skipColumns, columns.length - 1));

        parser.outputToFile("../../Analysis.txt");
    }

}

