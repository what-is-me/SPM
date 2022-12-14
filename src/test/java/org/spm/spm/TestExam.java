package org.spm.spm;

import com.google.gson.Gson;
import org.spm.spm.bean.ExamBean;
import org.spm.spm.bean.pro.Exam;

public class TestExam {
    static String text = "selection\n" +
            "1\n" +
            "111\n" +
            "11\n" +
            "A\n" +
            "A\n" +
            "Baaaa\n" +
            "Cssss\n" +
            "Dmmmm\n\n";

    public static void main(String[] args) throws Exception {
        ExamBean examBean = ExamBean.builder()
                .text(text).build();
        System.out.println(new Gson().toJson(new Exam(examBean)));
    }
}
