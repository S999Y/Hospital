import Hospital.Doctor;
import Hospital.Patient;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;



public class App {

    private final String url = "jdbc:sqlite:lib\\hospital.db";
    protected Connection hospitalDatabase;

    public void hospitalData(){
        try {
            hospitalDatabase = DriverManager.getConnection(url);
            System.out.println("Database Connected...\n");
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public App(){
        hospitalData();
        Scanner input  = new Scanner(System.in);
        Patient patient = new Patient(hospitalDatabase, input);
        Doctor doctor = new Doctor(hospitalDatabase);

        int choice = 0;
        while (true) {
            System.out.println("Hospital Management Sysytem.");
            System.out.println("1. Add Patient.");
            System.out.println("2. View Patient.");
            System.out.println("3. View Doctor.");
            System.out.println("4. View Appoinments.");
            System.out.println("5. Book Appoinment.");
            System.out.println("6. Visit Doctor.");
            System.out.println("7. Exit");
            System.out.print("Enter Choice[1-7]: ");
            choice = input.nextInt();
            input.nextLine();
            System.out.println();
            switch (choice) {
                case 1:
                    //add patient 
                    patient.addPatient();
                    break;
                case 2:
                    //view patient
                    patient.viewPatient();
                    break;
                case 3:
                    //view doctor
                    doctor.viewDoctor();
                    break;
                case 4:
                    //view appoinments
                    patient.checkAppoinment();
                    break;
                case 5:
                    //book appoinment
                    patient.bookAppoinment();
                    break;
                case 6:
                    //visit doctor
                    patient.visitDoctor();
                    break;
                case 7:
                    //exit the program
                    input.close();
                    try{
                        hospitalDatabase.close();
                    }
                    catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return;
                default:
                    System.out.println("Invalid Input...");
                    break;
            }
        }

    }


    public static void main(String[] args) {
        new App();
    }
}
