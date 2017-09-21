package utils

import java.nio.file.Files
import java.nio.file.Path
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

        List<Path> paths = Files.list(Paths.get(url.toURI())).collect().sort()
        return paths.stream()
                .map { path -> Files.readAllLines(path) }
                .filter { x -> x != null }
                .flatMap { x -> x.stream() }
                .map { line -> line.split(" ") }
                .flatMap { array -> Arrays.stream(array) }
                .collect()
    }
}

class TestCaseCounter {
    Deque<String> inputDeque
    int caseCount = -1

    TestCaseCounter(Deque<String> inputDeque) {
        this.inputDeque = inputDeque
    }

    boolean hasNext() {
        if (caseCount >= 0) {
            println("Test\t${caseCount}:\t[ ACCEPTED ]")
        }
        if (inputDeque.isEmpty()) {
            return false
        }
        caseCount++
        return true
    }
}
