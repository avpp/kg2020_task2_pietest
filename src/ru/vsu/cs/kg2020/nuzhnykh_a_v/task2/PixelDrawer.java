package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2;

import java.awt.*;

public interface PixelDrawer {
    /**
     * Рисует один пиксель
     * @param x горизонтальная координата пикселя
     * @param y вертикальная координата пикселя
     * @param c цвет пискселя
     */
    void setPixel(int x, int y, Color c);
}
