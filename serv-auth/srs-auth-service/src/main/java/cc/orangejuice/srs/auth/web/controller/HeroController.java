package cc.orangejuice.srs.auth.web.controller;

import cc.orangejuice.srs.auth.domain.Hero;
import cc.orangejuice.srs.auth.service.HeroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/heroes", produces = MediaType.APPLICATION_JSON_VALUE)
public class HeroController {

    private static final Logger logger = LoggerFactory.getLogger(HeroController.class);

    private final HeroService service;

    @Autowired
    public HeroController(HeroService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public Hero getHeroById(@PathVariable("id") Long id) throws Exception {
        return service.getHeroById(id);
    }

    @GetMapping()
    public List<Hero> getHeroes() {
        return service.getAllHeroes();
    }

    @GetMapping("/")
    public List<Hero> searchHeroes(@RequestParam("name") String name) {
        return service.findHeroesByName(name);
    }

    @PostMapping()
    public Hero addHero(@RequestBody Hero hero) {
        return service.saveHero(hero);
    }

    @PutMapping("")
    public Hero updateHero(@RequestBody Hero hero) {
        return service.saveHero(hero);
    }

    @DeleteMapping("/{id}")
    public void deleteHero(@PathVariable("id") Long id) {
        service.deleteHero(id);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Map<String, Object>> handleDataAccessException(DataAccessException exception) {
        logger.error(exception.getMessage(), exception);
        Map<String, Object> body = new HashMap<>();
        body.put("message", exception.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

}
