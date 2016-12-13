package com.sinlor.album.service;

import com.sinlor.album.model.UserOpenIdInfo;

public  interface UserOpenIdInfoService {

    UserOpenIdInfo getUser3rdSesseion(String code);

}
