package Hospital.demo.service;

import Hospital.demo.Entity.Doctor;
import Hospital.demo.Entity.Patient;
import Hospital.demo.Repository.DoctorRepository;
import Hospital.demo.Repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.*;

@Service
public class PatientService {

    @Autowired
    PatientRepository patientRepository;
     DoctorRepository doctorRepository;

    public Patient getPatientById(Integer id) {
        return patientRepository.findById(id).orElse(null);
    }

    public Patient getPatientDetails(int patientId) {
        return patientRepository.findById(patientId).orElse(null);
    }

    public String getDiseaseByPatientId(int patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        return (patient != null) ? patient.getDisease() : null;
    }

    public Doctor getAssignedDoctorByPatientId(int patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        return (patient != null) ? patient.getAssignedDoctor() : null;
    }

    public int getSeverityByPatientId(int patientId) {
        Patient patient = patientRepository.findById(patientId).orElse(null);
        return (patient != null) ? patient.getSeverity() : 0;
    }

    public List<Map<String, Object>> getDoctorPatients(int doctorId, String sortBy) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (doctor == null) {
            throw new NotFoundException("Doctor not found");
        }

        List<Patient> patients = doctor.get().getPatients();
        List<Map<String, Object>> patientDetails = new ArrayList<>();

        // Sorting logic
        if (sortBy.equalsIgnoreCase("date")) {
            patients.sort(Comparator.comparing(Patient::getJoinDate));
        } else if (sortBy.equalsIgnoreCase("severity")) {
            patients.sort(Comparator.comparingInt(Patient::getSeverity));
        } else {
            throw new IllegalArgumentException("Invalid sort option. Supported values are 'date' and 'severity'");
        }

        for (Patient patient : patients) {
            Map<String, Object> patientMap = new HashMap<>();
            patientMap.put("name", patient.getName());
            patientMap.put("disease", patient.getDisease());
            patientMap.put("severity", patient.getSeverity());
            patientMap.put("joinDate", patient.getJoinDate());
            patientDetails.add(patientMap);
        }

        return patientDetails;
    }


}

