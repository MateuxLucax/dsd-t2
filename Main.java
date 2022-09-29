import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
  public static void main(String[] args) throws IOException {

    for (var m = 1; m <= 3; m++) {
      var path = String.format("./malhas/malha-exemplo-%d.txt", m);
      var content = Files.readString(Path.of(path));
      var world = World.from(content);
      System.out.println(world.toString() + '\n');
    }

  }
}
