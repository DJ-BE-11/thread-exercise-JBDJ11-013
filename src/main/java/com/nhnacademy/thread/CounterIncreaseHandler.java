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

import com.nhnacademy.count.SharedCounter;
import lombok.extern.slf4j.Slf4j;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class CounterIncreaseHandler implements Runnable {
    private final SharedCounter sharedCounter;

    private final List<String> threadHistory;

    public CounterIncreaseHandler(SharedCounter sharedCounter) {
        // sharedCounter를 초기화 합니다.  sharedCounter가 null 이면 IllegalArgumentException이 발생 합니다.
        if(Objects.isNull(sharedCounter)){
            throw new IllegalArgumentException(String.format("SharedCount is null"));
        }

        this.sharedCounter = sharedCounter;
        this.threadHistory = Collections.synchronizedList(new ArrayList<>());
    }

    @Override
    public void run() {
        // 현재 Thread의 interrupted이 true <--  while의 종료조건 : interrupt가 발생 했다면 종료 합니다.
        while(!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                // sharedCounter의 count를 1증가 시키고 count값을 반환 합니다.
                long count = sharedCounter.increaseAndGet();

                // A~ E Thread가 공유자원을 사용한 횟수를 맵에 등록 합니다.
                threadHistory.add(Thread.currentThread().getName());
                log.debug("thread:{}, count:{}", Thread.currentThread().getName(), count);

                // A ~ E thread 중 먼저 실행하는 Thread가 무한 점유 합니다. 즉 다른 Thread는 increaseAndGet()를 실행 할 수 없습니다.
                synchronized (this) {   // 무한 점유: Starvation 현상 발생의 핵심
                    while (true) {      // 무한 루프
                        log.debug("{} is working",Thread.currentThread().getName());
                    }
                }
            } catch (Exception e) {
                log.debug("{} - interrupt!",Thread.currentThread().getName());

                // 현재 Thread에 interrupt()를 호출하여 interrupt()를 발생 시킵 니다. 즉 현재 Thread의 interrupted 값이 -> true로 변경 됩니다. -> 즉 while 문을 종료하게 됩니다.
                Thread.currentThread().interrupt();
            }
        }
    }

    public Map<String, Long> getThreadHistory() {
        return threadHistory.stream().collect(Collectors.groupingBy(s->s,Collectors.counting()));
    }

}
