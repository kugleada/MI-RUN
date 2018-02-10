package cz.schemeinterpreter.scheme_env;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.MaterializedFrame;

public class TEnvironment {

    private final FrameDescriptor baseFrameDescriptor; //< Descriptor
    private final MaterializedFrame baseFrame; //< "Storage"

    /**
     * Constructor of environment.
     *
     * @param bfd FrameDescriptor Where values are stored in frames.
     * @param mf  MaterializedFrame, in heap memory, can be accessed from different functions.
     */
    public TEnvironment(FrameDescriptor bfd, MaterializedFrame mf) {
        this.baseFrameDescriptor = bfd;
        this.baseFrame = mf;

        // good place to initialize build in functions and/or syntatic sugar
        // define all functions and then insert them to global environment
        // this.baseFrame.setObject(baseFrameDescriptor.findOrAddFrameSlot(builtinFunction.getName()), builtinFunction);
    }

    /**
     * Simple getter for accessing frame descriptor.
     *
     * @return Frame (data) description.
     */
    public FrameDescriptor getBaseFrameDescriptor() {
        return this.baseFrameDescriptor;
    }

    /**
     * Simple getter to access frame with data.
     *
     * @return Frame with data.
     */
    public MaterializedFrame getBaseFrame() {
        return this.baseFrame;
    }


    /* How to create a frame:
        MaterializedFrame frame = Truffle.getRuntime().createMaterializedFrame(<OBJECTS>, <FRAME DESCRIPTOR>);

       How to insert stuff:
        frame.setLong(frameDescriptor.findOrAddFrameSlot("ROOT", FrameSlotKind.Long), 100000);

       How to get stuff from the frame:
        frame.getLong(<FrameSlot>);

       The whole process:
        long a = 200; // variable to be stored
        FrameDescriptor fd = new FrameDescriptor(); // empty descriptor
        MaterializedFrame mf = Truffle.getRuntime().createMaterializedFrame(null, fd); // empty frame
        mf.setInt(fd.findOrAddFrameSlot("NAME_OF_THIS_SLOT", FrameSlotKind.Long), 100); // 100 is value of datatypes long we want to store
        try
        {
            a = mf.getInt(fd.findOrAddFrameSlot("NAME_OF_THIS_SLOT", FrameSlotKind.Long));
        } catch (FrameSlotTypeException e) {
            e.printStackTrace();
        }
        System.out.println(a); // prints out 100, not 200
    }
    */

}
