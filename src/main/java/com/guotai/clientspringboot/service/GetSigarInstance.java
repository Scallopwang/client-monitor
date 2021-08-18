/*
@File  : SigarInstance.java
@Author: WZC
@Date  : 2021-08-04 16:40
*/
package com.guotai.clientspringboot.service;


import org.hyperic.sigar.Sigar;
import org.springframework.stereotype.Service;

@Service
public class GetSigarInstance {
    public final Sigar sigar = new Sigar();

    public Sigar getSigar() {
        return sigar;
    }
}
