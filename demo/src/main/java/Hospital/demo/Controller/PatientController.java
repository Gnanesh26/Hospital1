package Hospital.demo.Controller;
import Hospital.demo.Entity.Doctor;
import Hospital.demo.Entity.Patient;
import Hospital.demo.Repository.DoctorRepository;
import Hospital.demo.service.DoctorService;
import Hospital.demo.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/patients")
public class PatientController {
    @Autowired
//    private PatientService patientService;
     PatientService patientService;
   private  DoctorService doctorService;

   private DoctorRepository doctorRepository;

    public PatientController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }
//    DoctorService doctorService = new DoctorService();
//    PatientController patientController = new PatientController(doctorService);

//    private DoctorService doctorService = new DoctorService();

//    @GetMapping("/{id}")
//    public Patient getPatientById(@PathVariable Integer id) {
//        return patientService.getPatientById(id);
//    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Map<String, Object>> getPatientDetails(@PathVariable int id) {
        Patient patient = patientService.getPatientById(id);

        Map<String, Object> response = new HashMap<>();
        response.put("severity", patient.getSeverity());
        response.put("disease", patient.getDisease());

        Doctor assignedDoctor = patient.getAssignedDoctor();
        Map<String, Object> assignedDoctorInfo = new HashMap<>();
        assignedDoctorInfo.put("doctorId", assignedDoctor.getDoctorId());
        assignedDoctorInfo.put("name", assignedDoctor.getName());

        response.put("assignedDoctor", assignedDoctorInfo);

        return ResponseEntity.ok(response);
    }
    @GetMapping("/doctors/{id}/patients")
    public ResponseEntity<List<Map<String, Object>>> getDoctorPatients(@PathVariable int id) {
        List<Map<String, Object>> patients = doctorService.getDoctorPatients(id);
        return ResponseEntity.ok(patients);
    }

}
