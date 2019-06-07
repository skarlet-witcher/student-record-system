package cc.orangejuice.srs.module.utils;

import cc.orangejuice.srs.module.client.dto.*;
import cc.orangejuice.srs.module.client.dto.enumeration.ProgressType;
import cc.orangejuice.srs.module.service.dto.ModuleDTO;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class PartTranscriptGenerator extends TranscriptGenerator {

    private List<ProgrammePropDTO> partList;
    private List<StudentModuleSelectionDTO> studentResults;
    private List<ModuleDTO> moduleInfo;
    private StudentDTO studentPersonalInfo;
    private StudentEnrollDTO studentEnrollInfo;
    private ProgrammeDTO programmeInfo;
    private List<StudentProgressionDTO> studentProgressionInfo;


    public PartTranscriptGenerator(List<ProgrammePropDTO> partList, List<StudentModuleSelectionDTO> studentResults, List<ModuleDTO> moduleInfo, StudentDTO studentPersonalInfo, StudentEnrollDTO studentEnrollInfo, ProgrammeDTO programmeInfo, List<StudentProgressionDTO> studentProgressionInfo) {
        this.partList = partList;
        this.studentResults = studentResults;
        this.moduleInfo = moduleInfo;
        this.studentPersonalInfo = studentPersonalInfo;
        this.studentEnrollInfo = studentEnrollInfo;
        this.programmeInfo = programmeInfo;
        this.studentProgressionInfo = studentProgressionInfo;
    }

    @Override
    // the method for generating the file
    public void generate() throws FileNotFoundException, DocumentException {

        PdfWriter.getInstance(this.getDocument(), new FileOutputStream(
            "PartTranscript - part " + partList.get(0).getValue() + " - " + this.studentPersonalInfo.getStudentNumber() + ".pdf"));
        this.getDocument().open();

        generateHeader();
        generateBody();

        this.getDocument().close();

    }

    private void generateHeader() throws DocumentException {
        Chunk schoolName = new Chunk("University of Limerick", this.getTitleFont());
        Chunk fileTypeName = new Chunk("Student Transcript for Part " + partList.get(0).getValue(), this.getSubTitleFont());

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
        generateTable();
    }

    private void generatePersonalInfo() throws DocumentException {
        Chunk studentName = new Chunk("Name: " + this.studentPersonalInfo.getFirstName() + " " + this.studentPersonalInfo.getLastName(), this.getBodyFont());
        Chunk studentNumber = new Chunk("Student Number: " + this.studentPersonalInfo.getStudentNumber(), this.getBodyFont());
        Chunk addressLine1 = new Chunk("Address: " + this.studentPersonalInfo.getAddressLine1(), this.getBodyFont());
        Chunk addressLine2 = new Chunk(this.studentPersonalInfo.getAddressLine2(), this.getBodyFont());
        Chunk telephone = new Chunk("Phone Number: " + this.studentPersonalInfo.getPhone(), this.getBodyFont());

        generateParagraph(studentName, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(studentNumber, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(addressLine1, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(addressLine2, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(telephone, Paragraph.ALIGN_LEFT, 20); // leave space for every block for scaling



    }

    private void generateProgrammeInfo() throws DocumentException {
        Chunk programmeName = new Chunk("Course: " + this.programmeInfo.getName(), this.getBodyFont());
        Chunk enrollStatus = new Chunk("Status: " + this.studentEnrollInfo.getStatus().name(), this.getBodyFont());

        generateParagraph(programmeName, Paragraph.ALIGN_LEFT, 5);
        generateParagraph(enrollStatus, Paragraph.ALIGN_LEFT, 20);
    }

    private void generateTable() throws DocumentException {
        for(ProgrammePropDTO part : partList) {
            int semNum = 1;
            int academicSemNum = 1;
            generateTableTitle(part, semNum);
            for(int i = 0; i < this.studentResults.size(); i++) {
                if(studentResults.get(i).getAcademicSemester() == academicSemNum) {
                    // add results to table
                    addResults(i);
                } else { // semester changed
                    // render the table
                    renderTable();
                    // print semester qca
                    generateSemesterQCA(academicSemNum);
                    // generate title for the next table
                    generateTableTitle(part, switchSemNum(semNum));
                    academicSemNum++;
                }
            }
            renderTable();
            // print semester qca
            generateSemesterQCA(academicSemNum);
            // generate progression decision
            generateProgressionInfo();
        }
    }

    private void addResults(int index) {
        // module
        this.getTable().addCell(this.moduleInfo.get(index).getCode());

        // title
        this.getTable().addCell(this.studentResults.get(index).getModuleName());

        // grade
        this.getTable().addCell(this.studentResults.get(index).getStudentModuleGradeTypeName());

        // credits
        this.getTable().addCell(this.studentResults.get(index).getCreditHour().toString());
    }

    private void renderTable() throws DocumentException {
        this.getTable().setSpacingAfter(20);
        this.getDocument().add(this.getTable());
    }

    private void generateSemesterQCA(int academicSemNum) throws DocumentException {
        for(StudentProgressionDTO studentProgression : studentProgressionInfo) {
            if(studentProgression.getForAcademicSemester() == academicSemNum) {
                generateQCAInfo(studentProgression.getQca());
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
        Chunk tableTitle = new Chunk("Year " + part.getForYearNo()  + "  SEM" + semNum, this.getBodyFont());
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }
    
    private void generateQCAInfo(double qca) throws DocumentException {
        Chunk tableTitle = new Chunk("Semester QCA: " + qca , this.getBodyFont());
        generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
    }

    private void generateProgressionInfo() throws DocumentException {
        for(StudentProgressionDTO studentProgressionDTO : this.studentProgressionInfo) {
            if(studentProgressionDTO.getProgressType() == ProgressType.PART && studentProgressionDTO.getForPartNo() == Integer.parseInt(partList.get(0).getValue())) {
                Chunk tableTitle = new Chunk("Cumulative QCA: " + studentProgressionDTO.getQca() + " Progression Decision: " + studentProgressionDTO.getProgressDecision() , this.getBodyFont());
                generateParagraph(tableTitle, Paragraph.ALIGN_LEFT, 5);
                break;
            }
        }

    }

    private void generateParagraph(Chunk content, int alignment, int spacing) throws DocumentException {
        Paragraph paragraph = new Paragraph(content);
        paragraph.setAlignment(alignment);
        paragraph.setSpacingAfter(spacing);
        this.getDocument().add(paragraph);
    }

    
}
