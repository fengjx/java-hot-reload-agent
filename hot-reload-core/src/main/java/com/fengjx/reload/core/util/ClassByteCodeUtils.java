package com.fengjx.reload.core.util;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.PackageDeclaration;
import org.objectweb.asm.ClassReader;

import java.util.Optional;

/*-
 * #%L
 * MIT License
 * %%
 * Copyright (c) 2019 刘正阳
 * %%
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * %%
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * %%
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * #%L
 */
public class ClassByteCodeUtils {

    public static String getClassNameFromByteCode(byte[] bytes) {
        ClassReader classReader = new ClassReader(bytes);
        String className = classReader.getClassName();
        return className.replaceAll("/", ".");
    }

    public static String getClassNameFromSourceCode(String sourceCode) {
        CompilationUnit compilationUnit = StaticJavaParser.parse(sourceCode);
        String className = compilationUnit.getTypes().get(0).getNameAsString();
        Optional<PackageDeclaration> packageDeclaration = compilationUnit.getPackageDeclaration();
        boolean packagePresent = packageDeclaration.isPresent();
        if (packagePresent) {
            return packageDeclaration.get().getNameAsString() + "." + className;
        }
        return className;
    }
}
