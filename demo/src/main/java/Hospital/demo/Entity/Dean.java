//package Hospital.demo.Entity;
//
//import jakarta.persistence.*;
//
//import java.util.List;
//
//
//@Entity
//@Table(name = "dean")
//public class Dean {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "dean_id")
//    private int dean_id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @JoinTable(
//            name = "dean_doctor",
//            joinColumns = @JoinColumn(name = "dean_id"),
//            inverseJoinColumns = @JoinColumn(name = "doctorId")
//    )
//    private List<Doctor> doctors;
//
//    public Dean(int dean_id, String name, List<Doctor> doctors) {
//        this.dean_id = dean_id;
//        this.name = name;
//        this.doctors = doctors;
//    }
//
//    public Dean() {
//    }
//
//    public int getDean_id() {
//        return dean_id;
//    }
//
//    public void setDean_id(int dean_id) {
//        this.dean_id = dean_id;
//    }
//
//    public List<Doctor> getDoctors() {
//        return doctors;
//    }
//
//    public void setDoctors(List<Doctor> doctors) {
//        this.doctors = doctors;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//}
//
package Hospital.demo.Entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "dean")
public class Dean {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dean_id")
    private Integer deanId;

    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "dean")
    private List<Patient> patients;

    public Dean(Integer deanId, String name) {
        this.deanId = deanId;
        this.name = name;
    }

    public Dean() {
    }

    public Integer getDeanId() {
        return deanId;
    }

    public void setDeanId(Integer deanId) {
        this.deanId = deanId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

