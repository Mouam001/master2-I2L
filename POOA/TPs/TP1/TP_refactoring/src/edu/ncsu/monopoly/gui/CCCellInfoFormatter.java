package edu.ncsu.monopoly.gui;

import edu.ncsu.monopoly.Cell;

public class CCCellInfoFormatter implements CellInfoFormatter {
    public String format(Cell Cell) {
        return "<html><font color='white'><b>" + Cell.getName() + "</b></font></html>";
    }
}
