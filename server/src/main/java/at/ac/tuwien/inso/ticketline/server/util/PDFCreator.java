package at.ac.tuwien.inso.ticketline.server.util;

import at.ac.tuwien.inso.ticketline.model.ReceiptEntry;
import at.ac.tuwien.inso.ticketline.model.Ticket;
import com.google.common.io.FileBackedOutputStream;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jayson Asenjo 1325387
 */
public class PDFCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(PDFCreator.class);

    private static final String OUTPUT_PATH = "";

    private Document pdf;
    private List<ReceiptEntry> receiptEntry;
    private Date soldDate;
    private String employeeName;
    private int receiptNumber;

    public PDFCreator(){
        this.receiptEntry = new ArrayList<>();
        this.soldDate = new Date();
    }

    public void setEmployee(String employee){
        this.employeeName = employee;
    }

    public void setReceiptNumber(int receiptNumber){
        this.receiptNumber = receiptNumber;
    }

    public void addEntry(ReceiptEntry item){
        LOGGER.debug("Item "+item.getId()+" has been added to receipt: "+this.receiptNumber
                    +" by employee: "+this.employeeName
                    +" on: "+formattedDate(this.soldDate)
                    +" at: "+formattedTime(this.soldDate));
        this.receiptEntry.add(item);
    }

    /**
     * Converts a util.Date into a readable format.
     * @param date
     * @return
     */
    private String formattedDate(Date date){
        return new SimpleDateFormat("dd-MM-YYYY").format(date);
    }

    private String formattedTime(Date date){
        return new SimpleDateFormat("HH:mm").format(date);
    }

    // DEBUG
    public void showResult(){
        LOGGER.debug("EMPLOYEE = "+this.employeeName);
        LOGGER.debug("RECEIPT ID = "+this.receiptNumber);
        LOGGER.debug("Date = "+this.formattedDate(this.soldDate));
        LOGGER.debug("Time = "+this.formattedTime(this.soldDate));
        for(ReceiptEntry t:this.receiptEntry){
            LOGGER.debug("ITEM = "+t.getId());
        }
    }

    /**
     * @authot Jayson Asenjo 1325387
     *
     * Createsname a valid receipt
     */
    public void create(String name){
        pdf = new Document();
        Paragraph company = new Paragraph("TICKETLINE 2.4");
        company.setSpacingAfter(10);
        Paragraph address = new Paragraph("Technische Universität Wien INSO\nKarlsplatz 13 \nWien, 1040 \nAUT");
        address.setSpacingAfter(10);
        address.setAlignment(Paragraph.ALIGN_RIGHT);
        Paragraph responsibleEmployee = new Paragraph("Mitarbeiter:        "+this.employeeName);
        Paragraph billNr = new Paragraph("Rechnugnsnr.:     "+this.receiptNumber);
        Paragraph dateAndTime = new Paragraph("Datum u. Uhrzeit:  "+this.formattedDate(this.soldDate)+" "+this.formattedTime(this.soldDate));
        Paragraph seperator = new Paragraph("--------------------------------------------");
        try {
            FileOutputStream fos = new FileOutputStream(OUTPUT_PATH+name);
            PdfWriter.getInstance(pdf, fos);

            pdf.open();
            pdf.add(address);
            pdf.add(company);
            pdf.add(billNr);
            pdf.add(responsibleEmployee);
            pdf.add(dateAndTime);
            pdf.add(seperator);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.addCell(getCell("Show", PdfPCell.ALIGN_LEFT));
            table.addCell(getCell("Stueck", PdfPCell.ALIGN_CENTER));
            table.addCell(getCell("Position", PdfPCell.ALIGN_CENTER));
            table.addCell(getCell("Preis", PdfPCell.ALIGN_RIGHT));
            table.setHeaderRows(1);
            float sumOfTickets = 0;
            for(ReceiptEntry entry:this.receiptEntry){
                LOGGER.debug("RETRIEVING DESCRIPTION!");
                Ticket ticket = entry.getTicketIdentifier().getTicket();
                int price = ticket.getPrice();
                table.addCell(getCell(ticket.getShow().getPerformance().getName(), PdfPCell.ALIGN_LEFT));
                table.addCell(getCell("1", PdfPCell.ALIGN_CENTER));
                table.addCell(getCell(ticket.getSeat().getRow().getName()+" - Sequenz: "+ticket.getSeat().getSequence(),PdfPCell.ALIGN_MIDDLE));
                table.addCell(getCell(String.valueOf((float)price)+"€", PdfPCell.ALIGN_RIGHT));
                sumOfTickets += price;
            }
            float umst = 1.1f;
            float percentage = 0.1f;
            float exkl = Math.round((sumOfTickets/umst)*100.0f)/100.0f;
            float priceUmst = Math.round((exkl*percentage)*100.0f)/100.0f;
            sumOfTickets = exkl+priceUmst;

            PdfPTable sumTable = new PdfPTable(2);
            sumTable.setWidthPercentage(60);
            sumTable.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);
            sumTable.addCell(getCell("Betrag (exkl. USt.)", PdfPTable.ALIGN_LEFT));
            sumTable.addCell(getCell(String.valueOf(exkl)+"€", PdfPTable.ALIGN_RIGHT));
            sumTable.addCell(getCell("+10% USt", PdfPTable.ALIGN_LEFT));
            sumTable.addCell(getCell(String.valueOf(priceUmst)+"€", PdfPTable.ALIGN_RIGHT));
            sumTable.addCell(getCell("Summe (inkl. USt.)", PdfPTable.ALIGN_LEFT));
            sumTable.addCell(getCell(String.valueOf(sumOfTickets)+"€", PdfPTable.ALIGN_RIGHT));

            pdf.add(table);
            pdf.add(seperator);
            pdf.add(sumTable);
            pdf.add(seperator);

            pdf.close();
            fos.close();

        } catch (DocumentException e) {
            LOGGER.error("Document can´t be created!");
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            LOGGER.error("File exception! "+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            LOGGER.error("I/O-Exception: "+e.getMessage(), e);
            e.printStackTrace();
        }

    }

    private PdfPCell getCell(String content, int allignment){
        PdfPCell cell = new PdfPCell(new Phrase(content));
        cell.setPadding(3);
        cell.setHorizontalAlignment(allignment);
        cell.setBorder(0);
        return cell;
    }
}