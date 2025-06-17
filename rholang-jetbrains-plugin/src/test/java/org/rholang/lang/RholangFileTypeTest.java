package org.rholang.lang;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RholangFileTypeTest {

    @Test
    public void testFileTypeProperties() {
        RholangFileType fileType = RholangFileType.INSTANCE;
        
        // Test basic properties
        assertEquals("Rholang", fileType.getName());
        assertEquals("Rholang language file", fileType.getDescription());
        assertEquals("rho", fileType.getDefaultExtension());
        assertNotNull(fileType.getIcon());
        
        // Test language association
        assertEquals(RholangLanguage.INSTANCE, fileType.getLanguage());
    }
}