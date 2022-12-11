import java.io.FileWriter;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.time.LocalDateTime;

public class Main {
    public static String ColocarNome(Scanner in)
    {
        String nome;
        String sobrenome;
        String nomeCompleto;
        in.useDelimiter("\n");


        System.out.println("Digite seu nome: ");
        nome = in.next();
        nome = nome.substring(0,1).toUpperCase() + nome.substring(1,nome.length()).toLowerCase();

        System.out.println("Digite seu sobrenome: ");
        sobrenome = in.next();
        sobrenome = sobrenome.substring(0,1).toUpperCase() + sobrenome.substring(1,sobrenome.length()).toLowerCase();

        nomeCompleto = ConfirmarNome(in, nome, sobrenome);

        return nomeCompleto;
    }
    public static String ConfirmarNome(Scanner in, String nome, String sobrenome){

        String confirmacaoNome;
        String nomeCompleto = null;

        System.out.println("Nome escolhido: " + nome + " "+ sobrenome);
        System.out.println("Confirmar nome? Digite S ou N.");

        confirmacaoNome = in.next().toUpperCase();

       if(confirmacaoNome.equals("S")){
           return nome + " " + sobrenome;
        } else if(confirmacaoNome.equals("N")){
           nomeCompleto = ColocarNome(in);
           return nomeCompleto;
        } else {
            System.out.println("Entrada incorreta");
            nomeCompleto = ConfirmarNome(in, nome, sobrenome);
        }
       return nomeCompleto;
    }

    public static String SelecionarClasse(Scanner in){

        int opcaoClasse = 0;
        String tipoClasse = null;

        System.out.println("Escolha sua Classe:\n1: Guerreiro\n2: Maga\n3: Bruxa\n4: Necromante");

        try {
            opcaoClasse = in.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Digite um número!");
            in.next();
            tipoClasse = SelecionarClasse(in);
            return tipoClasse;
        }

        switch (opcaoClasse){
            case 1:
                tipoClasse = "Guerreiro";
                break;
            case 2:
                tipoClasse = "Maga";
                break;
            case 3:
                tipoClasse = "Bruxa";
                break;
            case 4:
                tipoClasse = "Necromante";
                break;
            default:
                System.out.println("Escolheu incorretamente.");
                tipoClasse = SelecionarClasse(in);
                break;
        }
        return tipoClasse;
    }

    public static Personagem CriarPersonagem(Scanner in){

        String nomeCompleto;
        String tipoClasse;
        Personagem personagem = null;

        nomeCompleto = ColocarNome(in);
        System.out.println("Nome salvo: " + nomeCompleto);

        tipoClasse = SelecionarClasse(in);
        System.out.println("Classe escolhida: " + tipoClasse);

        switch (tipoClasse){
            case "Guerreiro":
                return new Guerreiro(nomeCompleto, tipoClasse, 200);
            case "Maga":
                return new Maga(nomeCompleto, tipoClasse, 100);
            case "Bruxa":
                return new Bruxa(nomeCompleto, tipoClasse, 150);
            case "Necromante":
                return new Necromante(nomeCompleto, tipoClasse, 70);
            default:
                break;
        }

        return personagem;
    }

