package org.rholang.lang.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class RholangPsiElement extends ASTWrapperPsiElement {
    public RholangPsiElement(@NotNull ASTNode node) {
        super(node);
    }
}