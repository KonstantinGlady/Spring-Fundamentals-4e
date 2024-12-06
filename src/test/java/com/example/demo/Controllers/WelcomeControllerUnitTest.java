package com.example.demo.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;

import static org.junit.jupiter.api.Assertions.*;

class WelcomeControllerUnitTest {

    @Test
    void helloWorld() {
        WelcomeController controller = new WelcomeController();
        Model model = new BindingAwareModelMap();
        String result = controller.helloWorld("Dolly", model);
        assertAll(() ->
                {
                    assertEquals("welcome", result);
                    assertEquals("Dolly", model.getAttribute("user"));
                }
        );
    }
}