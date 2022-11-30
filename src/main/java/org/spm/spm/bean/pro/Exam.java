package org.spm.spm.bean.pro;

import lombok.Builder;
import lombok.Data;
import org.spm.spm.bean.ExamBean;

import java.util.*;

@Data
public class Exam {
    Integer eid;
    Integer cid;
    Date begin;
    Date end;
    String name;
    List<Problem> problems;

    public Exam(ExamBean examBean) throws Exception {
        eid = examBean.getEid();
        cid = examBean.getCid();
        begin = examBean.getBegin();
        end = examBean.getEnd();
        name = examBean.getName();
        problems = new ArrayList<>();
        for (String source : examBean.getText().split("\n\n")) {
            String[] src = source.split("\n");
            switch (src[0]) {
                case "selection":
                    problems.add(new Selection(src));
                    break;
                case "blankfilling":
                    problems.add(new BlankFilling(src));
                    break;
                case "essay":
                    problems.add(new Essay(src));
                    break;
            }
        }
    }

    public ExamView view() {
        ExamView ret = ExamView.builder().eid(eid).cid(cid).begin(begin).end(end).name(name).build();
        ret.problems = new ArrayList<>();
        for (Problem problem : problems) {
            ret.problems.add(problem.toProblemView());
        }
        return ret;
    }

    public Map<String, Double> check(Map<String, String[]> stuAns) {
        Map<String, Double> ret = new HashMap<>();
        double total = 0;
        Date now = new Date();
        if (begin.before(now) && end.after(now)) {
            for (Problem problem : problems) {
                double tmpScore = problem.check(stuAns.getOrDefault(problem.getId(), null));
                ret.put(problem.getId(), tmpScore);
                total += tmpScore;
            }
        }
        ret.put("all", total);
        return ret;
    }
}

@Data
@Builder
class ExamView {
    Integer eid;
    Integer cid;
    Date begin;
    Date end;
    String name;
    List<ProblemView> problems;
}