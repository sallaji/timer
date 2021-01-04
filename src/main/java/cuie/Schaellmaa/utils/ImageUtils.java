package cuie.Schaellmaa.utils;


import cuie.Schaellmaa.skin.TimerSkin;
import java.io.InputStream;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;

public final class ImageUtils {

  private ImageUtils() {
  }
  public static ImageView getSVGImageAsImageView(String path) {

    InputStream svgFile = TimerSkin.class.getResourceAsStream(path);

    BufferedImageTranscoder transcoder = new BufferedImageTranscoder();
    TranscoderInput transcoderInput = new TranscoderInput(svgFile);

    try {
      transcoder.transcode(transcoderInput, null);
    } catch (TranscoderException e) {
      e.printStackTrace();
    }
    Image image = SwingFXUtils.toFXImage(transcoder.getBufferedImage(), null);
    ImageView imageView = new ImageView();
    imageView.setImage(image);
    return imageView;
  }
}
