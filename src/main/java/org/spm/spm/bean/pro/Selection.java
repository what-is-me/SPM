package org.spm.spm.bean.pro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.*;

/**
 * 选择题（包括单选和多选）
 * 第1行 题目种类：selection
 * 第2行 题号（id）
 * 第3行 题面
 * 第4行 总分
 * 第5行 答案，用';'隔开
 * 第6行到最后一行 选项，如果是正确答案，需要在行首添加'*'号，不用标出ABCD，会自动生成（前端实现）
 */
@AllArgsConstructor
@Builder
@Data
public class Selection implements Problem {
    String type = "selection";
    String id;
    String text;
    List<String> args = new ArrayList<>();
    List<String> ans = new ArrayList<>();
    double score;

    public Selection(String[] src) throws Exception {
        if (src.length < 6) throw new Exception("选择题格式错误");
        id = src[1];
        text = src[2];
        score = Double.parseDouble(src[3]);
        for (String an : src[4].split(";")) if (!an.equals("")) ans.add(an);
        if (ans.isEmpty()) throw new Exception("答案为空");
        for (int i = 5; i < src.length; i++) {
            if (!"".equals(src[i])) {
                args.add(src[i]);
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(String.format("section\n%s\n%s\n%f\n", id, text, score));
        for (String an : ans) {
            ret.append(an).append(";");
        }
        ret.append("\n");
        for (String arg : args) {
            ret.append(arg).append("\n");
        }
        ret.append("\n");
        return ret.toString();
    }

    @Override
    public double check(String[] stuAns) {
        if (stuAns == null) return 0;
        Set<String> stuAnsSet = new HashSet<>(Arrays.asList(stuAns));
        Set<String> ansSet = new HashSet<>(ans);
        if (stuAnsSet.size() != ansSet.size()) return 0;
        if (stuAnsSet.containsAll(ansSet)) return score;
        return 0;
    }

    @Override
    public ProblemView toProblemView() {
        return new ProblemView(id, text, args, score, "selection");
    }
}
