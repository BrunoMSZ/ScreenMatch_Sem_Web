package br.com.alura.ScreenMatch2.principal;

import br.com.alura.ScreenMatch2.model.DadosEpisodio;
import br.com.alura.ScreenMatch2.model.DadosSerie;
import br.com.alura.ScreenMatch2.model.DadosTemporada;
import br.com.alura.ScreenMatch2.model.Episodio;
import br.com.alura.ScreenMatch2.service.ConsumoAPI;
import br.com.alura.ScreenMatch2.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String _ENDERECO = "https://www.omdbapi.com/?t=";
    private final String _API_KEY = "&&apikey=66418c3a"; //final = não será alterado mais
    private ConsumoAPI consumo = new ConsumoAPI();
    private ConverteDados conversor = new ConverteDados();
    Scanner leitura = new Scanner(System.in);

    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca: ");
        var nomeSerie = leitura.nextLine();
        var json = consumo.obterDados(_ENDERECO + nomeSerie.replace(" ", "+") + _API_KEY);
        DadosSerie dados = conversor.obterDado(json, DadosSerie.class);
        System.out.println(dados);
        List<DadosTemporada> temporadas = new ArrayList<>();

		//listar as temporadas
		for(int i = 1; i <= dados.totalTemporadas(); i++){
			json = consumo.obterDados(_ENDERECO + nomeSerie.replace(" ", "+") + "&season="+ i + _API_KEY);
			DadosTemporada dadosTemporada = conversor.obterDado(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println); //printar a lista apartir do forEach (lambda)

//        for(int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();//pega episodios
//            for(int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo()); //imprime somente os titulos
//            }
//        } //Linha 43 é a mesma coisa mas de maneira simplificada (lambda)

        //Funções com parâmetros (letra) -> As funções Lambda - chamadas de funções anônimas - são uma maneira de definir funções que são frequentemente usadas uma única vez, direto no local onde elas serão usadas.
        temporadas.forEach(t ->t.episodios().forEach(e -> System.out.println(e.titulo())));

        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()) //puxar todas as listas, neste caso, possui uma lista dentro da outra
                .collect(Collectors.toList()); //coletar para uma lista
                //.toList(); //é uma lista imutável, mas possui a mesma função do .collect()
        System.out.println("\nTop 5 episódios");
        dadosEpisodios.stream()
                .filter(e-> !e.avaliacao().equalsIgnoreCase("N/A"))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .limit(5)
                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("A partir de que ano você deseja ver os episódios? ");
        var ano = leitura.nextInt();
        leitura.nextLine();

        LocalDate dataBusca = LocalDate.of(ano,1,1);

        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //formatando a data
        episodios.stream()
                .filter(e -> e.getDataLancamento()!=null && e.getDataLancamento().isAfter(dataBusca))
                .forEach(e-> System.out.println(
                        "Temporada: " + e.getTemporada() + "  Episódio: " +e.getTitulo() +
                                "  Data lançamento: " + e.getDataLancamento().format(formatador)
                ));
    }
}
