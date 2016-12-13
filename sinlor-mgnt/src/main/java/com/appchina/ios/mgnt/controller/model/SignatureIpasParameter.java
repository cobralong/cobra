// Copyright 2015 www.refanqie.com Inc. All Rights Reserved.

package com.appchina.ios.mgnt.controller.model;

/**
 * @author luofei@refanqie.com (Your Name Here)
 *
 */
public class SignatureIpasParameter extends StatusStartSizeParameter {
    private Long certSerial;

    public SignatureIpasParameter() {
    }

    public SignatureIpasParameter(Long certSerial) {
        super();
        this.certSerial = certSerial;
    }

    public Long getCertSerial() {
        return certSerial;
    }

    public void setCertSerial(Long certSerial) {
        this.certSerial = certSerial;
    }
}
