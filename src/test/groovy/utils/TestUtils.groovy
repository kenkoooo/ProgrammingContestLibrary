package utils

class TestUtils {
    static ArrayDeque<String> parseResourceInput(Class clazz, String filename) {
        InputStream stream = clazz.getClassLoader().getResourceAsStream(filename)
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream))

        String line
        ArrayDeque<String> inputStringDeque = new ArrayDeque<>()
        while ((line = reader.readLine()) != null) for (String s : line.split(" ")) inputStringDeque.add(s)
        return inputStringDeque
    }
}
