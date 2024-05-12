import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.*;
import java.util.Iterator;


public class PlanoTreino implements Serializable {

    private LocalDate data;
    private List<Atividade> atividades;
    private int iteracoes;
    private double calorias_totais;

    public PlanoTreino(){
        this.data = LocalDate.now();
        this.atividades = new ArrayList<>();
        this.iteracoes = 0;
    }


    public PlanoTreino(LocalDate data, List<Atividade> atividades, int it) {
        this.data = data;
        this.atividades = new ArrayList<>(atividades);
        this.iteracoes = it;
    }

    public PlanoTreino(PlanoTreino outro) {
        this.data = outro.getData();
        this.atividades = new ArrayList<>(outro.getAtividades());
        this.iteracoes = outro.getIteracoes();
    }

    public double getCalorias_totais(){
        return this.calorias_totais;
    }
    public void setCalorias_totais(double calorias){
        this.calorias_totais = calorias;
    }
    public LocalDate getData() {
        return this.data;
    }
    public void setData(LocalDate d) {
        this.data = d;
    }
    public List<Atividade> getAtividades() {
        return new ArrayList<>(this.atividades);
    }
    public void setAtividades(List<Atividade> a) {
        this.atividades = new ArrayList<>(a);
    }
    public int getIteracoes() {
        return this.iteracoes;
    }
    public int getTamanho(){
        return this.atividades.size();
    }
    public void setIteracoes(int i) {
        this.iteracoes = i;
    }

    public void addAtividade(Atividade a){
        this.atividades.add(a);
    }

    public boolean hasHard(){
        Iterator<Atividade> it = this.atividades.iterator();
        while(it.hasNext()){
            Atividade teste = it.next();
            if(teste.isHard()){
                return true;
            }
        }
        return false;
    }
    public double calculaCalorias(Utilizador user){
        Iterator<Atividade> it = this.atividades.iterator();
        double calorias = 0.0;
        while(it.hasNext()){
            Atividade atividade = it.next();
            calorias += atividade.calculaCaloriasGastas(user);
        }
        this.calorias_totais = calorias;
        return calorias;
    }

    public PlanoTreino gerarExerciciosAleatorios(McFit app,Utilizador utilizador,String tipo, int num_atividades, LocalDate data) {
        Random random = new Random();

        if(utilizador == null) {
            System.out.println("Utilizador não encontrado");
            return null;
        }

        PlanoTreino planoTreino = new PlanoTreino();

        for(int i = 0; i < num_atividades; i++){
            switch(tipo) {
                case "Corrida":
                    planoTreino.addAtividade(gerarAtividadeAleatoriaCorrida(random, data));
                    break;
                case "Ciclismo":
                    planoTreino.addAtividade(gerarAtividadeAleatoriaCiclismo(random, data));
                    break;
                case "LevantamentoPesos":
                    planoTreino.addAtividade(gerarAtividadeAleatoriaLevantamentoPesos(random, data));
                    break;
                case "Pilates":
                    planoTreino.addAtividade(gerarAtividadeAleatoriaPilates(random, data));
                    break;
                default:
                    System.out.println("Tipo de atividade não reconhecido.");
                    return null;
            }
        }

        System.out.println("Plano de treino gerado com sucesso: ");

        return planoTreino;
    }
    public Corrida gerarAtividadeAleatoriaCorrida(Random random, LocalDate data) {

        int tempoGasto = random.nextInt(120);
        double distancia = random.nextDouble() * 10;
        int ritmoMedio = random.nextInt(tempoGasto / 4 + 1);
        return new Corrida(tempoGasto,data,distancia,ritmoMedio);
    }
   public Ciclismo gerarAtividadeAleatoriaCiclismo(Random random, LocalDate data){

        int tempoGasto = random.nextInt(120) + 1;
        double distancia = random.nextDouble()*10;
        int ritmoMedio = random.nextInt(tempoGasto/8+1);

        return new Ciclismo(tempoGasto,data,distancia,ritmoMedio);
    }
    public Atividade gerarAtividadeAleatoriaLevantamentoPesos(Random random, LocalDate data) {

        int tempoGasto = random.nextInt(120) + 1;
        int peso = random.nextInt(100) + 1;
        int numeroRepeticoes = random.nextInt(8) + 1;
        int series = random.nextInt(3) + 1;

        return new LevantamentoPesos(tempoGasto, data, peso, numeroRepeticoes, series);
    }

    public Atividade gerarAtividadeAleatoriaPilates(Random random, LocalDate data) {

        int tempoGasto = random.nextInt(120) + 1;
        int numeroRepeticoes = random.nextInt(8) + 1;
        int series = random.nextInt(3) + 1;

        return new Pilates(tempoGasto, data, numeroRepeticoes, series);
    }

    public boolean hasHardActivityOnSameDay(LocalDate date) {
        for (Atividade atividade : atividades) {
            if (atividade instanceof Hard && atividade.getData().isEqual(date)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasHardActivityOnPreviousDay(LocalDate date) {
        LocalDate previousDay = date.minusDays(1);
        for (Atividade atividade : atividades) {
            if (atividade instanceof Hard && atividade.getData().isEqual(previousDay)) {
                return true;
            }
        }
        return false;
    }

    public String toString (){
        return new String("->Lista atividades: "+ this.atividades.toString() + "\n" + "\n->Iterações: "+ this.iteracoes + "\n->data: "+ this.data + "\n->Vezes por semana: " + this.iteracoes);
    }


}