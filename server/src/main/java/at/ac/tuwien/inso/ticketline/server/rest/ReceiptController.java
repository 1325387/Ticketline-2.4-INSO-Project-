package at.ac.tuwien.inso.ticketline.server.rest;

import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.server.util.PDFCreator;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;

/**
 * Controller for the Receipt PDF creation.
 *
 * @author Jayson Asenjo 1325387
 */
@Api(value="receipt", description = "Receipt PDF REST controller")
@RestController
@RequestMapping(value="/receipt")
public class ReceiptController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsController.class);

    private InputStream inputstream;

    @Autowired
    private ReceiptService receiptService;

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MimeTypeUtils.APPLICATION_JSON_VALUE, produces = {"application/octet-stream"})
    public ResponseEntity<InputStreamResource> printPdf(@RequestBody List<TicketIdentifierDto> items) throws ServiceException,IOException {

            String fileName = receiptService.createReceipt(items);

            //ClassPathResource pdfFile = new ClassPathResource(fileName);
            File pdf = new File(fileName);
            FileInputStream fis = new FileInputStream(pdf);

            //LOGGER.debug("PDF location: " + pdfFile.getFile().getPath());
            LOGGER.debug("PDF location: " + pdf.getAbsolutePath());
            // Set the input stream
            //inputstream = pdfFile.getInputStream();
            inputstream = fis;

            // asume that it was a PDF file
            HttpHeaders responseHeaders = new HttpHeaders();
            InputStreamResource inputStreamResource = new InputStreamResource(inputstream);
            //responseHeaders.setContentLength(pdfFile.contentLength());
            responseHeaders.setContentLength(pdf.length());
            responseHeaders.setContentType(MediaType.parseMediaType("application/octet-stream"));

            return new ResponseEntity<>(inputStreamResource,
                    responseHeaders,
                    HttpStatus.OK);
    }

    @RequestMapping(value="/success", method = RequestMethod.GET, produces = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Boolean closeInputStream() throws ServiceException{
        try {
            if(inputstream == null){
                LOGGER.debug("inputstream is null");
                throw new ServiceException("ERROR: no stream has been initialized! No PDF available to read from.");
            }
            inputstream.close();
        } catch (IOException e) {
            LOGGER.debug("ERROR: failed to close inputstream! "+e.getMessage(), e);
            throw new ServiceException("ERROR: failed to close inputstream! "+e.getMessage());
        }
        return true;
    }
}
