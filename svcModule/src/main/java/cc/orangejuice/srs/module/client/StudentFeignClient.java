package cc.orangejuice.srs.module.client;





import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@AuthorizedFeignClient(name = "svcStudent")
@RestController
public interface StudentFeignClient {
    /*
    @GetMapping(value = "/api/students/{id}")
    ResponseEntity getStudent(@PathVariable Long id);
    */

    @PutMapping(value = "/api/student-progressions/calculateQCA")
    ResponseEntity<Void> calculateQCA(@RequestParam("academicYear") Integer academicYear,
                                      @RequestParam("academicSemester") Integer academicSemester,
                                      @RequestBody List<StudentModuleSelectionDTO> resultsList);
}
