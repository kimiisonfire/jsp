package com.jsp.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.jsp.dto.AttachVO;

public class MakeFileName {
	
	// 파일명과 구분자(delim)를 주면 uuid에 붙여서 파일명으로 쓰려함
	public static String toUUIDFileName(String fileName, String delimiter) {
		String uuid = UUID.randomUUID().toString().replace("-", "");
		return uuid + delimiter + fileName;
	}
	
	// 파일 이름을 가져왔을때 
	public static String parseFileNameFromUUID(String fileName, String delimeter) {
		String[] uuidFileName = fileName.split(delimeter);
		return uuidFileName[uuidFileName.length - 1];
	}
	
	// 
	public static List<AttachVO> parseFileNameFromAttaches(
			List<AttachVO> attachList, String delimeter ) {
		
		List<AttachVO> renameAttachList = new ArrayList<AttachVO>();
		
		for (AttachVO attach : attachList) {
			String fileName = attach.getFileName();		// db상의 fileName
			fileName = parseFileNameFromUUID(fileName, delimeter);	// uuid가 제거된 filename	
			
			attach.setFileName(fileName);
			
			renameAttachList.add(attach);
		}
		return renameAttachList;
	}
	
}
