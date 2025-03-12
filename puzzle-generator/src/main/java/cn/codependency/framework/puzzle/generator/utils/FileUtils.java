package cn.codependency.framework.puzzle.generator.utils;

import cn.hutool.core.io.IoUtil;
import com.google.common.collect.Lists;
import cn.codependency.framework.puzzle.common.json.JsonUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

@Slf4j
public class FileUtils {

    public static void writeFile(String content, String file) {
        writeFile(content, file, true);
    }

    public static void writeFile(String content, String file, boolean overwrite) {
        if (!overwrite) {
            if (new File(file).exists()) {
                return;
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.error("写入文件失败", e);
            IoUtil.close(fileOutputStream);
        }
    }

    @SneakyThrows
    public static <T> List<T> loadPath(String viewDir, String fileExt, Class<T> clz) {
        List<T> functions = Lists.newArrayList();
        URL resource = FileUtils.class.getResource(viewDir);
        if (Objects.isNull(resource)) {
            log.warn("no resource dir on path: " + viewDir);
            return functions;
        }

        log.info("load resource file: " + resource.getFile());
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            File[] files = file.listFiles(f -> f.getName().endsWith(fileExt));
            for (File f : files) {
                String scriptFile = viewDir + File.separator + f.getName();
                T funcObj = load(scriptFile, clz);
                functions.add(funcObj);
            }
            File[] dirs = file.listFiles(f -> f.isDirectory());
            for (File dir : dirs) {
                List<T> load = loadPath(viewDir + "/" + dir.getName(), fileExt, clz);
                functions.addAll(load);
            }
        } else {
            String resourcePath = file.getPath().replace("/BOOT-INF/classes!", "/BOOT-INF/classes");
            boolean contains = resourcePath.contains("!");
            if (contains) {
                String[] split = resourcePath.split("!");
                String jarPath = split[0];
                JarFile jarFile = new JarFile(jarPath.replace("file:", ""));
                JarInputStream jarInputStream = null;
                JarEntry jarEntry = null;

                outer:
                for (int i = 1; i < split.length - 1; i++) {
                    String name = split[i].substring(1);
                    if (i == 1) {
                        Enumeration<JarEntry> entries = jarFile.entries();
                        while (entries.hasMoreElements()) {
                            jarEntry = entries.nextElement();
                            if (jarEntry.isDirectory()) {
                                List<T> load = loadPath(viewDir + "/" + jarEntry.getName(), fileExt, clz);
                                functions.addAll(load);
                            } else if (jarEntry.getName().equals(name)) {
                                jarInputStream = getJarEntryInputStream(jarFile, jarEntry);
                                continue outer;
                            }
                        }
                    } else {
                        while (Objects.nonNull(jarEntry = jarInputStream.getNextJarEntry())) {
                            if (jarEntry.getName().equals(name)) {
                                jarInputStream = getJarEntryInputStream(jarInputStream);
                            }
                        }
                    }
                }

                String lastDir = split[split.length - 1].substring(1);
                if (Objects.isNull(jarInputStream)) {
                    Enumeration<JarEntry> entries = jarFile.entries();
                    while (entries.hasMoreElements()) {
                        jarEntry = entries.nextElement();
                        if (jarEntry.isDirectory()) {
                            List<T> load = loadPath(viewDir + "/" + jarEntry.getName(), fileExt, clz);
                            functions.addAll(load);
                        } else if (jarEntry.getName().startsWith(lastDir) && jarEntry.getName().endsWith(fileExt)) {
                            InputStream inputStream = jarFile.getInputStream(jarEntry);
                            String script = getText(inputStream, "UTF-8");
                            T funcObj = JsonUtils.toObject(script, clz);
                            functions.add(funcObj);
                        }
                    }
                } else {
                    while (Objects.nonNull(jarEntry = jarInputStream.getNextJarEntry())) {
                        if (jarEntry.getName().startsWith(lastDir) && jarEntry.getName().endsWith(fileExt)) {
                            String script = getText(jarInputStream);
                            T funcObj = JsonUtils.toObject(script, clz);
                            functions.add(funcObj);
                        }
                    }
                }
            }
        }
        return functions;
    }

    @SneakyThrows
    public static <T> T load(String scriptFile, Class<T> clz) {
        InputStream resourceAsStream = FileUtils.class.getResourceAsStream(scriptFile);
        String script = getText(resourceAsStream, "UTF-8");
        return JsonUtils.toObject(script, clz);
    }

    public static JarInputStream getJarEntryInputStream(JarFile jarFile, JarEntry jarEntry) throws IOException {
        JarInputStream jarInputStream = new JarInputStream(jarFile.getInputStream(jarEntry));
        return jarInputStream;
    }

    public static JarInputStream getJarEntryInputStream(InputStream inputStream) throws IOException {
        JarInputStream jarInputStream = new JarInputStream(inputStream);
        return jarInputStream;
    }

    public static String getText(JarInputStream jarInputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(jarInputStream));
        return getText(reader);
    }


    public static String getText(BufferedReader reader) throws IOException {
        StringBuilder answer = new StringBuilder();
        char[] charBuffer = new char[8192];
        int nbCharRead = 0;
        while ((nbCharRead = reader.read(charBuffer)) != -1) {
            // appends buffer
            answer.append(charBuffer, 0, nbCharRead);
        }
        return answer.toString();
    }

    public static String getText(InputStream is, String charset) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, charset));
        return getText(reader);
    }
}
