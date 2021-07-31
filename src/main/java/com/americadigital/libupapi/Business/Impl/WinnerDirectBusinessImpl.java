package com.americadigital.libupapi.Business.Impl;

import com.americadigital.libupapi.Business.Interfaces.WinnersDirectBusiness;
import com.americadigital.libupapi.Dao.Entity.WinnerDirectEntity;
import com.americadigital.libupapi.Dao.Entity.WinnersEntity;
import com.americadigital.libupapi.Dao.Interfaces.Shop.ShopDao;
import com.americadigital.libupapi.Dao.Interfaces.User.UserEntityDao;
import com.americadigital.libupapi.Dao.Interfaces.WinnersDirect.WinnersDirectDao;
import com.americadigital.libupapi.Dao.Interfaces.WinnersDirect.WinnersDirectPagDao;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Winners.AwardWinner;
import com.americadigital.libupapi.Pojos.Winners.ShopWinner;
import com.americadigital.libupapi.Pojos.Winners.SucursalesPromoShopWinners;
import com.americadigital.libupapi.Pojos.Winners.UserWinner;
import com.americadigital.libupapi.Pojos.WinnersDirect.WinnersDirect;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.WsPojos.Request.Winners.WinnersShopRequest;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.WinnersDirect.WinnerListDirectResponse;
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

@Service("winnerDirectBusinessImpl")
@Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT)
public class WinnerDirectBusinessImpl implements WinnersDirectBusiness {
    private static final Logger LOG = LoggerFactory.getLogger(WinnerDirectBusinessImpl.class);


    @Autowired
    private UserEntityDao userEntityDao;

    @Autowired
    private WinnersDirectDao winnersDirectDao;

    @Autowired
    private ShopDao shopDao;

    @Autowired
    private WinnersDirectPagDao winnersPagDao;


    @Override
    public ResponseEntity<WinnerListDirectResponse> findHistoryWinnerDirectByUserId(String userId) {
        String msg;
        WinnerListDirectResponse response;
        userEntityDao.findByIdUser(userId).orElseThrow(() -> new ConflictException(ConstantsTexts.USER_BY_ID));
        List<WinnerDirectEntity> allHistoryWinnerDirectByIdUser = winnersDirectDao.getAllHistoryWinnerDirectByIdUser(userId);
        if (allHistoryWinnerDirectByIdUser.isEmpty()) {
            msg = ConstantsTexts.NOT_AWARDS;
            LOG.info(msg);
            response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<WinnersDirect> winnerList = new ArrayList<>();

        allHistoryWinnerDirectByIdUser.forEach(winner -> {
            List<SucursalesPromoShopWinners> sucursalesPromoShopWinners = new ArrayList<>();
            winner.promoEntity.getrPromoBranchEntity().forEach(branch -> {
                sucursalesPromoShopWinners.add(new SucursalesPromoShopWinners(branch.branchEntity.idBranch, branch.branchEntity.address, branch.branchEntity.phoneNumber, branch.branchEntity.type, branch.branchEntity.latitud, branch.branchEntity.longitud));
            });
            winnerList.add(new WinnersDirect(winner.idWinner, winner.statusWinner, new AwardWinner(winner.promoEntity.getIdPromo(), winner.promoEntity.getName(), winner.promoEntity.getDescription(),
                    winner.promoEntity.getUrlTerms(), winner.promoEntity.getUrlPromo(), winner.promoEntity.getImage(), ConstantsTexts.fecha.format(winner.promoEntity.getReleaseDate()), ConstantsTexts.fecha.format(winner.promoEntity.getDueDate()),
                    winner.promoEntity.getPromoType(), winner.promoEntity.getCode(), winner.promoEntity.isActive(), new ShopWinner(winner.promoEntity.getShopEntity().idShop, winner.promoEntity.getShopEntity().name, winner.promoEntity.getShopEntity().active,
                    winner.promoEntity.getShopEntity().image, sucursalesPromoShopWinners)), new UserWinner(winner.userEntity.idUser, winner.userEntity.name, winner.userEntity.lastName, winner.userEntity.email, winner.userEntity.name_profile, winner.userEntity.phoneNumber, winner.userEntity.accountType), ConstantsTexts.fecha.format(winner.winningDate)
            ));
        });
        msg = ConstantsTexts.WINNER_USERS;
        response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), winnerList, 0, 0l);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<WinnerListDirectResponse> getAllWinnersDirectByIdShop(WinnersShopRequest winnersShopRequest) {
        shopDao.findByIdShop(winnersShopRequest.idShop).orElseThrow(() -> new ConflictException(ConstantsTexts.SHOP_INVALID));
        Page<WinnerDirectEntity> allWinnersDirectByShop = winnersPagDao.getAllWinnersDirectByShop(winnersShopRequest.idShop, PageRequest.of(winnersShopRequest.page, winnersShopRequest.maxResults));
        return extractDataWinners(allWinnersDirectByShop);
    }

