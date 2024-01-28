package br.com.alura.ScreenMatch2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class ScreenMatch2ApplicationTests {

	@Test
	void contextLoads() {
		// EXPLICACAO LAMBDA E ENCADEAMENTO INTERMEDIARIOS E FINAIS



		//for(int i = 0; i < dados.totalTemporadas(); i++){
//            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();//pega episodios
//            for(int j = 0; j < episodiosTemporada.size(); j++){
//                System.out.println(episodiosTemporada.get(j).titulo()); //imprime somente os titulos
//            }
//        } //Linha 43 é a mesma coisa mas de maneira simplificada (lambda)

			//Funções com parâmetros (letra) -> As funções Lambda - chamadas de funções anônimas - são uma maneira de definir funções que são frequentemente usadas uma única vez, direto no local onde elas serão usadas.
			//temporadas.forEach(t ->t.episodios().forEach(e -> System.out.println(e.titulo())));

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
			//.stream -> Uma stream é uma sequência de elementos que pode ser processada em paralelo ou em série. Ela pode ser criada a partir de uma coleção, um array, um arquivo, entre outros. A partir daí, podemos realizar diversas operações nessa stream, como filtrar, mapear, ordenar, entre outras.
			// As operações intermediárias são aquelas que podem ser aplicadas em uma stream e retornam uma nova stream como resultado. Essas operações não são executadas imediatamente, mas apenas quando uma operação final é chamada.
			//Collect: permite coletar os elementos da stream em uma coleção ou em outro tipo de dado. Por exemplo, podemos coletar os números pares em um conjunto.
			//.forEach() -> Operações finais
			//Episodio((parametro) -> expressao)
	}

}
