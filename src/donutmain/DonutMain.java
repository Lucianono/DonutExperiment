
package donutmain;

import java.util.Arrays;

public class DonutMain {

    static double pi = Math.PI;
    String luminance_arr = ".,-~:;=!*#$@";
    int testctr = 0;

    double theta_spacing = .1;
    double phi_spacing = .05;

    double r1 = 2;
    double r2 = 3;
    double k2 = 5;
    double k1 = (screen_height * k2 * 3 / (8 * (r1 + r2)));

    static int screen_height = 50;
    static int screen_width = 100;

    public void render_frame(double A, double B, double C) {
        double cosA = Math.cos(A), sinA = Math.sin(A);
        double cosB = Math.cos(B), sinB = Math.sin(B);
        double cosC = Math.cos(C), sinC = Math.sin(C);

        char[][] output = new char[screen_width][screen_height];
        for (char[] row : output) {
            Arrays.fill(row, ' ');
        }

        double[][] zbuffer = new double[screen_width][screen_height];
        for (double[] row : zbuffer) {
            Arrays.fill(row, 0.0);
        }

        for (double theta = 0; theta < 2 * 2.5; theta += theta_spacing) {
            double costheta = Math.cos(theta), sintheta = Math.sin(theta);
            // System.out.println(testctr++);

            for (double phi = 0; phi < 2 * 2; phi += phi_spacing) {
                double cosphi = Math.cos(phi), sinphi = Math.sin(phi);

                double circlex = r2 + r1 * costheta;
                double circley = r1 * sintheta;

                double x = circlex * (cosB * cosphi + sinA * sinB * sinphi) - circley * cosA * sinB;
                double y = circlex * (sinB * cosphi - sinA * cosB * sinphi) + circley * cosA * cosB;
                double z = k2 + cosA * circlex * sinphi + circley * sinA;
                double ooz = 1 / z;

                int xp = (int) (screen_width / 2 + k1 * ooz * x);
                int yp = (int) (screen_height / 2 - k1 * ooz * y);

                
                double L = cosphi * costheta * sinB - cosA * costheta * sinphi - sinA * sintheta + cosB * (cosA * sintheta - costheta * sinA * sinphi);
                //double L = costheta * (-cosphi*sinB + sinA*cosB*sinphi - cosA*sinphi)+ sintheta * (-cosA*cosB - sinA);
                //double L = -sinC*(costheta*(cosB*cosphi + sinA*sinB*sinphi)-sintheta*cosA*sinB) + cosC*(costheta*(cosphi*sinB-sinA*cosB*sinphi)+sintheta*cosA*cosB) - (costheta*cosA*sinphi+sintheta*sinA);
                //double L = 0*(costheta*(cosB*cosphi + sinA*sinB*sinphi)-sintheta*cosA*sinB) + 0*(costheta*(cosphi*sinB-sinA*cosB*sinphi)+sintheta*cosA*cosB) - 1*(costheta*cosA*sinphi+sintheta*sinA);

                if ( outOfBounds(xp, yp)) {

                   L = Math.abs(L); 
                    if (ooz > zbuffer[xp][yp]) {
                        zbuffer[xp][yp] = ooz;
                        int luminance_index = (int) (L * 8);

                        output[xp][yp] = luminance_arr.charAt(luminance_index);

                    }
                }

            }

        }

        for (int j = 0; j < screen_height; j++) {
            for (int i = 0; i < screen_width; i++) {
                System.out.print(output[i][j]);
            }
            System.out.println("");
        }
    }

    public static void ClearConsole() {
        try {
            String operatingSystem = System.getProperty("os.name"); // Check the current operating system

            if (operatingSystem.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();

                startProcess.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean outOfBounds(int xp, int yp) {
        return xp < screen_width && yp < screen_height && xp >= 0 && yp >= 0;
    }

    public static void main(String[] args) throws InterruptedException {
        DonutMain dd = new DonutMain();
        double a = 0, b = 0, c=0;
        for (int i = 0; i >= 0; i++) {

            ClearConsole();
            dd.render_frame(a, b, c);
            a += 0.07;
            b += 0.07;
            c += 0.05;
            
            Thread.sleep(30);
        }

        System.out.println("Hello World");
    }

}
