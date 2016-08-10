package at.ac.tuwien.inso.ticketline.server.integrationtest.service;

import at.ac.tuwien.inso.ticketline.dto.TicketIdentifierDto;
import at.ac.tuwien.inso.ticketline.model.TicketIdentifier;
import at.ac.tuwien.inso.ticketline.server.exception.ServiceException;
import at.ac.tuwien.inso.ticketline.server.service.ReceiptService;
import at.ac.tuwien.inso.ticketline.server.service.TicketIdentifierService;
import org.apache.commons.logging.Log;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Jayson Asenjo 1325387
 */
public class ReceiptServiceIntegrationTest extends AbstractServiceIntegrationTest{

    @Autowired
    private ReceiptService receiptService;
    @Autowired
    private TicketIdentifierService ticketIdentifierService;

    @Test
    public void testCreateReceiptTest() throws ServiceException{
        TicketIdentifier tId = ticketIdentifierService.getTicketIdentifierById(1);
        TicketIdentifierDto dto = new TicketIdentifierDto();
        dto.setId(tId.getId());
        dto.setVoidedBy(tId.getVoidedBy());
        dto.setVoidationTime(tId.getVoidationTime());
        dto.setValid(tId.getValid());
        dto.setCancellationReason(tId.getCancellationReason());
        List<TicketIdentifierDto> dtos = new ArrayList<>();
        dtos.add(dto);

        String out = receiptService.createReceipt(dtos);
        File pdf = new File(out);
        assertTrue(pdf!=null);
        assertTrue(pdf.delete());
    }

}
