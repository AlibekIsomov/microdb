import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final String DATABASE = "db.sql";

    private static final List<TableDefinition> TABLES = new ArrayList<>();

        public static class TableDefinition {
        public final String name;
        public final List<String> columns;

        public TableDefinition(String name, List<String> columns) {
            this.name = name;
            this.columns = columns;
        }

        @Override
        public String toString() {
            return name + columns.toString();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to MicroDB :D");
        while (true) {
            System.out.print("db > ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            if (input.equals(".exit")) {
                System.out.println("Bye thanks for using MicroDB :D");
                break;
            }

            read_input(input);

        }
    }

    public static void read_input(String input) {
        String[] data = input.split("\\s+"); 
        String result = "";

        switch (data[0]) {
            case "select":

                result = "Recognized SELECT (not implemented yet)";
                break;
            case "insert":

                result = "Recognized INSERT (not implemented yet)";
                break;
            case "delete":
                result = "Recognized DELETE (not implemented yet)";
                break;
            case "create":
                result = create_function(data);
                break;
            case "load-all":
                try {
                    result = load_tables();
                    for (TableDefinition t : TABLES) {
                        System.out.println("Loaded table: " + t.name + " " + t.columns);
                    }
                } catch (IOException e) {
                    result = "Error: " + e.getMessage();
                }
            break;
            default:
                result = "Error: Unknown command";
                break;
        }
        System.out.println(result);
    }
    // create Book (Name,title,stars)
    public static String create_function(String[] data){

        String cleanData = data[2].replaceAll("[()\\s]", ""); 
        String[] columnsParsed = cleanData.split(",");

        try {
            write_disk(data[1], columnsParsed);
            return "Table created successfully.";
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }
    public static String select_function(String[] data){
        return "okay";
    }


    public static void write_disk(String tableName, String[] columns) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE, true))) {

            writer.write("TABLE " + tableName + "\n");
            writer.write("COLUMNS ");

            for (String col : columns) {
                writer.write(col + " ");
            }

            writer.write("\n");
            writer.write("END\n\n");
        }
    }

    public static String load_tables() throws IOException {
        TABLES.clear();

        File file = new File(DATABASE);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            String currentTableName = null;
            List<String> currentColumns = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.startsWith("TABLE ")) {
                    currentTableName = line.substring("TABLE ".length()).trim();
                    currentColumns = new ArrayList<>();
                } else if (line.startsWith("COLUMNS ")) {
                    String colsPart = line.substring("COLUMNS ".length()).trim();
                    
                    String[] cols = colsPart.split("\\s+");
                    currentColumns.addAll(Arrays.asList(cols));
                } else if (line.equals("END")) {
                    if (currentTableName != null && currentColumns != null) {
                        TABLES.add(new TableDefinition(currentTableName, currentColumns));
                        currentTableName = null;
                        currentColumns = null;
                    }
                }
            }
        }
        return "Tables loaded successfully.";
    }
}
