package at.ac.tuwien.inso.ticketline.client.extraObjects;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Jayson Asenjo 1325387
 */
public class PdfWriter {

    private static Logger LOGGER = LoggerFactory.getLogger(PdfWriter.class);

    private static String OUTPUT_PATH;
    private static ResponseEntity<byte[]> STREAM;

    public static void setOutputPath(String path){
        OUTPUT_PATH = path;
    }

    public static void setStream(ResponseEntity<byte[]> stream){
        STREAM = stream;
    }

    public static boolean savePdf() throws ServiceException{

        if(OUTPUT_PATH == null){
            throw new ServiceException("ERROR: no output path defined!");
        }
        if(STREAM == null){
            throw new ServiceException("ERROR: no Stream defined!");
        }

        OutputStream oos = null;
        LOGGER.debug("OUTPUTSTREAM created");
        try {
            // save PDF to a specified location here
            oos = new FileOutputStream(OUTPUT_PATH);
            LOGGER.debug("RESPONSE successful");

            try {
                byte[] data = STREAM.getBody();
                LOGGER.debug("INPUTSTREAM created");
                oos.write(data);
                oos.flush();

            } catch (IOException e) {
                LOGGER.debug("ERROR while creating new PDF from sent ResponseEntity: " + e.getMessage(), e);
            } finally{
                LOGGER.debug("DATA WRITTEN ON OUTPUTSTREAM");
                try {
                    oos.close();
                } catch (IOException e) {
                    LOGGER.error("ERROR: cant´t close output stream! "+e.getMessage(), e);
                    e.printStackTrace();
                }
                LOGGER.debug("stop");
            }
        } catch (RestClientException e) {
            throw new ServiceException("Could not retrieve PDF: " + e.getMessage(), e);
        } catch (IOException ioe){
            throw new ServiceException("Could not create outputstream: "+ ioe.getMessage(), ioe);
        }
        return true;
    }
}
