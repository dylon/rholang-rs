package org.rholang.lang;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class RholangFileType extends LanguageFileType {
    public static final RholangFileType INSTANCE = new RholangFileType();

    private RholangFileType() {
        super(RholangLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Rholang";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Rholang language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "rho";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return RholangIcons.FILE;
    }
}