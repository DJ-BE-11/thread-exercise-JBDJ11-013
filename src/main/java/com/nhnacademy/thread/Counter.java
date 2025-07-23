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

package com.nhnacademy.thread;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Counter {
    private final long countMaxSize;
    private long count;

    // Constructor
    public Counter(long countMaxSize) {
        // countMaxSize < 0 작다면 IllegalArgumentException 예외가 발생 합니다.
        if (countMaxSize < 0) {
            throw new IllegalArgumentException();
        }

        // this.countMaxSize 초기화 합니다.
        this.countMaxSize = countMaxSize;

        // this.count 값을 0으로 초기화 합니다.
        this.count = 0;

    }

    public void run() {

        do {

            /* 1초 간격 으로 count++ 됩니다.
              Thread.sleep method를 사용하세요.
              https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/lang/Thread.html#sleep(java.time.Duration)
            */

            // 1초 간격으로 count 값 1 증가
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.count++;

            /* count 출력
                name:{Thread name}, count:{count 변수}
                Thread name : Thread.currentThread().getName();
                ex) name:my-thread, count:1
             */

            System.out.printf("name: %s, count: %d%n", Thread.currentThread().getName(), this.count);

        } while (count<countMaxSize);
    }
}
