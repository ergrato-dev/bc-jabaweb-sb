package com.bootcamp.finalproject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Test de contexto de la aplicación.
 */
@SpringBootTest
@ActiveProfiles("test")
class FinalProjectApplicationTests {

    @Test
    void contextLoads() {
        // Verifica que el contexto de Spring se carga correctamente
    }
}
