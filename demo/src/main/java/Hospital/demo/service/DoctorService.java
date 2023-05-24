package Hospital.demo.service;

import Hospital.demo.Entity.Doctor;
import Hospital.demo.Entity.Patient;
import Hospital.demo.Repository.DoctorRepository;
import Hospital.demo.Repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DoctorService {

    @Autowired
  DoctorRepository doctorRepository;
    PatientRepository patientRepository;
    DeanService deanService;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctorById(Integer id) {
        return doctorRepository.findById(id).orElse(null);
    }

    public List<Map<String, Object>> getDoctorPatients(int doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + doctorId));

        List<Map<String, Object>> patients = new ArrayList<>();
        for (Patient patient : doctor.getPatients()) {
            Map<String, Object> patientInfo = new HashMap<>();
            patientInfo.put("joinDate", patient.getJoinDate());
            patientInfo.put("disease", patient.getDisease());
            patientInfo.put("severity", patient.getSeverity());
            patients.add(patientInfo);
        }

        return patients;
    }

    public List<Map<String, Object>> getDoctorPatientsSortedByDate(int doctorId) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        List<Patient> patients = doctor.get().getPatients();
        // Sort the patients by join date
        Collections.sort(patients, Comparator.comparing(Patient::getJoinDate));

        // Create a list of maps containing the required patient information
        List<Map<String, Object>> patientDetails = new ArrayList<>();
        for (Patient patient : patients) {
            Map<String, Object> patientMap = new HashMap<>();
            patientMap.put("patientId", patient.getPatientId());
            patientMap.put("name", patient.getName());
            patientMap.put("joinDate", patient.getJoinDate());
            patientMap.put("disease", patient.getDisease());
            patientMap.put("severity", patient.getSeverity());
            patientDetails.add(patientMap);
        }

        return patientDetails;
    }


    public Doctor createDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
