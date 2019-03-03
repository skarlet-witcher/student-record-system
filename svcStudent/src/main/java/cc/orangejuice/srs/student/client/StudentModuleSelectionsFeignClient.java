package cc.orangejuice.srs.student.client;

import cc.orangejuice.srs.student.service.dto.StudentModuleSelectionDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@AuthorizedFeignClient(name="svcModule" )
public interface StudentModuleSelectionsFeignClient {
    @RequestMapping("/api/student-module-selections")
    public Collection<StudentModuleSelectionDTO> findAll();
}
