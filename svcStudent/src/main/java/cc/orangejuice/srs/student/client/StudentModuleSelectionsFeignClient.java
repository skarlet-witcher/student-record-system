package cc.orangejuice.srs.student.client;

import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;

@AuthorizedFeignClient(name = "svcModule")
@RestController
public interface StudentModuleSelectionsFeignClient {
    @RequestMapping("/api/student-module-selections")
    Collection<StudentModuleSelectionDTO> findAll();

    @GetMapping("/api/student-module-selections/query")
    List<StudentModuleSelectionDTO> getStudentModuleSelections(
        @RequestParam("studentId") Long studentId,
        @RequestParam("academicYear") Integer academicYear,
        @RequestParam("yearNo") Integer yearNo);
}