    public static String ConfirmarExclusaoPersonagem(Scanner in, String nomePersonagem){

        String confirmacaoExclusao = null;

        System.out.println("Confirmar exclusão de " + nomePersonagem + "? Digite S ou N.");
        confirmacaoExclusao = in.next().toUpperCase();

        if(confirmacaoExclusao.equals("S")){
            return "S";
        } else if (confirmacaoExclusao.equals("N")) {
            return "N";
        } else {
            System.out.println("Entrada incorreta.");
            confirmacaoExclusao = ConfirmarExclusaoPersonagem(in, nomePersonagem);
        }
        return confirmacaoExclusao;
    }
    public static void ExcluirPersonagem(ArrayList<Personagem> personagens, Scanner in){

        int selecaoPersonagem = 0;
        String confirmarExclusao = null;

        if(personagens.size() > 0){
            System.out.println("Escolha qual personagem deseja excluir:");

            for(int i = 0; i < personagens.size(); i++){
                System.out.println((i+1)+": "+personagens.get(i).getNome());
            }

            try {
                selecaoPersonagem = in.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Digite um número!");
                in.next();
                ExcluirPersonagem(personagens, in);
            }

            if(selecaoPersonagem > personagens.size() || selecaoPersonagem == 0){
                System.out.println("Entrada incorreta.");
                ExcluirPersonagem(personagens, in);
            }

            confirmarExclusao = ConfirmarExclusaoPersonagem(in, personagens.get(selecaoPersonagem - 1).getNome());

            if(confirmarExclusao.equals("S")){

                System.out.println("Personagem "+personagens.get(selecaoPersonagem - 1).getNome()+" excluído.");
                personagens.remove(selecaoPersonagem - 1);

            } else if (confirmarExclusao.equals("N")){
                System.out.println("Personagem não foi excluído.");
            }

            MenuPrincipal(in, personagens);

        } else {
            System.out.println("Não há personagens para excluir.");
            MenuPrincipal(in, personagens);
        }
    }

    public static void AlterarPersonagem(ArrayList<Personagem> personagens, Scanner in){

        int selecaoPersonagem = 0;
        String nomeAntigo;
        String novoNome;

        if(personagens.size() > 0){

            System.out.println("Escolha qual personagem deseja alterar:");

            for(int i = 0; i < personagens.size(); i++){
                System.out.println((i+1)+": "+personagens.get(i).getNome());
            }

            try {
                selecaoPersonagem = in.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Digite um número!");
                in.next();
                AlterarPersonagem(personagens, in);
            }

            if(selecaoPersonagem > personagens.size() || selecaoPersonagem == 0){
                System.out.println("Entrada incorreta.");
                AlterarPersonagem(personagens, in);
            }

            nomeAntigo = personagens.get(selecaoPersonagem - 1).getNome();
            novoNome = ColocarNome(in);

            personagens.get(selecaoPersonagem - 1).setNome(novoNome);

            System.out.println(nomeAntigo + " alterou o nome para " + novoNome);

            MenuPrincipal(in, personagens);

        } else {
            System.out.println("Não há personagens para alterar.");
            MenuPrincipal(in, personagens);
        }
    }

    public static void CriarArquivoSave(){

        File arquivoSave = new File("jogoSalvo.txt");

        try {
            if(arquivoSave.createNewFile()){
                System.out.println("Arquivo de Save criado");
            } else {
                System.out.println("Há um arquivo de Save criado");
            }
        } catch (IOException e){
            System.out.println("Um erro ocorreu");
            e.printStackTrace();
        }

    }

    public static String ConfirmarSalvarJogo(){

        File arquivoSave = new File("jogoSalvo.txt");
        Scanner in = new Scanner(System.in);
        String confirmarSalvar = "";

        if(arquivoSave.length() > 0){

            System.out.println("Já há um jogo salvo. Deseja subscrever? Digite S ou N.");

            confirmarSalvar = in.next().toUpperCase();

            if(confirmarSalvar.equals("S")){
                return "S";
            } else if(confirmarSalvar.equals("N")){
                return "N";
            } else {
                System.out.println("Entrada incorreta.");
                confirmarSalvar = ConfirmarSalvarJogo();
            }
        }

        return confirmarSalvar;
    }

