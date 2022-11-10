import java.io.PrintWriter;
import java.io.File;
import java.io.FileNotFoundException;

public class Reset {
    public static void main(String[] args) {
        for (int number = 0; number <= 10; number++) {
            File f = null;
            PrintWriter pw = null;
            try {
                f = new File("/Users/david1/IdeaProjects/JourneyOfSamsara/src/gamestates" + number + ".txt");
                pw = new PrintWriter(f);
            } catch (FileNotFoundException e) {
                System.err.println("Could not find gamestates" + number + ".txt");
                System.exit(1);
            }

            for (int d = 1; d <= 6; d++) {
                pw.println(0 + " " + 0 + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
            }

            for (int j = 1; j < 31; j++) {
                for (int d = 1; d <= 6; d++) {
                    pw.println(0 + " " + j + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                }
                for (int d = 1; d <= 6; d++) {
                    pw.println(0 + " " + -j + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                }
            }

            for (int i = 1; i < 31; i++) {
                for (int d = 1; d <= 6; d++) {
                    pw.println(i + " " + 0 + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                }
                for (int d = 1; d <= 6; d++) {
                    pw.println(-i + " " + 0 + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                }
            }

            for (int i = 1; i < 31; i++) {
                for (int j = 1; j < 31; j++) {
                    if (i % 10 == j % 10)
                        continue;
                    for (int d = 1; d <= 6; d++) {
                        pw.println(i + " " + j + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                    }
                    for (int d = 1; d <= 6; d++) {
                        pw.println(i + " " + -j + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                    }
                    for (int d = 1; d <= 6; d++) {
                        pw.println(-i + " " + j + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                    }
                    for (int d = 1; d <= 6; d++) {
                        pw.println(-i + " " + -j + " " + d + " " + 1 + " " + 1 + " " + 1 + " " + 1);
                    }
                }
            }

            pw.close();
        }
        System.out.println("done");
    }
}
