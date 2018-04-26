import java.util.ArrayList;

/**
 * Loads and holds all image elements. Currently the google drive of images is at 32 mg, so should not be issue to hold all
 * @author Egan Johnson
 *
 */
public class Images {
	public static ArrayList<ArrayList<ArrayList<NamedImage>>>images=new ArrayList<ArrayList<ArrayList<NamedImage>>>();
	public static char[] alphabet=new char[] {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	public static void setup() {
		for(int i=0;i<26;i++) {
			images.add(new ArrayList<ArrayList<NamedImage>>());
			for(int i2=0;i<26;i++) {
				images.get(0).add(new ArrayList<NamedImage>());
			}
		}
	}
	/**
	 * gets the image with the specified path. If it has not been loaded before, loads it from storage. If not, loads it from ram
	 * @param path
	 */
	public static void get(String path) {
		
	}
}
