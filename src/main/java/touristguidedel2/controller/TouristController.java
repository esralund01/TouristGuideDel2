package touristguidedel2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import touristguidedel2.model.TouristAttraction;
import touristguidedel2.service.TouristService;

@Controller
public class TouristController {

    private final TouristService touristService;
    private boolean wrongName;

    public TouristController() {
        touristService = new TouristService();
        wrongName = false;
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
    public String attractionAdd(Model model) {
        model.addAttribute("cities", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        model.addAttribute("touristAttraction", new TouristAttraction());
        model.addAttribute("wrongName", wrongName);
        wrongName = false;
        return "forms";
    }

    @PostMapping("/attractions/save")
    public String attractionSave(@ModelAttribute TouristAttraction touristAttraction) {
        try {
            touristService.addTouristAttraction(touristAttraction);
            return "redirect:/attractions";
        } catch (IllegalStateException ise) {
            wrongName = true;
            return "redirect:/attractions/add";
        }

    }

    @GetMapping("/attractions/{name}/edit")
    public String attractionEdit(@PathVariable String name, Model model) {
        model.addAttribute("cities", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        model.addAttribute("touristAttraction", touristService.getAttractionByName(name));
        return "updateAttraction";
    }

    @PostMapping("/attractions/update")
    public String attractionUpdate(@ModelAttribute TouristAttraction touristAttraction) {
        touristService.updateTouristAttraction(touristAttraction);
        return "redirect:/attractions";
    }

    @PostMapping("/attractions/{name}/delete")
    public String attractionDelete(@PathVariable String name) {
        touristService.deleteAttraction(name);
        return "redirect:/attractions";
    }
}