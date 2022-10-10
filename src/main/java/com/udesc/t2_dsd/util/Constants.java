package com.udesc.t2_dsd.util;

import com.udesc.t2_dsd.model.ICell;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author guilu
 */
public class Constants {
    public static int ROWS;
    public static int COLUMNS;
    public static List<ICell> normalCells = Arrays.asList(ICell.ROAD_DOWN, ICell.ROAD_LEFT, ICell.ROAD_RIGHT, ICell.ROAD_UP);
}