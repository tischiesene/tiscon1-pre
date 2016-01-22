package tiscon1.repository.impl;

import com.hazelcast.mapreduce.Mapper;
import com.sun.javafx.collections.MappingChange;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tiscon1.model.Movie;
import tiscon1.model.Music;
import tiscon1.repository.CategoryRepository;

import javax.print.attribute.standard.Media;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author fujiwara
 */
@Component
public class CategoryRepositoryImpl implements CategoryRepository {
    /** 検索用APIアドレス */
    static final String SEARCH_URL = "https://itunes.apple.com/jp/rss/";
    static final String LOOKUP_ID_URL = "https://itunes.apple.com/lookup?country=JP&id=";

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

    @Override
    public List<Movie> findMovieTop10(String genreId, String subgenreId) throws IOException {
        String url = SEARCH_URL + "topmovies/limit=10/genre=" + subgenreId + "/json";
        String jsonString = restTemplate().getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> hoge = (Map<String, Object>)mapper.readValue(jsonString, Map.class).get("feed");
        List<Map<String, Object>> fuga = (List<Map<String, Object>>)hoge.get("entry");

        List<Movie> movieTop10 = new ArrayList<Movie>();
        for(Map<String, Object> mapMovie : fuga){
            Movie movie = new Movie();
            Map<String, Map<String, Object>> mapId = (Map<String, Map<String, Object>>)mapMovie.get("id");
            movieTop10.add(searchMovieItem((String)mapId.get("attributes").get("im:id")));
        }
        return movieTop10;
    }

    @Override
    public List<Music> findMusicTop10(String genreId, String subgenreId) throws IOException {
        String url = SEARCH_URL + "topsongs/limit=10/genre=" + subgenreId + "/json";
        String jsonString = restTemplate().getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> hoge = (Map<String, Object>)mapper.readValue(jsonString, Map.class).get("feed");
        List<Map<String, Object>> fuga = (List<Map<String, Object>>)hoge.get("entry");

        List<Music> musicTop10 = new ArrayList<Music>();
        for(Map<String, Object> mapMusic : fuga){
            Music music = new Music();
            Map<String, Map<String, Object>> mapId = (Map<String, Map<String, Object>>)mapMusic.get("id");
            musicTop10.add(searchMusicItem((String)mapId.get("attributes").get("im:id")));
        }
        return musicTop10;
    }

    public Movie searchMovieItem(String id) throws IOException {
        Movie movie = new Movie();
        String jsonString = restTemplate().getForObject(LOOKUP_ID_URL + id, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapMovie = (Map<String, Object>)((List<Object>)mapper.readValue(jsonString, Map.class).get("results")).get(0);

        movie.setId(id);
        movie.setTitle((String) mapMovie.get("trackName"));
        movie.setImage((String) mapMovie.get("artworkUrl100"));
        movie.setSummary((String) mapMovie.get("longDescription"));
        movie.setPrice(String.valueOf(mapMovie.get("collectionPrice")));
        movie.setGenre((String) mapMovie.get("primaryGenreName"));
        movie.setReleaseDate((String) mapMovie.get("releaseDate"));

        return movie;
    }

    public Music searchMusicItem(String id) throws IOException {
        Music music = new Music();
        String jsonString = restTemplate().getForObject(LOOKUP_ID_URL + id, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> mapMusic = (Map<String, Object>)((List<Object>)mapper.readValue(jsonString, Map.class).get("results")).get(0);

        music.setId(id);
        music.setTitle((String) mapMusic.get("trackName"));
        music.setImage((String) mapMusic.get("artworkUrl100"));
        music.setArtist((String) mapMusic.get("artistName"));
        music.setPrice(String.valueOf(mapMusic.get("trackPrice")));
        music.setGenre((String) mapMusic.get("primaryGenreName"));
        music.setReleaseDate((String) mapMusic.get("releaseDate"));

        return music;
    }
}