    public static ArrayList<Personagem> CarregarJogo(ArrayList<Personagem> _personagens){

        ArrayList<Personagem> personagens = _personagens;

        try {

            File meuSave = new File("jogoSalvo.txt");

            Scanner in = new Scanner(meuSave);

            System.out.println("Último jogo salvo em: " + in.nextLine());

            if(personagens.size() > 0){
                personagens.clear();
            }

            while (in.hasNextLine()) {

                String[] linha = in.nextLine().split(":");

                if (linha[1].equals("Guerreiro")) {

                    personagens.add(new Guerreiro(linha[0], linha[1], Integer.parseInt(linha[2])));

                } else if (linha[1].equals("Maga")) {

                    personagens.add(new Maga(linha[0], linha[1], Integer.parseInt(linha[2])));

                } else if (linha[1].equals("Bruxa")) {

                    personagens.add(new Bruxa(linha[0], linha[1], Integer.parseInt(linha[2])));

                } else if (linha[1].equals("Necromante")) {

                    personagens.add(new Necromante(linha[0], linha[1], Integer.parseInt(linha[2])));

                }
            }

            return personagens;

        } catch (IOException e){

            System.out.println("Um erro ocorreu");
            e.printStackTrace();

        }

        return personagens;
    }

    public static void SalvarJogo(ArrayList<Personagem> personagens){

        String confirmacaoSalvarJogo;
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dataHoraFormatada = formatador.format(dataHora);

        confirmacaoSalvarJogo = ConfirmarSalvarJogo();

        try {

            if(!confirmacaoSalvarJogo.equals("N")){
                FileWriter salvadorjogo = new FileWriter("jogoSalvo.txt");

                salvadorjogo.write(dataHoraFormatada.toString());
                salvadorjogo.write(System.lineSeparator());

                for (Personagem p: personagens) {
                    salvadorjogo.write(p.getNome()+":"+p.getClasse()+":"+p.getVida());
                    salvadorjogo.write(System.lineSeparator());
                }

                salvadorjogo.close();

                System.out.println("Jogo salvo com sucesso!");
            }


        } catch (IOException e){

            System.out.println("Um erro ocorreu");
            e.printStackTrace();
        }
    }
    public static ArrayList<Personagem> MenuPrincipal(Scanner in, ArrayList<Personagem> _personagens){
        System.out.println("O que deseja fazer?\n" +
                "1: Jogar\n2: Criar novo personagem\n3: Excluir um personagem\n4: Alterar nome de um personagem\n5: Salvar jogo\n6: Carregar jogo salvo");


        int opcaoMenu = 0;
        Personagem personagem = null;
        ArrayList<Personagem> personagens = _personagens;


        try {
            opcaoMenu = in.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Digite um número!");
            in.next();
            return MenuPrincipal(in, personagens);
        }

        switch (opcaoMenu){
            case 1:
                SelecionarPersonagem(in, personagens);
                break;
            case 2:
                personagens.add(personagem = CriarPersonagem(in));
                break;
            case 3:
                ExcluirPersonagem(personagens, in);
                break;
            case 4:
                AlterarPersonagem(personagens, in);
                break;
            case 5:
                SalvarJogo(personagens);
                MenuPrincipal(in, personagens);
                break;
            case 6:
                personagens = CarregarJogo(personagens);
                System.out.println("Jogo carregado.");
                MenuPrincipal(in, personagens);
                break;
            default:
                System.out.println("Digite um número válido no primeiro menu!");
                MenuPrincipal(in, personagens);
                break;
        }

        return personagens;
    }

