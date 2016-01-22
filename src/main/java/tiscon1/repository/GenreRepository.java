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
    Genre findByCd(String cd);
}
