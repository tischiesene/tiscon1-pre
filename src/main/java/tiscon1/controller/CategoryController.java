package tiscon1.controller;

import com.hazelcast.core.LifecycleService;
import com.hazelcast.spi.impl.operationservice.impl.responses.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import tiscon1.exception.SystemException;
import tiscon1.model.Genre;
import tiscon1.repository.CategoryRepository;
import tiscon1.repository.GenreRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author fujiwara
 */
@Controller
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    GenreRepository genreRepository;

    @RequestMapping(value={"/category","/my/category"}, method=RequestMethod.GET)
    public String category(@RequestParam("genreId") String genreId, @RequestParam("subgenreId") String subgenreId, Model model) {
        model.addAttribute("genreId", genreId);
        model.addAttribute("subgenreId", subgenreId);
        model.addAttribute("genreName", genreRepository.getGenreName(genreId));
        model.addAttribute("subgenreName",genreRepository.getSubGenreName(genreId, subgenreId));

        try {
            model.addAttribute("ranking", categoryRepository.findTop10(genreId, subgenreId));
        } catch (Exception e) {
            throw new SystemException();
        }
        return "category";
    }
}