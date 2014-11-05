import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.JPanel;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.apache.commons.io.output.ByteArrayOutputStream;


public class ImageGetter {
	
	public static BufferedImage getSVG(String path, int x, int y){
	    // Create a PNG transcoder.
	    Transcoder t = new PNGTranscoder();
	    URL image = ImageGetter.class.getResource(path);

	    // Set the transcoding hints.
	    t.addTranscodingHint( PNGTranscoder.KEY_WIDTH,  new Float(x) );
	    t.addTranscodingHint( PNGTranscoder.KEY_HEIGHT, new Float(y) );

	    // Create the transcoder input.
	    TranscoderInput input = new TranscoderInput(image.toString());

	    ByteArrayOutputStream ostream = null;
	    try {
	        // Create the transcoder output.
	        ostream = new ByteArrayOutputStream();
	        TranscoderOutput output = new TranscoderOutput( ostream );

	        // Save the image.
	        t.transcode( input, output );

	        // Flush and close the stream.
	        ostream.flush();
	        ostream.close();
	    } catch( Exception ex ){
	        ex.printStackTrace();
	    }

	    // Convert the byte stream into an image.
	    byte[] imgData = ostream.toByteArray();
	    Image img = Toolkit.getDefaultToolkit().createImage( imgData );

	    // Wait until the entire image is loaded.
	    MediaTracker tracker = new MediaTracker( new JPanel() );
	    tracker.addImage( img, 0 );
	    try {
	        tracker.waitForID( 0 );
	    } catch( InterruptedException ex ){
	        ex.printStackTrace();
	    }

	    if (img instanceof BufferedImage)
	    {
	        return (BufferedImage) img;
	    }

	    // Create a buffered image with transparency
//	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
	    BufferedImage bimage = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration().createCompatibleImage(img.getWidth(null), img.getHeight(null), Transparency.TRANSLUCENT);
        
	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}
}
