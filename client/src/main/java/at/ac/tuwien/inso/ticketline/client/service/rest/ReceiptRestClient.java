package at.ac.tuwien.inso.ticketline.client.service.rest;

import at.ac.tuwien.inso.ticketline.client.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.client.extraObjects.PdfWriter;
import at.ac.tuwien.inso.ticketline.client.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.*;
import java.net.URI;
import java.util.Arrays;
import java.util.List;

/**
 * @author Jayson Asenjo 1325387
 */
@Component
public class ReceiptRestClient implements ReceiptService{

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsRestClient.class);

    private static final String GET_PDF_OF_RECEIPT = "/service/receipt/";
    // testing
    private static final String CLOSE_STREAM = "/service/receipt/success";

    @Autowired
    private RestClient restClient;

    /**
     * {@inheritDoc}
     */
    @Override
    public void createPDF(List<TicketIdentifierDto> items) throws ServiceException{
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(GET_PDF_OF_RECEIPT);
        LOGGER.info("Create PDF", url);
        HttpHeaders headers = this.restClient.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        // stackoverflow
        restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
        HttpEntity<List<TicketIdentifierDto>> entity = new HttpEntity<>(items,headers);
        LOGGER.info("Inquire for PDF file");

        ResponseEntity<byte[]> pdfFile = restTemplate.exchange(URI.create(url).toString(),
                                                                HttpMethod.POST,
                                                                entity,
                                                                byte[].class,
                                                                "1");

        if(pdfFile==null){
            throw new ServiceException("ERROR: no File recieved from server!");
        } else {
            LOGGER.debug("Setting Pdf Stream!");
            PdfWriter.setStream(pdfFile);
            LOGGER.debug("Saving PDF!");
            PdfWriter.savePdf();
            LOGGER.debug("Saving PDF successfull!");
            close();
            LOGGER.debug("CLOSING INPUTSTREAM successfull!");
        }
    }
    private void close(){
        RestTemplate restTemplate = this.restClient.getRestTemplate();
        String url = this.restClient.createServiceUrl(CLOSE_STREAM);
        HttpEntity<Boolean> entity = new HttpEntity<>(this.restClient.getHttpHeaders());
        ParameterizedTypeReference<Boolean> ref = new ParameterizedTypeReference<Boolean>() {
        };
        ResponseEntity<Boolean> response = restTemplate.exchange(URI.create(url), HttpMethod.GET, entity, ref);
        LOGGER.debug("STREAM CLOSED: "+response.getBody());
    }
}
