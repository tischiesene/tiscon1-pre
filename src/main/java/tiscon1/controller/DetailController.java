package tiscon1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import tiscon1.model.Genre;
import tiscon1.repository.CategoryRepository;
import tiscon1.repository.GenreRepository;

import java.io.IOException;
import java.util.List;

/**
 * todo
 * @author fujiwara
 */
@Controller
public class DetailController {
    public static final String MOVIE_ID = "33";
    public static final String MUSIC_ID = "34";

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;

    @RequestMapping(value="/detail", method= RequestMethod.GET)
    public String detail(@RequestParam("genreId") String genreId, @RequestParam("subgenreId") String subgenreId, @RequestParam("id") String id, Model model) {
        model.addAttribute("genreId", genreId);
        model.addAttribute("subgenreId", subgenreId);
        if(genreId.equals(MOVIE_ID)) {
            try {
                model.addAttribute("item", categoryRepository.searchMovieItem(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("genreName", "MOVIE");
            for (Genre g:genreRepository.findMovieGenres()) {
                if (g.getId().equals(subgenreId)) {
                    model.addAttribute("subgenreName", g.getName());
                    break;
                }
            }
        } else if(genreId.equals(MUSIC_ID)) {
            try {
                model.addAttribute("item", categoryRepository.searchMusicItem(id));
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("genreName", "MUSIC");
            for (Genre g:genreRepository.findMusicGenres()) {
                if (g.getId().equals(subgenreId)) {
                    model.addAttribute("subgenreName", g.getName());
                    break;
                }
            }
        }

        return "detail";
    }
}