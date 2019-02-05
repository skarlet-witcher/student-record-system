package cc.orangejuice.srs.univ.course.module.client;

import cc.orangejuice.srs.univ.course.module.client.vo.StudentVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@AuthorizedFeignClient(name = "svcstudent")
interface StudentFeignClient {
    @GetMapping(value = "/api/students/{id}")
    StudentVo findStudent(@PathVariable("id") Long id);
}
