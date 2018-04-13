package com.chinac.doc.controller;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import com.chinac.doc.IPHelper;
import com.chinac.doc.dto.AddDoc;
import com.chinac.doc.repository.DocHistoryRepository;
import com.chinac.doc.repository.DocRepository;
import com.chinac.doc.repository.SDKHistoryRepository;
import com.chinac.doc.repository.SDKRepository;
import com.chinac.doc.repository.entities.DocEntity;
import com.chinac.doc.repository.entities.DocHistoryEntity;
import com.chinac.doc.repository.entities.SDKEntity;
import com.chinac.doc.repository.entities.SDKHistoryEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ApiController {

    private static Gson g = new Gson();
    private final DocRepository docRepository;
    private final DocHistoryRepository docHistoryRepository;
    private final SDKHistoryRepository sdkHistoryRepository;
    private final SDKRepository sdkRepository;

    @Autowired
    public ApiController(DocRepository docRepository,
                         DocHistoryRepository docHistoryRepository,
                         SDKHistoryRepository sdkHistoryRepository,
                         SDKRepository sdkRepository) {

        this.docRepository = docRepository;
        this.docHistoryRepository = docHistoryRepository;
        this.sdkHistoryRepository = sdkHistoryRepository;
        this.sdkRepository = sdkRepository;
    }

    @RequestMapping(value = "/api/doc", method = RequestMethod.GET)
    public @ResponseBody
    String getJson(@RequestParam String serviceName, @RequestParam String serviceVersion) {
//        return "{\"swagger\":\"2.0\",\"info\":{\"description\":\"hermes API<br/>haahahaha启发式<br/>以下是本项目的API文档\",\"version\":\"1.0.0.0\",\"title\":\"hermes\",\"termsOfService\":\"服务条款\",\"contact\":{\"name\":\"chenhaibin\"},\"license\":{\"name\":\"The Apache License, Version 2.0\",\"url\":\"http://hermes-ui.service.huayun.com\"}},\"host\":\"10.51.30.79:8081\",\"basePath\":\"/\",\"tags\":[{\"name\":\"home-controller\",\"description\":\"Home相关的API\"}],\"paths\":{\"/home/hello\":{\"get\":{\"tags\":[\"home-controller\"],\"summary\":\"sayHello\",\"description\":\"sayHello的API\",\"operationId\":\"sayHelloUsingGET\",\"produces\":[\"*/*\",\"application/json;charset=UTF-8\"],\"parameters\":[{\"name\":\"name\",\"in\":\"query\",\"description\":\"名字\",\"required\":true,\"type\":\"string\"},{\"name\":\"age\",\"in\":\"query\",\"description\":\"年龄\",\"required\":true,\"type\":\"ref\"}],\"responses\":{\"200\":{\"description\":\"成功\",\"schema\":{\"$ref\":\"#/definitions/ReturnData«Hello»\"}},\"500\":{\"description\":\"服务端错误\"}}}},\"/home/helloComplex\":{\"get\":{\"tags\":[\"home-controller\"],\"summary\":\"sayHelloComplex\",\"description\":\"sayHelloComplex的API\",\"operationId\":\"sayHelloComplexUsingGET\",\"produces\":[\"*/*\",\"application/json;charset=UTF-8\"],\"parameters\":[{\"name\":\"name\",\"in\":\"query\",\"description\":\"名字\",\"required\":true,\"type\":\"string\"},{\"name\":\"age\",\"in\":\"query\",\"description\":\"年龄\",\"required\":true,\"type\":\"integer\",\"format\":\"int32\"}],\"responses\":{\"200\":{\"description\":\"成功\",\"schema\":{\"$ref\":\"#/definitions/ReturnData«List«Hello»»\"}},\"500\":{\"description\":\"服务端错误\"}}}},\"/home/helloComplex1\":{\"post\":{\"tags\":[\"home-controller\"],\"summary\":\"sayHelloComplex1\",\"description\":\"sayHelloComplex2的API\",\"operationId\":\"sayHelloComplex1UsingPOST\",\"consumes\":[\"application/json\"],\"produces\":[\"*/*\",\"application/json;charset=UTF-8\"],\"parameters\":[{\"in\":\"body\",\"name\":\"woqu\",\"description\":\"woqu\",\"required\":true,\"schema\":{\"type\":\"array\",\"items\":{\"$ref\":\"#/definitions/Hello\"}}},{\"name\":\"age\",\"in\":\"query\",\"description\":\"age\",\"required\":false,\"type\":\"integer\",\"format\":\"int32\"}],\"responses\":{\"200\":{\"description\":\"成功\",\"schema\":{\"$ref\":\"#/definitions/ReturnData«List«Hello»»\"}},\"500\":{\"description\":\"服务端错误\"}}}}},\"definitions\":{\"Hello\":{\"type\":\"object\",\"properties\":{\"age\":{\"type\":\"integer\",\"format\":\"int32\",\"description\":\"年龄\",\"allowEmptyValue\":false},\"name\":{\"type\":\"string\",\"description\":\"姓名\",\"allowEmptyValue\":false}},\"title\":\"Hello\",\"description\":\"测试下\"},\"ReturnData«Hello»\":{\"type\":\"object\",\"properties\":{\"data\":{\"description\":\"data\",\"allowEmptyValue\":false,\"$ref\":\"#/definitions/Hello\"},\"errorCode\":{\"type\":\"string\",\"description\":\"errorCode\",\"allowEmptyValue\":false},\"errorMessage\":{\"type\":\"string\",\"description\":\"errorMessage\",\"allowEmptyValue\":false},\"requestId\":{\"type\":\"string\",\"description\":\"requestId\",\"allowEmptyValue\":false}},\"title\":\"ReturnData«Hello»\",\"description\":\"ReturnData desc\"},\"ReturnData«List«Hello»»\":{\"type\":\"object\",\"properties\":{\"data\":{\"type\":\"array\",\"description\":\"data\",\"allowEmptyValue\":false,\"items\":{\"$ref\":\"#/definitions/Hello\"}},\"errorCode\":{\"type\":\"string\",\"description\":\"errorCode\",\"allowEmptyValue\":false},\"errorMessage\":{\"type\":\"string\",\"description\":\"errorMessage\",\"allowEmptyValue\":false},\"requestId\":{\"type\":\"string\",\"description\":\"requestId\",\"allowEmptyValue\":false}},\"title\":\"ReturnData«List«Hello»»\",\"description\":\"ReturnData desc\"}}}";

        if (StringUtils.isEmpty(serviceName) || StringUtils.isEmpty(serviceVersion)) {
            return "";
        }

        String ret = "";
        try {
            DocEntity one = docRepository.findByServiceNameAndServiceVersion(serviceName, serviceVersion);
            ret = one.getJson();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ret;

    }

    @RequestMapping(value = "/api/add", method = RequestMethod.POST)
    public @ResponseBody
    void addDoc(@RequestBody AddDoc doc, HttpServletRequest request) {

        if (doc == null || StringUtils.isEmpty(doc.getServiceName()) || StringUtils.isEmpty(doc.getMetaInfo()) || StringUtils.isEmpty(doc.getServiceVersion())) {
            return;
        }

        try {
            DocEntity one = docRepository.findByServiceNameAndServiceVersion(doc.getServiceName(), doc.getServiceVersion());

            if (one == null) {
                one = new DocEntity();
                one.setJson(doc.getMetaInfo());
                one.setServiceName(doc.getServiceName());
                one.setServiceVersion(doc.getServiceVersion());
                one.setClientIP(IPHelper.getClientIp(request));
                Date date = new Date();
                one.setCreateTime(date);
                one.setUpdateTime(date);
                one.setHashcode(doc.getMetaInfo().hashCode());
                one.setId(UUID.randomUUID().toString());
                one.setLan(doc.getLan());

                docRepository.save(one);
                String s = g.toJson(one);
                DocHistoryEntity docHistoryEntity = g.fromJson(s, DocHistoryEntity.class);
                docHistoryEntity.setId(one.getId());
                docHistoryRepository.save(docHistoryEntity);

            } else if (one.getHashcode() != doc.getMetaInfo().hashCode()) {
                one.setClientIP(IPHelper.getClientIp(request));
                Date date = new Date();
                one.setUpdateTime(date);
                one.setHashcode(doc.getMetaInfo().hashCode());
                one.setJson(doc.getMetaInfo());
                one.setLan(doc.getLan());

                docRepository.save(one);
                String s = g.toJson(one);
                DocHistoryEntity docHistoryEntity = g.fromJson(s, DocHistoryEntity.class);
                docHistoryEntity.setId(UUID.randomUUID().toString());
                docHistoryRepository.save(docHistoryEntity);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SDKEntity sdkEntity = sdkRepository.findByServiceNameAndServiceVersion(doc.getServiceName(), doc.getServiceVersion());

            if (sdkEntity == null) {
                sdkEntity = new SDKEntity();
                sdkEntity.setLan(doc.getLan());
                sdkEntity.setSdkVersion(doc.getSdkVersion());
                sdkEntity.setServiceName(doc.getServiceName());
                sdkEntity.setServiceVersion(doc.getServiceVersion());
                sdkEntity.setId(UUID.randomUUID().toString());

                sdkRepository.save(sdkEntity);

                String s = g.toJson(sdkEntity);
                SDKHistoryEntity sdkHistoryEntity = g.fromJson(s, SDKHistoryEntity.class);
                sdkHistoryRepository.save(sdkHistoryEntity);
            } else if (sdkEntity.getSdkVersion() != doc.getSdkVersion()) {
                sdkEntity.setSdkVersion(doc.getSdkVersion());

                sdkRepository.save(sdkEntity);

                String s = g.toJson(sdkEntity);
                SDKHistoryEntity sdkHistoryEntity = g.fromJson(s, SDKHistoryEntity.class);
                sdkHistoryEntity.setId(UUID.randomUUID().toString());
                sdkHistoryRepository.save(sdkHistoryEntity);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return;
    }

    @RequestMapping(value = "/")
    public String index() {
        return "index";
    }

}
