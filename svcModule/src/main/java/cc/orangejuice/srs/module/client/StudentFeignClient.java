package cc.orangejuice.srs.module.client;





import cc.orangejuice.srs.module.client.dto.StudentEnrollDTO;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import cc.orangejuice.srs.module.client.dto.StudentDTO;
import cc.orangejuice.srs.module.client.dto.StudentProgressionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@AuthorizedFeignClient(name = "svcStudent")
@RestController
public interface StudentFeignClient {

    @GetMapping(value = "/api/students/{id}")
    ResponseEntity<StudentDTO> getStudent(@PathVariable("id") Long id);


    @PutMapping(value = "/api/student-progressions/calculateQCA")
    ResponseEntity<Void> calculateQCA(@RequestParam("academicYear") Integer academicYear,
                                      @RequestParam("academicSemester") Integer academicSemester,
                                      @RequestBody List<StudentModuleSelectionDTO> resultsList);

    @GetMapping("/api/student-progressions/progression-info")
    StudentProgressionDTO getProgressionInfo(@RequestParam("studentId") Long studentId,
                                                    @RequestParam("academicYear") Integer academicYear,
                                                    @RequestParam("academicSemester") Integer academicSemester);

    @GetMapping("/api/student-enrolls/student-enroll-detail/{studentId}")
    StudentEnrollDTO getStudentEnrollDetail(@PathVariable("studentId") Long studentId);
}
