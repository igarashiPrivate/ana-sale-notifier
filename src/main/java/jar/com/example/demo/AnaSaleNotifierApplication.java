package jar.com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class AnaSaleNotifierApplication implements CommandLineRunner {

    @Autowired
    private AnaCheckService anaCheckService;

    @Autowired
    private ApplicationContext context;

    public static void main(String[] args) {
        SpringApplication.run(AnaSaleNotifierApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        anaCheckService.checkSale();
        SpringApplication.exit(context, () -> 0);
    }
}