    public static int SelecionarPersonagem(Scanner in, ArrayList<Personagem> _personagens){

        int numeroPersonagemEscolhido = 1;

        if(_personagens.size() > 1) {

            System.out.println("Selecione seu personagem:");

            for (int i = 0; i < _personagens.size(); i++){
                System.out.println((i+1)+": " + _personagens.get(i).getNome() + " | Classe: " + _personagens.get(i).getClasse());
            }

            try {
                numeroPersonagemEscolhido = in.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Digite um número!");
                in.next();
                SelecionarPersonagem(in, _personagens);
            }

            if (numeroPersonagemEscolhido > _personagens.size() || numeroPersonagemEscolhido == 0) {
                System.out.println("Entrada incorreta.");
                SelecionarPersonagem(in, _personagens);
            }

            SegundoMenu(in, _personagens, numeroPersonagemEscolhido - 1);

        } else if (_personagens.size() == 1) {
            SegundoMenu(in, _personagens, numeroPersonagemEscolhido - 1);
        } else {
            System.out.println("Crie um personagem ou carregue um jogo salvo.");
            MenuPrincipal(in, _personagens);
        }

        return numeroPersonagemEscolhido;
    }
    public static void SegundoMenu(Scanner in, ArrayList<Personagem> personagens, int numeroPersonagemEscolhido){

        int numeroPersonagemTrocado;

        System.out.println("Personagem selecionado: " + personagens.get(numeroPersonagemEscolhido).getNome() + " | Classe: " + personagens.get(numeroPersonagemEscolhido).getClasse() + " | Vida: " + personagens.get(numeroPersonagemEscolhido).getVida());
        System.out.println("O que deseja fazer agora?\n" +
                "1: Atacar\n2: Repousar\n3: Escolher outro personagem\n4: Voltar ao menu principal");

        int opcaoMenu2 = 0;

        try {
            opcaoMenu2 = in.nextInt();
        } catch (InputMismatchException e){
            System.out.println("Digite um número!");
            in.next();
            SegundoMenu(in, personagens, numeroPersonagemEscolhido);
        }


        switch (opcaoMenu2){
            case 1:
                if(personagens.get(numeroPersonagemEscolhido).getVida() == 0){
                    System.out.println("Não é possível atacar com vida zero. Repouse antes de lutar novamente.");
                    SegundoMenu(in, personagens, numeroPersonagemEscolhido);
                } else {
                    personagens.get(numeroPersonagemEscolhido).darDano();
                    SegundoMenu(in, personagens, numeroPersonagemEscolhido);
                }
                break;
            case 2:
                personagens.get(numeroPersonagemEscolhido).descansar();
                SegundoMenu(in, personagens, numeroPersonagemEscolhido);
                break;
            case 3:
                if(personagens.size() == 1){
                    System.out.println("Há apenas um personagem criado: " + personagens.get(0).getNome());
                }
                numeroPersonagemTrocado = SelecionarPersonagem(in, personagens);
                SegundoMenu(in, personagens, numeroPersonagemTrocado);
                break;
            case 4:
                personagens = MenuPrincipal(in, personagens);
                if(personagens.size() > 0) {
                    SegundoMenu(in, personagens, personagens.size()-1);
                } else {
                    MenuPrincipal(in,personagens);
                }
                break;
            default:
                System.out.println("Escolheu incorretamente.");
                SegundoMenu(in, personagens, numeroPersonagemEscolhido);
                break;
        }
    }

    public static abstract class Personagem implements InterfaceCura{
        private String nome;
        private String classe;
        private int vida;
        protected int vidaInicial;

        public Personagem(String _nome, String _classe, int vida){
            this.nome = _nome;
            this.classe = _classe;
            this.vida = vida;
            this.vidaInicial = vida;
        }

        public void setNome(String _nome){
            this.nome = _nome;
        }

        public String getNome(){
            return this.nome;
        }

        public String getClasse(){
            return this.classe;
        }

        public int getVida(){
            return this.vida;
        }

        public void setVida(int _vida) {
            this.vida = _vida;
        }

        public abstract void darDano();
    }

    public static class Guerreiro extends Personagem {
        public Guerreiro(String _nome, String _classe, int _vida){
            super(_nome, _classe, _vida);
        }

        private int danoRecebido = 30;
        private int vidaRecuperada = 10;

        public void darDano(){
            System.out.println(this.getNome().toUpperCase() + " ATINGIU O INIMIGO E DEU 100 DE DANO");
            System.out.println("COM O IMPACTO DO COMBATE, " + this.getNome().toUpperCase() + " RECEBEU " + danoRecebido + " DE DANO");
            setVida(getVida() - danoRecebido);
            if(getVida() < 0){
                setVida(0);
            }
        }

