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
        // counterHandlerA 객체를 생성 합니다. countMaxSize : 10
        CounterHandler counterHandlerA = new CounterHandler(10);

        // threadA 생성시 counterHandlerA 객체를 paramter로 전달 합니다.
        Thread threadA = new Thread(counterHandlerA);

        // threadA의 name을 'my-counter-A' 로 설정 합니다.
        threadA.setName("my-counter-A");

        log.debug("threadA-state:{}",threadA.getState());


        // counterHandlerB 객체를 생성 합니다. countMaxSize : 10
        CounterHandler counterHandlerB = new CounterHandler(10);

        // threadB 생성시 counterHandlerB 객체를 paramter로 전달 합니다.
        Thread threadB = new Thread(counterHandlerB);

        // threadB의 name을 'my-counter-B' 로 설정 합니다.
        threadB.setName("my-counter-B");

        log.debug("threadB-state:{}",threadB.getState());



        // threadA를 시작 합니다.
        threadA.start();

        // threadA 작업이 완료될 때까지 main Thread는 대기 합니다.
        try {
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("threadA-state:{}",threadA.getState());


        // threadB를 시작 합니다.
        threadB.start();

        // threadB 작업이 완료될 때까지 main Thread는 대기 합니다.
        try {
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.debug("threadB-state:{}",threadB.getState());


        // 'Application exit!' message를 출력 합니다.
        log.debug("Application exit!");

    }
}