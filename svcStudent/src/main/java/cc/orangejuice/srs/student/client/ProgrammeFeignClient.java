package cc.orangejuice.srs.student.client;


import cc.orangejuice.srs.student.client.dto.ProgrammePropDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AuthorizedFeignClient(name = "svcProgramme")
@RestController
public interface ProgrammeFeignClient {
    @GetMapping(value = "/api/programme-props/query")
    List<ProgrammePropDTO> getProgrammeProps(
        @RequestParam(value = "type", defaultValue = "GENERAL") String type,
        @RequestParam(value = "forEnrollYear") Integer forEnrollYear,
        @RequestParam(value = "forYearNo", required = false) Integer forYearNo,
        @RequestParam(value = "forSemesterNo", required = false) Integer forSemesterNo,
        @RequestParam("key") String key);
}
