package tiscon1.repository;

import tiscon1.model.Movie;
import tiscon1.model.Music;

import java.io.IOException;
import java.util.List;

/**
 * @author fujiwara
 */
public interface CategoryRepository {
    List<Movie> findMovieTop10(String genre, String genreId) throws IOException;
    List<Music> findMusicTop10(String genre, String genreId) throws IOException;
    Movie searchMovieItem(String id) throws IOException;
    Music searchMusicItem(String id) throws IOException;
}
