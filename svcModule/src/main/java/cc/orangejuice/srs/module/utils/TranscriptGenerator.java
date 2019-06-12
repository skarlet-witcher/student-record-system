package cc.orangejuice.srs.module.utils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

import cc.orangejuice.srs.module.client.dto.*;
import cc.orangejuice.srs.module.client.dto.enumeration.ProgressType;
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

public class TranscriptGenerator {
    // semester transcript
    private List<StudentModuleSelectionDTO> studentResults;
    private List<ModuleDTO> moduleInfo;
    private StudentDTO studentPersonalInfo;
    private StudentEnrollDTO studentEnrollInfo;
    private ProgrammeDTO programmeInfo;
    private StudentProgressionDTO studentProgressionInfo;

    // year transcript
    private List<ProgrammePropDTO> partList;
    private List<StudentProgressionDTO> studentProgressionInfoList;


    // asset for the generator
    private Font titleFont;
    private Font subTitleFont;
    private Font bodyFont;

    private Document document;
    private PdfPTable table;

    // constructor for semester transcript
    public TranscriptGenerator(List<StudentModuleSelectionDTO> studentResults, List<ModuleDTO> moduleInfo, StudentDTO studentPersonalInfo, StudentEnrollDTO studentEnrollInfo, ProgrammeDTO programmeInfo, StudentProgressionDTO studentProgressionInfo) {
        this.studentResults = studentResults;
        this.moduleInfo = moduleInfo;
        this.studentPersonalInfo = studentPersonalInfo;
        this.studentEnrollInfo = studentEnrollInfo;
        this.programmeInfo = programmeInfo;
        this.studentProgressionInfo = studentProgressionInfo;

        titleFont = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        subTitleFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        bodyFont = FontFactory.getFont(FontFactory.COURIER, 11, BaseColor.BLACK);
        document = new Document();
        table = new PdfPTable(4);
        table.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
        table.setSpacingBefore(20);
    }

    // part transcript
    public TranscriptGenerator(List<StudentModuleSelectionDTO> studentResults, List<ModuleDTO> moduleInfo, StudentDTO studentPersonalInfo, StudentEnrollDTO studentEnrollInfo, ProgrammeDTO programmeInfo, List<ProgrammePropDTO> partList, List<StudentProgressionDTO> studentProgressionInfoList) {
        this.studentResults = studentResults;
        this.moduleInfo = moduleInfo;
        this.studentPersonalInfo = studentPersonalInfo;
        this.studentEnrollInfo = studentEnrollInfo;
        this.programmeInfo = programmeInfo;
        this.partList = partList;
        this.studentProgressionInfoList = studentProgressionInfoList;

        titleFont = FontFactory.getFont(FontFactory.COURIER, 20, BaseColor.BLACK);
        subTitleFont = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        bodyFont = FontFactory.getFont(FontFactory.COURIER, 11, BaseColor.BLACK);
        document = new Document();
        table = new PdfPTable(4);
        table.setHorizontalAlignment(Paragraph.ALIGN_LEFT);
        table.setSpacingBefore(20);
    }

    public void generateSemesterTranscript() throws FileNotFoundException, DocumentException {
        PdfWriter.getInstance(document, new FileOutputStream(
            "StudentTranscript Semester - " + studentPersonalInfo.getStudentNumber() + ".pdf"));
        document.open();

        generateHeader();
        generateBody();

        document.close();

    }

    public void generatePartTranscript() throws FileNotFoundException, DocumentException {
        PdfWriter.getInstance(this.document, new FileOutputStream(
            "PartTranscript - part " + partList.get(0).getValue() + " - " + this.studentPersonalInfo.getStudentNumber() + ".pdf"));
        this.document.open();

        generateHeader();
        generateBodyForPart();

        this.document.close();
    }

    private void generateBodyForPart() throws DocumentException {
        generatePersonalInfo();
        generateProgrammeInfo();
        generateTable();
    }

    private void generateTable() throws DocumentException {
        for(ProgrammePropDTO part : partList) {
            int semNum = 1;
            int academicSemNum = 1;
            generateTableTitle(part, semNum);
            for(int i = 0; i < this.studentResults.size(); i++) {
                if(studentResults.get(i).getAcademicSemester() == academicSemNum) {
                    // module
                    this.table.addCell(this.moduleInfo.get(i).getCode());

                    // title
                    this.table.addCell(this.studentResults.get(i).getModuleName());

                    // grade
                    this.table.addCell(this.studentResults.get(i).getStudentModuleGradeTypeName());

                    // credits
                    this.table.addCell(this.studentResults.get(i).getCreditHour().toString());
                } else {
                    // semester changed

                    // print semester qca
                    for(StudentProgressionDTO studentProgression : this.studentProgressionInfoList) {
                        if(studentProgression.getForAcademicSemester() == academicSemNum) {
                            generateQCAInfo(studentProgression.getQca());
                            break;
                        }
                    }
                    this.table.setSpacingAfter(20);
                    this.document.add(this.table);
                    generateTableTitle(part, switchSemNum(semNum));
                    academicSemNum++;
                }
            }
        }
        this.table.setSpacingAfter(20);
        this.document.add(this.table);
        // generate progression decision
        for(StudentProgressionDTO studentProgressionDTO : this.studentProgressionInfoList) {
            if(studentProgressionDTO.getProgressType() == ProgressType.PART && studentProgressionDTO.getForPartNo() == Integer.parseInt(partList.get(0).getValue())) {
                generateProgressionInfo(studentProgressionDTO);
                break;
            }
        }
    }

