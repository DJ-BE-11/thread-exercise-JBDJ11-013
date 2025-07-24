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
// Runnable interface을 implements(구현) 합니다.
public class CounterHandler implements Runnable {
    private final long countMaxSize;

    private long count;

    public CounterHandler(long countMaxSize) {
        // countMaxSize <=0 이면 IllegalArgumentException()이 발생 합니다.
        if (countMaxSize <= 0) {
            throw new IllegalArgumentException("countMaxSize는 0보다 커야 합니다.");
        }

        this.countMaxSize = countMaxSize;
        this.count=0l;
    }

    @Override
    public void run() {
        /* run method를 구현 합니다.
             - 1초에 한 번씩 다음과 같이 출력 됩니다.
             - count 1 ~ 10 까지 출력 됩니다.
            ex) thread:my-thread,count:1 ....
         */

        do {

            this.count++;

            try {
                Thread.sleep(1000);
                System.out.printf("thread: %s, count: %d%n", Thread.currentThread().getName(), count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } while (count < countMaxSize);
    }
}
