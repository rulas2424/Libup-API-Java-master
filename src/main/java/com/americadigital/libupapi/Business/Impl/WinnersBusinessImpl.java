package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.WinnersBusiness;
import com.americadigital.libupapi.Dao.Entity.WinnersEntity;
import com.americadigital.libupapi.Dao.Interfaces.Broadcaster.BroadcasterDao;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserEntityDao;
import com.americadigital.libupapi.Dao.Interfaces.Winners.WinnersEntityDao;
import com.americadigital.libupapi.Dao.Interfaces.Winners.WinnersPagDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Winners.*;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.Winners.WinnersRequest;
import com.americadigital.libupapi.WsPojos.Request.Winners.WinnersShopRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Winners.WinnerListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("winnersBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class WinnersBusinessImpl implements WinnersBusiness {

    private static final Logger LOG = LoggerFactory.getLogger(WinnersBusinessImpl.class);

    @Autowired
    private WinnersEntityDao winnersEntityDao;

    @Autowired
    private WinnersPagDao winnersPagDao;

    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private BroadcasterDao broadcasterDao;

    @Override
    public ResponseEntity<WinnerListResponse> findAllWinnersByIdBroadcaster(WinnersRequest winnersRequest) {
        broadcasterDao.findById(winnersRequest.idBroadcaster).orElseThrow(()-> new ConflictException(ConstantsTexts.BROADCASTER_INVALID));
        Page<WinnersEntity> allListWinners = winnersPagDao.getAllWinnersByBroadcaster(winnersRequest.idBroadcaster, winnersRequest.typeWinner.toString(), PageRequest.of(winnersRequest.page, winnersRequest.maxResults));
        return extractDataWinners(allListWinners);
    }

    @Override
    public ResponseEntity<WinnerListResponse> findHistoryWinnerByUserId(String userId) {
        String msg;
        WinnerListResponse response;
        userEntityDao.findByIdUser(userId).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        List<WinnersEntity> allHistoryWinnerByIdUser = winnersEntityDao.getAllHistoryWinnerByIdUser(userId);
        if (allHistoryWinnerByIdUser.isEmpty()) {
            msg = ConstantsTexts.NOT_AWARDS;
            LOG.info(msg);
            response = new WinnerListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<Winners> winnerList = new ArrayList<>();
        allHistoryWinnerByIdUser.forEach(winner -> {
            List<SucursalesPromoShopWinners> sucursalesPromoShopWinners = new ArrayList<>();
            winner.tContestEntity.getPromoEntity().getrPromoBranchEntity().forEach(branch -> {
                sucursalesPromoShopWinners.add(new SucursalesPromoShopWinners(branch.branchEntity.idBranch, branch.branchEntity.address, branch.branchEntity.phoneNumber, branch.branchEntity.type, branch.branchEntity.latitud, branch.branchEntity.longitud));
            });
            winnerList.add(new Winners(winner.idWinner, winner.statusWinner, new ContestWinner(winner.tContestEntity.getIdContest(), winner.tContestEntity.getIdAdmin(), new AwardWinner(winner.tContestEntity.getPromoEntity().idPromo, winner.tContestEntity.getPromoEntity().getName(),
                    winner.tContestEntity.getPromoEntity().getDescription(), winner.tContestEntity.getPromoEntity().getUrlTerms(), winner.tContestEntity.getPromoEntity().getUrlPromo(), winner.tContestEntity.getPromoEntity().getImage(), ConstantsTexts.fecha.format(winner.tContestEntity.getPromoEntity().getReleaseDate()), ConstantsTexts.fecha.format(winner.tContestEntity.getPromoEntity().getDueDate()),
                    winner.tContestEntity.getPromoEntity().getPromoType(), winner.tContestEntity.getPromoEntity().getCode(), winner.tContestEntity.getPromoEntity().isActive(),
                    new ShopWinner(winner.tContestEntity.getPromoEntity().getIdShop(), winner.tContestEntity.getPromoEntity().getShopEntity().name, winner.tContestEntity.getPromoEntity().getShopEntity().active, winner.tContestEntity.getPromoEntity().getShopEntity().image, sucursalesPromoShopWinners)), winner.tContestEntity.getStatusContest(), winner.tContestEntity.getIdAcr(), winner.tContestEntity.getAudioTitle(), winner.tContestEntity.getAudio(), ConstantsTexts.fecha.format(winner.tContestEntity.getReleaseDate()), winner.tContestEntity.getDataType(), winner.tContestEntity.getBucketName()), new UserWinner(winner.userEntity.idUser, winner.userEntity.name, winner.userEntity.lastName, winner.userEntity.email, winner.userEntity.name_profile, winner.userEntity.phoneNumber, winner.userEntity.accountType), winner.tickCount, ConstantsTexts.fecha.format(winner.winningDate), winner.typeWinner, winner.idBroadcaster, winner.broadcasterEntity == null ? "" : winner.broadcasterEntity.name));
        });
        msg = ConstantsTexts.WINNER_USERS;
        response = new WinnerListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), winnerList, 0, 0l);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WinnerListResponse> getAllWinnersByIdShop(WinnersShopRequest winnersShopRequest) {
        shopDao.findByIdShop(winnersShopRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        Page<WinnersEntity> allWinnersByShop = winnersPagDao.getAllWinnersByShop(winnersShopRequest.idShop, PageRequest.of(winnersShopRequest.page, winnersShopRequest.maxResults));
        return extractDataWinners(allWinnersByShop);
    }

    @Override
    public ResponseEntity<HeaderResponse> claimAward(String idWinner) {
        String msg;
        HeaderResponse response;
        WinnersEntity winnersEntity = winnersEntityDao.findWinnerById(idWinner).orElseThrow(() -> new ConflictException(ConstantsTexts.WINNER_INVALID));
        winnersEntity.statusWinner = WinnersEntity.StatusWinner.Reclamado;
        winnersEntityDao.save(winnersEntity);
        msg = ConstantsTexts.WINNER_STATUS;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private ResponseEntity<WinnerListResponse> extractDataWinners(Page<WinnersEntity> allWinners) {
        String msg;
        WinnerListResponse response;
        if (allWinners.isEmpty()) {
            msg = ConstantsTexts.NOT_AWARDS;
            LOG.info(msg);
            response = new WinnerListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<Winners> winnerList = new ArrayList<>();
        allWinners.forEach(winner -> {
            List<SucursalesPromoShopWinners> sucursalesPromoShopWinners = new ArrayList<>();
            winner.tContestEntity.getPromoEntity().getrPromoBranchEntity().forEach(branch -> {
                sucursalesPromoShopWinners.add(new SucursalesPromoShopWinners(branch.branchEntity.idBranch, branch.branchEntity.address, branch.branchEntity.phoneNumber, branch.branchEntity.type, branch.branchEntity.latitud, branch.branchEntity.longitud));
            });
            winnerList.add(new Winners(winner.idWinner, winner.statusWinner, new ContestWinner(winner.tContestEntity.getIdContest(), winner.tContestEntity.getIdAdmin(), new AwardWinner(winner.tContestEntity.getPromoEntity().idPromo, winner.tContestEntity.getPromoEntity().getName(),
                    winner.tContestEntity.getPromoEntity().getDescription(), winner.tContestEntity.getPromoEntity().getUrlTerms(), winner.tContestEntity.getPromoEntity().getUrlPromo(), winner.tContestEntity.getPromoEntity().getImage(), ConstantsTexts.fecha.format(winner.tContestEntity.getPromoEntity().getReleaseDate()), ConstantsTexts.fecha.format(winner.tContestEntity.getPromoEntity().getDueDate()),
                    winner.tContestEntity.getPromoEntity().getPromoType(), winner.tContestEntity.getPromoEntity().getCode(), winner.tContestEntity.getPromoEntity().isActive(),
                    new ShopWinner(winner.tContestEntity.getPromoEntity().getIdShop(), winner.tContestEntity.getPromoEntity().getShopEntity().name, winner.tContestEntity.getPromoEntity().getShopEntity().active, winner.tContestEntity.getPromoEntity().getShopEntity().image, sucursalesPromoShopWinners)), winner.tContestEntity.getStatusContest(), winner.tContestEntity.getIdAcr(), winner.tContestEntity.getAudioTitle(), winner.tContestEntity.getAudio(), ConstantsTexts.fecha.format(winner.tContestEntity.getReleaseDate()), winner.tContestEntity.getDataType(), winner.tContestEntity.getBucketName()), new UserWinner(winner.userEntity.idUser, winner.userEntity.name, winner.userEntity.lastName, winner.userEntity.email, winner.userEntity.name_profile, winner.userEntity.phoneNumber, winner.userEntity.accountType), winner.tickCount, ConstantsTexts.fecha.format(winner.winningDate), winner.typeWinner, winner.idBroadcaster, winner.idBroadcaster == null ? null : winner.broadcasterEntity.name));
        });
        msg = ConstantsTexts.WINNERS;
        response = new WinnerListResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), winnerList, allWinners.getTotalPages(), allWinners.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
