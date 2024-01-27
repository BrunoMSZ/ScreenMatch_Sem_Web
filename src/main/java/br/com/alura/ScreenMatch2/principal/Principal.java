package br.com.alura.ScreenMatch2.principal;

import br.com.alura.ScreenMatch2.model.DadosEpisodio;
import br.com.alura.ScreenMatch2.model.DadosSerie;
import br.com.alura.ScreenMatch2.model.DadosTemporada;
import br.com.alura.ScreenMatch2.service.ConsumoAPI;
import br.com.alura.ScreenMatch2.service.ConverteDados;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

        List<String> nomes = Arrays.asList("Jacque", "Iasmin", "Paulo", "Rodrigo","Nico");
        //Operacao encadeada
        nomes.stream()
                .sorted().
                limit(3)
                .filter(n ->n.startsWith("N"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
        //.sorted() ->ordena string e printou
        //.filter() -> Gera um filtro, nesse caso, nomes que comecam com "N"
        //.map() -> Faz uma tranformação, nesse caso, coloca o valor retornado pelo  filter e coloca em letras maiúsculas
        //.limit(valor) -> limita ate o valor pedido, no caso 3
        //.stream -> São operações intermediárias
        //.forEach() -> Operações finais
        //Episodio((parametro) -> expressao)



    }
}
