package tiscon1.repository.impl;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import tiscon1.model.Genre;
import tiscon1.repository.GenreRepository;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kawasima
 */
@Component
public class CachedGenreRepository implements GenreRepository {
    private List<Genre> genres;

    @PostConstruct
    protected void findFromApi() {
        RestTemplate rest = new RestTemplate();
        Map<String, Map<String, Object>> map = rest.getForObject("https://itunes.apple.com/WebObjects/MZStoreServices.woa/ws/genres?cc=jp", Map.class);
        genres = map.values().stream()
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
    public Genre findByCd(String cd) {
        return genres.stream()
                .filter(genre -> genre.getId().equals(cd))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Genre code %s is invalid.", cd)));
    }
}
