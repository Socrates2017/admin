package com.zrzhen.admin.module.os.service;

import com.zrzhen.admin.module.os.rsp.News;

public interface IndexService {

    News getLastNews();

    void delNeWsCache();
}
