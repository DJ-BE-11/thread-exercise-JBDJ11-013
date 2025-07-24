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

@Slf4j
public class App
{
    public static void main( String[] args )
    {
        //counterHandlerA 객체를 생성 합니다. countMaxSize : 10
        CounterHandler counterHandlerA = new CounterHandler(10l);

        //threadA 생성시 counterHandlerA 객체를 paramter로 전달 합니다.
        Thread threadA = new Thread(counterHandlerA);

        //threadA의 name을 'my-counter-A' 로 설정 합니다.
        threadA.setName("my-counter-A");

        log.debug("threadA-state:{}",threadA.getState());


        //counterHandlerB 객체를 생성 합니다. countMaxSize : 10
        CounterHandler counterHandlerB = new CounterHandler(10l);

        //threadB 생성시 counterHandlerB 객체를 paramter로 전달 합니다.
        Thread threadB = new Thread(counterHandlerB);

        //threadB의 name을 'my-counter-B' 로 설정 합니다.
        threadB.setName("my-counter-B");

        log.debug("threadB-state:{}",threadB.getState());


        //threadA를 시작 합니다.
        threadA.start();
        log.debug("threadA-state:{}",threadA.getState());

        //threadB를 시작 합니다.
        threadB.start();
        log.debug("threadB-state:{}",threadB.getState());

        // Main Thread가 threadA, threadB가 종료될 때 까지 대기 합니다. Thread.yield를 사용 합니다.
        do {
            Thread.yield();
        } while (threadA.isAlive() || threadB.isAlive());

        
        // threadA, threadB가 종료되면 'Application exit!' message를 출력 합니다.
        log.debug("Application exit!");

    }
}