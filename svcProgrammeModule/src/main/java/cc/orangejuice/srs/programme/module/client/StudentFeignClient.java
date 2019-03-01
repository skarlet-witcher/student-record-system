package cc.orangejuice.srs.programme.module.client;


import cc.orangejuice.srs.student.service.dto.StudentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@AuthorizedFeignClient(name = "svc-student")
public interface StudentFeignClient {
    @GetMapping(value = "/api/students/{id}")
    ResponseEntity<StudentDTO> getStudent(@PathVariable Long id);
}
