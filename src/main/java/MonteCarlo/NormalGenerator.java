package GPU_MC;
import com.nativelibs4java.opencl.*;
import org.bridj.Pointer;

import java.util.Random;

import static org.bridj.Pointer.allocateFloats;
/**
 * Created by binhaozhou on 12/12/14.
 */



public class NormalGenerator implements RandomVectorGenerator {


    private int batch;// batch number
    private double[] normal; // normal vector to get Gaussian
    private int times; // tracks whether we need to regenerate the normal random variable or not


    public NormalGenerator(int batch){
        this.batch = batch;
        normal = getGPUGaussian(this.batch);
        times = 0;
    }

    @Override

    // Generate normal random variable
     public double[] getVector( int n) {
        double[] vector = new double[n];
        for(int i = 0; i < n; i++){
            // we regnerate another  batch when times reaches a given number
            if(times == 2*batch - 1) {
                normal = getGPUGaussian(this.batch);
            }
            vector[i] = normal[times]; //put the generated normal to vector for simulation
            times++;
        }
        return vector;
    }



   // generate normal random variable using the best device chosen by kernal
    public double[] getGPUGaussian(int batch){
        int times = 0;
        CLPlatform clPlatform = JavaCL.listPlatforms()[0];
        CLDevice device = clPlatform.listAllDevices(true)[0];
        CLContext context = JavaCL.createContext(null, device);
        CLQueue queue = context.createDefaultQueue();

        // Read the program sources and compile them :
        String src = "__kernel void fill_in_values(__global const float* a, __global const float* b, __global float* out1, __global float* out2, float pi, int n) \n" +
                "{\n" +
                "    int i = get_global_id(0);\n" +
                "    if (i >= n)\n" +
                "        return;\n" +
                "\n" +
                "    out1[i] = sqrt(-2*log(a[i]))*cos(2*pi*b[i]);\n" +
                "    out2[i] = sqrt(-2*log(a[i]))*sin(2*pi*b[i]);\n" +
                "}";
        CLProgram program = context.createProgram(src);
        program.build();

        CLKernel kernel = program.createKernel("fill_in_values");

        final int n = batch;
        final Pointer<Float>
                aPtr = allocateFloats(n),
                bPtr = allocateFloats(n);
        // store the uniform vector in order for GPU to generate Gaussian
        double[] uniformSequence = new UniformGenerator().getVector(2*batch);
        // Generate uniform sequence and assign to aPtr and bPtr
        for (int i = 0; i < n; i++) {
            aPtr.set(i, (float) uniformSequence[2*i]);
            bPtr.set(i, (float) uniformSequence[2*i+1]);
            //System.out.println(uniformSequence[2*i]);
        }

        // Create OpenCL input buffers (using the native memory pointers aPtr and bPtr) :
        CLBuffer<Float>
                a = context.createFloatBuffer(CLMem.Usage.Input, aPtr),
                b = context.createFloatBuffer(CLMem.Usage.Input, bPtr);

        // Create an OpenCL output buffer :
        CLBuffer<Float>
                out1 = context.createFloatBuffer(CLMem.Usage.Output, n),
                out2 = context.createFloatBuffer(CLMem.Usage.Output, n);

        kernel.setArgs(a, b, out1, out2, (float) Math.PI, batch);
        CLEvent event = kernel.enqueueNDRange(queue, new int[]{n}, new int[]{128});
        event.invokeUponCompletion(new Runnable() {
            @Override
            public void run() {

            }
        });
        final Pointer<Float> c1Ptr = out1.read(queue,event);
        final Pointer<Float> c2Ptr = out2.read(queue,event);

        double[] normalSequence = new double[2*batch];
        for(int i = 0; i < batch; i++){
            normalSequence[2*i] = (double) c1Ptr.get(i);
            normalSequence[2*i+1] = (double) c2Ptr.get(i);
        }
        return normalSequence;
    }

}