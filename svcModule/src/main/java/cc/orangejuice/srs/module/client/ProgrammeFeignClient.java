package cc.orangejuice.srs.module.client;


import cc.orangejuice.srs.module.client.dto.ProgrammePropDTO;
import cc.orangejuice.srs.module.client.dto.ProgrammeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/api/programmes/{id}")
    ResponseEntity<ProgrammeDTO> getProgramme(@PathVariable("id") Long id);

    @GetMapping(value = "/api/programme-props/getByEnrollYearAndPart/")
    List<ProgrammePropDTO> getProgrammePropsByYearAndPart(
        @RequestParam(value = "enrollYear") Integer forEnrollYear,
        @RequestParam(value = "part") String partNo
    );
}
