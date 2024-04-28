/*
 *     
 *
 */
package net.michaelhofmann.graphic.simpic.spi;

import net.michaelhofmann.graphic.simpic.api.SimpicProcess;
import net.michaelhofmann.graphic.simpic.generic.ImageCard;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import net.michaelhofmann.graphic.simpic.generic.Vuzzy;
import net.michaelhofmann.graphic.simpic.util.Parameter;


public class GreyChecker extends SimpicProcess {
    
    public final static String IDENT = "GreyChecker";
    public final static String HASH = IDENT + ".hash";
    public final static String ERROR_FLAG = IDENT + ".error";
    public final static String ERROR_FLAG_ACCEPT = ERROR_FLAG + ".accept";

    public GreyChecker(Parameter para) {
        super(IDENT, para);
    }

    @Override
    public void accept(ImageCard card) {
        try {
            card.getAttributes().put(HASH, getImageHash(card));
        } catch (IOException ex) {
            System.out.println("error while processing with " + IDENT + ": " + ex);
            card.getExceptions().put(ERROR_FLAG, ex);
            card.getExceptions().put(ERROR_FLAG_ACCEPT, ex);
        }
    }

    @Override
    public boolean test(ImageCard card) {
        return ! card.getExceptions().containsKey(ERROR_FLAG);
    }
    
    public String getImageHash(ImageCard card) throws IOException {
        BufferedImage fooImage = resizeAndGreyImage(card.getBufferedImage(), 9, 8);
        int[][] greyPixelArray = getGreyPixelArray(fooImage);
        return generateImageHash(greyPixelArray);
    }

    @Override
    public Vuzzy getSimilarity(ImageCard a, ImageCard b) {
        String hashA = (String)a.getAttributes().get(HASH);
        String hashB = (String)b.getAttributes().get(HASH);
        // length shoud always be equals
        if (hashA.length() != hashB.length()) {
            return Vuzzy.FALSE;
        }
        double distance = 1.0 ;
        for (int x = 0; x < hashA.length(); x++) {
            if (hashA.charAt(x) != hashB.charAt(x)) {
                distance = distance - 0.05;
            }
        }
        return Vuzzy.of(distance);
    }
    
    public BufferedImage resizeAndGreyImage(BufferedImage image,
            int width, int height) {
        BufferedImage resizedImage = new BufferedImage(
                width, height, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D graphics = resizedImage.createGraphics();
        graphics.drawImage(image, 0, 0, width, height, null);
        graphics.dispose();
        graphics.setComposite(AlphaComposite.Src);
        graphics.setRenderingHint(RenderingHints.KEY_INTERPOLATION, 
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, 
                RenderingHints.VALUE_RENDER_QUALITY);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        return resizedImage;
    }
    
    public int[][] getGreyPixelArray(BufferedImage image){
        int width = image.getWidth();
        int height = image.getHeight();
        int[][] greyPixelArray = new int[height][width];
        for (int y=0; y < image.getHeight(); y++)
            for(int x=0; x < image.getWidth(); x++)
                greyPixelArray[y][x] = image.getRGB(x, y) & 0xFF;
        return  greyPixelArray;
    }
    
    public String generateImageHash(int[][] colorArray){
        StringBuilder sb = new StringBuilder();
        int height = colorArray.length;
        int width = colorArray[0].length - 1;
        for(int y = 0; y < width; y++) {
            for (int x = 0; x < height; x++) {
                sb.append(colorArray[y][x] < colorArray[y][x+1] ? "1" : "0");
            }
        }
        return sb.toString();
    }
}
