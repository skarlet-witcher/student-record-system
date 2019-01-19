package cc.orangejuice.srs.auth.service;

import cc.orangejuice.srs.auth.domain.Hero;
import cc.orangejuice.srs.auth.exception.HeroNotFoundException;
import cc.orangejuice.srs.auth.repository.HeroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HeroService {

    private final HeroRepository repository;

    @Autowired
    public HeroService(HeroRepository repository) {
        this.repository = repository;
    }

    public Hero getHeroById(Long id) {
        return repository.findById(id).orElseThrow(() -> new HeroNotFoundException(id));
    }

    public List<Hero> getAllHeroes() {
        return repository.findAll();
    }

    public List<Hero> findHeroesByName(String name) {
        return repository.findByName(name);
    }

    public Hero saveHero(Hero hero) {
        return repository.save(hero);
    }

    public void deleteHero(Long id) {
        repository.deleteById(id);
    }
}