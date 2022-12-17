package org.spm.spm.controller;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.spm.spm.bean.User;
import org.spm.spm.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;


@RestController
@RequestMapping("file")
@Slf4j
@Api(tags = "文件操作")
public class FileController {
    private String suffix(String filename) {
        String[] tmp = filename.split("\\.");
        if (tmp.length == 0) return "txt";
        return tmp[tmp.length - 1];
    }

    @Value("${file.url}")
    private String filePath;
    @Value("${prefix}")
    private String prefix;
    @Autowired
    FileMapper fileMapper;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "files", value = "文件", allowMultiple = true, allowEmptyValue = true, paramType = "body", dataTypeClass = MultipartFile[].class),
            @ApiImplicitParam(name = "email", value = "老师邮箱，测试用接口", paramType = "body", dataTypeClass = String.class)
    })
    @ApiOperation(value = "上传文件", notes = "同老师同名文件会直接替换,email参数测试用，禁止暴露给用户")
    @RequestMapping(value = "/upload", method = RequestMethod.POST, consumes = "multipart/form-data")
    public String httpUpload(@RequestParam(value = "email", required = false) String email, @RequestPart("files") MultipartFile[] files, HttpServletRequest req) throws JSONException {
        if (email == null || "".equals(email)) email = ((User) req.getSession().getAttribute("user")).getEmail();
        JSONObject object = new JSONObject();
        for (MultipartFile multipartFile : files) {
            String fileName = multipartFile.getOriginalFilename();  // 文件名
            log.info(fileName);
            File dest = new File(filePath + '/' + email + '/' + fileName);
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();
            }
            try {
                multipartFile.transferTo(dest);
                assert fileName != null;
                org.spm.spm.bean.File file = org.spm.spm.bean.File.builder().email(email).filename(fileName).url("file/download?fileName=" + email + "/" + URLEncoder.encode(fileName, "UTF-8")).build();
                fileMapper.insert(file);
            } catch (Exception e) {
                log.error(e.getMessage());
                object.put("success", 2);
                object.put("result", "程序错误，请重新上传");
                return object.toString();
            }
        }

        object.put("success", 1);
        object.put("result", "文件上传成功");
        return object.toString();
    }

    @ApiOperation("当前用户或者输入email对应用户的文件库")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public List<org.spm.spm.bean.File> fileOf(@RequestParam(value = "email", required = false) String email, HttpServletRequest req) {
        if (email == null || "".equals(email)) email = ((User) req.getSession().getAttribute("user")).getEmail();
        List<org.spm.spm.bean.File> files = fileMapper.fileOf(email);
        for (org.spm.spm.bean.File file : files) {
            file.setUrl(prefix + '/' + file.getUrl());
        }
        return files;
    }

    @ApiOperation(value = "删除文件", notes = "只有拥有者才能删除，email参数测试用，禁止暴露给用户")
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public String fileDel(@RequestParam("filename") String filename, @RequestParam(value = "email", required = false) String email, HttpServletRequest req) {
        if (email == null || "".equals(email)) email = ((User) req.getSession().getAttribute("user")).getEmail();
        new File(filePath + '/' + email + '/' + filename).deleteOnExit();
        try {
            fileMapper.delete(email, filename);
        } catch (Exception e) {
            return "数据库问题，删除失败";
        }
        return "true";
    }

    @ApiOperation(value = "下载文件", notes = "pdf可以直接预览")
    @ApiImplicitParam(name = "fileName", value = "文件名:{邮箱}/{文件名}")
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void fileDownLoad(HttpServletResponse response, @RequestParam("fileName") String fileName) throws IOException {
        //fileName = java.net.URLDecoder.decode(fileName, "UTF-8");
        log.info(fileName);
        File file = new File(filePath + '/' + fileName);
        if (!file.exists()) {
            return;
        }
        response.reset();
        String suf = suffix(fileName);
        if ("md".equals(suf)) {
            response.setContentType("text/html;charset:utf-8;");
            response.setCharacterEncoding("utf-8");
            try {
                StringBuilder texts = new StringBuilder();
                InputStreamReader isr = new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8);//加上编码转换
                BufferedReader read = new BufferedReader(isr);
                String line;
                while ((line = read.readLine()) != null) {
                    texts.append(line).append("\n");
                }
                read.close();
                String html = markdownToHtmlExtensions(texts.toString());
                //log.info(html);
                response.getWriter().println(html);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return;
        }
        try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()))) {
            byte[] buff = new byte[1024];
            OutputStream os = response.getOutputStream();
            int i;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            return;
        }
        if ("pdf".equals(suf)) {
            response.setContentType("application/pdf");
            return;
        } else if ("jpg".equals(suf)) {
            response.setContentType("application/jpeg");
            return;
        } else if ("png".equals(suf)) {
            response.setContentType("application/png");
            return;
        } else if ("mp4".equals(suf)) {
            response.setContentType("video/mp4");
            return;
        } else if ("mp3".equals(suf)) {
            response.setContentType("audio/mp3");
            return;
        } else if ("txt".equals(suf)) {
            response.setContentType("text/plain;charset:utf-8;");
            return;
        } else response.setContentType("multipart/form-data");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));//URLEncoder.encode(fileName, ENCODING));
        //log.info(java.net.URLEncoder.encode(fileName, "UTF-8"));
        //response.setHeader("Content-Disposition","attachment;filename=" + new String(fileName.getBytes(ENCODING), "ISO8859-1"));
    }

    private String markdownToHtmlExtensions(String markdown) {
        return "<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"utf-8\"/>\n" +
                "    <title>Markdown</title>\n" +
                "    <link rel=\"stylesheet\" href=\"https://sindresorhus.com/github-markdown-css/github-markdown.css\">" +
                "</head>\n" +
                "<body>\n" +
                "<div id=\"content\"></div>\n" +
                "<script src=\"https://cdn.jsdelivr.net/npm/marked/marked.min.js\"></script>\n" +
                "<script>\n" +
                "    document.getElementById('content').innerHTML =\n" +
                "        marked.parse(" + new Gson().toJson(markdown) + ");\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";
    }
}