    private int switchSemNum(int semNum) {
        int result;
        result = (semNum == 1) ? 2 : 1;
        return result;
    }

    private void generateTableTitle(ProgrammePropDTO part, int semNum) throws DocumentException {
        Chunk tableTitle = new Chunk("Year " + part.getForYearNo()  + "  SEM" + semNum, this.bodyFont);
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateQCAInfo(double qca) throws DocumentException {
        Chunk tableTitle = new Chunk("Semester QCA: " + qca , this.bodyFont);
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateProgressionInfo(StudentProgressionDTO studentProgressionDTO) throws DocumentException {
        Chunk tableTitle = new Chunk("Cumulative QCA: " + studentProgressionDTO.getQca() + " Progression Decision: " + studentProgressionDTO.getProgressDecision() , this.bodyFont);
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateHeader() throws DocumentException {
        Chunk schoolName = new Chunk("University of Limerick", titleFont);
        Chunk fileTypeName = new Chunk("Student Transcript", subTitleFont);

        // the title of a university
        Paragraph schoolTitle = new Paragraph(schoolName);
        schoolTitle.setAlignment(Paragraph.ALIGN_CENTER);
        schoolTitle.setSpacingAfter(20);
        document.add(schoolTitle);

        // subtitle
        Paragraph subTitle = new Paragraph(fileTypeName);
        subTitle.setAlignment(Paragraph.ALIGN_CENTER);
        subTitle.setSpacingAfter(40);
        document.add(subTitle);
    }

    private void generateBody() throws DocumentException {
        generatePersonalInfo();
        generateProgrammeInfo();
        generateTableTitle();
        generateQCAInfo();
        generateResultTable();
    }

    private void generatePersonalInfo() throws DocumentException {
        Chunk studentName = new Chunk("Name: " + studentPersonalInfo.getFirstName() + " " + studentPersonalInfo.getLastName(), bodyFont);
        Chunk studentNumber = new Chunk("Student Number: " + studentPersonalInfo.getStudentNumber(), bodyFont);
        Chunk addressLine1 = new Chunk("Address: " + studentPersonalInfo.getAddressLine1(), bodyFont);
        Chunk addressLine2 = new Chunk(studentPersonalInfo.getAddressLine2(), bodyFont);
        Chunk telephone = new Chunk("Phone Number: " + studentPersonalInfo.getPhone(), bodyFont);

        generateParagraph(studentName, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(studentNumber, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(addressLine1, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(addressLine2, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(telephone, Paragraph.ALIGN_LEFT, 20); // leave space for every block for scaling



    }

    private void generateProgrammeInfo() throws DocumentException {
        Chunk programmeName = new Chunk("Course: " + programmeInfo.getName(), bodyFont);
        Chunk enrollStatus = new Chunk("Status: " + studentEnrollInfo.getStatus().name(), bodyFont);

        generateParagraph(programmeName, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(enrollStatus, Paragraph.ALIGN_LEFT, 20);
    }

    private void generateTableTitle() throws DocumentException {
        Chunk tableTitle = new Chunk(studentResults.get(0).getAcademicYear() + "  SEM" + studentResults.get(0).getAcademicSemester() , bodyFont);
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateResultTable() throws DocumentException {
        addTableHeader();
        generateTableBody();
        table.setSpacingAfter(20);
        document.add(table);
    }

    private void generateQCAInfo() throws DocumentException {
        Chunk tableTitle = new Chunk("QCA: " + studentProgressionInfo.getQca() , bodyFont);
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateParagraph(Chunk content, int alignment, int spacing) throws DocumentException {
        Paragraph paragraph = new Paragraph(content);
        paragraph.setAlignment(alignment);
        paragraph.setSpacingAfter(spacing);
        document.add(paragraph);
    }

    private void addTableHeader() {
        Stream.of("Module", "Title", "Grade", "Credits").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void generateTableBody() {
        for(int i = 0; i < studentResults.size(); i++) {
            // module
            table.addCell(moduleInfo.get(i).getCode());

            // title
            table.addCell(studentResults.get(i).getModuleName());

            // grade
            table.addCell(studentResults.get(i).getStudentModuleGradeTypeName());

            // credits
            table.addCell(studentResults.get(i).getCreditHour().toString());
        }
    }

}
