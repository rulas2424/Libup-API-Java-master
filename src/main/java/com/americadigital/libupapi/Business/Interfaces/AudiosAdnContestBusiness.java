package com.americadigital.libupapi.Business.Interfaces;

import com.americadigital.libupapi.Pojos.Contest.AddContestNotAudio;
import com.americadigital.libupapi.WsPojos.Request.Contest.AllContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.ChangeStatusContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.ContestShopRequest;
import com.americadigital.libupapi.WsPojos.Request.Contest.TerminateContestRequest;
import com.americadigital.libupapi.WsPojos.Request.Notifications.NotificationAcrRequest;
import com.americadigital.libupapi.WsPojos.Response.Contest.ContestGetResponse;
import com.americadigital.libupapi.WsPojos.Response.Contest.ContestListResponse;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AudiosAdnContestBusiness {
    ResponseEntity<HeaderResponse> uploadAudioAndAddContest(String audioTitle, String dataType, String bucketName, MultipartFile file, String idAdmin, String idPromo, String idShop, String idBroadcaster);

    ResponseEntity<HeaderResponse> createContestNotAudio(AddContestNotAudio request);

    ResponseEntity<HeaderResponse> updateStatusContest(ChangeStatusContestRequest changeStatusContestRequest);

    ResponseEntity<HeaderResponse> updateAudioAndContest(String audioTitle, Optional<MultipartFile> file, String idContest);

    ResponseEntity<ContestGetResponse> getContestByIdAcr(String idAcr);

    ResponseEntity<ContestListResponse> getListContestByIdBroadcaster(AllContestRequest allContestRequest);

    ResponseEntity<ContestListResponse> getListContestByIdShop(ContestShopRequest contestShopRequest);

    ResponseEntity<HeaderResponse> sendNotifications(NotificationAcrRequest notificationAcrRequest);

    ResponseEntity<HeaderResponse> terminateContest(TerminateContestRequest terminateContestRequest);
}
