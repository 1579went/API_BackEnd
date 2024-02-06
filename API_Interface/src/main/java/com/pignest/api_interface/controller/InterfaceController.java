package com.pignest.api_interface.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.pignest.api_client_sdk.service.ApiService;
import com.pignest.api_common.common.BaseResponse;
import com.pignest.api_common.common.ErrorCode;
import com.pignest.api_common.common.ResultUtils;
import com.pignest.api_common.exception.BusinessException;
import com.pignest.api_interface.model.DirtyJoke;
import com.pignest.api_interface.model.Encouragement;
import com.pignest.api_interface.model.Flirt;
import com.pignest.api_interface.model.sayWhat;
import com.pignest.api_interface.model.vo.SentenceVO;
import com.pignest.api_interface.service.DirtyJokeService;
import com.pignest.api_interface.service.EncouragementService;
import com.pignest.api_interface.service.FlirtService;
import com.pignest.api_interface.service.WeatherMapService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static com.pignest.api_interface.util.util.enCodeStr;

/**
 * @author Black
 */
@RestController
@RequestMapping("/api")
@Slf4j
public class InterfaceController {

    @Resource
    private ApiService apiService;
    @Resource
    private DirtyJokeService dirtyJokeService;
    @Resource
    private FlirtService flirtService;
    @Resource
    private EncouragementService encouragementService;
    @Resource
    private WeatherMapService weatherMapService;


    @GetMapping("/sayHi")
    public BaseResponse<String> sayHi(String name) {
        log.info("Hi~" + (name == null ? "" : name));
        return ResultUtils.success( "Hi~" + (name == null ? "" : name));
    }

    @PostMapping("/sayWhat")
    public BaseResponse<SentenceVO> sayWhat(@RequestBody sayWhat saying) {
        String name = enCodeStr(saying.getName());
        String words = enCodeStr(saying.getWords());
        if(StringUtils.isAnyBlank(name,words)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不能为空！");
        }
        return ResultUtils.success(new SentenceVO(name + " say:" + words));
    }

    @GetMapping("/say/flirt")
    public BaseResponse<SentenceVO> sayFlirt() {
        Flirt flirt;
        while (true){
            Long cnt = new Random().nextLong(flirtService.count());
            Optional<Flirt> opt = flirtService.getOptById(cnt);
            if(opt.isPresent()){
                flirt = opt.get();
                System.out.println(flirt.getId());
                break;
            }
        }
        return ResultUtils.success(new SentenceVO(flirt.getValue()));
    }

    @GetMapping("/say/encouragement")
    public BaseResponse<SentenceVO> sayEncouragement() {
        Encouragement encouragement;
        while (true){
            Long cnt = new Random().nextLong(encouragementService.count());
            Optional<Encouragement> opt = encouragementService.getOptById(cnt);
            if(opt.isPresent()){
                encouragement = opt.get();
                System.out.println(encouragement.getId());
                break;
            }
        }
        return ResultUtils.success(new SentenceVO(encouragement.getValue()));
    }


    @GetMapping("/say/dirty_joke")
    public BaseResponse<Object> sayDirtyJoke() {
        DirtyJoke dirtyJoke;
        while (true){
            Long cnt = new Random().nextLong(dirtyJokeService.count());
            Optional<DirtyJoke> opt = dirtyJokeService.getOptById(cnt);
            if(opt.isPresent()){
                dirtyJoke = opt.get();
                System.out.println(dirtyJoke.getId());
                break;
            }
        }
        return ResultUtils.success(new SentenceVO(dirtyJoke.getValue()));
    }

    @GetMapping("/weather")
    public BaseResponse<Object> weather(@RequestParam String area, @RequestParam String extensions) {
        Object res = weatherMapService.getWeatherReport(area,extensions);
        if(Objects.isNull(res)){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success( res );
    }

    @PostMapping("/test")
    public BaseResponse<String> test(@RequestParam String url) {
//        String name = enCodeStr(saying.getName());
//        String words = enCodeStr(saying.getWords());
        if(StringUtils.isAnyBlank(url)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数不能为空！");
        }

        String res = apiService.setUrl(url)
                .get();
        BaseResponse baseResponse = new Gson().fromJson(res,BaseResponse.class);
        //JSONObject jsonObject = JSONUtil.parseObj((Object) res);
        if(null == baseResponse){
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
        if(!("0".equals(baseResponse.getCode()))){
            return  baseResponse;
        }
        return ResultUtils.success(baseResponse.getData().toString());
    }
}