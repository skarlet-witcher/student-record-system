package cc.orangejuice.srs.module.client;


import cc.orangejuice.srs.module.client.dto.ProgrammePropDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@AuthorizedFeignClient(name = "svcprogramme")
@RestController
public interface ProgrammeFeignClient {
    @GetMapping(value = "/api/programme-props/query")
    ResponseEntity<ProgrammePropDTO> getProgrammeProps(
        @RequestParam(value = "type", defaultValue = "GENERAL") String type,
        @RequestParam(value = "forEnrollYear", required = false) Integer forEnrollYear,
        @RequestParam(value = "forYearNo", required = false) Integer forYearNo,
        @RequestParam(value = "forSemesterNo", required = false) Integer forSemesterNo,
        @RequestParam("key") String key);
}
