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
//        System.out.println("\nTop 10 episódios");
//        dadosEpisodios.stream()
//                .filter(e-> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .peek(e-> System.out.println("Primeiro Filtro(N/A)" + e)) //peek serve para analisar o que está acontecendo dentro do código, como se fosse um "DEBUG"
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .peek(e-> System.out.println("Ordenação " + e))
//                .limit(10)
//                .peek(e-> System.out.println("Limite " + e))
//                .map(e->e.titulo().toUpperCase())
//                .peek(e-> System.out.println("Mapeamento " + e))
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(),d))).collect(Collectors.toList());

        episodios.forEach(System.out::println);

        System.out.println("Digite um titulo de um episódio :");
        var trechoTitulo = leitura.nextLine();
        Optional<Episodio> episodioBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo.toUpperCase()))//se tiver algum trecho de um titulo de um episodio em especifico
                .findFirst();//busca a mesma ordem de busca, o ANY busca qualquer coisa
        //Optional é um objeto contêiner que pode ou não conter um valor não nulo (parecido como uma lista)
        //O principal uso do Optional é fornecer um tipo de retorno alternativo quando um método pode não retornar um valor
        if(episodioBuscado.isPresent()){ //se está presente na "lista"
            System.out.println("Episódio encontrado!");
            System.out.println("Temporada: " + episodioBuscado.get().getTemporada());
        }else{
            System.out.println("Episódio não encontrado!");
        }
//        System.out.println("A partir de que ano você deseja ver os episódios? ");
//        var ano = leitura.nextInt();
//        leitura.nextLine();
//
//        LocalDate dataBusca = LocalDate.of(ano,1,1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //formatando a data
//        episodios.stream()
//                .filter(e -> e.getDataLancamento()!=null && e.getDataLancamento().isAfter(dataBusca))
//                .forEach(e-> System.out.println(
//                        "Temporada: " + e.getTemporada() + "  Episódio: " +e.getTitulo() +
//                                "  Data lançamento: " + e.getDataLancamento().format(formatador)
//                ));

        System.out.println("Avaliações por Temporada: \n");
        Map<Integer, Double> avaliacoesPorTemporada = episodios.stream()
                .filter(e->e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao))); //Usa a classe collectors para realizar um "dicionario" de chave e valor, além de realizar a média das avaliações dos episodios e atribuindo a cada temporada

        System.out.println(avaliacoesPorTemporada);

        //Estatisticas
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e->e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));
        //count = qtd de episódios, sum = soma, min = minimo, max = máximo, average = media (todos com base me getAvaliacao)

        System.out.println("Média: " + est.getAverage()
        + "\nMelhor episódio: " + est.getMax() + "\nPior episódio: " + est.getMin() + "\nQuantidade episódios: " + est.getCount());

    }
}
