package tiscon1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tiscon1.model.Genre;
import tiscon1.repository.CategoryRepository;
import tiscon1.repository.GenreRepository;

import java.io.IOException;
import java.util.List;

/**
 * @author kawasima
 */
@Controller
public class IndexController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;

    @RequestMapping("/")
    public String index(Model model) {
        try {
            model.addAttribute("movieRank", categoryRepository.findMovieTop10("33", null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            model.addAttribute("musicRank", categoryRepository.findMusicTop10("34", null));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "index";
    }

}
