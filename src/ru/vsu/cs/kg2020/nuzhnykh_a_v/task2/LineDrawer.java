package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2;

import java.awt.*;

public interface LineDrawer {
    /**
     * Рисует пряму линию
     * @param x1 горизонтальная координата первой точки (исходной)
     * @param y1 вертикальная координата первой точки (исходной)
     * @param x2 горизонтальная координата второй точки (конечной)
     * @param y2 вертикальная координата второй точки (конечной)
     * @param color цвет линии
     */
    void drawLine(int x1, int y1, int x2, int y2, Color color);
}
