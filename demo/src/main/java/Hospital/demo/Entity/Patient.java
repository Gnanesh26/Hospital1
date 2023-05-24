package Hospital.demo.Entity;
//
//import jakarta.persistence.*;
//
//import java.time.LocalDate;
//
//
//@Entity
//@Table(name = "patients")
//public class Patient {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "patient_id")
//    private int patientId;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(nullable = false)
//    private String disease;
//
//    @Column(nullable = false)
//    private int severity;
//
//    @ManyToOne
//    @JoinColumn(name = "assigned_doctor_id")
//    private Doctor assignedDoctor;
//
//    @Column(nullable = false)
//    private LocalDate joinDate;
//
//    @Column(nullable = false)
//    private String status;
//
//
//    public Patient(int patientId, String name, String disease, int severity, Doctor assignedDoctor, LocalDate joinDate, String status) {
//        this.patientId = patientId;
//        this.name = name;
//        this.disease = disease;
//        this.severity = severity;
//        this.assignedDoctor = assignedDoctor;
//        this.joinDate = joinDate;
//        this.status = status;
//    }
//
//    public Patient() {
//    }
//
//
//    public int getPatientId() {
//        return patientId;
//    }
//
//    public void setPatientId(int patientId) {
//        this.patientId = patientId;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getDisease() {
//        return disease;
//    }
//
//    public void setDisease(String disease) {
//        this.disease = disease;
//    }
//
//    public int getSeverity() {
//        return severity;
//    }
//
//    public void setSeverity(int severity) {
//        this.severity = severity;
//    }
//
//    public Doctor getAssignedDoctor() {
//        return assignedDoctor;
//    }
//
//    public void setAssignedDoctor(Doctor assignedDoctor) {
//        this.assignedDoctor = assignedDoctor;
//    }
//
//    public LocalDate getJoinDate() {
//        return joinDate;
//    }
//
//    public void setJoinDate(LocalDate joinDate) {
//        this.joinDate = joinDate;
//    }
//
//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//}

import Hospital.demo.Entity.Doctor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "name")
    private String name;

    @Column(name = "disease")
    private String disease;

    @Column(name = "severity")
    private Integer severity;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "assigned_doctor_id")
//    private Doctor assignedDoctor;
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "assigned_doctor_id")
//    private Doctor assignedDoctor;



//    @JoinColumn(name = "assigned_doctor_id", insertable = false, updatable = false)
//    private Doctor assignedDoctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dean_id")
    private Dean dean;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "assigned_doctor_id")
//    @JsonIgnore  // Exclude assignedDoctor property from JSON serialization
//    private Doctor assignedDoctor;

    @ManyToOne
    @JoinColumn(name = "assigned_doctor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Doctor assignedDoctor;

    @Column(name = "join_date")
    private LocalDate joinDate;

    @Column(name = "status")
    private String status;

    public Patient(Integer patientId, String name, String disease, Integer severity, Doctor assignedDoctor, LocalDate joinDate, String status) {
        this.patientId = patientId;
        this.name = name;
        this.disease = disease;
        this.severity = severity;
        this.assignedDoctor = assignedDoctor;
        this.joinDate = joinDate;
        this.status = status;
    }

    public Patient() {
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setId(Integer id) {
    }
}
