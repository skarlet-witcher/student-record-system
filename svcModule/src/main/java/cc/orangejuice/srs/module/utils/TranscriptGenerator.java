package cc.orangejuice.srs.module.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

import cc.orangejuice.srs.module.client.dto.ProgrammeDTO;
import cc.orangejuice.srs.module.client.dto.StudentDTO;
import cc.orangejuice.srs.module.client.dto.StudentEnrollDTO;
import cc.orangejuice.srs.module.client.dto.StudentProgressionDTO;
import cc.orangejuice.srs.module.service.dto.ModuleDTO;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.sun.media.jfxmedia.logging.Logger;

public abstract class TranscriptGenerator {   // adapter identified supporting semester transcript and year transcript (for the extension point)

    // basic for generating the pdf file.
    private Font titleFont;
    private Font subTitleFont;
    private Font bodyFont;

    private Document document;
    private PdfPTable table;

    public TranscriptGenerator() {
        titleFont = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        subTitleFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        bodyFont = FontFactory.getFont(FontFactory.COURIER, 11, BaseColor.BLACK);
        document = new Document();
        table = new PdfPTable(4);
        table.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
        table.setSpacingBefore(20);
    }


    // the method for generating the file. now it becomes the request()
    public abstract void generate() throws FileNotFoundException, DocumentException;


    // getter and setter
    public Font getTitleFont() {
        return titleFont;
    }

    public void setTitleFont(Font titleFont) {
        this.titleFont = titleFont;
    }

    public Font getSubTitleFont() {
        return subTitleFont;
    }

    public void setSubTitleFont(Font subTitleFont) {
        this.subTitleFont = subTitleFont;
    }

    public Font getBodyFont() {
        return bodyFont;
    }

    public void setBodyFont(Font bodyFont) {
        this.bodyFont = bodyFont;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public PdfPTable getTable() {
        return table;
    }

    public void setTable(PdfPTable table) {
        this.table = table;
    }
}
