package touristguidedel2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import touristguidedel2.model.TouristAttraction;
import touristguidedel2.service.TouristService;

@Controller
public class TouristController {

    private final TouristService touristService;

    public TouristController(TouristService touristService) {
        this.touristService = touristService;
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
    public String attractionAdd(@RequestParam(defaultValue = "") String error, Model model) {
        model.addAttribute("cities", touristService.getCities());
        model.addAttribute("tags", touristService.getTags());
        model.addAttribute("touristAttraction", new TouristAttraction());
        model.addAttribute("no_name", error.equals("no_name"));
        model.addAttribute("name_taken", error.equals("name_taken"));
        return "forms";
    }

    @PostMapping("/attractions/save")
    public String attractionSave(@ModelAttribute TouristAttraction touristAttraction) {
        String name = touristAttraction.getName();
        if (name.isBlank()) {
            return "redirect:/attractions/add?error=no_name";
        }
        if (touristService.touristAttractionExists(name)) {
            return "redirect:/attractions/add?error=name_taken";
        }
        touristService.addTouristAttraction(touristAttraction);
        return "redirect:/attractions";
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