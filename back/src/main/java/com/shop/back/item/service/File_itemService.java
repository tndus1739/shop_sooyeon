package com.shop.back.item.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shop.back.item.entity.File_item;
import com.shop.back.item.repository.File_itemRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class File_itemService {
	
	@Value("${itemImgLocation}")
	private String itemImgLocation;	    // C:/shop/item 
	
	private final File_itemRepository file_itemRepository;
	private final FileService fileService;
	
	
	public void saveItemImg ( File_item file_item , MultipartFile itemImgFile) throws IOException {
		
		// oriImgName : MultipartFile에서 넘어오는 원본이미지 이름을 
        String originImgName = itemImgFile.getOriginalFilename();		// 원본 이미지 파일 이름 
        String imgName = "";										    // 서버에 저장할 이미지 이름 
        String imgUrl = "";											    // 이미지 URL
        
        // 내용있는 경우
        if (!StringUtils.isEmpty(originImgName)) {
        	imgName = fileService.uploadFile(itemImgLocation, originImgName, itemImgFile.getBytes());
        	imgUrl = "/images/item/" + imgName;
        }
        
        file_item.updateItemImg(imgName, imgUrl, originImgName);
        file_itemRepository.save(file_item);
        

	}
}