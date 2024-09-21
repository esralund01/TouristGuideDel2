package touristguidedel2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import touristguidedel2.service.TouristService;

@Controller
public class TouristController {

    private final TouristService touristService;

    public TouristController() {
        touristService = new TouristService();
    }

    @GetMapping("/attractions")
    public String attractionList(Model model) {
        model.addAttribute("touristAttractions",touristService.getAllAttractions());
        return "attractionList";
    }

    @GetMapping("/attractions/{name}")
    public String attractions1() {
        return null;
    }

    @GetMapping("/attractions/{name}/tags")
    public String attractionTags(@PathVariable String name, Model model) {
        model.addAttribute("touristAttraction", touristService.getAttractionByName(name));
        return "tags";
    }

    @GetMapping("/attractions/add")
    public String attractions3(Model model) {
        model.addAttribute("cities", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        return "forms";
    }

    @PostMapping("/attractions/save")
    public String attractions4() {
        return null;
    }

    @GetMapping("/attractions/{name}/edit")
    public String attractions5() {
        return null;
    }

    @PostMapping("/attractions/update")
    public String attractions6() {
        return null;
    }

    @PostMapping("/attractions/delete/{name}")
    public String attractions7() {
        return null;
    }
}