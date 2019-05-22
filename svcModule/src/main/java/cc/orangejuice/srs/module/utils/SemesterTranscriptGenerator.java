package cc.orangejuice.srs.module.utils;

import cc.orangejuice.srs.module.client.dto.ProgrammeDTO;
import cc.orangejuice.srs.module.client.dto.StudentDTO;
import cc.orangejuice.srs.module.client.dto.StudentEnrollDTO;
import cc.orangejuice.srs.module.client.dto.StudentProgressionDTO;
import cc.orangejuice.srs.module.service.dto.ModuleDTO;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class SemesterTranscriptGenerator extends TranscriptGenerator {
    public SemesterTranscriptGenerator(List<StudentModuleSelectionDTO> studentResults,
                                       List<ModuleDTO> moduleInfo, StudentDTO studentPersonalInfo,
                                       StudentEnrollDTO studentEnrollInfo,
                                       ProgrammeDTO programmeInfo,
                                       StudentProgressionDTO studentProgressionInfo) {
        super(studentResults, moduleInfo, studentPersonalInfo, studentEnrollInfo,
            programmeInfo, studentProgressionInfo);
    }

    // the method for generating the file
    public void generate() throws FileNotFoundException, DocumentException {

        PdfWriter.getInstance(this.getDocument(), new FileOutputStream(
            "StudentTranscript - " + this.getStudentPersonalInfo().getStudentNumber() + ".pdf"));
        this.getDocument().open();

        generateHeader();
        generateBody();

        this.getDocument().close();

    }

    private void generateHeader() throws DocumentException {
        Chunk schoolName = new Chunk("University of Limerick", this.getTitleFont());
        Chunk fileTypeName = new Chunk("Student Transcript", this.getSubTitleFont());

        // the title of a university
        Paragraph schoolTitle = new Paragraph(schoolName);
        schoolTitle.setAlignment(Paragraph.ALIGN_CENTER);
        schoolTitle.setSpacingAfter(20);
        this.getDocument().add(schoolTitle);

        // subtitle
        Paragraph subTitle = new Paragraph(fileTypeName);
        subTitle.setAlignment(Paragraph.ALIGN_CENTER);
        subTitle.setSpacingAfter(40);
        this.getDocument().add(subTitle);
    }

    private void generateBody() throws DocumentException {
        generatePersonalInfo();
        generateProgrammeInfo();
        generateTableTitle();
        generateQCAInfo();
        generateResultTable();
    }

    private void generatePersonalInfo() throws DocumentException {
        Chunk studentName = new Chunk("Name: " + this.getStudentPersonalInfo().getFirstName() + " " + this.getStudentPersonalInfo().getLastName(), this.getBodyFont());
        Chunk studentNumber = new Chunk("Student Number: " + this.getStudentPersonalInfo().getStudentNumber(), this.getBodyFont());
        Chunk addressLine1 = new Chunk("Address: " + this.getStudentPersonalInfo().getAddressLine1(), this.getBodyFont());
        Chunk addressLine2 = new Chunk(this.getStudentPersonalInfo().getAddressLine2(), this.getBodyFont());
        Chunk telephone = new Chunk("Phone Number: " + this.getStudentPersonalInfo().getPhone(), this.getBodyFont());

        generateParagraph(studentName, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(studentNumber, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(addressLine1, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(addressLine2, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(telephone, Paragraph.ALIGN_LEFT, 20); // leave space for every block for scaling



    }

    private void generateProgrammeInfo() throws DocumentException {
        Chunk programmeName = new Chunk("Course: " + this.getProgrammeInfo().getName(), this.getBodyFont());
        Chunk enrollStatus = new Chunk("Status: " + this.getStudentEnrollInfo().getStatus().name(), this.getBodyFont());

        generateParagraph(programmeName, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(enrollStatus, Paragraph.ALIGN_LEFT, 20);
    }

    private void generateTableTitle() throws DocumentException {
        Chunk tableTitle = new Chunk(this.getStudentResults().get(0).getAcademicYear() + "  SEM" + this.getStudentResults().get(0).getAcademicSemester() , this.getBodyFont());
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateResultTable() throws DocumentException {
        addTableHeader();
        generateTableBody();
        this.getTable().setSpacingAfter(20);
        this.getDocument().add(this.getTable());
    }

    private void generateQCAInfo() throws DocumentException {
        Chunk tableTitle = new Chunk("QCA: " + this.getStudentProgressionInfo().getQca() , this.getBodyFont());
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateParagraph(Chunk content, int alignment, int spacing) throws DocumentException {
        Paragraph paragraph = new Paragraph(content);
        paragraph.setAlignment(alignment);
        paragraph.setSpacingAfter(spacing);
        this.getDocument().add(paragraph);
    }

    private void addTableHeader() {
        Stream.of("Module", "Title", "Grade", "Credits").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            this.getTable().addCell(header);
        });
    }

    private void generateTableBody() {
        for(int i = 0; i < this.getStudentResults().size(); i++) {
            // module
            this.getTable().addCell(this.getModuleInfo().get(i).getCode());

            // title
            this.getTable().addCell(this.getStudentResults().get(i).getModuleName());

            // grade
            this.getTable().addCell(this.getStudentResults().get(i).getStudentModuleGradeTypeName());

            // credits
            this.getTable().addCell(this.getStudentResults().get(i).getCreditHour().toString());
        }
    }
}
