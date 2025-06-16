package org.rholang.lang.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.rholang.lang.RholangFileType;
import org.rholang.lang.RholangLanguage;

public class RholangFile extends PsiFileBase {
    public RholangFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RholangLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return RholangFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Rholang File";
    }
}