package Hospital.demo.Controller;

import Hospital.demo.Entity.Doctor;
import Hospital.demo.Entity.Patient;
import Hospital.demo.Repository.DoctorRepository;
import Hospital.demo.Repository.PatientRepository;
import Hospital.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
 DoctorService doctorService;

    DoctorRepository doctorRepository;

    PatientRepository patientRepository;

    @GetMapping("/{id}")
    public Doctor getDoctorById(@PathVariable Integer id) {
        return doctorService.getDoctorById(id);
    }


    @PreAuthorize("hasRole('dean')")
    @PostMapping
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
        Doctor createdDoctor = doctorService.createDoctor(doctor);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDoctor);
    }

//    @PreAuthorize("hasRole('dean')")
//    @PostMapping("/doctors-and-patients")
//    public ResponseEntity<String> createDoctorAndPatient(@RequestBody Map<String, Object> request) {
//        // Extract doctor and patient details from the request
//        Map<String, Object> doctorDetails = (Map<String, Object>) request.get("doctor");
//        Map<String, Object> patientDetails = (Map<String, Object>) request.get("patient");
//
//        // Create a new Doctor entity
//        Doctor doctor = new Doctor();
//        doctor.setName((String) doctorDetails.get("name"));
//        doctor.setJoinDate(LocalDate.now());
//
//        // Create a new Patient entity
//        Patient patient = new Patient();
//        patient.setPatientId((Integer) patientDetails.get("id")); // Set the desired patient ID
//        patient.setName((String) patientDetails.get("name"));
//        patient.setJoinDate(LocalDate.now());
//        patient.setDisease((String) patientDetails.get("disease"));
//        patient.setSeverity((Integer) patientDetails.get("severity"));
//
//        // Associate the doctor and patient
//        patient.setAssignedDoctor(doctor);
//
//        // Save the doctor and patient entities
//        doctorRepository.save(doctor);
//        patientRepository.save(patient);
//
//        return ResponseEntity.ok("Doctor and Patient created successfully.");
//    }


//        @PreAuthorize("hasRole('dean')")
//
//
//    @PostMapping("/doctors-and-patients")
//    public ResponseEntity<String> createDoctorAndPatient(@RequestBody Map<String, Object> request) {
//        // Extract doctor and patient details from the request
//        Map<String, Object> doctorDetails = (Map<String, Object>) request.get("doctor");
//        Map<String, Object> patientDetails = (Map<String, Object>) request.get("patient");
//
//        // Create a new Doctor entity
//        Doctor doctor = new Doctor();
//        doctor.setName((String) doctorDetails.get("name"));
//        doctor.setJoinDate(LocalDate.now());
//
//        // Create a new Patient entity
//        Patient patient = new Patient();
//        patient.setName((String) patientDetails.get("name"));
//        patient.setDisease((String) patientDetails.get("disease"));
//        patient.setSeverity((Integer) patientDetails.get("severity"));
//        patient.setJoinDate(LocalDate.now());
//        patient.setStatus("Active");
//
//        // Associate the doctor and patient
//        patient.setAssignedDoctor(doctor);
//
//        // Save the doctor and patient entities
//        doctorRepository.save(doctor);
//        patientRepository.save(patient);
//
//        return ResponseEntity.ok("Doctor and Patient created successfully.");
//    }


    @GetMapping("/doctors/{id}")
    public ResponseEntity<Map<String, Object>> getDoctorDetails(@PathVariable int id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            return ResponseEntity.notFound().build();
        }

        Map<String, Object> doctorDetails = new HashMap<>();
        doctorDetails.put("doctorId", doctor.getDoctorId());
        doctorDetails.put("name", doctor.getName());
        doctorDetails.put("joinDate", doctor.getJoinDate());

        List<Map<String, Object>> patients = new ArrayList<>();
        for (Patient patient : doctor.getPatients()) {
            Map<String, Object> patientDetails = new HashMap<>();
            patientDetails.put("patientId", patient.getPatientId());
            patientDetails.put("name", patient.getName());
            patientDetails.put("joinDate", patient.getJoinDate());
            patientDetails.put("disease", patient.getDisease());
            patientDetails.put("severity", patient.getSeverity());
            patients.add(patientDetails);
        }

        doctorDetails.put("patients", patients);

        return ResponseEntity.ok(doctorDetails);
    }
    @GetMapping("/doctors")
    public ResponseEntity<List<Map<String, Object>>> getAllDoctorsWithPatients() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<Map<String, Object>> doctorDetailsList = new ArrayList<>();

        for (Doctor doctor : doctors) {
            Map<String, Object> doctorDetails = new HashMap<>();
            doctorDetails.put("doctorId", doctor.getDoctorId());
            doctorDetails.put("name", doctor.getName());
            doctorDetails.put("joinDate", doctor.getJoinDate());

            List<Map<String, Object>> patients = new ArrayList<>();
            for (Patient patient : doctor.getPatients()) {
                Map<String, Object> patientDetails = new HashMap<>();
                patientDetails.put("patientId", patient.getPatientId());
                patientDetails.put("name", patient.getName());
                patientDetails.put("joinDate", patient.getJoinDate());
                patientDetails.put("disease", patient.getDisease());
                patientDetails.put("severity", patient.getSeverity());
                patients.add(patientDetails);
            }

            doctorDetails.put("patients", patients);
            doctorDetailsList.add(doctorDetails);
        }

        return ResponseEntity.ok(doctorDetailsList);
    }
    @PreAuthorize("hasRole('dean')")
    @GetMapping("/doctorssort")
    public ResponseEntity<List<Map<String, Object>>> getAllDoctorsWithPatients(
            @RequestParam(required = false, defaultValue = "id") String sortBy) {

        List<Doctor> doctors = doctorService.getAllDoctors();
        List<Map<String, Object>> doctorDetailsList = new ArrayList<>();

        // Sort doctors based on the given sortBy parameter
        if (sortBy.equals("id")) {
            doctors.sort(Comparator.comparing(Doctor::getDoctorId));
        } else if (sortBy.equals("date")) {
            doctors.sort(Comparator.comparing(Doctor::getJoinDate));
        } else if (sortBy.equals("severity")) {
            // Sort doctors based on the maximum severity of their patients
            doctors.sort(Comparator.comparing(doctor -> {
                int maxSeverity = doctor.getPatients().stream()
                        .mapToInt(Patient::getSeverity)
                        .max()
                        .orElse(0);
                return -maxSeverity; // Sort in descending order
            }));
        }

        for (Doctor doctor : doctors) {
            Map<String, Object> doctorDetails = new HashMap<>();
            doctorDetails.put("doctorId", doctor.getDoctorId());
            doctorDetails.put("name", doctor.getName());
            doctorDetails.put("joinDate", doctor.getJoinDate());

            List<Map<String, Object>> patients = new ArrayList<>();
            for (Patient patient : doctor.getPatients()) {
                Map<String, Object> patientDetails = new HashMap<>();
                patientDetails.put("patientId", patient.getPatientId());
                patientDetails.put("name", patient.getName());
                patientDetails.put("joinDate", patient.getJoinDate());
                patientDetails.put("disease", patient.getDisease());
                patientDetails.put("severity", patient.getSeverity());
                patients.add(patientDetails);
            }

            doctorDetails.put("patients", patients);
            doctorDetailsList.add(doctorDetails);
        }

        return ResponseEntity.ok(doctorDetailsList);
    }

}

