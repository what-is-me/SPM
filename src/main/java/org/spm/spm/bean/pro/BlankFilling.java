package org.spm.spm.bean.pro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 填空题
 * 第1行 题目种类：blankfilling
 * 第2行 题号（id）
 * 第3行 题面
 * 第4行 总分
 * 第5行 答案，用';'隔开
 */
@AllArgsConstructor
@Builder
@Data
public class BlankFilling implements Problem {
    String type = "blankfilling";
    String id;
    String text;
    List<String> ans = new ArrayList<>();
    double score;

    public BlankFilling(String[] src) throws Exception {
        if (src.length < 5) throw new Exception("填空题格式错误");
        id = src[1];
        text = src[2];
        score = Double.parseDouble(src[3]);
        for (String an : src[4].split(";")) if (!an.equals("")) ans.add(an);
        if (ans.isEmpty()) throw new Exception("答案为空");
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(String.format("blankfilling\n%s\n%s\n%f\n", id, text, score));
        for (String an : ans) {
            ret.append(an).append(";");
        }
        ret.append("\n");
        return ret.toString();
    }

    @Override
    public double check(String[] stuAns) {
        if (stuAns == null) return 0;
        Set<String> ansSet = new HashSet<>(ans);
        for (String stuAn : stuAns) {
            if (ansSet.contains(stuAn)) return score;
        }
        return 0;
    }

    @Override
    public ProblemView toProblemView() {
        return new ProblemView(id, text, null, score, "blankfilling");
    }
}
