package cc.orangejuice.srs.student.client;

import cc.orangejuice.srs.student.client.dto.ModuleGradeDTO;
import cc.orangejuice.srs.student.client.dto.StudentModuleSelectionDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AuthorizedFeignClient(name = "svcModule")
@RestController
public interface StudentModuleSelectionsFeignClient { // unused

    @GetMapping("/api/student-module-selections/query")
    List<StudentModuleSelectionDTO> getStudentModuleSelections(
        @RequestParam("studentId") Long studentId,
        @RequestParam("academicYear") Integer academicYear,
        @RequestParam("yearNo") Integer yearNo);

    @GetMapping("/api/module-grades/get-all-module-grades")
    List<ModuleGradeDTO> getAllModuleGradeWithQCAAffected();

}
