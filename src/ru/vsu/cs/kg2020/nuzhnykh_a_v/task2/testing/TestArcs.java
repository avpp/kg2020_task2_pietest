package ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.testing;

import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.*;
import ru.vsu.cs.kg2020.nuzhnykh_a_v.task2.graphics_impl.PrimitivesFactoryWithDefaultGraphicsImplementation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class TestArcs {
    public static class FullTestConfig {
        public int r_hor, r_vert, from_start, to_start, start_step, from_shift, to_shift, shift_step;

        public FullTestConfig(int r_hor, int r_vert, int from_start, int to_start, int start_step, int from_shift, int to_shift, int shift_step) {
            this.r_hor = r_hor;
            this.r_vert = r_vert;
            this.from_start = from_start;
            this.to_start = to_start;
            this.start_step = start_step;
            this.from_shift = from_shift;
            this.to_shift = to_shift;
            this.shift_step = shift_step;
        }
        public FullTestConfig(int... data) throws Exception {
            if (data == null || data.length < 8)
                throw new Exception("Недостаточно параметров!!!");
            r_hor = data[0];
            r_vert = data[1];
            from_start = data[2];
            to_start = data[3];
            start_step = data[4];
            from_shift = data[5];
            to_shift = data[6];
            shift_step = data[7];
        }
    }
    private static BufferedImage fullTest(PrimitivesFactoryWithDefaultGraphicsImplementation factory, boolean withMarkers,FullTestConfig cfg) {
        return fullTest(factory, withMarkers, cfg.r_hor, cfg.r_vert, cfg.from_start, cfg.to_start, cfg.start_step, cfg.from_shift, cfg.to_shift, cfg.shift_step);
    }

    private static final Font font = new Font("Arial", Font.PLAIN, 14);

    private static BufferedImage fullTest(PrimitivesFactoryWithDefaultGraphicsImplementation factory, boolean withMarkers, int r_hor, int r_vert, int from_start, int to_start, int start_step, int from_shift, int to_shift, int shift_step) {
        int LEN_START = to_start - from_start;
        int LEN_STOP = to_shift - from_shift;
        int W = r_hor, H = r_vert, L = r_hor*2, T = r_vert*2;
        int VERT_SPACE = 5, HOR_SPACE = 5;
        int STEP_START = start_step, STEP_STOP = shift_step;
        int START_STEP_N = LEN_START / STEP_START;
        int STOP_STEP_N = LEN_STOP / STEP_STOP;
        int FULL_W = START_STEP_N*(W*2+HOR_SPACE) + L*2, FULL_H = STOP_STEP_N*(H*2+VERT_SPACE) + T*2;

        int TOTAL_W = W*2+HOR_SPACE + font.getSize()*2, TOTAL_H = H*2+VERT_SPACE + font.getSize()*2;

        BufferedImage bi = new BufferedImage(FULL_W, FULL_H, BufferedImage.TYPE_INT_RGB);
        final Graphics2D gr2d = bi.createGraphics();
        gr2d.setColor(Color.WHITE);
        gr2d.fillRect(0, 0, FULL_W, FULL_H);
        gr2d.setColor(Color.BLACK);
        //LineDrawer ld = new GraphicsLineDrawer(gr2);// new BresenhamLineDrawer(pd2);
        //ArcDrawer ad = new GraphicsArcDrawer(gr2);// new BresenhamPieDrawer(pd2, ld);

        GraphicsProvider gpInstance = new GraphicsProvider() {
            @Override
            public Graphics2D getGraphics() {
                return gr2d;
            }
        };
        PieDrawer pieDrawer = factory.createPieDrawer(gpInstance);
        LineDrawer ld = factory.createLineDrawer(gpInstance);

        gr2d.setFont(font);
        for (int i = 0; i < START_STEP_N; i++) {
            for (int j = 0; j < STOP_STEP_N; j++) {
                int a1 = from_start + i*STEP_START;
                int da = from_shift + j*STEP_STOP;
                int a2 = a1 + da;
                int x = L + i*TOTAL_W;
                int y = T + j*TOTAL_H;
                int left = x-TOTAL_W/2, top = y-TOTAL_H/2 - font.getSize()/2;
                gr2d.drawString(String.format("%d + %d = %d", a1, da, a2), left+3, top+3+font.getSize());
                gr2d.setColor(Color.gray);
                gr2d.drawLine(left, top, left+TOTAL_W/2, top);
                gr2d.drawLine(left, top, left, top+TOTAL_H/2);
                if (withMarkers) {
                    gr2d.setColor(Color.blue);
                    gr2d.drawLine(x, y - H / 2 - 7, x, y - H / 2 - 4);
                    gr2d.drawLine(x, y + H / 2 + 7, x, y + H / 2 + 4);
                    gr2d.drawLine(x - W / 2 - 7, y, x - W / 2 - 4, y);
                    gr2d.drawLine(x + W / 2 + 7, y, x + W / 2 + 4, y);
                    gr2d.drawLine(x - W / 2, y - H / 2, x - W / 2 + 3, y - H / 2); //TL
                    gr2d.drawLine(x - W / 2, y - H / 2, x - W / 2, y - H / 2 + 3); //TL
                    gr2d.drawLine(x + W / 2, y - H / 2, x + W / 2 - 3, y - H / 2); //TR
                    gr2d.drawLine(x + W / 2, y - H / 2, x + W / 2, y - H / 2 + 3); //TR
                    gr2d.drawLine(x - W / 2, y + H / 2, x - W / 2 + 3, y + H / 2); //BL
                    gr2d.drawLine(x - W / 2, y + H / 2, x - W / 2, y + H / 2 - 3); //BL
                    gr2d.drawLine(x + W / 2, y + H / 2, x + W / 2 - 3, y + H / 2); //BR
                    gr2d.drawLine(x + W / 2, y + H / 2, x + W / 2, y + H / 2 - 3); //BR
                }
                gr2d.setColor(Color.black);
                pieDrawer.drawPie(x-W/2, y-H/2, W, H, a1, da, Color.black);
            }
        }
        gr2d.dispose();
        return bi;
    }

    public static void startSimpleTest(String path, PrimitivesFactoryWithDefaultGraphicsImplementation factory, boolean withMarkers, int[] args) throws Exception {
        BufferedImage bi_your = fullTest(factory, withMarkers, new FullTestConfig(args));
        ImageIO.write(bi_your, "bmp", new File(path + "_your.bmp"));
        BufferedImage bi_ideal = fullTest(new PrimitivesFactoryWithDefaultGraphicsImplementation(), withMarkers, new FullTestConfig(args));
        ImageIO.write(bi_ideal, "bmp", new File(path + "_ideal.bmp"));
        BufferedImage bi_diff = diff(bi_ideal, bi_your, 0, 0);
        ImageIO.write(bi_diff, "bmp", new File(path + "_diff.bmp"));
    }

    private static BufferedImage diff(BufferedImage ideal, BufferedImage test, int dx, int dy) {
        int dxa = dx >= 0 ? dx : 0;
        int dya = dy >= 0 ? dy : 0;
        int dxb = dx <= 0 ? -dx : 0;
        int dyb = dy <= 0 ? -dy : 0;

        int w = Math.min(ideal.getWidth()-dxa, test.getWidth()-dxb);
        int h = Math.min(ideal.getHeight()-dya, test.getHeight()-dyb);

        BufferedImage result = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

        for (int xx = 0; xx < w; xx++)
            for (int yy = 0; yy < h; yy++) {
                int a = ideal.getRGB(xx, yy);
                int b = test.getRGB(xx, yy);
                Color ca = new Color(a);
                Color cb = new Color(b);
                if (a == b) {
                    result.setRGB(xx, yy, ca.brighter().getRGB());
                } else {

                    float[] tmp = new float[3];
                    float la = ca.getRGB() + ca.getGreen() + ca.getBlue();
                    float lb = cb.getRed() + cb.getGreen() + cb.getBlue();
                    if (la < lb) {
                        //В идеальном пиксель чёрный, а в проверяемом -- белый. Значит нехватает пикселя.
                        result.setRGB(xx, yy, Color.green.getRGB());
                    } else {
                        result.setRGB(xx, yy, Color.red.getRGB());
                    }
                }
            }

        return result;
    }

    public static void startTest(PrimitivesFactoryWithDefaultGraphicsImplementation factory, boolean withMarkers, String basePath) throws Exception {
        Map<String, int[]> testCases = new HashMap<>();
        testCases.put("main", new int[]{50, 50, 0, 360, 10, 0, 360, 10});
        testCases.put("small", new int[]{50, 50, 0, 360, 10, 0, 5, 1});
        testCases.put("big", new int[]{50, 50, 0, 360, 10, 355, 360, 1});
        testCases.put("sq", new int[]{50, 50, 0, 360, 90, 0, 360, 90});
        testCases.put("ellipse_ver_main", new int[]{30, 70, 0, 360, 10, 0, 360, 10});
        testCases.put("ellipse_hor_main", new int[]{70, 30, 0, 360, 10, 0, 360, 10});

        for (var tc : testCases.entrySet())
            startSimpleTest(basePath + "/" + tc.getKey(), factory, withMarkers, tc.getValue());
    }
}
