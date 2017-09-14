package com.mmall.service;

import com.mmall.common.ServerResponse;

import javax.servlet.http.HttpSession;

public interface IMixedService {
    ServerResponse<String> upLoadMixed(HttpSession session,String mixedtext);
}
