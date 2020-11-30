package com.excise._25_pstream;
/**
 * 如果只需要计算那一次(B+C)*B/2  那么只能使用串行设计，但是如果需要计算一万步，那么就可以拆分成多个步骤通过消息传递来执行
 * 并行流解决数据相关性问题
 * (B+C)*B/2
 * P1:A＝B+C 
 * P2:D＝A×B 
 * P3:D=D/2 
 * Output:D
 */
public class PStreamMain {

    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();

        for (int i = 1; i <= 1000; i++) {
            for (int j = 1; j <= 1000; j++) {
                Msg msg = new Msg();
                msg.i = i;
                msg.j = j;
                msg.orgStr = "((" + i + "+" + j + ")*" + i + ")/2";
                Plus.bq.add(msg);
            }
        }
    }

}
