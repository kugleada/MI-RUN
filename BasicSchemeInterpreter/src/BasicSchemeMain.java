import datatypes.SchemeNode;
import datatypes.SchemeList;
import reader.Reader;
import scheme_env.Environment;

import java.io.*;

public class BasicSchemeMain {
        public static void main(String[] args) throws IOException {
            assert args.length < 2 : "BasicScheme only accepts 1 or 0 files";
            if (args.length == 0) {
                startREPL();
            } else {
                runScheme(args[0]);
            }
        }

        private static void startREPL() throws IOException {
            Environment topEnv = Environment.getBaseEnvironment();

            //Console console = System.console();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                // READ
                //String data = console.readLine("~> ");
                System.out.print("~> ");
                String data = br.readLine();
                if (data == null) {
                    // EOF sent
                    break;
                }
                SchemeList nodes = Reader.read(
                        new ByteArrayInputStream(data.getBytes()));

                // EVAL
                Object result = SchemeList.EMPTY;
                for (SchemeNode node : nodes) {
                    result = node.eval(topEnv);
                }

                // PRINT
                if (result != SchemeList.EMPTY) {
                    System.out.println(result);
                }
            }
        }

        private static void runScheme(String filename) throws IOException {
            Environment topEnv = Environment.getBaseEnvironment();

            SchemeList nodes = Reader.read(new FileInputStream(filename));
            for (SchemeNode node : nodes) {
                node.eval(topEnv);
            }
        }
}

