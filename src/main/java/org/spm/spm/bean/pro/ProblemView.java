package org.spm.spm.bean.pro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProblemView {
    String id;
    String text;
    List<String> args;
    double score;
    String type;
}
