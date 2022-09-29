public class World {

  private final Cell[][] cells;
  private final int rows;
  private final int cols;

  private World(int rows, int cols) {
    this.cells = new Cell[rows][cols];
    this.rows = rows;
    this.cols = cols;
  }

  public static World from(String str) {
    var lines = str.split("\n");
    for (var l = 0; l < lines.length; l++) {
      lines[l] = lines[l].trim();
    }

    var rows = Integer.parseInt(lines[0]);
    var cols = Integer.parseInt(lines[1]);

    var world = new World(rows, cols);

    for (var k = 2; k < lines.length; k++) {
      var cells = lines[k].split("\t");
      var i = k - 2;
      for (var j = 0; j < cols; j++) {
        var id = Integer.parseInt(cells[j]);
        var cell = Cell.from(id).orElseThrow(() -> new RuntimeException("Invalid cell id" + id));
        world.cells[i][j] = cell;
      }
    }

    return world;
  }

  public String toString() {
    var builder = new StringBuilder();
    for (var i = 0; i < rows; i++) {
      for (var j = 0; j < cols; j++) {
        builder.append(cells[i][j].toString());
        if (j < cols-1) {
          builder.append('\t');
        }
      }
      if (i < rows-1) {
        builder.append('\n');
      }
    }
    return builder.toString();
  }
}
