import com.fazecast.jSerialComm.SerialPort;


public class toByte implements Runnable{

    public static String getMotorPWM() {
        return MotorPWM;
    }
    public static String MotorPWM ;
    public static boolean change = false;
    public static String Torque ;
    String dane;
    SerialPort[] comPort;
    Integer num;
    public  byte[] pidsBuffer;
    toByte(SerialPort[] comPort, Integer num)
    {
    this.comPort = comPort;
    this.num = num;
}
    private  String  PID(){

    dane = "";
    dane =String.valueOf(1)+String.valueOf(1) + String.valueOf(1)+String.valueOf(1)+String.valueOf(apps.start)+String.valueOf(apps.stop)+String.valueOf(apps.rotaryLeft)+String.valueOf(apps.rotaryRight)+String.valueOf(apps.manual)
        +String.valueOf(pidRegulator.getReset())+String.valueOf(7)+String.valueOf(1)+String.valueOf(1)+String.valueOf(pidRegulator.pidP)+String.valueOf(pidRegulator.pidI)+String.valueOf(pidRegulator.pidD);

    pidsBuffer = dane.getBytes();
    return dane;
    }

    public void run() {

        while(true) {
            readBYTES();
            if(change == true) {
                sendFRAME();
            }
            change = false;
            try { Thread.sleep(300); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    void sendFRAME(){
        comPort[apps.numberPort].setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 1000, 0);
        comPort[apps.numberPort].setBaudRate(57600);
        System.out.println(" Baudrate  " + comPort[apps.numberPort].getBaudRate());
        PID();
        try {
            System.out.println("wysyłam ramke ");
            System.out.println(dane);
            comPort[apps.numberPort].writeBytes( pidsBuffer,    pidsBuffer.length );

        }catch( NullPointerException e){

            System.out.println(" null pointer exeption sendFrame");

        }catch (ArrayIndexOutOfBoundsException f){

            System.out.println("array index out of bount exaption sendFrame");

        }



    }
    void readBYTES(){

        try {

            byte[] readBuffer = new byte[10];
            comPort[apps.numberPort].setBaudRate(57600);
            int numRead = comPort[apps.numberPort].readBytes(readBuffer, readBuffer.length);
            if (numRead > 0) {

                String stringReadBuffer = new String(readBuffer);
                CharSequence charSEC=stringReadBuffer;
                try {
                    int firstIndex = stringReadBuffer.indexOf("F");
                    int secIndex = stringReadBuffer.indexOf("P");
                    int thiIndex = stringReadBuffer.indexOf("L");
                    MotorPWM = charSEC.subSequence(firstIndex + 1, secIndex).toString();
                    Torque = charSEC.subSequence(secIndex + 1, thiIndex).toString();
                }catch(StringIndexOutOfBoundsException e){

                }

            }
        } catch (Exception e) { e.printStackTrace(); }


    }
    static  String  datasplit(String data,String reg,String reg2, String reg3)
    {
        CharSequence c=data;
        String Pozition = "";
        String troque = "";
        try {
            int firstIndex = data.indexOf(reg);

            int secIndex = data.indexOf(reg2);

            int thiIndex = data.indexOf(reg3);

            Pozition = c.subSequence(firstIndex + 1, secIndex).toString();
            troque = c.subSequence(secIndex + 1, thiIndex).toString();
        }catch(StringIndexOutOfBoundsException e){


    }
return Pozition;
    }



}


