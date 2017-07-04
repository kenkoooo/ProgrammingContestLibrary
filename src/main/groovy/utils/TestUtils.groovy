package utils

import java.nio.file.Files
import java.nio.file.Paths

class TestUtils {
    static ArrayDeque<String> parseResourceInput(Class clazz, String filename) {
        return Files.readAllLines(Paths.get(clazz.getClassLoader().getResource(filename).toURI()))
                .stream()
                .map { line -> line.split(" ") }
                .flatMap { array -> Arrays.stream(array) }
                .collect()
    }

    /**
     * load and parse input or output files in the given resource directory
     *
     * @param directory {@link String} directory of loading resource files
     * @return parsed {@link String} list
     */
    static ArrayDeque<String> loadResourceFiles(String directory, Class clazz)
            throws URISyntaxException, IOException {
        URL url = clazz.getClassLoader().getResource(directory)
        if (url == null) {
            throw new IllegalArgumentException(directory + " is null")
        }
        return Files.walk(Paths.get(url.toURI()), 1)
                .filter { path -> path.toFile().isFile() }
                .map { path -> Files.readAllLines(path) }
                .filter { x -> x != null }
                .flatMap { x -> x.stream() }
                .map { line -> line.split(" ") }
                .flatMap { array -> Arrays.stream(array) }
                .collect()
    }
}
