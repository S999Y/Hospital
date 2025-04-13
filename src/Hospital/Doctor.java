package Hospital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Doctor {
    private Connection database;



    public Doctor(Connection connection){
        this.database = connection;

    }

    public void viewDoctor(){
        String name;
        String specialty;
        String getAllDoctor = "SELECT * FROM doctor";
        try (PreparedStatement allDoctor = database.prepareStatement(getAllDoctor)) {
            ResultSet doctors = allDoctor.executeQuery();

            System.out.println("  Doctors...");
            System.out.println("~~~~~~~~~~~~~~~~~~");
            System.out.println("Name\t| Specialty");
            System.out.println("---------------------");
            while (doctors.next()){
                name = doctors.getString("Name");
                specialty = doctors.getString("Specialty");
                System.out.println(name+"\t| "+specialty);
            }
            doctors.close();
            System.out.println();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void checkAppoinment(){
        String patientName;
        String doctorName;
        String date;
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
                date = appoinments.getString("Date");
                System.out.println(patientName+"\t| "+doctorName+"\t | "+date);
            }
            System.out.println();

            appoinments.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public boolean doctorFree(String name){
        boolean isFree = true;
        String doctorName;
        String doctorNameQuery = "SELECT doctor.Name FROM appoinment JOIN doctor ON appoinment.Doctor_Id = doctor.Id";

        try (PreparedStatement getDoctorId = database.prepareStatement(doctorNameQuery)){
            ResultSet doctor = getDoctorId.executeQuery();
            while (doctor.next()) {
                doctorName = doctor.getString("Name");
                if (doctorName.equals(name)){
                    isFree =  false;
                }
            }
            doctor.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return isFree;
    }
}

