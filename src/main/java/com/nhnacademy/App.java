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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();

    public static void main(String[] args) {

        // Thread-1이 resource1의 접근 권한을 획득하기 위해 대기합니다.
        // Thread-1 : resource1 잠금
        Thread thread1 = new Thread(() -> {
            synchronized (resource1) {
                log.debug("{}: locked resource 1", Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        });
        thread1.setName("Thread-1");

        // Thread-2가 resource1의 접근 권한을 획득한 상태에서 resource2의 접근 권한을 대기하고 있습니다.
        // Thread-2 : resource1 -> resource2 순서로 잠금 시도
        Thread thread2 = new Thread(() -> {
            synchronized (resource1) { // resource1을 먼저 잠금
                log.debug("{}: locked resource 1", Thread.currentThread().getName());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }

                synchronized (resource2) {
                    log.debug("{}: locked resource 2", Thread.currentThread().getName());
                }
            }
        });
        thread2.setName("Thread-2");

        // Thread-3가 resource2의 접근 권한을 획득합니다.
        // Thread-3 : resource2 잠금하고 절대 놓지 않음 (무한 루프)
        Thread thread3 = new Thread(() -> {
            synchronized (resource2) { // resource2을 먼저 잠금
                log.debug("{}: locked resource 2", Thread.currentThread().getName());

                try {
                    while (true) {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    log.debug(e.getMessage());
                }
            }
        });
        thread3.setName("Thread-3");


        /* Hold & Wait Deadlock
             Thread-3: [점유 -> resource2]                           // 절대 놓지 않음
             Thread-2: [점유 -> resource1] -> [대기 -> resource2]     // Hold & Wait
             Thread-1: [대기 -> resource1]                           // Wait
        */
        try {
            thread3.start();
            Thread.sleep(1000);

            thread2.start();
            Thread.sleep(1000);

            thread1.start();
        } catch (InterruptedException e) {
            log.debug(e.getMessage());
        }
    }

}
