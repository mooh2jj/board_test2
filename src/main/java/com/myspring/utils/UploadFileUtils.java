package com.myspring.utils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		
		UUID uuid = UUID.randomUUID();		// ���� ���� �ĺ���, universally unique identifier
		
		String savedName = uuid.toString()+"_"+originalName;
		
		String savedPath = calcPath(uploadPath);  
		
		File target = new File(uploadPath + savedPath, savedName);		// parent: uploadPath + savedPath, child: savedName
		// uploadPath + savedPath:  I://temp+��¥
		// FileCopyUtils.copy(����Ʈ�迭, ���ϰ�ü); �ӽ� ���丮�� ���ε�� ������ ������ ���丮�� ����
		FileCopyUtils.copy(fileData, target);		// FileCopyUtils.copy(inputStream in, outputStream out)
		
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);	// ������
		String uploadedFileName = null;
		
		// �̹��� ������ ����� ���
		if(MediaUtils.getMediaType(formatName) != null) {	// MediaUtils ���� formatName �빮�ڷ� ����
			// ����� ����
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
			// �������� ������	
		}else {
			// ������ ���� , �������� ����??
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		
		return uploadedFileName;
	}
	

	// ��¥�� ���丮 ����
	private static String calcPath(String uploadPath) {

		Calendar cal = Calendar.getInstance();	// �̱�������
		// File.separator : ������ ���丮 ������(\\)
		// ����, ex) \\2017
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		System.out.println(yearPath);
		// ��, ex) \\2017\\03
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH));
		System.out.println(monthPath);
		// ��¥, ex) \\2017\\03\\01
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		System.out.println(datePath);
		
		// ���丮 ���� �޼��� ȣ��
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;
	}

	// ���丮 ����
	private static void makeDir(String uploadPath, String... paths) {		// String... paths -> String ������ ǥ��
		// ���丮�� �����ϸ�
		if(new File(paths[paths.length - 1]).exists()) {		// �ε��� ����
			return;
		}
		// ���丮�� �������� ������
		for(String path : paths) {
			File dirPath = new File(uploadPath+path);
			// ���丮�� �������� ������
			if(!dirPath.exists()) {
				dirPath.mkdir();	// ���丮 ���� ����
			}
		}		
	}
	
	// ������̹��� ���� -> s_�ٿ��� ����� �̹���
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception{
		
		// �̹����� �б� ���� ����
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));	// �̹��� ������ �о�ͼ� BufferedImage�� ����
		// 100�ȼ� ������ ����� ����
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);		// Scalr : �̹��� ������¡ Ŭ����
		// ������� �̸��� ����(�������ϸ� 's_'�� ����)
		String thumbnailName = uploadPath + path + File.separator + "s_"+fileName;
		File newFile = new File(thumbnailName);		// ����� �̹��� ���� ����
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		// ����� ����
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);		// destImg ������� formatName Ȯ���ڷ� �� �̹��������� write(������)!
		// ������� �̸��� ������
		
		// File.separatorChar : ���丮 ������
		// ������ \, ���н�(������) /
		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '\\');		
		// substring(n) : uploadPath = I://temp ���Ŀ� '\\'�� replace�Ѵ�.
		
	}

	// ������ ���� : ������̹����� ���ϵ��� �����ϰ� ���� ����
	private static String makeIcon(String uploadPath, String path, String fileName)throws Exception {
		// ������ �̸�
		String iconName = uploadPath + path + File.separator + fileName;
		// ������ �̸��� ����
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '\\');
	}

}
