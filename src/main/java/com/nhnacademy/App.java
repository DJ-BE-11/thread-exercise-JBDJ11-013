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


        // Main Thread 에서 3초 후  threadA에 interrupt 예외를 발생 시킵니다.
        try {
            Thread.sleep(3000);

            // interrupt 발생
            log.debug("threadA.interrupt() 호출");
            threadA.interrupt();

            // 2초 후 threadA의 상태가 TERMINATED로 변경된 것을 확인할 수 있음
            Thread.sleep(2000);
            log.debug("threadA state:{}", threadA.getState());
        } catch (Throwable e) {
            log.debug("exception:{}", e);
        }


        // Main Thread가 threadA, ThreadB가 종료될 때 까지 대기 합니다. Thread.yield를 사용 합니다.
        do {
            Thread.yield();
        } while (threadA.isAlive() || threadB.isAlive());

        //threadA, threadB 상태를 출력 합니다.
        log.debug("threadA-status:{}",threadA.getState());
        log.debug("threadB-status:{}",threadB.getState());

        //main thread 종료, 'Application exit!' message를 출력 합니다.
        log.debug("Application exit!");
    }
}