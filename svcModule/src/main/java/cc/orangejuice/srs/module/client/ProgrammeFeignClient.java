package cc.orangejuice.srs.module.client;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "svc-programme")
interface ProgrammeFeignClient {
    @GetMapping(value = "/api/programme-prop")
    ResponseEntity getProgrammeProp(
        @RequestParam(value = "type", defaultValue = "GENERAL") String type,
        @RequestParam(value = "forEnrollYear", required = false) Integer forEnrollYear,
        @RequestParam(value = "forYearNo", required = false) Integer forYearNo,
        @RequestParam(value = "forSemesterNo", required = false) Integer forSemesterNo,
        @RequestParam("key") String key);
}
