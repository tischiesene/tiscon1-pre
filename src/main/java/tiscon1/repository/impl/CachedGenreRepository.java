package tiscon1.repository.impl;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
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

/**
 * @author kawasima
 */
@Component
public class CachedGenreRepository implements GenreRepository {
    private List<Genre> genres;

    private List<Genre> movieGenres;
    private List<Genre> musicGenres;

    private static final String MOVIE_ID = "33";
    private static final String MUSIC_ID = "34";

    /**
     * プロキシ設定を必要とする場合のRestTemplate生成メソッド。
     *
     * @param host   ホスト名
     * @param portNo ポート番号
     * @return プロキシが設定されたrestTemplate
     */
    private RestTemplate myRest(String host, int portNo) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, portNo)));

        return new RestTemplate(factory);
    }

    @PostConstruct
    protected void findFromApi() {
        // プロキシ設定が不要の場合
        RestTemplate rest = new RestTemplate();
        // プロキシ設定が必要の場合
        // RestTemplate rest = myRest("proxy.co.jp", 8080);

        /** ジャンル一覧検索結果 */
        final String MAP_GENRES = "https://itunes.apple.com/WebObjects/MZStoreServices.woa/ws/genres?cc=jp";

        Map<String, Map<String, Object>> mapGenres = rest.getForObject(MAP_GENRES, Map.class);
        Map<String, Map<String, Map<String, Map<String, Object>>>> mapMovies = rest.getForObject(MAP_GENRES + "&id=" + MOVIE_ID, Map.class);
        Map<String, Map<String, Map<String, Map<String, Object>>>> mapMusic = rest.getForObject(MAP_GENRES + "&id=" + MUSIC_ID, Map.class);


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
    public String getGenreName(String genreId) {
        if (genreId.equals(MOVIE_ID)) {
            return "MOVIE";
        } else if (genreId.equals(MUSIC_ID)) {
            return "MUSIC";
        } else {
            return null;
        }
    }

    @Override
    public String getSubGenreName(String genreId, String subgenreId) {
        if (genreId.equals(MOVIE_ID)) {
            for (Genre g : findMovieGenres()) {
                if (g.getId().equals(subgenreId)) {
                    return g.getName();
                }
            }
        } else if (genreId.equals(MUSIC_ID)) {
            for (Genre g : findMusicGenres()) {
                if (g.getId().equals(subgenreId)) {
                    return g.getName();
                }
            }
        }
        return null;
    }

    @Override
    public Genre findByCd(String cd) {
        return genres.stream()
                .filter(genre -> genre.getId().equals(cd))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Genre code %s is invalid.", cd)));
    }
}
