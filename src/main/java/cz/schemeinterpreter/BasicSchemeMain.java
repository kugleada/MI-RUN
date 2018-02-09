package cz.schemeinterpreter;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.DirectCallNode;
import cz.schemeinterpreter.nodes.SchemeNode;
import cz.schemeinterpreter.nodes.builtin.AddBuiltinNodeFactory;
import cz.schemeinterpreter.reader.TruffleReader;

import cz.schemeinterpreter.types.SchemeFunction;
import cz.schemeinterpreter.types.SchemeList;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.StreamSupport;

import static cz.schemeinterpreter.nodes.builtin.BuiltinNode.createBuiltinFunction;

public class BasicSchemeMain {
        public static void main(String[] args) throws IOException {
            /*
            assert args.length < 2 : "BasicScheme only accepts 1 or 0 files";
            if (args.length == 0) {
                startREPL();
            } else {
                runScheme(args[0]);
            }
            */
            System.out.println("start");
            runTestScheme();
            System.out.println("end");
        }
        /*
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
        */
        /*
        private static void runScheme(String filename) throws IOException {
            Environment topEnv = Environment.getBaseEnvironment();

            SchemeList nodes = Reader.read(new FileInputStream(filename));
            for (SchemeNode node : nodes) {
                node.eval(topEnv);
            }
        }
        */
        private static void runTestScheme() throws IOException {
            VirtualFrame topFrame = createTopFrame(TruffleReader.frameDescriptors.peek());
            //String source = "(define add (lambda (x y) (+ x y)))"; // (add 4 5)
            String source = "(+ (+ 1 2) (+ 3 4))";
            InputStream streamIn = new ByteArrayInputStream(source.getBytes(StandardCharsets.UTF_8));
            SchemeList<SchemeNode> nodes = TruffleReader.read(streamIn);
            System.out.println(execute(nodes, topFrame).toString());
        }

    private static void runScheme(String filename) throws IOException {
        VirtualFrame topFrame = createTopFrame(TruffleReader.frameDescriptors.peek());
        SchemeList<SchemeNode> nodes = TruffleReader.read(new FileInputStream(filename));
        execute(nodes, topFrame);
    }

    private static Object execute(SchemeList<SchemeNode> nodes, VirtualFrame topFrame) {
        FrameDescriptor frameDescriptor = topFrame.getFrameDescriptor();
        SchemeFunction function = SchemeFunction.create(new FrameSlot[] {},
                StreamSupport.stream(nodes.spliterator(), false)
                        .toArray(size -> new SchemeNode[size]),
                frameDescriptor);
        DirectCallNode directCallNode = Truffle.getRuntime()
                .createDirectCallNode(function.callTarget);
        return directCallNode.call(
                topFrame,
                new Object[] {topFrame.materialize()});
    }

    private static VirtualFrame createTopFrame(FrameDescriptor frameDescriptor) {
        VirtualFrame virtualFrame = Truffle.getRuntime().createVirtualFrame(
                new Object[] {}, frameDescriptor);
        virtualFrame.setObject(frameDescriptor.addFrameSlot("+"),
                createBuiltinFunction(AddBuiltinNodeFactory.getInstance()));
        // more buitins ...
        return virtualFrame;
    }
}

