public class Main {
    public static void main (String[] args) {
        GPS g1 = new GPS();
        Robot05 r1 = new Robot05( 5, g1);
        r1.go_center();
        fazermovimento(r1);
        r1.clockwise();
        fazermovimento(r1);
        r1.counter_cw();
        fazermovimento(r1);
        r1.alternating();
        fazermovimento(r1);
        r1.counter_cw();
        fazermovimento(r1);
        r1.alternating();
        fazermovimento(r1);
        r1.print();
    }

    public static void fazermovimento(Robot05 r1){
        int i;
        for(i=0;i<51;i++){
            System.out.println(r1.MOVE());
        }
        System.out.println("Posição:" + r1.llinha + " "+ r1.clinha);
    }
}