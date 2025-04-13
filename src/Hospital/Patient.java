package Hospital;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection database;
    private Scanner input;


    public Patient(Connection connection,Scanner object){
        this.database = connection;
        this.input = object;
    }

    public void addPatient(){
        String name;
        int age;
        String gender;
        String addPatientQuery = "INSERT INTO patient (Name, Age, Gender) VALUES (?,?,?)";

        try (PreparedStatement addPatient = database.prepareStatement(addPatientQuery);) {
            
            System.out.print("Enter Patient Name: ");
            name = input.nextLine();
            addPatient.setString(1, name);
            System.out.print("Enter Patient Age: ");
            age = input.nextInt();
            input.nextLine();
            addPatient.setInt(2, age);
            System.out.print("Enter Patient Gender: ");
            gender = input.nextLine();
            addPatient.setString(3, gender);

            int dataInsert = addPatient.executeUpdate();

            if (dataInsert!=0){
                System.out.println("Patient Added..");
            }
            else{
                System.out.println("Patient Not Added..");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }

    public void viewPatient(){
        String name;
        int age;
        String gender;
        String getAll = "SELECT * FROM patient";

        try{
            PreparedStatement allPatient = database.prepareStatement(getAll);
            ResultSet patients = allPatient.executeQuery();
            System.out.println("  All Patients..");
            System.out.println("~~~~~~~~~~~~~~~~~~");
            System.out.println("Name\t|Age\t|Gender");
            System.out.println("----------------------------");
            while (patients.next()){
                name = patients.getString("Name");
                age = patients.getInt("Age");
                gender = patients.getString("Gender");

                System.out.println(name+"\t| " +age +"\t| "+gender);
            }
            System.out.println();
            patients.close();
            allPatient.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void checkAppoinment(){
        String patientName;
        String doctorName;
        String appoinmentDate;
        String appoinmentQuery = "SELECT patient.Name AS patient_name,doctor.Name AS doctor_name,appoinment.Date FROM appoinment JOIN patient ON appoinment.Patient_Id = patient.Id JOIN doctor ON appoinment.Doctor_Id = doctor.Id";

        try (PreparedStatement allAppoinment = database.prepareStatement(appoinmentQuery)){
            ResultSet appoinments = allAppoinment.executeQuery();

            System.out.println("  All Appoinments  ");
            System.out.println("~~~~~~~~~~~~~~~~~~~");
            System.out.println("Patient | Doctor | Date");
            System.out.println("----------------------------");
            while (appoinments.next()) {
                patientName = appoinments.getString("patient_name");
                doctorName = appoinments.getString("doctor_name");
                appoinmentDate = appoinments.getString("Date");
                System.out.println(patientName+"\t| "+doctorName+"\t | "+appoinmentDate);
            }
            System.out.println();

            appoinments.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void bookAppoinment(){

        String patientName;
        String doctorName;
        String appoinmentDate;
        String bookAppoinment = "INSERT INTO appoinment (Patient_Id,Doctor_Id,Date) VALUES ((SELECT Id FROM patient WHERE Name = ?),(SELECT Id FROM doctor WHERE Name = ?),?)";

        try (PreparedStatement appoinment = database.prepareStatement(bookAppoinment)) {
            System.out.print("Enter Patient Name: ");
            patientName = input.nextLine();
            System.out.print("Enter Doctor Name: ");
            doctorName = input.nextLine();
            System.out.print("Enter Date[DD-MM-YYYY]: ");
            appoinmentDate = input.nextLine();

            appoinment.setString(1, patientName);
            appoinment.setString(2, doctorName);
            appoinment.setString(3, appoinmentDate);

            if (new Doctor(database).doctorFree(doctorName)){
                int row = appoinment.executeUpdate();
                if (row!=0){
                    System.out.println("Appoinment Booked..");
                }
                else{
                    System.out.println("Appoinment not Booked..");
                }
            }
            else{
                System.out.println("Doctor Not Available..");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    public void visitDoctor(){
        String patientName;
        String deleteAppoinmentQuery = "DELETE FROM appoinment WHERE Patient_Id = (SELECT Id FROM patient WHERE Name = ?)";

        try (PreparedStatement deleteAppoinment = database.prepareStatement(deleteAppoinmentQuery)) {
            System.err.print("Enter Patient Name: ");
            patientName = input.nextLine();
            deleteAppoinment.setString(1, patientName);
            int deleted = deleteAppoinment.executeUpdate();

            if (deleted!=0){
                System.out.println("Visit Completed....");
            }
            else{
                System.out.println("Something Went Wrong..");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

    }
}
