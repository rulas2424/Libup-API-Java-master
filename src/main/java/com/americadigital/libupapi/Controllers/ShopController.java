package com.americadigital.libupapi.Controllers;

import com.americadigital.libupapi.Business.Interfaces.ShopBusiness;
import com.americadigital.libupapi.Exceptions.ConflictException;
import com.americadigital.libupapi.Pojos.Shop.ActivePojo;
import com.americadigital.libupapi.Utils.ConstantsTexts;
import com.americadigital.libupapi.Utils.HeaderGeneric;
import com.americadigital.libupapi.Utils.StringFormatUtilities;
import com.americadigital.libupapi.WsPojos.Request.Shop.*;
import com.americadigital.libupapi.WsPojos.Response.HeaderResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.AllShopResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.CommerceResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.ShopGetAllResponse;
import com.americadigital.libupapi.WsPojos.Response.Shop.ShopResponse;
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
import java.util.Date;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/commerce")
public class ShopController {
    private static final Logger LOG = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    private ShopBusiness shopBusiness;

    @RequestMapping(value = "/add",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommerceResponse> addCommerce(@Valid @RequestBody AddShopRequest request) {
        CommerceResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /commerce/add Request: " + request + " at " + new Date());
            return shopBusiness.addCommerce(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.SHOP_DUPLICATE;
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/update",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommerceResponse> updateCommerce(@RequestBody final UpdateShopRequest request) {
        CommerceResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry PUT Method: /commerce/update Request: " + request + " at " + new Date());
            return shopBusiness.updateCommerce(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (DataIntegrityViolationException e) {
            msg = ConstantsTexts.SHOP_DUPLICATE;
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @RequestMapping(value = "/status",
            method = RequestMethod.PUT,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CommerceResponse> changeStatusActive(@RequestBody final ActivePojo request) {
        String msg;
        CommerceResponse response;
        try {
            LOG.info("Endpoint entry PUT Method: /commerce/status Request: " + request + " at " + new Date());
            return shopBusiness.changeActiveStatus(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new CommerceResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getById",
            method = RequestMethod.GET)
    public ResponseEntity<ShopResponse> getCommerceById(@RequestParam("id_shop") String id_shop) {
        String msg;
        ShopResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /commerce/getById Request: " + id_shop + " at " + new Date());
            return shopBusiness.getCommerceById(id_shop);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new ShopResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.CONFLICT);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ShopResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/getAll",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ShopGetAllResponse> getAllCommerces(@RequestBody final AllShopRequest request) {
        String msg;
        ShopGetAllResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /commerce/getAll Request: " + request + " at " + new Date());
            return shopBusiness.getAllShopsPanel(request);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ShopGetAllResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null, 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/search",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<ShopGetAllResponse> searchCommerces(@RequestBody final SearchShopRequest request) {
        String msg;
        ShopGetAllResponse response;
        try {
            LOG.info("Endpoint entry POST Method: /commerce/search Request: " + request + " at " + new Date());
            return shopBusiness.searchCommercesPanel(request);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new ShopGetAllResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), null, 0, 0l);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/findAll",
            method = RequestMethod.GET)
    public ResponseEntity<AllShopResponse> findAllCommercesActives(@RequestParam(required = false) Long idState) {
        String msg;
        AllShopResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /commerce/findAll Request: " + " at " + new Date());
            return shopBusiness.getAllShopsActives(idState);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new AllShopResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/shops",
            method = RequestMethod.GET)
    public ResponseEntity<AllShopResponse> findAllCommerces() {
        String msg;
        AllShopResponse response;
        try {
            LOG.info("Endpoint entry GET Method: /commerce/findAll Request: " + " at " + new Date());
            return shopBusiness.getAllShops();
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new AllShopResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg), new ArrayList<>());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/delete",
            method = RequestMethod.DELETE)
    public ResponseEntity<HeaderResponse> deleteCommerce(@RequestParam("id_shop") String id_shop) {
        String msg;
        HeaderResponse response;
        try {
            LOG.info("Endpoint entry DELETE Method: /commerce/delete Request: " + id_shop + " at " + new Date());
            return shopBusiness.deleteCommerce(id_shop);
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

    @RequestMapping(value = "/addCommerce",
            method = RequestMethod.POST,
            headers = "Accept=application/json",
            consumes = MediaType.APPLICATION_JSON_UTF8_VALUE,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<HeaderResponse> addCommerceFromLibupMx(@Valid @RequestBody AddCommercesRequest request) {
        HeaderResponse response;
        String msg;
        try {
            LOG.info("Endpoint entry POST Method: /commerce/addCommerce Request: " + request + " at " + new Date());
            return shopBusiness.addCommercesFromPage(request);
        } catch (ConflictException e) {
            msg = e.getMessage();
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            String message = e.getMostSpecificCause().getMessage();
            String[] key_s = message.split("key ");
            msg = key_s[1].equals("'unique_name'") ? ConstantsTexts.SHOP_DUPLICATE : key_s[1].equals("'email_unique_admin'") ? ConstantsTexts.USER_DUPLICATE : e.getMessage();
            LOG.error(key_s.toString());
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.CONFLICT, HttpStatus.CONFLICT.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            msg = StringFormatUtilities.exceptionMsgBuilder(e);
            LOG.error(msg);
            response = new HeaderResponse(new HeaderGeneric(ConstantsTexts.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(), msg));
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }
}
