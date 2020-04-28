package com.adj.xiaotuanti.util;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.IndexAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;

import java.util.ArrayList;
import java.util.List;

public class WordUtils {

    // 标准分词
    public static List<String> getToSeg(String text) {
        List<String> words = new ArrayList<>();
        Result parse = ToAnalysis.parse(text);
        for (Term term : parse) {
            if (null != term.getName() && !term.getName().trim().isEmpty()) {
                words.add(term.getName());
            }
        }
        return words;
    }

    // NLP分词
    public static List<String> getNlpSeg(String text) {
        List<String> words = new ArrayList<String>();
        Result parse = NlpAnalysis.parse(text);
        for (Term term : parse) {
            if (null != term.getName() && !term.getName().trim().isEmpty()) {
                words.add(term.getName());
            }
        }
        return words;
    }

    // Index分词
    public static List<String> getIndexSeg(String text) {
        List<String> words = new ArrayList<String>();
        Result parse = IndexAnalysis.parse(text);
        for (Term term : parse) {
            if (null != term.getName() && !term.getName().trim().isEmpty()) {
                words.add(term.getName());
            }
        }
        return words;
    }
}