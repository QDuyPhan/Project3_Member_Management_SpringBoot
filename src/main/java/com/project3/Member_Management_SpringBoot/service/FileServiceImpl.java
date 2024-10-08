/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project3.Member_Management_SpringBoot.service;


import com.project3.Member_Management_SpringBoot.model.Member;
import com.project3.Member_Management_SpringBoot.repository.MemberRepository;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 *
 * @author buing
 */
@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final MemberRepository fileRepository;

    @Override
    public Boolean hasCSVformat(MultipartFile file) {
        String contentType = file.getContentType();
        System.out.println(contentType);
        return contentType != null && (contentType.equals("application/vnd.ms-excel") || contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet") || contentType.equals("application/vnd.ms-office"));
    }

    @Override
    public void processAndSaveData(MultipartFile file) {
        try
        {
            List<Member> members = csvToMembers(file.getInputStream());
            System.out.println("Process save members: " + members.size());
            for (Member member : members)
            {
                System.out.println(member.getId());
            }
            fileRepository.saveAll(members);
        } catch (IOException ex)
        {
            Logger.getLogger(FileServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private List<Member> csvToMembers(InputStream inputStream) {
        try (Workbook workbook = WorkbookFactory.create(inputStream))
        {
            Sheet sheet = workbook.getSheetAt(0); // Chọn sheet đầu tiên, bạn có thể thay đổi nếu cần
            List<Member> members = new ArrayList<>();

            Iterator<Row> rowIterator = sheet.iterator();
            // Bỏ qua dòng tiêu đề
            if (rowIterator.hasNext())
            {
                rowIterator.next();
            }

            while (rowIterator.hasNext())
            {
                Row row = rowIterator.next();

                // Đọc dữ liệu từ mỗi ô trong dòng và tạo thành đối tượng Member
                Member member = new Member();
                member.setId(Integer.valueOf(row.getCell(0).getStringCellValue()));
                member.setName(row.getCell(1).getStringCellValue());
                member.setDepartment(row.getCell(2).getStringCellValue());
                member.setMajor(row.getCell(3).getStringCellValue());
                member.setPhone( row.getCell(4).getStringCellValue());
                member.setPassword( row.getCell(5).getStringCellValue());
                member.setEmail(row.getCell(6).getStringCellValue());

                members.add(member);
            }

            return members;
        } catch (Exception ex)
        {
            ex.printStackTrace(); 
        }
        return Collections.emptyList();

    }

}
