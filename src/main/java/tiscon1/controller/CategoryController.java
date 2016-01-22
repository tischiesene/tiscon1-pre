package tiscon1.controller;

import com.hazelcast.core.LifecycleService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * todo
 * @author fujiwara
 */
@Controller
public class CategoryController {
    public static final String MOVIE_ID = "33";
    public static final String MUSIC_ID = "34";

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;

    @RequestMapping(value="/category", method=RequestMethod.GET)
    public String category(@RequestParam("genreId") String genreId, @RequestParam("subgenreId") String subgenreId, Model model) {
        model.addAttribute("genreId", genreId);
        model.addAttribute("subgenreId", subgenreId);
        if(genreId.equals(MOVIE_ID)) {
            try {
                model.addAttribute("ranking", categoryRepository.findMovieTop10(genreId, subgenreId));
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("genreName", "MOVIE");
            for (Genre subgenre:genreRepository.findMovieGenres()) {
                if (subgenre.getId().equals(subgenreId)) {
                    model.addAttribute("subgenreName", subgenre.getName());
                    break;
                }
            }
        } else if(genreId.equals(MUSIC_ID)) {
            try {
                model.addAttribute("ranking", categoryRepository.findMusicTop10(genreId, subgenreId));
            } catch (IOException e) {
                e.printStackTrace();
            }
            model.addAttribute("genreName", "MUSIC");
            for (Genre subgenre:genreRepository.findMusicGenres()) {
                if (subgenre.getId().equals(subgenreId)) {
                    model.addAttribute("subgenreName", subgenre.getName());
                    break;
                }
            }
        }

        return "category";
    }
}