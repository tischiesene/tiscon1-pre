package tiscon1.repository.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tiscon1.model.Genre;
import tiscon1.repository.GenreRepository;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 社内環境での設定
import org.springframework.http.client.SimpleClientHttpRequestFactory;

/**
 * @author kawasima
 */
@Component
public class CachedGenreRepository implements GenreRepository {
    private List<Genre> genres;

    private List<Genre> movieGenres;
    private List<Genre> musicGenres;

    /**
     * 社内での開発の際必要となるプロキシ設定。
     * @return 社内用プロキシが設定されたrestTemplate
     * @note 社内の設定のためpushしない！！
     */
    private RestTemplate restTemplate() {
        final String PROXY_HOST = "tkyproxy.intra.tis.co.jp";
        final int PROXY_PORT = 8080;
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(PROXY_HOST, PROXY_PORT)));

        return new RestTemplate(factory);
    }

    @PostConstruct
    protected void findFromApi() {
        // 社外用
        // RestTemplate rest = new RestTemplate();

        // 社内用proxy設定
        RestTemplate rest = restTemplate();

        /** ジャンル一覧検索結果 */
        final String MAP_GENRES = "https://itunes.apple.com/WebObjects/MZStoreServices.woa/ws/genres?cc=jp";

        Map<String, Map<String, Object>> mapGenres = rest.getForObject(MAP_GENRES, Map.class);
        Map<String, Map<String, Map<String, Map<String, Object>>>> mapMovies = rest.getForObject(MAP_GENRES+"&id=33", Map.class);
        String temp = rest.getForObject(MAP_GENRES+"&id=33", String.class);
        Map<String, Map<String, Map<String, Map<String, Object>>>> mapMusic =  rest.getForObject(MAP_GENRES+"&id=34", Map.class);


        genres = mapGenres.values().stream()
                .map(v ->
                        new Genre((String) v.get("id"),
                                (String) v.get("name"),
                                (String) v.get("url")))
                .collect(Collectors.toList());

        movieGenres = mapMovies.values().stream()
                .filter(v -> v.containsKey("subgenres"))
                .flatMap(v -> v.get("subgenres").values().stream())
                .map(v ->
                        new Genre((String) v.get("id"),
                                (String) v.get("name"),
                                (String) v.get("url")))
                .collect(Collectors.toList());

        musicGenres = mapMusic.values().stream()
                .filter(v -> v.containsKey("subgenres"))
                .flatMap(v -> v.get("subgenres").values().stream())
                .map(v ->
                        new Genre((String) v.get("id"),
                                (String) v.get("name"),
                                (String) v.get("url")))
                .collect(Collectors.toList());
    }

    @Override
    public List<Genre> findAll() {
        return genres;
    }

    @Override
    public List<Genre> findMovieGenres() {
        return movieGenres;
    }

    @Override
    public List<Genre> findMusicGenres() {
        return musicGenres;
    }

    @Override
    public Genre findByCd(String cd) {
        return genres.stream()
                .filter(genre -> genre.getId().equals(cd))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Genre code %s is invalid.", cd)));
    }
}
