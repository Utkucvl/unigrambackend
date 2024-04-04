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
    @Override
    public AnnouncementDto save(AnnouncementDto announcementDto) {

        Announcement announcement = new Announcement();
        announcement.setTitle(announcementDto.getTitle());
        announcement.setContent(announcementDto.getContent());
        announcement.setAnnouncementDate(announcementDto.getAnnouncementDate());
        announcement.setPhotoUrl(announcementDto.getPhotoUrl());
        announcement= announcementRepository.save(announcement);
        announcementDto.setId(announcement.getId());
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(announcement.getId());
        dto.setTitle(announcement.getTitle());
        dto.setContent(announcement.getContent());
        dto.setAnnouncementDate(announcement.getAnnouncementDate());
        dto.setPhotoUrl(announcement.getPhotoUrl());
        return dto;
    }
    @Override
    public Boolean delete(Long id) {
        announcementRepository.deleteById(id);
        return true;
    }

    @Override
    public AnnouncementDto update(Long id, AnnouncementDto announcementDto) {
        Announcement announcementDb = announcementRepository.getOne(id);
        if (announcementDb == null)
            throw new IllegalArgumentException("Activity Does Not Exist ID:" + id);

        announcementDb.setPhotoUrl(announcementDto.getPhotoUrl());
        announcementDb.setAnnouncementDate(announcementDto.getAnnouncementDate());
        announcementDb.setContent(announcementDto.getContent());
        announcementDb.setTitle(announcementDto.getTitle());
        Announcement n = announcementRepository.save(announcementDb);
        AnnouncementDto dto = new AnnouncementDto();
        dto.setId(announcementDb.getId());
        dto.setPhotoUrl(announcementDb.getPhotoUrl());
        dto.setTitle(announcementDb.getTitle());
        dto.setContent(announcementDb.getContent());
        dto.setAnnouncementDate(announcementDb.getAnnouncementDate());
        return dto;
    }
}
