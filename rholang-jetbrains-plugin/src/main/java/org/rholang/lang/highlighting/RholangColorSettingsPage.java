package org.rholang.lang.highlighting;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rholang.lang.RholangIcons;

import javax.swing.*;
import java.util.Map;

public class RholangColorSettingsPage implements ColorSettingsPage {
    private static final AttributesDescriptor[] DESCRIPTORS = new AttributesDescriptor[]{
            new AttributesDescriptor("Keyword", RholangSyntaxHighlighter.KEYWORD),
            new AttributesDescriptor("Comment", RholangSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("String", RholangSyntaxHighlighter.STRING),
            new AttributesDescriptor("Number", RholangSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("Operator", RholangSyntaxHighlighter.OPERATOR),
            new AttributesDescriptor("Parentheses", RholangSyntaxHighlighter.PARENTHESES),
            new AttributesDescriptor("Braces", RholangSyntaxHighlighter.BRACES),
            new AttributesDescriptor("Brackets", RholangSyntaxHighlighter.BRACKETS),
            new AttributesDescriptor("Identifier", RholangSyntaxHighlighter.IDENTIFIER),
            new AttributesDescriptor("Bad character", RholangSyntaxHighlighter.BAD_CHARACTER)
    };

    @Nullable
    @Override
    public Icon getIcon() {
        return RholangIcons.FILE;
    }

    @NotNull
    @Override
    public SyntaxHighlighter getHighlighter() {
        return new RholangSyntaxHighlighter();
    }

    @NotNull
    @Override
    public String getDemoText() {
        return "// This is a sample Rholang program\n" +
               "/* Block comment */\n" +
               "new stdout(`rho:io:stdout`) in {\n" +
               "  stdout!(\"Hello, World!\")\n" +
               "}\n\n" +
               "// Contract definition\n" +
               "contract add(@x, @y, return) = {\n" +
               "  return!(x + y)\n" +
               "}\n\n" +
               "// Pattern matching\n" +
               "match x {\n" +
               "  42 => { \"The answer\" }\n" +
               "  y => { \"Not the answer\" }\n" +
               "}\n\n" +
               "// Arithmetic operations\n" +
               "let result = 10 * (5 + 3) / 2 - 1 in {\n" +
               "  stdout!(result)\n" +
               "}\n";
    }

    @Nullable
    @Override
    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

    @NotNull
    @Override
    public AttributesDescriptor[] getAttributeDescriptors() {
        return DESCRIPTORS;
    }

    @NotNull
    @Override
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Rholang";
    }
}