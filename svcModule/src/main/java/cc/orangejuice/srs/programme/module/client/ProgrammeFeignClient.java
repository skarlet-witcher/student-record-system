package cc.orangejuice.srs.programme.module.client;

import cc.orangejuice.srs.programme.domain.enumeration.ProgrammePropType;
import cc.orangejuice.srs.programme.service.dto.ProgrammePropDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@AuthorizedFeignClient(name = "svc-programme")
interface ProgrammeFeignClient {
    @GetMapping(value = "/api/programme-prop")
    Optional<ProgrammePropDTO> getProgrammeProp(
        @RequestParam(value = "type", defaultValue = "GENERAL") ProgrammePropType type,
        @RequestParam(value = "forEnrollYear", required = false) Integer forEnrollYear,
        @RequestParam(value = "forYearNo", required = false) Integer forYearNo,
        @RequestParam(value = "forSemesterNo", required = false) Integer forSemesterNo,
        @RequestParam("key") String key);
}
