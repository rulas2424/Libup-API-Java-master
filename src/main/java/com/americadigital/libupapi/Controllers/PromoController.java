package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.PromoBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Promo.DateSystem;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Notificator.NotificatorAwardsRequest;
import com.americadigital.libupapi.WsPojos.Request.Notificator.NotificatorRequest;
import com.americadigital.libupapi.WsPojos.Request.Promo.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetAllPromosResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetDateSystemResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetPromoResponse;
import com.americadigital.libupapi.WsPojos.Response.Promo.GetPromosAppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/promo")
public class PromoController {
    private static final Logger LOG = LoggerFactory.getLogger(PromoController.class);

    @Autowired
    private PromoBusiness promoBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> addPromotion(@RequestBody final AddPromoRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/add Request: " + request + " at " + new Date());
            return promoBusiness.addPromo(request);
        } catch (DataIntegrityViolationException e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> updatePromotion(@RequestBody final UpdatePromoRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /promo/update Request: " + request + " at " + new Date());
            return promoBusiness.updatePromo(request);
        } catch (DataIntegrityViolationException e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/status",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> changeStatusPromo(@RequestBody final ChangeStatusPromo request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /promo/status Request: " + request + " at " + new Date());
            return promoBusiness.changeStatusPromo(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> deletePromo(@RequestParam(value = "idPromo") String idPromo) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /promo/delete Request: " + idPromo + " at " + new Date());
            return promoBusiness.deletePromo(idPromo);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getPromosForShop",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllPromosResponse> getPromosForIdShop(@RequestBody final AllPromosShopRequest request) {
        String msg;
        GetAllPromosResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/getPromosForShop Request: " + request + " at " + new Date());
            return promoBusiness.filterPromosForIdShop(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getAwards",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllPromosResponse> getAwardsForIdShop(@RequestBody final AllAwardsRequest request) {
        String msg;
        GetAllPromosResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/getAwards Request: " + request + " at " + new Date());
            return promoBusiness.getAwardsDirectosForIdShop(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getAwardsAudio",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllPromosResponse> getAwardsAudio(@RequestBody final AllAwardsRequest request) {
        String msg;
        GetAllPromosResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/getAwardsAudio Request: " + request + " at " + new Date());
            return promoBusiness.getAwardsAudioForIdShop(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getPromos",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetPromosAppResponse> getAllPromosForIdCategory(@Valid @RequestBody GetPromoByCategoryRequest request) {
        String msg;
        GetPromosAppResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/getPromos Request: " + request + " at " + new Date());
            return promoBusiness.getPromosActivesByIdCategory(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/searchPromos",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetPromosAppResponse> searchPromosWithDistanceOrNot(@Valid @RequestBody SearchPromosRequest request) {
        String msg;
        GetPromosAppResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/searchPromos Request: " + request + " at " + new Date());
            return promoBusiness.searchPromosOrDiscounts(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetPromosAppResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/notificate/winnerDirect",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> notificateWinnerDirect(@RequestBody final NotificatorAwardsRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /notificate/winnerDirect Request: " + request + " at " + new Date());
            return promoBusiness.notificatorAwardWinnerDirect(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/notificator",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> notificatorUsersPromos(@RequestBody final NotificatorRequest request) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /notificate/notificator Request: " + request + " at " + new Date());
            return promoBusiness.sendNotificatorUsers(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getPromosOrDiscounts",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllPromosResponse> getPromosOrDiscounts(@RequestBody final AllPromosRequest request) {
        String msg;
        GetAllPromosResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/getPromosOrDiscounts Request: " + request + " at " + new Date());
            return promoBusiness.getPromotionsOrDiscountsByIdShop(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getPromosOrDiscountsActives",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetAllPromosResponse> getPromosOrDiscountsActives(@RequestParam(value = "idShop") String idShop) {
        String msg;
        GetAllPromosResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/getPromosOrDiscountsActives Request: " + idShop + " at " + new Date());
            return promoBusiness.getAllPromosOrdDiscounts(idShop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetAllPromosResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>(), 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getById",
            method = RequestMethod.GET,
            headers = "Accept=application/json",
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetPromoResponse> getPromoOrDiscountById(@RequestParam(value = "idPromo") String idPromo) {
        String msg;
        GetPromoResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /promo/getById Request: " + idPromo + " at " + new Date());
            return promoBusiness.getPromoOrDiscountById(idPromo);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new GetPromoResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetPromoResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getTime",
            method = RequestMethod.GET)
    public ResponseEntity<GetDateSystemResponse> getTime() {
        String msg;
        GetDateSystemResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /promo/getTime Request: " + " at " + new Date());
            msg = "Hora del sistema";
            response = new GetDateSystemResponse(new HeaderGeneric(ConstantsTexts.SUCCESS, HttpStatus.OK.value(), msg), new DateSystem(new Date().toString(), subtractDays(new Date(), 1).toString()));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new GetDateSystemResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new DateSystem("", ""));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Date subtractDays(Date fecha, int dias) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, -dias);
        return cal.getTime();
    }
}
