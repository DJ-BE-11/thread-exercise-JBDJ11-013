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

import java.util.concurrent.Semaphore;

public class SharedCounter {
    private long count;
    private Semaphore semaphore;

    public SharedCounter(){
        this.count =0l;
    }

    public SharedCounter(long count) {
        if(count < 0){
            throw new IllegalArgumentException("count > 0");
        }

        this.count = count;
        // semaphore를 생성 합니다. ( 동시에 하나의 Thread만 접근할 수 있습니다. ), permits prameter를 확인하세요.
        // 파라미터: 동시에 접근을 허용할 허가(permits) 수를 1로 지정 --> 동시에 하나의 스레드만 접근 가능
        this.semaphore = new Semaphore(1);
    }

    public long getCount(){
        /* count 를 반환 합니다.
            semaphore.acquire()를 호출하여 허가를 획득 합니다.
            쓰레드가 작업이 완료되면
            semaphore.release()를 호출하여
            허가를 반환 합니다.
         */

        try {
            this.semaphore.acquire();
            return this.count;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.semaphore.release();
        }
    }

    public long increaseAndGet(){
        /* count = count + 1 증가시키고 count를 반환 합니다.
           1-2 처럼 semaphore를 이용해서 동기화할 수 있도록 구현 합니다.
        */

        try {
            this.semaphore.acquire();
            return ++this.count;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.semaphore.release();
        }
    }

    public long decreaseAndGet(){
        /* count = count-1 감소시키고 count를 반환 합니다.
           1-2 처럼 semaphore를 이용해서 동기화할 수 있도록 구현 합니다.
        */

        try {
            this.semaphore.acquire();
            return --this.count;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            this.semaphore.release();
        }
    }
}
