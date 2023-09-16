public class Robot05 {
    private final int DRE = 122121823;
    private final String nome = "Luiz Claudio";
    public int llinha;//Recebe a linha atual do robo
    public int clinha;//Recebe a coluna atual do robo
    public int l;//Recebe a quantidade de linhas da sala
    public int c;//Recebe a quantidade de linhas da sala
    public boolean GO_Center;//Modo para ir ao centro
    public boolean CLOCKWISE;//Modo para sentido horario
    public boolean COUNTER_CW;//Modo para sentido antihorario
    public boolean Stop;//Modo Stop
    public boolean RoomSize;//Modo RoomSize, para descobrir tamanho da sala
    public boolean Alternating;//Modo alternating
    public int id;
    public GPS g1;
    public int lanterior;//Recebe a linha anterior do robo
    public int canterior;//Recebe a coluna anterior do robo
    public int centerl;//Recebe a linha do centro da sala
    public int centerc; //Recebe a coluna do centro da sala
    public int cinicial; //Recebe a coluna inicial do robo
    public int linicial; //Recebe a linha inicial do robo
    public boolean sentidoincialhorario; //Define o sentido inicial do alternating

    public Robot05(int id, GPS g1){
        //Robo inicializa com o estado Stop e Roomsize verdadeiros, para descobrir o tamanho da sala.
        this.Stop = true;
        this.RoomSize = true;
        this.CLOCKWISE = false;
        this.COUNTER_CW = false;
        this.GO_Center = false;
        this.sentidoincialhorario = true;
        this.Alternating = false;
        this.id = id;
        this.g1 = g1;
    }

    public Move MOVE(){
        this.llinha = g1.getL(id);//Recebe a linha do robo pelo GPS.
        this.clinha = g1.getC(id);//Recebe a coluna do robo pelo GPS.
        if(RoomSize){ //Robo descobre o tamanho da sala, indo ate os limites dela. Apos isso verifica seus outros estados.
            canterior = clinha-1;
            lanterior = llinha-1;
            while(canterior != clinha){
                canterior = clinha;
                g1.move(Move.RIGHT);
                clinha = g1.getC(id);
            }
            c = clinha;
            while(lanterior != llinha){
                lanterior = llinha;
                g1.move(Move.DOWN);
                llinha = g1.getL(id);
            }
            l = llinha;
            this.centerl = l / 2 + 1;
            this.centerc = c / 2 + 1;
            //Variaveis que fazem o modo alternating sempre "resetar" quando sai desse estado.
            this.linicial = l+1;
            this.cinicial = c+1;
            //
            System.out.print("Tamanho da sala:");
            System.out.print(c + " ");
            System.out.println(l);
            this.RoomSize = false;
        }
        if(Stop){//Retorna que o robo esta parado
            return Move.STOP;
        }
        else if (GO_Center) {//Faz o robo ir ate o centro da sala
            //Variaveis que fazem o modo alternating sempre "resetar" quando sai desse estado
            this.linicial = l+1;
            this.cinicial = c+1;
            llinha = g1.getL(id);
            clinha = g1.getC(id);
            if (llinha < centerl) {
                g1.move(Move.DOWN);
                llinha = g1.getL(id);
                return Move.DOWN;
            }
            else if (llinha > centerl) {
                g1.move(Move.UP);
                llinha = g1.getL(id);
                llinha = g1.getL(id);
                return Move.UP;
            }
            else if (clinha > centerc) {
                g1.move(Move.LEFT);
                clinha = g1.getC(id);
                return Move.LEFT;
            }
            else if (clinha < centerc) {
                g1.move(Move.RIGHT);
                clinha = g1.getC(id);
                return Move.RIGHT;
            }
            else{
                System.out.println("Robo no centro:");
                System.out.print(llinha + " ");
                System.out.println(clinha);
                stop();
                return Move.STOP;
            }
        }
        else if(CLOCKWISE) {//Faz o robo andar no sentido horario pelas bordas da sala
            //Variaveis que fazem o modo alternating sempre "resetar" quando sai desse estado
            this.linicial = l+1;
            this.cinicial = c+1;
            if (llinha == l && clinha != 1) {
                g1.move(Move.LEFT);
                clinha = g1.getC(id);
                return Move.LEFT;
            }
            else if (clinha == c && llinha != l) {
                g1.move(Move.DOWN);
                llinha = g1.getL(id);
                return Move.DOWN;
            } else if (clinha == 1 && llinha != 1) {
                g1.move(Move.UP);
                llinha = g1.getL(id);
                return Move.UP;
            } else if (llinha == 1 && clinha != c) {
                g1.move(Move.RIGHT);
                clinha = g1.getC(id);
                return Move.RIGHT;
            } else {
                g1.move(Move.DOWN);
                llinha = g1.getL(id);
                return Move.DOWN;
            }
        }
        else if(COUNTER_CW){//Faz o robo andar no sentido antihorario pelas bordas da sala
            //Variaveis que fazem o modo alternating sempre "resetar" quando sai desse estado
            this.linicial = l+1;
            this.cinicial = c+1;
            if (llinha == l && clinha != c) {
                g1.move(Move.RIGHT);
                clinha = g1.getC(id);
                return Move.RIGHT;
            }
            else if (clinha == c && llinha != 1) {
                g1.move(Move.UP);
                llinha = g1.getL(id);
                return Move.UP;
            } else if (clinha == 1 && llinha != l) {
                g1.move(Move.DOWN);
                llinha = g1.getL(id);
                return Move.DOWN;
            } else if (llinha == 1 && clinha != 1) {
                g1.move(Move.LEFT);
                clinha = g1.getC(id);
                return Move.LEFT;
            } else {
                g1.move(Move.DOWN);
                llinha = g1.getL(id);
                return Move.DOWN;
            }
        }
        else if(Alternating){//Faz o robo alternar os sentidos horario e antihorario pelas bordas da sala
            if(llinha != l && llinha != 1 && clinha != c && clinha != 1 ){//Verifica se o robo ja esta na borda e o manda para la
                g1.move(Move.DOWN);
                llinha = g1.getL(id);
                return Move.DOWN;
            }
            else{
                if(llinha == linicial && clinha == cinicial){//Troca o sentido do movimento do robo
                    sentidoincialhorario = !sentidoincialhorario;
                }
                if(linicial>l){//Define a linha inicial do movimento do robo
                    linicial = llinha;
                    sentidoincialhorario = true;
                }
                if(cinicial>c){//Define a coluna inicial do movimento do robo
                    cinicial = clinha;
                    sentidoincialhorario = true;
                }
                if(sentidoincialhorario){//Verifica se o robo esta andando no sentido horario
                    //Movimenta o robo no sentido horario
                    if (llinha == l && clinha != 1) {
                        g1.move(Move.LEFT);
                        clinha = g1.getC(id);
                        return Move.LEFT;
                    } else if (clinha == c && llinha != l) {
                        g1.move(Move.DOWN);
                        llinha = g1.getL(id);
                        return Move.DOWN;
                    } else if (clinha == 1 && llinha != 1) {
                        g1.move(Move.UP);
                        llinha = g1.getL(id);
                        return Move.UP;
                    } else {
                        g1.move(Move.RIGHT);
                        clinha = g1.getC(id);
                        return Move.RIGHT;
                    }
                }
                //Caso o robo nao esteja no sentido horario, estara no antihorario, chegando nessa parte do codigo
                //Movimenta o robo no sentido antihorario
                if (llinha == l && clinha != c) {
                    g1.move(Move.RIGHT);
                    clinha = g1.getC(id);
                    return Move.RIGHT;
                } else if (clinha == c && llinha != 1) {
                    g1.move(Move.UP);
                    llinha = g1.getL(id);
                    return Move.UP;
                } else if (clinha == 1 && llinha != l) {
                    g1.move(Move.DOWN);
                    llinha = g1.getL(id);
                    return Move.DOWN;
                }
                else{
                    g1.move(Move.LEFT);
                    clinha = g1.getC(id);
                    return Move.LEFT;
                }
            }
        }
        //Faz o robo parar caso não reconheça nenhum estado
        stop();
        return Move.STOP;
    }
    public void stop(){ // Método que define o status como Stop
        System.out.println("Status do robo alterado para: Stop");
        this.Stop = true;
        this.CLOCKWISE = false;
        this.COUNTER_CW = false;
        this.Alternating = false;
        this.GO_Center = false;
    }
    public void clockwise(){ // Método que define o status como Clockwise
        System.out.println("Status do robo alterado para: Clockwise");
        this.Stop = false;
        this.CLOCKWISE = true;
        this.COUNTER_CW = false;
        this.Alternating = false;
        this.GO_Center = false;
    }
    public void counter_cw(){ // Método que define o status como Counter_CW
        System.out.println("Status do robo alterado para: Counter_CW");
        this.Stop = false;
        this.CLOCKWISE = false;
        this.COUNTER_CW = true;
        this.Alternating = false;
        this.GO_Center = false;
    }
    public void alternating(){ // Método que define o status como Alternating
        System.out.println("Status do robo alterado para: Alternating");
        this.Stop = false;
        this.CLOCKWISE = false;
        this.COUNTER_CW = false;
        this.Alternating = true;
        this.GO_Center = false;
    }
    public void go_center(){ // Método que define o status como Go_Center
        System.out.println("Status do robo alterado para: Go_Center");
        this.Stop = false;
        this.CLOCKWISE = false;
        this.COUNTER_CW = false;
        this.Alternating = false;
        this.GO_Center = true;
    }
    public int getDRE(){ //Getter do DRE
        return this.DRE;
    }
    public String getNome(){ //Getter do Nome
        return this.nome;
    }
    public void print(){ // Método que printa Nome e DRE
        System.out.println("DRE:" + getDRE());
        System.out.println("NOME:" + getNome());
    }
}