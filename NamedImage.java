import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

public class NamedImage {
	public String name;
	public Image image;
	NamedImage(String path){
		try {
		image=ImageIO.read(new File(path));
		}catch(Exception e) {
			
		}
	}
}
