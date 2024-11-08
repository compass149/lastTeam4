package com.projectdemo1.board4.controller;


import com.projectdemo1.board4.dto.CboardDTO;
import com.projectdemo1.board4.dto.CpageRequestDTO;
import com.projectdemo1.board4.dto.CpageResponseDTO;
import com.projectdemo1.board4.dto.upload.CuploadFileDTO;
import com.projectdemo1.board4.service.CboardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Controller
@RequestMapping("/cboard")
@Log4j2
@RequiredArgsConstructor
public class CboardController {

    @Autowired
    private CboardService cboardService;

    @Value("${com.team4project.board4.upload.path}")
    private String uploadPath; // 업로드 경로

    @GetMapping("/clist")
    public void list(CpageRequestDTO cpageRequestDTO, Model model) {

        CpageResponseDTO<CboardDTO> cpageresponseDTO = cboardService.list(cpageRequestDTO);
        log.info(cpageresponseDTO);
        model.addAttribute("cpageresponseDTO", cpageRequestDTO);
    }
    @GetMapping("/cregister")
    public void registerGET() {
    }

    @PostMapping("/cregister")
    public String registerPOST(CuploadFileDTO cuploadFileDTO,
                               CboardDTO cboardDTO,
                               RedirectAttributes redirectAttributes) {
        List<String> strFileName = null;
        if (cuploadFileDTO.getFiles() != null &&
                !cuploadFileDTO.getFiles().get(0).getOriginalFilename().equals("")) {
            strFileName = fileUpload(cuploadFileDTO);
        }
        cboardDTO.setFileNames(strFileName);

        log.info("board POST register....");

        log.info(cboardDTO);
        Long cno = cboardService.register(cboardDTO);
        return "redirect:/cboard/clist";
    }

    @GetMapping({"/cread", "/cmodify"})
    public void read(Long cno, CpageRequestDTO cpageRequestDTO, Model model) {
        CboardDTO cboardDTO = cboardService.readOne(cno);
        log.info(cboardDTO);
        model.addAttribute("dto", cboardDTO);
    }
    @PostMapping("/cmodify")
    public String modify(CuploadFileDTO cuploadFileDTO, CpageRequestDTO cpageRequestDTO,
                         @Valid CboardDTO cboardDTO,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("board modify post...." + cboardDTO);

        List<String> strFileNames = null;
        if (cuploadFileDTO.getFiles() != null &&
                !cuploadFileDTO.getFiles().get(0).getOriginalFilename().equals("")) { //첨부파일이 있을 때

            List<String> fileNames = cboardDTO.getFileNames(); //기존 파일명을 가져와서

            if(fileNames != null && fileNames.size() > 0){
                removeFile(fileNames); //기존 파일 삭제
            }
            strFileNames = fileUpload(cuploadFileDTO); //새로운 파일 업로드
            log.info("strFileNames: " + strFileNames.size());
            cboardDTO.setFileNames(strFileNames); //새로운 파일로 boardDTO에 저장
        }
        if (bindingResult.hasErrors()) {
            log.info("has errors.......");
            String link = cpageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("cno", cboardDTO.getCno());
            return "redirect:/cboard/cmodify?" + link;
        }
        cboardService.modify(cboardDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("cno", cboardDTO.getCno());
        return "redirect:/cboard/cread";

    }
    @PostMapping("/cremove")
    public String remove(CboardDTO cboardDTO, RedirectAttributes redirectAttributes) {
        log.info("remove post.. " + cboardDTO);
        List<String> fileNames = cboardDTO.getFileNames();

        if(fileNames != null && fileNames.size() > 0){
            removeFile(fileNames);
        } //첨부파일 삭제는 modify에서 처리

        cboardService.remove(cboardDTO.getCno()); //여기까지는 게시글 삭제


        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/cboard/clist";
    }

    private List<String> fileUpload(CuploadFileDTO cuploadFileDTO) {

        List<String> list = new ArrayList<>();
        cuploadFileDTO.getFiles().forEach(multipartFile -> {
            String originalName = multipartFile.getOriginalFilename();
            log.info(originalName);

            String uuid = UUID.randomUUID().toString();
            Path savePath = Paths.get(uploadPath, uuid + "_" + originalName);
            boolean image = false;
            try {
                multipartFile.transferTo(savePath);
                if (Files.probeContentType(savePath).startsWith("image")) {
                    image = true;
                    File thumbFile = new File(uploadPath, "s_" + uuid + "_" + originalName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumbFile, 100, 100);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.add(uuid + "_" + originalName);
        });

        return list;
    }

    @GetMapping("/view/{fileName}")
    @ResponseBody
    public ResponseEntity<Resource> viewFileGet(@PathVariable("fileName") String fileName) {
        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();


        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().headers(headers).body(resource);
    }

    private void removeFile(List<String> fileNames) {
        log.info(fileNames.size());

        for (String fileName : fileNames) {
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();
            boolean removed = false;

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                removed = resource.getFile().delete();

                if (contentType.startsWith("image")) {
                    String fileName1 = fileName.replace("s_", "");
                    File originalFile = new File(uploadPath + File.separator + fileName1);
                    originalFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }

        }
    }
    public String removeFile(@RequestParam("fileName") String fileName){
        log.info(fileName);


        Resource resource = new FileSystemResource(uploadPath+File.separator + fileName);
        String resourceName = resource.getFilename();
        Map<String, Boolean> resultMap = new HashMap<>();
        boolean removed = false;
        try {
            String contentType = Files.probeContentType(resource.getFile().toPath());
            removed = resource.getFile().delete();
            //섬네일이존재한다면
            if(contentType.startsWith("image")){
                String fileName1 = fileName.replace("s_", "");
                File originalFile = new File(uploadPath+File.separator + fileName1);
                originalFile.delete();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        resultMap.put("result", removed);
        return "/cupload/cuploadForm";
    }


}
