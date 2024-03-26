package unigram.demo.service.impl;

import unigram.demo.dao.entity.Announcement;
import unigram.demo.dao.entity.Club;
import unigram.demo.dto.AnnouncementDto;
import unigram.demo.dto.ClubDto;
import unigram.demo.repository.AnnouncementRepository;
import unigram.demo.repository.ClubRepository;
import unigram.demo.service.AnnouncementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;


    @Override
    public AnnouncementDto getById(Long id) {
        Announcement p = announcementRepository.getReferenceById(id);
        AnnouncementDto announcementDto = new AnnouncementDto();
        announcementDto.setTitle(p.getTitle());
        announcementDto.setId(p.getId());
        announcementDto.setContent(p.getContent());
        announcementDto.setPhotoUrl((p.getPhotoUrl()));
        announcementDto.setAnnouncementDate(p.getAnnouncementDate());
        return announcementDto;
    }
    @Override
    public List<AnnouncementDto> getAnnouncements() {
        List<Announcement> data = announcementRepository.findAll();
        List<AnnouncementDto> announcementDtos = data.stream()
                .map(announcement -> {
                    AnnouncementDto announcementDto = new AnnouncementDto();
                    announcementDto.setId(announcement.getId());
                    announcementDto.setTitle(announcement.getTitle());
                    announcementDto.setContent(announcement.getContent());
                    announcementDto.setPhotoUrl(announcement.getPhotoUrl());
                    announcementDto.setAnnouncementDate(announcement.getAnnouncementDate());
                    return announcementDto;
                })
                .collect(Collectors.toList());

        return announcementDtos;
    }
}
