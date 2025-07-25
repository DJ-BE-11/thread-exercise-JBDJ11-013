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

package com.nhnacademy.count;

import java.util.concurrent.locks.ReentrantLock;

public class SharedCounter {
    private long count;
    private final ReentrantLock mutex;

    public SharedCounter(){
        this(0l);
    }

    public SharedCounter(long count) {
        if(count < 0){
            throw new IllegalArgumentException("count > 0");
        }

        this.count = count;

        /* ReentrantLock 생성 합니다.( mutex는 동시에 하나의 Thread만 접근할 수 있습니다. )
           ReentrantLock은 기본적으로 비공정한 락 입니다. 공정성을 보장 하도록 초기화 합니다.
         */
        // Fair Lock 생성
        this.mutex = new ReentrantLock(true);
    }

    public long getCount(){
        /* count 를 반환 합니다.
            mutex.lock()를 호출하여 다른 thread가 접근할 수 없도록 lock을 걸어 줍니다.
            쓰레드가 작업이 완료되면
            mutex.unlock()를 호출하여
            잠금을 해제 합니다. 뮤텍스는 lock을 건 쓰레드만 lock을 해제할 수 있습니다.
         */

        try {
            this.mutex.lock();
            return this.count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.mutex.unlock();
        }
    }

    public long increaseAndGet(){
        /* count = count + 1 증가시키고 count를 반환 합니다.
           1-2 처럼 mutex를 이용해서 동기화 될 수 있도록 구현 합니다.
        */
        try {
            mutex.lock();
            return ++this.count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.mutex.unlock();
        }
    }

    public long decreaseAndGet(){
        /* count = count-1 감소시키고 count를 반환 합니다.
           1-2 처럼 mutex를 이용해서 동기화 될 수 있도록 구현 합니다.
        */
        try {
            this.mutex.lock();
            return --this.count;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            this.mutex.unlock();
        }
    }
}
