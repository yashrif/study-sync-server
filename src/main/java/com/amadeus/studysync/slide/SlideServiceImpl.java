package com.amadeus.studysync.slide;

import com.amadeus.studysync.exception.NotFoundException;
import com.amadeus.studysync.upload.Upload;
import com.amadeus.studysync.upload.UploadRepository;
import com.amadeus.studysync.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SlideServiceImpl implements SlideService {

    private final SlideRepository repository;
    private final UploadRepository uploadRepository;

    @Override
    public List<Slide> findAll(Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findAllByUser(user.getId());
    }

    @Override
    public Slide findById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        return repository.findByIdAndUser(theId, user.getId()).orElse(null);
    }

    @Override
    public Slide save(SlidePostRequest request) {

        Slide slide = Slide.builder()
                .id(request.getId())
                .name(request.getName())
                .content(request.getContent())
                .build();

        if (request.getUploads() == null) {
            return repository.save(slide);
        }
        request.getUploads().forEach(upload -> {
            Upload theUpload = uploadRepository.findById(upload.getId())
                    .orElseThrow(() -> new NotFoundException("Slide not found with id - " + upload.getId()));
            slide.addUpload(theUpload);
        });

        return repository.save(slide);
    }

    @Override
    public void deleteById(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Slide not found with id - " + theId));

        repository.deleteById(theId);
    }

    @Override
    public Slide update(Slide theSlide, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        repository.findByIdAndUser(theSlide.getId(), user.getId())
                .orElseThrow(() -> new NotFoundException("Slide not found with id - " + theSlide.getId()));
        return repository.save(theSlide);
    }

    @Override
    public Slide partialUpdate(UUID theId, SlidePatchRequest updates, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Slide slide = repository.findByIdAndUser(theId, user.getId())
                .orElseThrow(() -> new NotFoundException("Slide not found with id - " + theId));

        slide.setName(updates.getName() != null ? updates.getName() : slide.getName());
        slide.setContent(updates.getContent() != null ? updates.getContent() : slide.getContent());


        return repository.save(slide);
    }

    @Override
    public Slide findSlideByIdJoinFetch(UUID theId, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        Optional<Slide> slide = repository.findSlideByIdJoinFetch(theId, user.getId());
        return slide.orElseGet(() -> repository.findById(theId).orElse(null));
    }

}

