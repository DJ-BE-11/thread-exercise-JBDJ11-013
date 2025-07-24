/*
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 * + Copyright 2024. NHN Academy Corp. All rights reserved.
 * + * While every precaution has been taken in the preparation of this resource,  assumes no
 * + responsibility for errors or omissions, or for damages resulting from the use of the information
 * + contained herein
 * + No part of this resource may be reproduced, stored in a retrieval system, or transmitted, in any
 * + form or by any means, electronic, mechanical, photocopying, recording, or otherwise, without the
 * + prior written permission.
 * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
 */

package com.nhnacademy;

import com.nhnacademy.thread.CounterHandler;
import lombok.extern.slf4j.Slf4j;

import javax.management.monitor.CounterMonitor;
import javax.management.monitor.Monitor;

@Slf4j
public class App
{

    // monitor로 사용할 객체를 생성 합니다.
    public static Object monitor = new Object();

    public static void main( String[] args )
    {

        // counterHandlerA 객체를 생성 합니다. countMaxSize : 10, monitor
        CounterHandler counterHandlerA = new CounterHandler(10, monitor);

        // threadA 생성시 counterHandlerA 객체를 paramter로 전달 합니다.
        Thread threadA = new Thread(counterHandlerA);

        // threadA의 name을 'my-counter-A' 로 설정 합니다.
        threadA.setName("my-counter-A");
        log.debug("threadA-state:{}",threadA.getState());

        // threadA를 시작 합니다.
        threadA.start();
        log.debug("threadA-state:{}",threadA.getState());

        // Main Thread에서 2초 후 monitor를 이용하여 대기하고 있는 threadA를 깨웁 니다.
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        synchronized (monitor) {
            log.debug("call monitor.notify()");
            monitor.notify();
        }

        // Main Thread가 threadA  종료될 때 까지 대기 합니다. Thread.yield를 사용 합니다.
        do {
            Thread.yield();
        }while (threadA.isAlive());

        // 'Application exit!' message를 출력 합니다.
        log.debug("Application exit!");

    }

}