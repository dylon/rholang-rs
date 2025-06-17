package org.rholang.lang;

import com.intellij.lang.Language;

public class RholangLanguage extends Language {
    public static final RholangLanguage INSTANCE = new RholangLanguage();

    private RholangLanguage() {
        super("Rholang");
    }
}