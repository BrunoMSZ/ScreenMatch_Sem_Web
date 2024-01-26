package br.com.alura.ScreenMatch2;

import br.com.alura.ScreenMatch2.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatch2Application  implements CommandLineRunner { //Criar algumas chamadas (CommandLineRunner)

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatch2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception { //método Main
		Principal principal = new Principal();
		principal.exibeMenu();
	}
}
