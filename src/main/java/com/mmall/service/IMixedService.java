package com.mmall.service;

import com.mmall.common.ServerResponse;
import com.mmall.pojo.Mixed;

import javax.servlet.http.HttpSession;

public interface IMixedService {
    ServerResponse<String> upLoadMixed(Mixed mixed);
}
