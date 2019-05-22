package cc.orangejuice.srs.module.utils;

import cc.orangejuice.srs.module.client.dto.ProgrammeDTO;
import cc.orangejuice.srs.module.client.dto.StudentDTO;
import cc.orangejuice.srs.module.client.dto.StudentEnrollDTO;
import cc.orangejuice.srs.module.client.dto.StudentProgressionDTO;
import cc.orangejuice.srs.module.service.dto.ModuleDTO;
import cc.orangejuice.srs.module.service.dto.StudentModuleSelectionDTO;

import java.util.List;

public class SemesterTranscriptGenerator extends TranscriptGenerator {
    public SemesterTranscriptGenerator(List<StudentModuleSelectionDTO> studentResults,
                                       List<ModuleDTO> moduleInfo, StudentDTO studentPersonalInfo,
                                       StudentEnrollDTO studentEnrollInfo,
                                       ProgrammeDTO programmeInfo,
                                       StudentProgressionDTO studentProgressionInfo) {
        super(studentResults, moduleInfo, studentPersonalInfo, studentEnrollInfo,
            programmeInfo, studentProgressionInfo);
    }
}
