package cc.orangejuice.srs.module.client;




import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@AuthorizedFeignClient(name = "svcStudent")
@RestController
public interface StudentFeignClient {
    @GetMapping(value = "/api/students/{id}")
    ResponseEntity getStudent(@PathVariable Long id);
}
