package cc.orangejuice.srs.auth.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InitDatabase implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args) {
        //todo generate test data
    }
}