    private ResponseEntity<WinnerListDirectResponse> extractDataWinners(Page<WinnerDirectEntity> allWinners) {
        String msg;
        WinnerListDirectResponse response;
        if (allWinners.isEmpty()) {
            msg = ConstantsTexts.NOT_AWARDS;
            LOG.info(msg);
            response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        List<WinnersDirect> winnerList = new ArrayList<>();
        allWinners.forEach(winner -> {
            List<SucursalesPromoShopWinners> sucursalesPromoShopWinners = new ArrayList<>();
            winner.promoEntity.getrPromoBranchEntity().forEach(branch -> {
                sucursalesPromoShopWinners.add(new SucursalesPromoShopWinners(branch.branchEntity.idBranch, branch.branchEntity.address, branch.branchEntity.phoneNumber, branch.branchEntity.type, branch.branchEntity.latitud, branch.branchEntity.longitud));
            });
            winnerList.add(new WinnersDirect(winner.idWinner, winner.statusWinner, new AwardWinner(winner.promoEntity.getIdPromo(), winner.promoEntity.getName(), winner.promoEntity.getDescription(),
                    winner.promoEntity.getUrlTerms(), winner.promoEntity.getUrlPromo(), winner.promoEntity.getImage(), ConstantsTexts.fecha.format(winner.promoEntity.getReleaseDate()), ConstantsTexts.fecha.format(winner.promoEntity.getDueDate()),
                    winner.promoEntity.getPromoType(), winner.promoEntity.getCode(), winner.promoEntity.isActive(), new ShopWinner(winner.promoEntity.getShopEntity().idShop, winner.promoEntity.getShopEntity().name, winner.promoEntity.getShopEntity().active,
                    winner.promoEntity.getShopEntity().image, sucursalesPromoShopWinners)), new UserWinner(winner.userEntity.idUser, winner.userEntity.name, winner.userEntity.lastName, winner.userEntity.email, winner.userEntity.name_profile, winner.userEntity.phoneNumber, winner.userEntity.accountType), ConstantsTexts.fecha.format(winner.winningDate)
            ));
        });
        msg = ConstantsTexts.WINNERS;
        response = new WinnerListDirectResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), winnerList, allWinners.getTotalPages(), allWinners.getTotalElements());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @Override
    public ResponseEntity<HeaderResponse> claimAwardDirect(String idWinner) {
        String msg;
        HeaderResponse response;
        WinnerDirectEntity winnerDirectEntity = winnersDirectDao.findById(idWinner).orElseThrow(() -> new ConflictException(ConstantsTexts.WINNER_INVALID));
        winnerDirectEntity.statusWinner = WinnersEntity.StatusWinner.Reclamado;
        winnersDirectDao.save(winnerDirectEntity);
        msg = ConstantsTexts.WINNER_STATUS;
        response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
