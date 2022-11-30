package org.spm.spm.bean.pro;

public interface Problem {
    String getId();

    double getScore();

    double check(String[] ans);

    ProblemView toProblemView();
}
