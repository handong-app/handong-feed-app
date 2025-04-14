package app.handong.feed.service.impl;

import app.handong.feed.domain.TbKaFeed;
import app.handong.feed.dto.TagDto;
import app.handong.feed.dto.TbmessageDto;
import app.handong.feed.exception.data.NotFoundException;
import app.handong.feed.mapper.TbmessageMapper;
import app.handong.feed.repository.TbKaFeedRepository;
import app.handong.feed.repository.TbSubjectTagRepository;
import app.handong.feed.repository.TbUserSearchRepository;
import app.handong.feed.service.FirebaseService;
import app.handong.feed.service.ShortHashService;
import app.handong.feed.service.TbKaFeedService;
import app.handong.feed.service.TbLoggerService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class TbKaFeedServiceImpl implements TbKaFeedService {

    private final TbKaFeedRepository tbKaFeedRepository;
    private final TbmessageMapper tbmessageMapper;
    private final FirebaseService firebaseService;
    private final TbUserSearchRepository tbUserSearchRepository;
    private final TbLoggerService tbLoggerService;
    private final TbSubjectTagRepository tbSubjectTagRepository;

    public TbKaFeedServiceImpl(
            TbKaFeedRepository tbKaFeedRepository,
            TbmessageMapper tbmessageMapper,
            FirebaseService firebaseService,
            TbUserSearchRepository tbUserSearchRepository, TbLoggerService tbLoggerService, TbSubjectTagRepository tbSubjectTagRepository) {
        this.tbKaFeedRepository = tbKaFeedRepository;
        this.tbmessageMapper = tbmessageMapper;
        this.firebaseService = firebaseService;
        this.tbUserSearchRepository = tbUserSearchRepository;
        this.tbLoggerService = tbLoggerService;
        this.tbSubjectTagRepository = tbSubjectTagRepository;
    }

    public Map<String, Object> create(Map<String, Object> param) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        System.out.println(param);
//        Tbuser tbuser = Tbuser.of(param.get("username") + "", param.get("password") + "");
//        tbuserRepository.save(tbuser);
        System.out.println(param);
        TbKaFeed tbKaFeed = TbKaFeed.of(param.get("id") + "", param.get("chat_id") + "", param.get("user_id") + "", param.get("message") + "", param.get("sent_at") + "", param.get("client_message_id") + "");
        tbKaFeedRepository.save(tbKaFeed);
        returnMap.put("id", tbKaFeed.getId());
        return returnMap;
    }

    public Map<String, Object> getLastKaFeed() {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        TbKaFeed tbKaFeed = tbKaFeedRepository.findTopByOrderBySentAtDesc();
        if (tbKaFeed != null) {
            returnMap.put("id", tbKaFeed.getId());
            returnMap.put("sentAt", tbKaFeed.getSentAt());
        } else {
            returnMap.put("id", 0);
            returnMap.put("sentAt", 0);
        }
        return returnMap;
    }

    public List<Map<String, Object>> getAll() {
        List<Map<String, Object>> result = new ArrayList<>();
        List<TbKaFeed> allFeed = tbKaFeedRepository.findAllByDeletedNotOrderBySentAtDesc("Y");

        for (TbKaFeed entry : allFeed) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("id", entry.getId());
            returnMap.put("message", entry.getMessage());
            returnMap.put("sentAt", entry.getSentAt());
            returnMap.put("createdAt", entry.getCreatedDate());
            returnMap.put("modifiedAt", entry.getModifiedDate());
            result.add(returnMap);
        }

        return result;
    }

    public List<TbmessageDto.Detail> scrollList(String type, String userId, String search) {
        return scrollList(type, Integer.MAX_VALUE, userId, search);
    }

    public List<TbmessageDto.Detail> scrollList(String type, int afterSentAt, String userId, String search) {
        String squery = search;
        if(squery == null) squery = "";
        else if(!squery.isEmpty()){
            tbLoggerService.logSearchAsync(userId, type, search);
        }
        List<TbmessageDto.Detail> result = tbmessageMapper.scrollList(type, afterSentAt, userId, search);

        // Process each TbmessageDto.Detail asynchronously
        List<CompletableFuture<TbmessageDto.Detail>> futures = result.stream()
                .map(message -> {
                    // <<-- PROCESS THE TAGS -->>
                    List<TagDto.ReadUserResDto> tags = tbSubjectTagRepository.findByTbSubjectId(message.getSubjectId()).stream().map(subjectTag ->
                            new TagDto.ReadUserResDto(
                                    subjectTag.getTag().getCode(),
                                    subjectTag.getTag().getLabel(),
                                    subjectTag.getTag().getUserDesc(),
                                    subjectTag.getTag().getColorHex()
                            )
                    ).toList();
                    message.setTags(tags);

                    // <<-- PROCESS THE FILE -->>
                    // Fetch file details asynchronously
                    CompletableFuture<List<TbmessageDto.FileDetail>> fileDetailsFuture = tbmessageMapper.fileDetailsAsync(message.getId());

                    // When file details are retrieved, process them asynchronously to get signed URLs
                    return fileDetailsFuture.thenCompose(fileDetails -> {
                        // Asynchronously fetch signed URLs for each file
                        List<CompletableFuture<String>> fileFutures = fileDetails.stream()
                                .map(file -> firebaseService.getSignedUrlAsync("KaFile/" + file.getHash() + "." + file.getExt()))
                                .toList();

                        // Combine all futures for signed URLs and update the message when complete
                        return CompletableFuture.allOf(fileFutures.toArray(new CompletableFuture[0]))
                                .thenApply(v -> {
                                    List<String> signedUrls = fileFutures.stream()
                                            .map(CompletableFuture::join)
                                            .collect(Collectors.toList());
                                    message.setFiles(signedUrls);
                                    return message;  // Return updated message
                                });
                    });
                })
                .toList();

        // Wait for all tasks to complete and collect the results
        List<TbmessageDto.Detail> completedMessages = futures.stream()
                .map(CompletableFuture::join)
                .toList();

        // Sort by sentAt in descending order
        return completedMessages.stream()
                .sorted((m1, m2) -> Integer.compare(m2.getSentAt(), m1.getSentAt()))  // Sorting in descending order
                .collect(Collectors.toList());
    }

    public int count(int afterSentAt, String userId) {
        return tbmessageMapper.countAll(afterSentAt, userId);
    }

    public int count(String userId) {
        return count(Integer.MAX_VALUE, userId);
    }

    public TbmessageDto.Detail getOneHash(String hash) {
        if (hash.length() < ShortHashService.MINLENGTH) return null;
        TbmessageDto.Detail detail = tbmessageMapper.getOneHash(hash, null);
        if (detail == null) return null;
        List<TbmessageDto.FileDetail> fileDetail = tbmessageMapper.fileDetails(detail.getId());
        if (!fileDetail.isEmpty()) {
            detail.setFiles(Collections.singletonList(firebaseService.getSignedUrl("KaFile/" + fileDetail.get(0).getHash() + "." + fileDetail.get(0).getExt(), 60 * 24)));
        }
        return detail;

    }

    public TbmessageDto.Detail getOne(String messageId, String userId) {
        // TODO: Need to change getOneHash to messageId Check
        if (messageId.length() != 32) throw new NotFoundException("GetOne Not Found: " + messageId);
        TbmessageDto.Detail detail = tbmessageMapper.getOneHash(messageId, userId);
        if (detail == null) throw new NotFoundException("GetOne Not Found: " + messageId);

        // Fetch file details asynchronously
        CompletableFuture<List<TbmessageDto.FileDetail>> fileDetailsFuture = tbmessageMapper.fileDetailsAsync(detail.getId());

        // Wait for the file details to complete
        List<TbmessageDto.FileDetail> fileDetails = fileDetailsFuture.join();

        // Fetch signed URLs for each file
        List<CompletableFuture<String>> fileFutures = fileDetails.stream()
                .map(file -> firebaseService.getSignedUrlAsync("KaFile/" + file.getHash() + "." + file.getExt()))
                .toList();

        // Wait for all signed URLs to complete
        CompletableFuture<Void> allSignedUrlsFuture = CompletableFuture.allOf(fileFutures.toArray(new CompletableFuture[0]));
        allSignedUrlsFuture.join(); // Block until all signed URL fetches complete

        // Collect the signed URLs
        List<String> signedUrls = fileFutures.stream()
                .map(CompletableFuture::join) // Join to get the actual signed URL values
                .collect(Collectors.toList());

        // Set the signed URLs to the detail object
        detail.setFiles(signedUrls);

        return detail;  // Return the populated message detail
    }


}
