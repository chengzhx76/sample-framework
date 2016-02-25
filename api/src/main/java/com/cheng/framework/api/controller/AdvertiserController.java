package com.cheng.framework.api.controller;

import com.cheng.framework.core.bean.PagingParam;
import com.cheng.framework.core.bean.PagingResult;
import com.cheng.framework.core.bean.Response;
import com.cheng.framework.core.jdbc.paging.Paging;
import com.cheng.framework.api.param.AdvertiserParam;
import com.cheng.framework.api.result.AdvertiserResult;
import com.cheng.framework.api.service.AdvertiserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 广告主控制器
 *
 * @author huangyong
 * @since 1.0.0
 */
@RestController
public class AdvertiserController {

    @Autowired
    private AdvertiserService advertiserService;

    @RequestMapping(value = "/advertisers", method = RequestMethod.GET)
    public Response listAdvertiser(PagingParam pagingParam) {
        int pageNumber = pagingParam.getPagingNumber();
        int pageSize = pagingParam.getPageSize();
        String whereCondition = pagingParam.getWhereCondition("advertiser_name");
        String orderBy = pagingParam.getOrderBy();
        Paging<AdvertiserResult> advertiserResultPaging = advertiserService.getAdvertiserPaging(pageNumber, pageSize, whereCondition, orderBy);
        return new Response().success(new PagingResult<>(advertiserResultPaging));
    }

    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.GET)
    public Response getAdvertiser(@PathVariable("id") String advertiserId) {
        AdvertiserResult advertiserResult = advertiserService.getAdvertiser(advertiserId);
        return new Response().success(advertiserResult);
    }

    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.PUT)
    public Response updateAdvertiser(@PathVariable("id") String advertiserId, @RequestBody @Valid AdvertiserParam advertiserParam) {
        String advertiserName = advertiserParam.getAdvertiserName();
        String description = advertiserParam.getDescription();
        advertiserService.updateAdvertiser(advertiserId, advertiserName, description);
        return new Response().success();
    }

    @RequestMapping(value = "/advertiser/{id}", method = RequestMethod.DELETE)
    public Response deleteAdvertiser(@PathVariable("id") String advertiserId) {
        advertiserService.deleteAdvertiser(advertiserId);
        return new Response().success();
    }

    @RequestMapping(value = "/advertiser", method = RequestMethod.POST)
    public Response createAdvertiser(@RequestBody @Valid AdvertiserParam advertiserParam) {
        String advertiserName = advertiserParam.getAdvertiserName();
        String description = advertiserParam.getDescription();
        advertiserService.createAdvertiser(advertiserName, description);
        return new Response().success();
    }
}
