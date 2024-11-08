package com.projectdemo1.board4.dto.upload;


import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CuploadFileDTO {

    private List<MultipartFile> files;
}