        public void descansar(){

            if(getVida() + vidaRecuperada >= vidaInicial){
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + (vidaInicial - getVida()) + " DE VIDA");
                setVida(vidaInicial);
            } else {
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + vidaRecuperada + " DE VIDA");
                setVida(getVida() + vidaRecuperada);
            }

        }
    }

    public static class Maga extends Personagem {
        public Maga(String _nome, String _classe, int _vida){
            super(_nome, _classe, _vida);
        }

        private int danoRecebido = 40;
        private int vidaRecuperada = 50;

        public void darDano(){
            System.out.println(this.getNome().toUpperCase() + " ATINGIU O INIMIGO E DEU 70 DE DANO");
            System.out.println("COM O IMPACTO DO COMBATE, " + this.getNome().toUpperCase() + " RECEBEU " + danoRecebido + " DE DANO");
            setVida(getVida() - danoRecebido);
            if(getVida() < 0){
                setVida(0);
            }
        }

        public void descansar(){
            if(getVida() + vidaRecuperada >= vidaInicial){
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + (vidaInicial - getVida()) + " DE VIDA");
                setVida(vidaInicial);
            } else {
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + vidaRecuperada + " DE VIDA");
                setVida(getVida() + vidaRecuperada);
            }
        }
    }

    public static class Bruxa extends Personagem {
        public Bruxa(String _nome, String _classe, int _vida){
            super(_nome, _classe, _vida);
        }

        private int danoRecebido = 50;
        private int vidaRecuperada = 30;

        public void darDano(){
            System.out.println(this.getNome().toUpperCase() + " ATINGIU O INIMIGO E DEU 80 DE DANO");
            System.out.println("COM O IMPACTO DO COMBATE, " + this.getNome().toUpperCase() + " RECEBEU " + danoRecebido + " DE DANO");
            setVida(getVida() - danoRecebido);
            if(getVida() < 0){
                setVida(0);
            }
        }

        public void descansar(){
            if(getVida() + vidaRecuperada >= vidaInicial){
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + (vidaInicial - getVida()) + " DE VIDA");
                setVida(vidaInicial);
            } else {
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + vidaRecuperada + " DE VIDA");
                setVida(getVida() + vidaRecuperada);
            }
        }
    }

    public static class Necromante extends Personagem {
        public Necromante(String _nome, String _classe, int _vida){
            super(_nome, _classe, _vida);
        }

        private int danoRecebido = 60;
        private int vidaRecuperada = 70;

        public void darDano(){
            System.out.println(this.getNome().toUpperCase() + " ATINGIU O INIMIGO E DEU 60 DE DANO");
            System.out.println("COM O IMPACTO DO COMBATE, " + this.getNome().toUpperCase() + " RECEBEU " + danoRecebido + " DE DANO");
            setVida(getVida() - danoRecebido);
            if(getVida() < 0){
                setVida(0);
            }
        }

        public void descansar(){
            if(getVida() + vidaRecuperada >= vidaInicial){
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + (vidaInicial - getVida()) + " DE VIDA");
                setVida(vidaInicial);
            } else {
                System.out.println(this.getNome().toUpperCase() + " DESCANSA E RECUPERA " + vidaRecuperada + " DE VIDA");
                setVida(getVida() + vidaRecuperada);
            }
        }
    }

    public static interface InterfaceCura {
        public void descansar();
    }

    public static void main(String[] args) {

        CriarArquivoSave();

        System.out.println("########################################");
        System.out.println("DESEJAMOS BOAS VINDAS À ARCADIA DENTÁRIA");
        System.out.println("########################################");

        Scanner in = new Scanner(System.in);
        ArrayList<Personagem> personagens = new ArrayList<Personagem>();

        personagens = MenuPrincipal(in, personagens);

        SelecionarPersonagem(in, personagens);

    }
}
