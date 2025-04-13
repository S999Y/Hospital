-- CREATE TABLE IF NOT EXISTS patient (Id INTEGER PRIMARY KEY AUTOINCREMENT,Name Text NOT NULL,Age INTEGER NOT NULL,Gender Text NOT NULL);

-- CREATE TABLE IF NOT EXISTS doctor (Id INTEGER PRIMARY KEY AUTOINCREMENT,Name Text NOT NULL,Specialty Text NOT NULL);

-- CREATE TABLE IF NOT EXISTS appoinment (Id INTEGER PRIMARY KEY AUTOINCREMENT,Patient_Id INTEGER NOT NULL,Doctor_Id INTEGER NOT NULL,Date Text NOT NULL,FOREIGN KEY (Patient_Id) REFERENCES patient(Id),FOREIGN KEY (Doctor_Id) REFERENCES doctor(Id));

SELECT * FROM sqlite_master WHERE type='table';

-- INSERT INTO appoinment (Patient_Id,Doctor_Id,Date) VALUES((SELECT Id FROM patient WHERE Name = 'John'),(SELECT Id FROM doctor WHERE Name = 'Alice'),'12-11-2-24');

-- SELECT patient.Name AS PatiendName,doctor.Name as DoctorName,appoinment.Date FROM appoinment JOIN patient ON appoinment.Patient_Id = patient.Id JOIN doctor ON appoinment.Doctor_Id = doctor.Id;

-- SELECT patient.Name AS patient_name,doctor.Name AS doctor_name,appoinment.Date FROM appoinment JOIN patient ON appoinment.Patient_Id = patient.Id JOIN doctor ON appoinment.Doctor_Id = doctor.Id;

-- SELECT doctor.Name FROM appoinment JOIN doctor ON appoinment.Doctor_Id = doctor.Id;

-- SELECT Id FROM doctor WHERE Name = 'David';
-- INSERT INTO appoinment (Patient_Id,Doctor_Id,Date) VALUES ((SELECT Id FROM patient WHERE Name = ?),(SELECT Id FROM doctor WHERE Name = ?),?);

DELETE FROM appoinment WHERE Patient_Id = (SELECT Id FROM patient WHERE Name = ?);

-- SELECT * FROM patient;
-- SELECT * FROM doctor;
-- SELECT * FROM appoinment;
