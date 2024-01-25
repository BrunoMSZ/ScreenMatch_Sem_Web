package br.com.alura.ScreenMatch2;

import br.com.alura.ScreenMatch2.model.DadosEpisodio;
import br.com.alura.ScreenMatch2.model.DadosSerie;
import br.com.alura.ScreenMatch2.model.DadosTemporada;
import br.com.alura.ScreenMatch2.service.ConsumoAPI;
import br.com.alura.ScreenMatch2.service.ConverteDados;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ScreenMatch2Application  implements CommandLineRunner { //Criar algumas chamadas (CommandLineRunner)

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatch2Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception { //método Main
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=Reacher&&apikey=66418c3a");
		//System.out.println(json);
		//json = consumoAPI.obterDados("https://coffee.alexflipnote.dev/random.json");//imagem de café randomizada
		System.out.println(json);
		ConverteDados conversor = new ConverteDados();
		DadosSerie dados = conversor.obterDado(json, DadosSerie.class);
		System.out.println(dados);
		json = consumoAPI.obterDados("https://www.omdbapi.com/?t=Reacher&season=1&episode=2&apikey=66418c3a");
		DadosEpisodio dadosEpisodio = conversor.obterDado(json, DadosEpisodio.class);
		System.out.println(dadosEpisodio);

		List<DadosTemporada> temporadas = new ArrayList<>();

		//listar as temporadas
		for(int i = 1; i <= dados.totalTemporadas(); i++){
			json = consumoAPI.obterDados("https://www.omdbapi.com/?t=Reacher&season=" + i + "&apikey=66418c3a");
			DadosTemporada dadosTemporada = conversor.obterDado(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println); //printar a lista apartir do forEach
	}
}
