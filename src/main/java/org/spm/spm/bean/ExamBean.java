package org.spm.spm.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamBean {
    Integer eid;
    Integer cid;
    Date begin;
    Date end;
    String name;
    String text;

    /*public ExamBean(Exam exam) {
        eid = exam.getEid();
        cid = exam.getCid();
        begin = exam.getBegin();
        end = exam.getEnd();
        name = exam.getName();
        StringBuilder srcBuf = new StringBuilder();
        for (Problem problem : exam.getProblems()) {
            srcBuf.append(problem.toString());
        }
        src = srcBuf.toString();
    }*/
}
