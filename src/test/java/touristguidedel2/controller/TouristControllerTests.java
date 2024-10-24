package touristguidedel2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import touristguidedel2.model.TouristAttraction;
import touristguidedel2.service.TouristService;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TouristController.class)
class TouristControllerTests {

    private TouristAttraction touristAttraction = new TouristAttraction();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TouristService touristService;

    @Test
    void attractionList() throws Exception {
        mockMvc.perform(get("/attractions"))
                .andExpect(status().isOk())
                .andExpect(view().name("attractionList"));
    }
    @Test
    void attractionTags() throws Exception {
        mockMvc.perform(get("/attractions/Test/tags"))
                .andExpect(status().isOk())
                .andExpect(view().name("tags"))
                .andExpect(content().string(containsString("Test tags")));
    }
    @Test
    void attractionAdd() throws Exception {
        mockMvc.perform(get("/attractions/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("touristAttraction", touristAttraction))
                .andExpect(view().name("forms"));
    }
    @Test
    void attractionSave() throws Exception {
        mockMvc.perform(post("/attractions/save").sessionAttr("touristAttraction", touristAttraction))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/attractions/add?error=no_name"));
    }
}