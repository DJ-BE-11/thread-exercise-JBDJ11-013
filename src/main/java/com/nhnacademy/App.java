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
        CounterHandler counterHandler = new CounterHandler(10l);
        Thread thread = new Thread(counterHandler);

        log.debug("thread-state:{}",thread.getState());
        thread.setName("my-counter");

        // thread가 실행 후 (1-10 count 증가 후  아래 로그가 출력 됩니다.)
        // thread.join()을 호출 하면 thread가 종료될 때 까지 main thread가 대기하게 됩니다.
        // - B { A.join() } : A가 종료될 때까지 B가 대기함
        //   -- A: my-counter Thread
        //   -- B: Main Thread
        try {
            thread.start();
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        log.debug("Application exit!");
        log.debug("thread-state:{}",thread.getState());
    }
}