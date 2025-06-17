package org.rholang.lang;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RholangLanguageTest {

    @Test
    public void testLanguageProperties() {
        RholangLanguage language = RholangLanguage.INSTANCE;
        
        // Test basic properties
        assertEquals("Rholang", language.getID());
        assertEquals("Rholang", language.getDisplayName());
        
        // Test singleton pattern
        assertSame(RholangLanguage.INSTANCE, language);
    }
}