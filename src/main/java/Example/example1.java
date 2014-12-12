package Example;
import com.nativelibs4java.opencl.CLPlatform;
import com.nativelibs4java.opencl.JavaCL;

/**
 * Created by binhaozhou on 12/12/14.
 */
public class example1 {
    public static void main(String[] args){
        // Creating the array of platform in our computer
        CLPlatform[] clPlatforms = JavaCL.listPlatforms();
        // Iterating over the platform and reporing some details about them
        for (CLPlatform platform : clPlatforms ){
            System.out.println("Vendor: " + platform.getVendor() );
            System.out.println("Name: " + platform.getName() );
            System.out.println("Extensions: " + String.format("%s\t", platform.getExtensions()));
        }
    }

}
