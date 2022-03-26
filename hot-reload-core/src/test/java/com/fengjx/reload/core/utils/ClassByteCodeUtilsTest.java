package com.fengjx.reload.core.utils;

import com.fengjx.reload.core.util.ClassByteCodeUtils;
import org.junit.Test;

public class ClassByteCodeUtilsTest {

    @Test
    public void testGetClassNameFromSourceCode() {
        String fileContent = "package com.fengjx.reload.core.utils;\n" +
                "\n" +
                "import com.fengjx.reload.core.util.ClassByteCodeUtils;\n" +
                "import org.junit.Test;\n" +
                "\n" +
                "public class ClassByteCodeUtilsTest {\n" +
                "\n" +
                "    @Test\n" +
                "    public void testGetClassNameFromSourceCode() {\n" +
                "        String fileContent = \"package com.github.liuzhengyang.hotreload.bytecode.util;\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"import java.util.Optional;\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"import com.github.javaparser.StaticJavaParser;\\n\" +\n" +
                "                \"import com.github.javaparser.ast.CompilationUnit;\\n\" +\n" +
                "                \"import com.github.javaparser.ast.PackageDeclaration;\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"/**\\n\" +\n" +
                "                \" * @author liuzhengyang\\n\" +\n" +
                "                \" * Make something people want.\\n\" +\n" +
                "                \" * 2020/4/21\\n\" +\n" +
                "                \" */\\n\" +\n" +
                "                \"public class ClassByteCodeUtils {\\n\" +\n" +
                "                \"    public static String getClassNameFromByteCode(byte[] bytes) {\\n\" +\n" +
                "                \"        return null;\\n\" +\n" +
                "                \"    }\\n\" +\n" +
                "                \"\\n\" +\n" +
                "                \"    public static String getClassNameFromSourceCode(String sourceCode) {\\n\" +\n" +
                "                \"        CompilationUnit compilationUnit = StaticJavaParser.parse(sourceCode);\\n\" +\n" +
                "                \"        String className = compilationUnit.getTypes().get(0).getNameAsString();\\n\" +\n" +
                "                \"        Optional<PackageDeclaration> packageDeclaration = compilationUnit.getPackageDeclaration();\\n\" +\n" +
                "                \"        boolean packagePresent = packageDeclaration.isPresent();\\n\" +\n" +
                "                \"        if (packagePresent) {\\n\" +\n" +
                "                \"            return packageDeclaration.get().getNameAsString() + \\\".\\\" + className;\\n\" +\n" +
                "                \"        }\\n\" +\n" +
                "                \"        return className;\\n\" +\n" +
                "                \"    }\\n\" +\n" +
                "                \"}\\n\";\n" +
                "        System.out.println(ClassByteCodeUtils.getClassNameFromSourceCode(fileContent));\n" +
                "    }\n" +
                "\n" +
                "}";
        System.out.println(ClassByteCodeUtils.getClassNameFromSourceCode(fileContent));
    }

}
