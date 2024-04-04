package unigram.demo.service;

import unigram.demo.dto.AnnouncementDto;
import unigram.demo.dto.ClubDto;

import java.util.List;

public interface AnnouncementService {
    AnnouncementDto getById(Long id);

    List<AnnouncementDto> getAnnouncements();

    AnnouncementDto save(AnnouncementDto announcementDto);

    Boolean delete(Long id);

    AnnouncementDto update(Long id, AnnouncementDto announcementDto);
}
