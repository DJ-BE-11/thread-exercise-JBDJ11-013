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

import com.nhnacademy.thread.AlertDaemon;
import com.nhnacademy.thread.Counter;

/**
 * Hello world!
 *
 */
public class App {
    public static void main(String[] args) {
        // AlertDaemon Thread를 초기화하고 start() 메서드를 호출해서 실행 합니다.
        AlertDaemon alertDaemon = new AlertDaemon();
        alertDaemon.start();

        Thread.currentThread().setName("my-thread");
        Counter counter = new Counter(10);
        counter.run();
    }
}