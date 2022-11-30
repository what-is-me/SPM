package org.spm.spm.bean.pro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * 问答题
 * 第1行 题目种类：essay
 * 第2行 题号（id）
 * 第3行 题面
 * 第4行 总分
 * 第5行 答案（踩分点），用';'隔开
 */
@AllArgsConstructor
@Builder
@Data
public class Essay implements Problem {
    String id;
    String text;
    List<String> ans;
    double score;

    public Essay(String[] src) throws Exception {
        if (src.length < 5) throw new Exception("问答题格式错误");
        id = src[1];
        text = src[2];
        score = Double.parseDouble(src[3]);
        for (String an : src[4].split(";")) if (!an.equals("")) ans.add(an);
        if (ans.isEmpty()) throw new Exception("答案为空");
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(String.format("essay\n%s\n%s\n%f\n", id, text, score));
        for (String an : ans) {
            ret.append(an).append(";");
        }
        ret.append("\n");
        return ret.toString();
    }

    @Override
    public double check(String[] stuAnsLines) {
        if (stuAnsLines == null) return 0;
        StringBuilder stuAnsBuf = new StringBuilder(new String());
        for (String stuAn : stuAnsLines) stuAnsBuf.append(stuAn);
        String stuAns = stuAnsBuf.toString();
        int tmp = 0;
        for (String an : ans) {
            if (stuAns.contains(an)) tmp++;
        }
        return score * tmp / ans.size();
    }

    @Override
    public ProblemView toProblemView() {
        return new ProblemView(id, text, null, score, "essay");
    }
}
