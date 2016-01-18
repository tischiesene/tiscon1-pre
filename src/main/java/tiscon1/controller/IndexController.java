package tiscon1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import tiscon1.model.Genre;
import tiscon1.repository.GenreRepository;

import java.util.Date;
import java.util.List;

/**
 * @author kawasima
 */
@Controller
public class IndexController {
    @Autowired
    GenreRepository genreRepository;

    @RequestMapping("/")
    public String index(Model model) {
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "index";
    }
}
