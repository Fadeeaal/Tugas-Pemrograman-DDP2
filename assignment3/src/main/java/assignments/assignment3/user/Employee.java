package assignments.assignment3.user;

public class Employee extends Member {
    public static int employeeCount;
    public Employee(String nama, String password) {
        super(nama, generateId(nama), password);
    }

    private static String generateId(String nama) {
        String id = nama.strip().split(" ")[0].toUpperCase() + "-" + employeeCount;
        employeeCount++;
        return id;
    }
}