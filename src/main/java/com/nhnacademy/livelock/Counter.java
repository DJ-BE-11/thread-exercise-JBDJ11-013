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

package com.nhnacademy.livelock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Counter {
    private int count = 0;
    private final Lock lock = new ReentrantLock();


    /* Livelock :
     *  여러 스레드가 서로를 배려해서 계속 양보만 하며, 아무 일도 진행하지 못하는 상태
     *  즉, 두 스레드가 lock을 얻으려다 동시에 못 얻고, 서로 반복적으로 양보하거나 릴리즈/재시도만 하면서 작업 자체는 못 하는 상황이 되어야 발생함
     *    >> 주로 여러 lock을 끼고 서로 양보하는 논리에서 발생함 (자주 사용X, 중요X)
     *
     *  실제 출력 결과 :
     *    counter-A만 작업(카운트 증가) 수행
     *    counter-B만 계속 양보
     *      >> 100% Livelock (X)
     */
    public void increment() {
        while (true) {
            if (lock.tryLock()) {
                try {
                    count++;
                    log.debug("{} count++ : {}", Thread.currentThread().getName(), count );
                    break;
                } finally {
                    lock.unlock();
                }
            } else {
                log.debug("{} lock 획득 시도...", Thread.currentThread().getName());
            }
        }
    }

    public int getCount() {
        return count;
    }
}