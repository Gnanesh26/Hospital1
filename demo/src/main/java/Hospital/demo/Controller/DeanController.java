package Hospital.demo.Controller;

import Hospital.demo.Entity.Dean;
import Hospital.demo.service.DeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deans")
public class DeanController {

    @Autowired
    private DeanService deanService;

    @GetMapping("/{id}")
    public Dean getDeanById(@PathVariable Integer id) {
        return deanService.getDeanById(id);
    }

}

