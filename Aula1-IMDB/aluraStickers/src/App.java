import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class App {
    public static void main(String[] args) throws Exception {
        // fazer uma conexão HTTP e buscar os top 250 filmes
        String urlTop250 = "https://imdb-api.com/en/API/Top250Movies/k_dock2u66";
        String urlPopulares = "https://imdb-api.com/en/API/MostPopularMovies/k_dock2u66";
        URI enderecoTop250 = URI.create(urlTop250);
        URI enderecoPopulares = URI.create(urlPopulares);

        var client = HttpClient.newHttpClient();
        var requestTop250 = HttpRequest.newBuilder(enderecoTop250).GET().build();
        HttpResponse<String> responseTop250 = client.send(requestTop250, HttpResponse.BodyHandlers.ofString());
        String bodyTop250 = responseTop250.body();
        var requestPopulares = HttpRequest.newBuilder(enderecoPopulares).GET().build();
        HttpResponse<String> responsePopulares = client.send(requestPopulares, HttpResponse.BodyHandlers.ofString());
        String bodyPopulares = responsePopulares.body();

        // extrair só os dados que interessam (titulo, poster, nota)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmesTop250 = parser.parse(bodyTop250);
        List<Map<String, String>> listaDeFilmesPopulares = parser.parse(bodyPopulares);

        // exibir e manipular os dados
        System.out.println("TOP 250 IMDB");
        for(Map<String, String> filme : listaDeFilmesTop250) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));
            for (int i = 0; i < Math.round(Float.parseFloat(filme.get("imDbRating"))); i++) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println("************************************************");
        System.out.println("POPULARES IMDB");
        for(Map<String, String> filme : listaDeFilmesPopulares) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("year"));
            System.out.println(filme.get("image"));
            System.out.println();
        }
    }
}
