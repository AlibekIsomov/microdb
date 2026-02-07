import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to MicroDB :D");
        while (true) {
            System.out.print("db > ");
            String input = sc.nextLine().trim();

            if (input.isEmpty()) {
                continue; // ignore empty lines
            }

            if (input.equals(".exit")) {
                System.out.println("Bye thanks for using MicroDB :D");
                break;
            }

            String result = read_input(input);
            if (!result.isEmpty()) {
                System.out.println(result);
            }
        }
    }

    public static String read_input(String input) {
        String[] data = input.split("\\s+"); // split by spaces
        String result = "";

        switch (data[0]) {
            case "select":
                // TODO: call select_function later
                result = "Recognized SELECT (not implemented yet)";
                break;
            case "insert":
                // TODO: parse id + name, insert into table
                result = "Recognized INSERT (not implemented yet)";
                break;
            case "delete":
                // TODO: implement delete logic
                result = "Recognized DELETE (not implemented yet)";
                break;
            default:
                result = "Error: Unknown command";
                break;
        }
        return result;
    }

    public String select_function(String[] data){
        return "okay";
    }
}
