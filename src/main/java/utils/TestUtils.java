package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class TestUtils {

  public static ArrayDeque<String> parseResourceInput(Class clazz, String filename)
      throws IOException {
    InputStream stream = clazz.getClassLoader().getResourceAsStream(filename);
    BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

    String line;
    ArrayDeque<String> inputStringDeque = new ArrayDeque<>();
    while ((line = reader.readLine()) != null) {
      inputStringDeque.addAll(Arrays.asList(line.split(" ")));
    }
    return inputStringDeque;
  }

  /**
   * load and parse input or output files in the given resource directory
   *
   * @param directory {@link String} directory of loading resource files
   * @return parsed {@link String} list
   */
  public static ArrayDeque<String> loadResourceFiles(String directory, Class clazz)
      throws URISyntaxException, IOException {
    URL url = clazz.getClassLoader().getResource(directory);
    if (url == null) {
      throw new IllegalArgumentException(directory + " is null");
    }
    return Files.walk(Paths.get(url.toURI()), 1)
        .filter(path -> path.toFile().isFile())
        .map(path -> {
          try {
            return Files.readAllLines(path);
          } catch (IOException e) {
            return null;
          }
        })
        .filter(Objects::nonNull)
        .flatMap(Collection::stream)
        .map(line -> line.split(" "))
        .flatMap(Arrays::stream)
        .collect(Collectors.toCollection(ArrayDeque::new));
  }
}
