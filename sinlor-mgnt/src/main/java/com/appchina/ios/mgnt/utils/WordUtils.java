package com.appchina.ios.mgnt.utils;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class WordUtils {

    /**
     * 根据模板导出word2003
     */
    public static void createWord2003(Map<String, Object> dataMap, String file) {

        try {
            Configuration configuration = null;
            configuration = new Configuration();
            configuration.setDefaultEncoding("UTF-8");
            configuration.setDirectoryForTemplateLoading(new File(file));
            Template t = configuration.getTemplate("resume.ftl"); // 文件名
            File outFile = new File("/Users/liuxinglong/Desktop/resume/word2003" + Math.random() * 10000 + ".doc");
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8"));
            t.process(dataMap, out);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

    }

    /**
     * 根据模板导出word2007
     */
    public static void createWord2007(Map<String, Object> params, String file, String outfile) {
        try {
            InputStream is = new FileInputStream(file);

            CustomXWPFDocument doc = new CustomXWPFDocument(is);
            // 替换段落里面的变量
            replaceInPara(doc, params);
            // 替换表格里面的变量
            replaceInTable(doc, params);
            OutputStream os = new FileOutputStream(outfile);
            doc.write(os);
            close(os);
            close(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 替换段落里面的变量
     * 
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceInPara(CustomXWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFParagraph> iterator = doc.getParagraphsIterator();
        XWPFParagraph para;
        while (iterator.hasNext()) {
            para = iterator.next();
            replaceInPara(para, params, doc);
        }
    }

    private static Matcher matcher(String str) {
        Pattern pattern = Pattern.compile("\\$\\{(.+?)\\}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(str);
        return matcher;
    }

    /**
     * 替换表格里面的变量
     * 
     * @param doc 要替换的文档
     * @param params 参数
     */
    private static void replaceInTable(CustomXWPFDocument doc, Map<String, Object> params) {
        Iterator<XWPFTable> iterator = doc.getTablesIterator();
        XWPFTable table;
        List<XWPFTableRow> rows;
        List<XWPFTableCell> cells;
        List<XWPFParagraph> paras;
        while (iterator.hasNext()) {
            table = iterator.next();
            rows = table.getRows();
            for (XWPFTableRow row : rows) {
                cells = row.getTableCells();
                for (XWPFTableCell cell : cells) {
                    paras = cell.getParagraphs();
                    for (XWPFParagraph para : paras) {
                        replaceInPara(para, params, doc);
                    }
                }
            }
        }
    }

    /**
     * 替换段落里面的变量
     * 
     * @param para 要替换的段落
     * @param params 参数
     */
    private static void replaceInPara(XWPFParagraph para, Map<String, Object> params, CustomXWPFDocument doc) {
        List<XWPFRun> runs;
        Matcher matcher;
        if (matcher(para.getParagraphText()).find()) {
            runs = para.getRuns();
            for (int i = 0; i < runs.size(); i++) {
                XWPFRun run = runs.get(i);
                String runText = run.toString();
                matcher = matcher(runText);
                if (matcher.find()) {
                    // boolean isImage = false;
                    while ((matcher = matcher(runText)).find()) {
                        if (params.get(matcher.group(1)) instanceof String) {// 文本替换
                            runText = matcher.replaceFirst(String.valueOf(params.get(matcher.group(1))));
                            // 直接调用XWPFRun的setText()方法设置文本时，在底层会重新创建一个XWPFRun，把文本附加在当前文本后面，
                            // 所以我们不能直接设值，需要先删除当前run,然后再自己手动插入一个新的run。
                            para.removeRun(i);
                            XWPFRun newRun = para.insertNewRun(i);
                            newRun.setBold(true);
                            newRun.setColor("FF0000");
                            newRun.setItalic(true);
                            newRun.setFontFamily("monospace,楷体");
                            newRun.setText(runText);
                        } else if (params.get(matcher.group(1)) instanceof Map) {// 图片替换
                            runText = matcher.replaceFirst("");
                            para.removeRun(i);
                            para.insertNewRun(i).setText(runText);
                            Map pic = (Map) params.get(matcher.group(1));
                            int width = Integer.parseInt(pic.get("width").toString());
                            int height = Integer.parseInt(pic.get("height").toString());
                            int picType = getPictureType(pic.get("type").toString());
                            byte[] byteArray = (byte[]) pic.get("content");
                            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
                            try {
                                doc.addPictureData(byteInputStream, picType);
                                doc.createPicture(doc.getAllPictures().size() - 1, width, height, para);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 根据图片类型，取得对应的图片类型代码
     * 
     * @param picType
     * @return int
     */
    private static int getPictureType(String picType) {
        int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
        if (picType != null) {
            if (picType.equalsIgnoreCase("png")) {
                res = CustomXWPFDocument.PICTURE_TYPE_PNG;
            } else if (picType.equalsIgnoreCase("dib")) {
                res = CustomXWPFDocument.PICTURE_TYPE_DIB;
            } else if (picType.equalsIgnoreCase("emf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_EMF;
            } else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg")) {
                res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
            } else if (picType.equalsIgnoreCase("wmf")) {
                res = CustomXWPFDocument.PICTURE_TYPE_WMF;
            }
        }
        return res;
    }

    /**
     * 将输入流中的数据写入字节数组
     * 
     * @param in
     * @return
     */
    public static byte[] inputStream2ByteArray(InputStream in, boolean isClose) {
        byte[] byteArray = null;
        try {
            int total = in.available();
            byteArray = new byte[total];
            in.read(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isClose) {
                try {
                    in.close();
                } catch (Exception e2) {
                    System.out.println("关闭流失败");
                }
            }
        }
        return byteArray;
    }

    /**
     * 关闭输入流
     * 
     * @param is
     */
    private static void close(InputStream is) {
        if (is != null) {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭输出流
     * 
     * @param os
     */
    private static void close(OutputStream os) {
        if (os != null) {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // WordUtils.createWord2003();
        // WordUtils.createWord2007();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", "sinlor");
        params.put("sex", "男");
        params.put("birthday", "1970-01-01");
        params.put("telephone", "123456");
        params.put("email", "qq@qq.com");
        params.put("address", "北京");
        params.put("education", "博士");
        params.put("company", "google");
        params.put("position", "总裁");
        params.put("content", "指挥部下工作");
        params.put("certificate", "世界首富");
        params.put("skill", "能用挖掘机炒菜");
        params.put("introduce", "每天被自己帅醒");
        Map<String, Object> header = new HashMap<String, Object>();
        header.put("width", 100);
        header.put("height", 150);
        header.put("type", "jpg");
        try {
            header.put("content", WordUtils.inputStream2ByteArray(new FileInputStream(
                    "/Users/liuxinglong/Desktop/证书/images/handsome.jpg"), true));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        params.put("header", header);
        String file = "/Users/liuxinglong/Desktop/resume/demo.docx";
        String suffix = System.currentTimeMillis() + "_" + (int) Math.random() * 10000 + ".doc";
        String outfile = "/Users/liuxinglong/Desktop/resume/" + suffix;
        WordUtils.createWord2007(params, file, outfile);
    }
}
