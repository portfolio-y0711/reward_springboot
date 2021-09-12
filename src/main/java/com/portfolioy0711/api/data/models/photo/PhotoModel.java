package com.portfolioy0711.api.data.models.photo;

import com.portfolioy0711.api.data.entities.Photo;
import com.portfolioy0711.api.data.entities.QPhoto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PhotoModel {

    private final PhotoCmdRepository photoCmdRepository;
    private final JPAQueryFactory query;

    public Photo save(Photo photo) {
        return photoCmdRepository.save(photo);
    }

    public List<Photo> findPhotos() {
        final QPhoto photo = QPhoto.photo;
        return query.select(photo)
                .from(photo)
                .fetch();
    }

    public Photo findPhotoByPhotoId(String photoId) {
       final QPhoto photo = QPhoto.photo;
       return query.select(photo)
               .from(photo)
               .where(photo.photoId.eq(photoId))
               .fetchOne();
    }
}
