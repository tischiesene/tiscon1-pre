package tiscon1.repository;

import tiscon1.model.Genre;

import java.util.List;

/**
 * @author kawasima
 */
public interface GenreRepository {
    List<Genre> findAll();

    List<Genre> findMovieGenres();

    List<Genre> findMusicGenres();

    String getGenreName(String genreId);

    String getSubGenreName(String genreId, String subgenreId);

    Genre findByCd(String cd);
}
