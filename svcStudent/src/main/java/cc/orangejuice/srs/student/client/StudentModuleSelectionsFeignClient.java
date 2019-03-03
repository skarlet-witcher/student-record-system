package cc.orangejuice.srs.student.client;

import cc.orangejuice.srs.student.service.dto.StudentModuleSelectionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@AuthorizedFeignClient(name="svcModule" )
public interface StudentModuleSelectionsFeignClient {
    @RequestMapping("/api/student-module-selections")
    public Collection<StudentModuleSelectionDTO> findAll();

    @GetMapping("/api/student-module-selections/selection-details")
    public List<StudentModuleSelectionDTO> getStudentModuleSelections(@RequestParam("studentId") Long studentId,
                                                                      @RequestParam("academicYear") Integer academicYear,
                                                                      @RequestParam("yearNo") Integer yearNo);
}
